package io.choedeb.android.memo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import io.choedeb.android.memo.data.entity.DataEntity
import java.util.*

@Database(entities = [DataEntity.Memo::class, DataEntity.Image::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDatabaseDao(): AppDatabaseDao
}

class DateConverter {
    @TypeConverter fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }
    @TypeConverter fun dateToTimestamp(date: Date?): Long? = date?.time
}