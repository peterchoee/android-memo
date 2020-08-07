package io.choedeb.android.memo.data.entity

import androidx.room.*
import java.util.*

/**
 * Local Room Database Entity & Data Layer Entity
 */
sealed class DataEntity {

    @Entity(tableName = "memos")
    data class Memo (
        @PrimaryKey(autoGenerate = true) val memoId: Long,
        @ColumnInfo(name = "title") val title: String? = null,
        @ColumnInfo(name = "contents") val contents: String? = null,
        @ColumnInfo(name = "updateAt") val updateAt: Date = Date()
    ): DataEntity()

    @Entity(
        tableName = "images",
        foreignKeys = [ForeignKey(
            entity = Memo::class,
            parentColumns = arrayOf("memoId"),
            childColumns = arrayOf("memoId"),
            onDelete = ForeignKey.CASCADE
        )])
    data class Image (
        @PrimaryKey(autoGenerate = true) val imageId: Long,
        @ColumnInfo(name = "memoId") var memoId: Long? = 0,
        @ColumnInfo(name = "image") val image: String,
        @ColumnInfo(name = "order") var order: Int? = 0
    ): DataEntity()

    data class MemoAndImages (
        @Embedded val memo: Memo,
        @Relation(parentColumn = "memoId", entityColumn = "memoId")
        var images: List<Image> = ArrayList()
    ): DataEntity()
}