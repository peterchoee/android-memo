package io.choedeb.android.memo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "memos")
data class Memo (
    @PrimaryKey(autoGenerate = true) val memoId: Long,
    @ColumnInfo(name = "title") val title: String? = null,
    @ColumnInfo(name = "contents") val contents: String? = null,
    @ColumnInfo(name = "updateAt") val updateAt: Date = Date()
)