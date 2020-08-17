package io.choedeb.android.memo.domain.entity

import java.util.*

sealed class DomainEntity {

    data class Memo (
        val memoId: Long,
        val title: String? = null,
        val contents: String? = null,
        val updateAt: Date = Date()
    ): DomainEntity()

    data class Image (
        val imageId: Long,
        var memoId: Long? = 0,
        val image: String,
        var order: Int? = 0
    ): DomainEntity()

    data class MemoAndImages (
        val memo: Memo,
        var images: List<Image> = ArrayList()
    ): DomainEntity()
}