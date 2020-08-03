package io.choedeb.android.memo.data

import hu.akarnokd.rxjava3.bridge.RxJavaBridge
import io.choedeb.android.memo.data.entity.DataEntity
import io.choedeb.android.memo.data.local.AppDatabaseDao
import io.choedeb.android.memo.domain.repository.MemoRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class MemoRepositoryImpl(
    private val appDatabaseDao: AppDatabaseDao
) : MemoRepository {

    override fun getAllMemo(): Single<List<DataEntity.MemoAndImages>> {
        return RxJavaBridge.toV3Single(appDatabaseDao.getAllMemo())
    }

    override fun getMemo(memoId: Long): Single<DataEntity.MemoAndImages> {
        return RxJavaBridge.toV3Single(appDatabaseDao.getMemo(memoId))
    }

    override fun deleteMemo(memo: DataEntity.Memo): Completable {
        return RxJavaBridge.toV3Completable(appDatabaseDao.deleteMemo(memo))
    }

    override fun saveMemoAndImages(memo: DataEntity.Memo, images: List<DataEntity.Image>): Completable {
        return RxJavaBridge.toV3Completable(
            io.reactivex.Completable.fromAction {
                appDatabaseDao.saveMemoAndImages(memo, images)
            })
    }
}