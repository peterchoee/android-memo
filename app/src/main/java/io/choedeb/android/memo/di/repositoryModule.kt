package io.choedeb.android.memo.di

import io.choedeb.android.memo.data.source.MemoDataSource
import io.choedeb.android.memo.data.source.MemoRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<MemoDataSource> { MemoRepository(get()) }
}