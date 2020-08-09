package io.choedeb.android.memo.presentation.mapper

import io.choedeb.android.memo.domain.entity.DomainEntity
import io.choedeb.android.memo.presentation.entity.PresentationEntity

class PresentationMemoMapper : PresentationMapper<DomainEntity.Memo, PresentationEntity.Memo> {

    override fun toPresentationEntity(type: DomainEntity.Memo): PresentationEntity.Memo =
        PresentationEntity.Memo(memoId = type.memoId, title = type.title, contents = type.contents, updateAt = type.updateAt)

    override fun toDomainEntity(type: PresentationEntity.Memo): DomainEntity.Memo =
        DomainEntity.Memo(memoId = type.memoId, title = type.title, contents = type.contents, updateAt = type.updateAt)
}