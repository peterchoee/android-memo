package io.choedeb.android.memo.di

import androidx.room.Room
import io.choedeb.android.memo.data.source.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "app.db").build()
    }

    single {
        get<AppDatabase>().appDatabaseDao()
    }
}