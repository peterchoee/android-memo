package io.choedeb.android.memo.presentation.mapper

import io.choedeb.android.memo.domain.entity.DomainEntity
import io.choedeb.android.memo.presentation.entity.PresentationEntity

fun PresentationEntity.Memo.map() = DomainEntity.Memo(
    memoId = memoId,
    title = title,
    contents = contents,
    updateAt = updateAt
)

fun PresentationEntity.Image.map() = DomainEntity.Image(
    imageId = imageId,
    memoId = memoId,
    image = image,
    order = order
)

fun PresentationEntity.MemoAndImages.map() = DomainEntity.MemoAndImages(
    memo = memo.map(),
    images = images.map { image ->
        image.map()
    }
)