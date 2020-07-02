package io.choedeb.android.memo.ui.main

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.Observer
import io.choedeb.android.memo.BR
import io.choedeb.android.memo.R
import io.choedeb.android.memo.databinding.ActivityMainBinding
import io.choedeb.android.memo.ui.base.BaseActivity
import io.choedeb.android.memo.ui.detail.DetailActivity
import io.choedeb.android.memo.ui.write.WriteActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val layoutId: Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModel()
    override val bindingVariable: Int = BR.viewModel

    override fun onStart() {
        super.onStart()
        viewModel.getAllMemo()
    }

    override fun setObserve() {
        viewModel.fabClick.observe(this, Observer {
            startActivity(Intent(this, WriteActivity::class.java))
        })
        viewModel.memoClick.observe(this, Observer {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("memoId", it)
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
            Toast.makeText(this, getString(R.string.toast_retry), Toast.LENGTH_SHORT).show()
        })
    }
}
