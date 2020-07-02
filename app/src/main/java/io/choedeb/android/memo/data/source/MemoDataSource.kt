package io.choedeb.android.memo.data.source

import io.choedeb.android.memo.data.Image
import io.choedeb.android.memo.data.Memo
import io.choedeb.android.memo.data.MemoAndImages
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface MemoDataSource {

    fun getAllMemo(): Single<List<MemoAndImages>>

    fun getMemo(memoId : Long): Single<MemoAndImages>

    fun deleteMemo(memo: Memo): Completable

    fun saveMemoAndImages(memo: Memo, images: List<Image>): Completable
}