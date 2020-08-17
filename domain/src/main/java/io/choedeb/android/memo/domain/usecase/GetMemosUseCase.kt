package io.choedeb.android.memo.domain.usecase

import io.choedeb.android.memo.domain.entity.DomainEntity
import io.choedeb.android.memo.domain.repository.MemoRepository
import io.reactivex.rxjava3.core.Single

class GetMemosUseCase(
    private val memoRepository: MemoRepository
) {

    fun execute(): Single<List<DomainEntity.MemoAndImages>> =
        memoRepository.getMemos()
}