package io.choedeb.android.memo.domain.usecase

import io.choedeb.android.memo.domain.entity.DomainEntity
import io.choedeb.android.memo.domain.repository.MemoRepository
import io.reactivex.rxjava3.core.Completable

class SetMemoUseCase(
    private val memoRepository: MemoRepository
) {

    fun execute(memo: DomainEntity.Memo, images: List<DomainEntity.Image>): Completable =
        memoRepository.setMemoAndImages(memo, images)
}