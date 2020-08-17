package io.choedeb.android.memo.presentation.ui.write

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.orhanobut.logger.Logger
import io.choedeb.android.memo.common.toast
import io.choedeb.android.memo.presentation.R
import io.choedeb.android.memo.presentation.databinding.ActivityWriteBinding
import io.choedeb.android.memo.presentation.ui.base.ui.BaseActivity
import io.choedeb.android.memo.presentation.ui.main.MainActivity
import io.choedeb.android.memo.presentation.util.AppValueUtil
import io.choedeb.android.memo.presentation.util.ExpandedImageUtil
import io.choedeb.android.memo.presentation.util.permission.PermissionStatus
import io.choedeb.android.memo.presentation.util.permission.PermissionUtil
import io.choedeb.android.memo.presentation.util.PickImageUtil
import kotlinx.android.synthetic.main.activity_write.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class WriteActivity : BaseActivity<ActivityWriteBinding>(R.layout.activity_write) {

    private val viewModel: WriteViewModel by viewModel()
    private val permissionUtil: PermissionUtil by inject { parametersOf(this) }
    private val expandedImageUtil: ExpandedImageUtil by inject { parametersOf(this) }
    private val pickImageUtil: PickImageUtil by inject { parametersOf(this) }

    override fun setBind() {
        binding.apply {
            writeVM = viewModel
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val memoId = intent.getLongExtra(AppValueUtil.MEMO_ID, 0)
        if (memoId.toString() != "0") {
            viewModel.getMemo(memoId)
        }
    }

    override fun setObserve() {
        viewModel.imageClick.observe(this, Observer { image ->
            expandedImageUtil.showDialog(image)
        })
        viewModel.completeClick.observe(this, Observer {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })
        viewModel.showMessage.observe(this, Observer { message ->
            this.toast(message)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_write, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_camera -> {
                when (permissionUtil.getPermissionStatus(Manifest.permission.CAMERA)) {
                    PermissionStatus.GRANTED -> {
                        pickImageUtil.getImageToIntent(AppValueUtil.REQUEST_CODE_CAMERA)
                    }
                    PermissionStatus.CAN_ASK -> {
                        permissionUtil.getPermissionRequest(
                            Manifest.permission.CAMERA,
                            AppValueUtil.REQUEST_CODE_CAMERA)
                    }
                }
                true
            }
            R.id.action_gallery -> {
                when (permissionUtil.getPermissionStatus(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    PermissionStatus.GRANTED -> {
                        pickImageUtil.getImageToIntent(AppValueUtil.REQUEST_CODE_GALLERY)
                    }
                    PermissionStatus.CAN_ASK -> {
                        permissionUtil.getPermissionRequest(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            AppValueUtil.REQUEST_CODE_GALLERY)
                    }
                }
                true
            }
            R.id.action_link -> {
                pickImageUtil.openLinkDialog(object: PickImageUtil.OnDialogListener {
                    override fun onPositiveClicked(images: ArrayList<String>) {
                        viewModel.setImageUri(images)
                    }
                })
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Permission Result for Camera & Gallery
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            AppValueUtil.REQUEST_CODE_CAMERA -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    pickImageUtil.getImageToIntent(AppValueUtil.REQUEST_CODE_CAMERA)
                } else {
                    this.toast(getString(R.string.text_permission_denied))
                }
                return
            }
            AppValueUtil.REQUEST_CODE_GALLERY -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    pickImageUtil.getImageToIntent(AppValueUtil.REQUEST_CODE_GALLERY)
                } else {
                    this.toast(getString(R.string.text_permission_denied))
                }
                return
            }
        }
    }

    /**
     * Activity Result for Camera & Gallery
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val images = pickImageUtil.getImageFromResult(requestCode, data)
        viewModel.setImageUri(images)
    }
}
