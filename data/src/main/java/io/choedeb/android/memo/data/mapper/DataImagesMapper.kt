package io.choedeb.android.memo.data.mapper

import io.choedeb.android.memo.data.entity.DataEntity
import io.choedeb.android.memo.domain.entity.DomainEntity

class DataImagesMapper : DataMapper<List<DataEntity.Image>, List<DomainEntity.Image>> {

    override fun toDomainEntity(type: List<DataEntity.Image>): List<DomainEntity.Image> =
        type.map { data ->
            DomainEntity.Image(imageId = data.imageId, memoId = data.memoId, image = data.image, order = data.order)
        }

    override fun toDataEntity(type: List<DomainEntity.Image>): List<DataEntity.Image> =
        type.map { data ->
            DataEntity.Image(imageId = data.imageId, memoId = data.memoId, image = data.image, order = data.order)
        }
}