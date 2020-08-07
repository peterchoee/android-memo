package io.choedeb.android.memo.di

import io.choedeb.android.memo.domain.usecase.MemoUseCase
import io.choedeb.android.memo.domain.usecase.MemoUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    single<MemoUseCase> { MemoUseCaseImpl(get()) }
}