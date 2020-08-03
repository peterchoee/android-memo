package io.choedeb.android.memo.domain.repository

import io.choedeb.android.memo.data.entity.DataEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface MemoRepository {

    fun getAllMemo(): Single<List<DataEntity.MemoAndImages>>

    fun getMemo(memoId : Long): Single<DataEntity.MemoAndImages>

    fun deleteMemo(memo: DataEntity.Memo): Completable

    fun saveMemoAndImages(memo: DataEntity.Memo, images: List<DataEntity.Image>): Completable
}