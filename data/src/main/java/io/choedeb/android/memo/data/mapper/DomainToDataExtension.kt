package io.choedeb.android.memo.data.mapper

import io.choedeb.android.memo.data.entity.DataEntity
import io.choedeb.android.memo.domain.entity.DomainEntity

fun DomainEntity.Memo.map() = DataEntity.Memo(
    memoId = memoId,
    title = title,
    contents = contents,
    updateAt = updateAt
)

fun DomainEntity.Image.map() = DataEntity.Image(
    imageId = imageId,
    memoId = memoId,
    image = image,
    order = order
)

fun DomainEntity.MemoAndImages.map() = DataEntity.MemoAndImages(
    memo = memo.map(),
    images = images.map { image ->
        image.map()
    }
)