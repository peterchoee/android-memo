package io.choedeb.android.memo.presentation.ui.main

import android.content.Intent
import androidx.lifecycle.Observer
import io.choedeb.android.memo.common.toast
import io.choedeb.android.memo.presentation.R
import io.choedeb.android.memo.presentation.databinding.ActivityMainBinding
import io.choedeb.android.memo.presentation.ui.base.ui.BaseActivity
import io.choedeb.android.memo.presentation.ui.detail.DetailActivity
import io.choedeb.android.memo.presentation.ui.write.WriteActivity
import io.choedeb.android.memo.presentation.util.AppValueUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModel()

    override fun setBind() {
        binding.apply {
            mainVM = viewModel
        }
    }

    override fun setObserve() {
        viewModel.fabClick.observe(this, Observer {
            startActivity(Intent(this, WriteActivity::class.java))
        })
        viewModel.memoClick.observe(this, Observer {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(AppValueUtil.MEMO_ID, it)
            startActivity(intent)
        })
        viewModel.isFabVisible.observe(this, Observer { isVisible ->
            if (isVisible) {
                fab.show()
            } else {
                fab.hide()
            }
        })
        viewModel.showMessage.observe(this, Observer {
            this.toast(getString(R.string.toast_retry))
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.getMemos()
    }
}
