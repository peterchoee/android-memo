package io.choedeb.android.memo.presentation.entity

import java.util.*

sealed class PresentationEntity {

    data class Memo (
        val memoId: Long,
        val title: String? = null,
        val contents: String? = null,
        val updateAt: Date = Date()
    ): PresentationEntity()

    data class Image (
        val imageId: Long,
        var memoId: Long? = 0,
        val image: String,
        var order: Int? = 0
    ): PresentationEntity()

    data class MemoAndImages (
        val memo: Memo,
        var images: List<Image> = ArrayList()
    ): PresentationEntity()
}