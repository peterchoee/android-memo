package io.choedeb.android.memo.presentation.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import io.choedeb.android.memo.presentation.BR
import io.choedeb.android.memo.presentation.R
import io.choedeb.android.memo.presentation.databinding.ActivityDetailBinding
import io.choedeb.android.memo.presentation.ui.base.ui.BaseActivity
import io.choedeb.android.memo.presentation.ui.main.MainActivity
import io.choedeb.android.memo.presentation.ui.write.WriteActivity
import io.choedeb.android.memo.presentation.util.AppValueUtil
import io.choedeb.android.memo.presentation.util.ExpandedImageDialog
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {

    override val layoutId: Int = R.layout.activity_detail
    override val viewModel: DetailViewModel by viewModel()
    override val bindingVariable: Int = BR.viewModel

    private val expandedImageDialog: ExpandedImageDialog by inject {
        parametersOf(this)
    }

    private var memoId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        memoId = intent.getLongExtra(AppValueUtil.MEMO_ID, 0)
        if (memoId.toString() != "0") {
            viewModel.getMemoDetail(memoId)
        }
    }

    override fun setObserve() {
        viewModel.imageClick.observe(this, Observer {
            expandedImageDialog.show(it)
        })
        viewModel.completeDelete.observe(this, Observer {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })
        viewModel.showMessage.observe(this, Observer {
            Toast.makeText(this, getString(R.string.toast_retry), Toast.LENGTH_SHORT).show()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_modify -> {
                val intent = Intent(this, WriteActivity::class.java)
                intent.putExtra(AppValueUtil.MEMO_ID, memoId)
                startActivity(intent)
                true
            }
            R.id.action_delete -> {
                viewModel.onDeleteClicked(memoId)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
