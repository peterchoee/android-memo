package io.choedeb.android.memo.di

import io.choedeb.android.memo.domain.repository.MemoRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<MemoRepository> { MemoRepository(get()) }
}