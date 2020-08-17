package io.choedeb.android.memo.presentation.mapper

import io.choedeb.android.memo.domain.entity.DomainEntity
import io.choedeb.android.memo.presentation.entity.PresentationEntity

fun PresentationEntity.Memo.toDomainMemo(): DomainEntity.Memo =
    DomainEntity.Memo(memoId, title, contents)

fun List<PresentationEntity.Image>.toDomainImageList(): List<DomainEntity.Image> =
    map(PresentationEntity.Image::toDomainImages)

fun PresentationEntity.Image.toDomainImages(): DomainEntity.Image =
    DomainEntity.Image(imageId, memoId, image, order)

