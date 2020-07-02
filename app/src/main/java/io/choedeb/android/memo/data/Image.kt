package io.choedeb.android.memo.data

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "images",
    foreignKeys = [ForeignKey(
        entity = Memo::class,
        parentColumns = arrayOf("memoId"),
        childColumns = arrayOf("memoId"),
        onDelete = CASCADE)])
data class Image (
    @PrimaryKey(autoGenerate = true) val imageId: Long,
    @ColumnInfo(name = "memoId") var memoId: Long? = 0,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "order") var order: Int? = 0
)
