package io.choedeb.android.memo.domain.usecase

import io.choedeb.android.memo.domain.entity.DomainEntity
import io.choedeb.android.memo.domain.repository.MemoRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class MemoUseCaseImpl(
    private val memoRepository: MemoRepository
) : MemoUseCase {

    override fun getMemos(): Single<List<DomainEntity.MemoAndImages>> =
        memoRepository.getMemos()

    override fun getMemo(memoId: Long): Single<DomainEntity.MemoAndImages> =
        memoRepository.getMemo(memoId)

    override fun deleteMemo(memo: DomainEntity.Memo): Completable =
        memoRepository.deleteMemo(memo)

    override fun setMemoAndImages(memo: DomainEntity.Memo, images: List<DomainEntity.Image>): Completable =
        memoRepository.setMemoAndImages(memo, images)
}