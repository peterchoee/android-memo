package io.choedeb.android.memo.data.mapper

import io.choedeb.android.memo.data.entity.DataEntity
import io.choedeb.android.memo.domain.entity.DomainEntity

fun DataEntity.Memo.map() = DomainEntity.Memo(
    memoId = memoId,
    title = title,
    contents = contents,
    updateAt = updateAt
)

fun DataEntity.Image.map() = DomainEntity.Image(
    imageId = imageId,
    memoId = memoId,
    image = image,
    order = order
)

fun DataEntity.MemoAndImages.map() = DomainEntity.MemoAndImages(
    memo = memo.map(),
    images = images.map { image ->
        image.map()
    }
)