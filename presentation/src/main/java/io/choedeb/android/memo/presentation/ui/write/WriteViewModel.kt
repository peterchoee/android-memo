package io.choedeb.android.memo.presentation.ui.write

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.choedeb.android.memo.domain.entity.DomainEntity
import io.choedeb.android.memo.domain.usecase.GetMemoUseCase
import io.choedeb.android.memo.domain.usecase.SetMemoUseCase
import io.choedeb.android.memo.presentation.R
import io.choedeb.android.memo.presentation.entity.PresentationEntity
import io.choedeb.android.memo.presentation.mapper.PresentationImagesMapper
import io.choedeb.android.memo.presentation.mapper.PresentationMemoMapper
import io.choedeb.android.memo.presentation.ui.base.ui.BaseViewModel
import io.choedeb.android.memo.presentation.util.AppValueUtil
import io.choedeb.android.memo.presentation.util.DateFormatUtil
import io.choedeb.android.memo.presentation.util.SingleLiveEvent
import kotlin.collections.ArrayList

class WriteViewModel(
    private val context: Context,
    private val getMemoUseCase: GetMemoUseCase,
    private val setMemoUseCase: SetMemoUseCase,
    private val memoMapper: PresentationMemoMapper,
    private val imagesMapper: PresentationImagesMapper
) : BaseViewModel() {

    private val _todayDate = MutableLiveData<String>()
    val todayDate: LiveData<String> = _todayDate

    private val _memoId = MutableLiveData<Long>(0)

    private val _titleText = MutableLiveData<String>()
    val titleText: LiveData<String> = _titleText

    private val _contentsText = MutableLiveData<String>()
    val contentsText: LiveData<String> = _contentsText

    private val _imageList = MutableLiveData<List<PresentationEntity.Image>>()
    val imageList: LiveData<List<PresentationEntity.Image>> = _imageList

    private val _isImageVisible = MutableLiveData<Boolean>()
    val isImageVisible: LiveData<Boolean> = _isImageVisible

    private val _completeState = MutableLiveData<Boolean>()
    val completeState: LiveData<Boolean> = _completeState

    val imageClick = SingleLiveEvent<String>()

    val completeClick = SingleLiveEvent<Void>()

    val showMessage = SingleLiveEvent<String>()

    private var tempImageList = ArrayList<PresentationEntity.Image>()

    init {
        _isImageVisible.value = false
        getTodayDate()
    }

    private fun getTodayDate() {
        _todayDate.value = DateFormatUtil.todayFormatDate()
    }

    fun getMemo(memoId: Long) {
        addDisposable(getMemoUseCase.execute(memoId)
            .map {
                PresentationEntity.MemoAndImages(
                    memoMapper.toPresentationEntity(it.memo),
                    imagesMapper.toPresentationEntity(it.images))
            }
            .subscribe({ data ->
                _memoId.value = data.memo.memoId
                _titleText.value = data.memo.title
                _contentsText.value = data.memo.contents
                if (data.images.isNotEmpty()) {
                    _imageList.value = data.images
                    _isImageVisible.value = true
                    tempImageList = data.images as ArrayList<PresentationEntity.Image>
                }
            }, {
                Logger.d(it.message)
            })
        )
    }

    fun saveMemo() {
        addDisposable(setMemoUseCase.execute(
            DomainEntity.Memo(
                _memoId.value!!,
                _titleText.value.toString(),
                _contentsText.value.toString()
            ), tempImageList.map {
                DomainEntity.Image(imageId = it.imageId, memoId = it.memoId, image = it.image, order = it.order)
            })
            .subscribe({
                completeClick.call()
            }, {
                Logger.d(it.message)
                showMessage.value = context.getString(R.string.toast_retry)
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

    fun setImageUri(images: ArrayList<String>) {
        for (image in images.indices) {
            tempImageList.add(PresentationEntity.Image(imageId = 0, image = images[image]))
        }
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

    fun onRequestPermissionsResult(activity: Activity, requestCode: Int, grantResults: IntArray) {
        when (requestCode) {
            AppValueUtil.REQUEST_CODE_CAMERA -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //openCamera(activity)
                } else {
                    showMessage.value = context.getString(R.string.text_permission_denied)
                }
                return
            }
            AppValueUtil.REQUEST_CODE_GALLERY -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //openGallery(activity)
                } else {
                    showMessage.value = context.getString(R.string.text_permission_denied)
                }
                return
            }
        }
    }
}