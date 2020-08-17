package io.choedeb.android.memo.domain.usecase

import io.choedeb.android.memo.domain.entity.DomainEntity
import io.choedeb.android.memo.domain.repository.MemoRepository
import io.reactivex.rxjava3.core.Single

class GetMemoUseCase(
    private val memoRepository: MemoRepository
) {

    fun execute(memoId: Long): Single<DomainEntity.MemoAndImages> =
        memoRepository.getMemo(memoId)
}