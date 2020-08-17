package io.choedeb.android.memo.presentation.util

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import io.choedeb.android.memo.presentation.R
import kotlinx.android.synthetic.main.dialog_image_link.view.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PickImageUtil(
    private val activity: Activity,
    private val context: Context
) {

    private var images = ArrayList<String>()

    private lateinit var currentImagePath: String
    private lateinit var imageEncoded: String
    private lateinit var imagesEncodedList: MutableList<String>

    fun getImageToIntent(requestCode: Int) {
        when (requestCode) {
            AppValueUtil.REQUEST_CODE_CAMERA -> {
                openCameraCapture()
            }
            AppValueUtil.REQUEST_CODE_GALLERY -> {
                openGalleryPicker()
            }
        }
    }

    private fun openCameraCapture() {
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
                    val photoUri = FileProvider.getUriForFile(context, "${context.packageName}.provider", photoFile)
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    activity.startActivityForResult(intent, AppValueUtil.REQUEST_CODE_CAMERA)
                }
            }
        }
    }

    /**
     * Create Camera Image File
     */
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(Date())
        val imageName = "${timeStamp}_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(imageName, ".jpg", storageDir)
        currentImagePath = image.absolutePath
        return image
    }

    private fun openGalleryPicker() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        activity.startActivityForResult(
            Intent.createChooser(intent, context.getString(R.string.text_gallery_pick)),
            AppValueUtil.REQUEST_CODE_GALLERY)
    }

    fun getImageFromResult(requestCode: Int, data: Intent?): ArrayList<String> {
        when (requestCode) {
            AppValueUtil.REQUEST_CODE_CAMERA -> {
                val file = File(currentImagePath)
                val photoUri = Uri.fromFile(file)

                images.clear()
                images.add(photoUri.toString())

                return images
            }
            AppValueUtil.REQUEST_CODE_GALLERY -> {
                val filePathColumn = arrayOf(MediaStore.Images.Media._ID)
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

                images.clear()
                for (i in uriList.indices) {
                    images.add(uriList[i].toString())
                }

                return images
            }
            else -> {
                images.clear()
                return images
            }
        }
    }

    fun openLinkDialog(onDialogListener: OnDialogListener) {
        val linkDialog = AlertDialog.Builder(activity)
        val dialogView = activity.layoutInflater.inflate(R.layout.dialog_image_link, null)
        linkDialog.setView(dialogView)
            .setPositiveButton(context.getString(R.string.text_dialog_link_positive)) { _, _ ->
                images.clear()
                images.add(dialogView.etLink.text.toString())
                onDialogListener.onPositiveClicked(images)
            }
            .setNegativeButton(context.getString(R.string.text_dialog_link_negative)) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    interface OnDialogListener {
        fun onPositiveClicked(images: ArrayList<String>)
    }
}