package io.choedeb.android.memo.common

import hu.akarnokd.rxjava3.bridge.RxJavaBridge
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T> Single<T>.ioWithMainThread(): io.reactivex.rxjava3.core.Single<T> {
    return RxJavaBridge.toV3Single(this)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun Completable.ioWithMainThread(): io.reactivex.rxjava3.core.Completable {
    return RxJavaBridge.toV3Completable(this)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}