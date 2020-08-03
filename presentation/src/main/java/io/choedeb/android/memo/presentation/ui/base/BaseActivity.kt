package io.choedeb.android.memo.presentation.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T: ViewDataBinding, V: BaseViewModel> : AppCompatActivity() {

    protected lateinit var viewDataBinding: T
    abstract val layoutId: Int
    abstract val viewModel: V
    abstract val bindingVariable: Int

    abstract fun setObserve()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.setVariable(bindingVariable, viewModel)

        setObserve()
    }
}