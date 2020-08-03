package io.choedeb.android.memo.ui.write

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.choedeb.android.memo.R
import io.choedeb.android.memo.data.Image
import io.choedeb.android.memo.data.Memo
import io.choedeb.android.memo.data.source.MemoDataSource
import io.choedeb.android.memo.ui.base.BaseViewModel
import io.choedeb.android.memo.util.AppValueUtil
import io.choedeb.android.memo.util.DateFormatUtil
import io.choedeb.android.memo.util.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WriteViewModel(
    private val context: Context,
    private val memoDataSource: MemoDataSource
) : BaseViewModel() {

    private val _todayDate = MutableLiveData<String>()
    val todayDate: LiveData<String> = _todayDate

    private val _memoId = MutableLiveData<Long>(0)

    private val _titleText = MutableLiveData<String>()
    val titleText: LiveData<String> = _titleText

    private val _contentsText = MutableLiveData<String>()
    val contentsText: LiveData<String> = _contentsText

    private val _imageList = MutableLiveData<List<Image>>()
    val imageList: LiveData<List<Image>> = _imageList

    private val _isImageVisible = MutableLiveData<Boolean>()
    val isImageVisible: LiveData<Boolean> = _isImageVisible

    private val _completeState = MutableLiveData<Boolean>()
    val completeState: LiveData<Boolean> = _completeState

    val imageClick = SingleLiveEvent<String>()

    val completeClick = SingleLiveEvent<Void>()

    val showMessage = SingleLiveEvent<String>()

    private var tempImageList = ArrayList<Image>()

    private lateinit var currentImagePath: String
    private lateinit var imageEncoded: String
    private lateinit var imagesEncodedList: MutableList<String>

    init {
        _isImageVisible.value = false
        getTodayDate()
    }

    private fun getTodayDate() {
        _todayDate.value = DateFormatUtil.todayFormatDate()
    }

    fun getMemo(memoId: Long) {
        addDisposable(memoDataSource.getMemo(memoId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _memoId.value = it.memo.memoId
                _titleText.value = it.memo.title
                _contentsText.value = it.memo.contents
                if (it.images.isNotEmpty()) {
                    _imageList.value = it.images
                    _isImageVisible.value = true
                    tempImageList = it.images as ArrayList<Image>
                }
            }, {
                Logger.d(it.message)
            })
        )
    }

    fun saveMemo() {
        addDisposable(memoDataSource.saveMemoAndImages(
                Memo(_memoId.value!!, _titleText.value.toString(), _contentsText.value.toString()), tempImageList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                completeClick.call()
            }, {
                Logger.d(it.message)
                showMessage.call()
            })
        )
    }

    fun getTitleTextWatcher(): TextWatcher? {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                _titleText.value = s.toString()
                _completeState.value = !_titleText.value.isNullOrBlank() && !_contentsText.value.isNullOrBlank()
            }
            override fun afterTextChanged(s: Editable) {

            }
        }
    }

    fun getContentsTextWatcher(): TextWatcher? {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                _contentsText.value = s.toString()
                _completeState.value = !_titleText.value.isNullOrBlank() && !_contentsText.value.isNullOrBlank()
            }
            override fun afterTextChanged(s: Editable) {

            }
        }
    }

    fun setImageFromCamera() {
        val file = File(currentImagePath)
        val photoUri = Uri.fromFile(file)

        tempImageList.add(Image(imageId = 0, image = photoUri.toString()))
        _imageList.value = tempImageList
        _isImageVisible.value = true
    }

    fun setImageFromGallery(data: Intent?) {

        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val uriList = ArrayList<Uri>()
        imagesEncodedList = ArrayList()

        if (data!!.data != null) {
            val imageUri = data.data
            val cursor = context.contentResolver.query(
                imageUri!!, filePathColumn,
                null, null, null
            )
            cursor!!.moveToFirst()

            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            imageEncoded = cursor.getString(columnIndex)
            cursor.close()

            uriList.add(imageUri)

        } else {

            if (data.clipData != null) {
                val clipData = data.clipData

                for (i in 0 until clipData!!.itemCount) {
                    val item = clipData.getItemAt(i)
                    val uri = item.uri
                    uriList.add(uri)
                    val cursor = context.contentResolver.query(
                        uri, filePathColumn,
                        null, null, null
                    )
                    cursor!!.moveToFirst()

                    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                    imageEncoded = cursor.getString(columnIndex)
                    imagesEncodedList.add(imageEncoded)
                    cursor.close()
                }
            }
        }

        for (i in uriList.indices) {
            tempImageList.add(Image(imageId = 0, image = uriList[i].toString()))
        }
        _imageList.value = tempImageList
        _isImageVisible.value = true
    }

    fun setImageFromLink(url: String) {
        tempImageList.add(Image(imageId = 0, image = url))
        _imageList.value = tempImageList
        _isImageVisible.value = true
    }

    fun onImageClicked(imageUri: String) {
        imageClick.value = imageUri
    }

    fun onImageDeleteClicked(index: Int) {
        tempImageList.removeAt(index)
        _imageList.value = tempImageList
        _isImageVisible.value = true
    }

    fun openCamera(activity: Activity) {
        if (context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intent.resolveActivity(context.packageManager) != null) {
                var photoFile: File? = null
                try {
                    photoFile = createImageFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                if (photoFile != null) {
                    val photoUri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", photoFile)
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    activity.startActivityForResult(intent, AppValueUtil.PICK_IMAGE_CAPTURE)
                }
            }
        } else {
            showMessage.value = context.getString(R.string.toast_camera_disable)
        }
    }

    fun openGallery(activity: Activity) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        activity.startActivityForResult(
            Intent.createChooser(intent, context.getString(R.string.text_gallery_pick)),
            AppValueUtil.PICK_IMAGE_GALLERY)
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageName = "${timeStamp}_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(imageName, ".jpg", storageDir)
        currentImagePath = image.absolutePath
        return image
    }

    fun onRequestPermissionsResult(activity: Activity, requestCode: Int, grantResults: IntArray) {
        when (requestCode) {
            AppValueUtil.PERMISSION_CAMERA -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    openCamera(activity)
                } else {
                    showMessage.value = context.getString(R.string.text_permission_denied)
                }
                return
            }
            AppValueUtil.PERMISSION_GALLERY -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    openGallery(activity)
                } else {
                    showMessage.value = context.getString(R.string.text_permission_denied)
                }
                return
            }
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            if (resultCode == Activity.RESULT_OK) {
                when (requestCode) {
                    AppValueUtil.PICK_IMAGE_CAPTURE -> {
                        setImageFromCamera()
                    }
                    AppValueUtil.PICK_IMAGE_GALLERY -> {
                        setImageFromGallery(data)
                    }
                    else -> {
                        showMessage.value = context.getString(R.string.text_image_nothing)
                    }
                }
            } else {
                showMessage.value = context.getString(R.string.text_image_error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            showMessage.value = context.getString(R.string.text_image_error)
        }
    }
}