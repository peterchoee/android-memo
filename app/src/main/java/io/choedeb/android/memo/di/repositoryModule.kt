package io.choedeb.android.memo.di

import io.choedeb.android.memo.data.mapper.DataImagesMapper
import io.choedeb.android.memo.data.mapper.DataMemoMapper
import io.choedeb.android.memo.data.repository.MemoRepositoryImpl
import io.choedeb.android.memo.domain.repository.MemoRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<MemoRepository> { MemoRepositoryImpl(get(), get(), get()) }

    single { DataMemoMapper() }
    single { DataImagesMapper() }
}