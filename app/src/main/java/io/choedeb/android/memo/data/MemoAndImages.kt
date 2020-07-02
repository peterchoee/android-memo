package io.choedeb.android.memo.data

import androidx.room.Embedded
import androidx.room.Relation

data class MemoAndImages (
    @Embedded val memo: Memo,
    @Relation(parentColumn = "memoId", entityColumn = "memoId")
    var images: List<Image> = ArrayList()
)