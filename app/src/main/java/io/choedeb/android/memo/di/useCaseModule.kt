package io.choedeb.android.memo.di

import io.choedeb.android.memo.domain.usecase.DeleteMemoUseCase
import io.choedeb.android.memo.domain.usecase.GetMemoUseCase
import io.choedeb.android.memo.domain.usecase.GetMemosUseCase
import io.choedeb.android.memo.domain.usecase.SetMemoUseCase
import org.koin.dsl.module

val useCaseModule = module {

    single { GetMemosUseCase(get()) }

    single { GetMemoUseCase(get()) }

    single { SetMemoUseCase(get()) }

    single { DeleteMemoUseCase(get()) }
}