package io.choedeb.android.memo.data.source

import hu.akarnokd.rxjava3.bridge.RxJavaBridge
import io.choedeb.android.memo.data.Image
import io.choedeb.android.memo.data.Memo
import io.choedeb.android.memo.data.MemoAndImages
import io.choedeb.android.memo.data.source.local.AppDatabaseDao
import io.reactivex.functions.Action
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class MemoRepository(
    private val appDatabaseDao: AppDatabaseDao
) : MemoDataSource {

    override fun getAllMemo(): Single<List<MemoAndImages>> {
        return RxJavaBridge.toV3Single(appDatabaseDao.getAllMemo())
    }

    override fun getMemo(memoId: Long): Single<MemoAndImages> {
        return RxJavaBridge.toV3Single(appDatabaseDao.getMemo(memoId))
    }

    override fun deleteMemo(memo: Memo): Completable {
        return RxJavaBridge.toV3Completable(appDatabaseDao.deleteMemo(memo))
    }

    override fun saveMemoAndImages(memo: Memo, images: List<Image>): Completable {
        return RxJavaBridge.toV3Completable(
            io.reactivex.Completable.fromAction {
                appDatabaseDao.saveMemoAndImages(memo, images)
            })
    }
}