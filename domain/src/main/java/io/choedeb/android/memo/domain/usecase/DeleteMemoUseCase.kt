package io.choedeb.android.memo.domain.usecase

import io.choedeb.android.memo.domain.entity.DomainEntity
import io.choedeb.android.memo.domain.repository.MemoRepository
import io.reactivex.rxjava3.core.Completable

class DeleteMemoUseCase(
    private val memoRepository: MemoRepository
) {

    fun execute(memo: DomainEntity.Memo): Completable =
        memoRepository.deleteMemo(memo)
}