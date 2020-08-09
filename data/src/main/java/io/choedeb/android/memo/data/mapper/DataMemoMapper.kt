package io.choedeb.android.memo.data.mapper

import io.choedeb.android.memo.data.entity.DataEntity
import io.choedeb.android.memo.domain.entity.DomainEntity

class DataMemoMapper : DataMapper<DataEntity.Memo, DomainEntity.Memo> {

    override fun toDomainEntity(type: DataEntity.Memo): DomainEntity.Memo =
        DomainEntity.Memo(memoId = type.memoId, title = type.title, contents = type.contents, updateAt = type.updateAt)

    override fun toDataEntity(type: DomainEntity.Memo): DataEntity.Memo =
        DataEntity.Memo(memoId = type.memoId, title = type.title, contents = type.contents, updateAt = type.updateAt)
}