package io.choedeb.android.memo.data.mapper

import io.choedeb.android.memo.data.entity.DataEntity
import io.choedeb.android.memo.domain.entity.DomainEntity

fun List<DataEntity.MemoAndImages>.toDomainMemoAndImageList(): List<DomainEntity.MemoAndImages> =
    map(DataEntity.MemoAndImages::toDomainMemoAndImages)

fun DataEntity.MemoAndImages.toDomainMemoAndImages(): DomainEntity.MemoAndImages =
    DomainEntity.MemoAndImages(
        memo = memo.toDomainMemo(),
        images = images.toDomainImagesList()
    )

fun DataEntity.Memo.toDomainMemo(): DomainEntity.Memo =
    DomainEntity.Memo(memoId, title, contents, updateAt)

fun List<DataEntity.Image>.toDomainImagesList(): List<DomainEntity.Image> =
    map(DataEntity.Image::toDomainImages)

fun DataEntity.Image.toDomainImages(): DomainEntity.Image =
    DomainEntity.Image(imageId, memoId, image, order)
