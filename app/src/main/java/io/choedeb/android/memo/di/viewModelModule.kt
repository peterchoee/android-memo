package io.choedeb.android.memo.di

import io.choedeb.android.memo.ui.detail.DetailViewModel
import io.choedeb.android.memo.ui.main.MainViewModel
import io.choedeb.android.memo.ui.write.WriteViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { WriteViewModel(androidContext(), get()) }
}