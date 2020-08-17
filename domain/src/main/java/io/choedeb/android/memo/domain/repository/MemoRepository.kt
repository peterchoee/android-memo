package io.choedeb.android.memo.domain.repository

import io.choedeb.android.memo.domain.entity.DomainEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface MemoRepository {

    fun getMemos(): Single<List<DomainEntity.MemoAndImages>>

    fun getMemo(memoId : Long): Single<DomainEntity.MemoAndImages>

    fun deleteMemo(memo: DomainEntity.Memo): Completable

    fun setMemoAndImages(memo: DomainEntity.Memo, images: List<DomainEntity.Image>): Completable
}