package io.choedeb.android.memo.data.repository

import hu.akarnokd.rxjava3.bridge.RxJavaBridge
import io.choedeb.android.memo.common.ioSchedulerWithMainThread
import io.choedeb.android.memo.data.local.AppDatabaseDao
import io.choedeb.android.memo.data.mapper.*
import io.choedeb.android.memo.domain.entity.DomainEntity
import io.choedeb.android.memo.domain.repository.MemoRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class MemoRepositoryImpl(
    private val appDatabaseDao: AppDatabaseDao,
    private val memoMapper: DataMemoMapper,
    private val imagesMapper: DataImagesMapper
) : MemoRepository {

    override fun getMemos(): Single<List<DomainEntity.MemoAndImages>> {
        return RxJavaBridge.toV3Single(appDatabaseDao.selectMemos().map {
            it.map { data ->
                DomainEntity.MemoAndImages(memoMapper.toDomainEntity(data.memo), imagesMapper.toDomainEntity(data.images))
            }
        }).ioSchedulerWithMainThread()
    }

    override fun getMemo(memoId: Long): Single<DomainEntity.MemoAndImages> {
        return RxJavaBridge.toV3Single(appDatabaseDao.selectMemo(memoId).map { data ->
            DomainEntity.MemoAndImages(memoMapper.toDomainEntity(data.memo), imagesMapper.toDomainEntity(data.images))
        }).ioSchedulerWithMainThread()
    }

    override fun deleteMemo(memo: DomainEntity.Memo): Completable {
        return RxJavaBridge.toV3Completable(appDatabaseDao.deleteMemo(memoMapper.toDataEntity(memo)))
            .ioSchedulerWithMainThread()
    }

    override fun setMemoAndImages(memo: DomainEntity.Memo, images: List<DomainEntity.Image>): Completable {
        return RxJavaBridge.toV3Completable(
            io.reactivex.Completable.fromAction {
                appDatabaseDao.insertMemoAndImages(memoMapper.toDataEntity(memo), imagesMapper.toDataEntity(images))
            }).ioSchedulerWithMainThread()
    }
}