package io.choedeb.android.memo.presentation.ui.write

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import io.choedeb.android.memo.presentation.BR
import io.choedeb.android.memo.presentation.R
import io.choedeb.android.memo.presentation.databinding.ActivityWriteBinding
import io.choedeb.android.memo.presentation.ui.base.ui.BaseActivity
import io.choedeb.android.memo.presentation.ui.main.MainActivity
import io.choedeb.android.memo.presentation.util.AppValueUtil
import io.choedeb.android.memo.presentation.util.ExpandedImageDialog
import io.choedeb.android.memo.presentation.util.permission.PermissionStatus
import io.choedeb.android.memo.presentation.util.permission.PermissionUtil
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.android.synthetic.main.dialog_image_link.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class WriteActivity : BaseActivity<ActivityWriteBinding, WriteViewModel>() {

    override val layoutId: Int = R.layout.activity_write
    override val viewModel: WriteViewModel by viewModel()
    override val bindingVariable: Int = BR.viewModel

    private val permissionUtil: PermissionUtil by inject {
        parametersOf(this)
    }

    private val expandedImageDialog: ExpandedImageDialog by inject {
        parametersOf(this)
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
        viewModel.imageClick.observe(this, Observer {
            expandedImageDialog.show(it)
        })
        viewModel.completeClick.observe(this, Observer {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })
        viewModel.showMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_write, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("InflateParams")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_camera -> {
                when (permissionUtil.getPermissionStatus(Manifest.permission.CAMERA)) {
                    PermissionStatus.GRANTED -> {
                        viewModel.openCamera(this)
                    }
                    PermissionStatus.CAN_ASK -> {
                        permissionUtil.getPermissionRequest(Manifest.permission.CAMERA, AppValueUtil.PERMISSION_CAMERA)
                    }
                    PermissionStatus.DENIED -> {
                        Toast.makeText(this, getString(R.string.text_permission_denied), Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
            R.id.action_gallery -> {
                when (permissionUtil.getPermissionStatus(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    PermissionStatus.GRANTED -> {
                        viewModel.openGallery(this)
                    }
                    PermissionStatus.CAN_ASK -> {
                        permissionUtil.getPermissionRequest(Manifest.permission.READ_EXTERNAL_STORAGE, AppValueUtil.PERMISSION_GALLERY)
                    }
                    PermissionStatus.DENIED -> {
                        Toast.makeText(this, getString(R.string.text_permission_denied), Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
            R.id.action_link -> {
                openLinkDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        viewModel.onRequestPermissionsResult(this, requestCode, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onActivityResult(requestCode, resultCode, data)
    }

    @SuppressLint("InflateParams")
    private fun openLinkDialog() {
        val linkDialog = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_image_link, null)
        linkDialog.setView(dialogView)
            .setPositiveButton(getString(R.string.text_dialog_link_positive)) { _, _->
                viewModel.setImageFromLink(dialogView.etLink.text.toString())
            }
            .setNegativeButton(getString(R.string.text_dialog_link_negative)) { dialog, _ ->
                dialog.dismiss()
            }
        linkDialog.show()
    }
}
