package io.choedeb.android.memo.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

open class BaseViewModel : ViewModel() {

    private val mDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        mDisposable.add(disposable)
    }

    override fun onCleared() {
        mDisposable.clear()
        super.onCleared()
    }
}