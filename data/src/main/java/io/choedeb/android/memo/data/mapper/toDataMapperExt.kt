package io.choedeb.android.memo.data.mapper

import io.choedeb.android.memo.data.entity.DataEntity
import io.choedeb.android.memo.domain.entity.DomainEntity

fun DomainEntity.Memo.toDataMemo(): DataEntity.Memo =
    DataEntity.Memo(memoId, title, contents)

fun List<DomainEntity.Image>.toDataImageList(): List<DataEntity.Image> =
    map(DomainEntity.Image::toDataImages)

fun DomainEntity.Image.toDataImages(): DataEntity.Image =
    DataEntity.Image(imageId, memoId, image, order)