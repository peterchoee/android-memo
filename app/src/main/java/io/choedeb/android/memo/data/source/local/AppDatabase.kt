package io.choedeb.android.memo.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.choedeb.android.memo.data.Image
import io.choedeb.android.memo.data.Memo
import io.choedeb.android.memo.util.DateConverter

@Database(entities = [Memo::class, Image::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDatabaseDao(): AppDatabaseDao
}