package io.choedeb.android.memo.presentation.mapper

import io.choedeb.android.memo.domain.entity.DomainEntity
import io.choedeb.android.memo.presentation.entity.PresentationEntity

fun DomainEntity.Memo.map() = PresentationEntity.Memo(
    memoId = memoId,
    title = title,
    contents = contents,
    updateAt = updateAt
)

fun DomainEntity.Image.map() = PresentationEntity.Image(
    imageId = imageId,
    memoId = memoId,
    image = image,
    order = order
)

fun DomainEntity.MemoAndImages.map() = PresentationEntity.MemoAndImages(
    memo = memo.map(),
    images = images.map { image ->
        image.map()
    }
)