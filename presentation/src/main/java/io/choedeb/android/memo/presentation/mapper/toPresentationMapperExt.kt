package io.choedeb.android.memo.presentation.mapper

import io.choedeb.android.memo.domain.entity.DomainEntity
import io.choedeb.android.memo.presentation.entity.PresentationEntity

fun List<DomainEntity.MemoAndImages>.toPresentationMemoAndImageList(): List<PresentationEntity.MemoAndImages> =
    map(DomainEntity.MemoAndImages::toPresentationMemoAndImages)

fun DomainEntity.MemoAndImages.toPresentationMemoAndImages(): PresentationEntity.MemoAndImages =
    PresentationEntity.MemoAndImages(
        memo = memo.toPresentationMemo(),
        images = images.toPresentationImagesList()
    )

fun DomainEntity.Memo.toPresentationMemo(): PresentationEntity.Memo =
    PresentationEntity.Memo(memoId, title, contents, updateAt)

fun List<DomainEntity.Image>.toPresentationImagesList(): List<PresentationEntity.Image> =
    map(DomainEntity.Image::toPresentationImages)

fun DomainEntity.Image.toPresentationImages(): PresentationEntity.Image =
    PresentationEntity.Image(imageId, memoId, image, order)

