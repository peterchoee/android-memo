package io.choedeb.android.memo.presentation.mapper

import io.choedeb.android.memo.domain.entity.DomainEntity
import io.choedeb.android.memo.presentation.entity.PresentationEntity

class PresentationImagesMapper : PresentationMapper<List<DomainEntity.Image>, List<PresentationEntity.Image>> {

    override fun toPresentationEntity(type: List<DomainEntity.Image>): List<PresentationEntity.Image> =
        type.map { data ->
            PresentationEntity.Image(imageId = data.imageId, memoId = data.memoId, image = data.image, order = data.order)
        }

    override fun toDomainEntity(type: List<PresentationEntity.Image>): List<DomainEntity.Image> =
        type.map { data ->
            DomainEntity.Image(imageId = data.imageId, memoId = data.memoId, image = data.image, order = data.order)
        }
}