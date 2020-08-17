package io.choedeb.android.memo.data.repository

import io.choedeb.android.memo.common.ioWithMainThread
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
        return appDatabaseDao.selectMemos().map {
            it.map { data ->
                DomainEntity.MemoAndImages(memoMapper.toDomainEntity(data.memo), imagesMapper.toDomainEntity(data.images))
            }
        }.ioWithMainThread()
    }

    override fun getMemo(memoId: Long): Single<DomainEntity.MemoAndImages> {
        return appDatabaseDao.selectMemo(memoId).map { data ->
            DomainEntity.MemoAndImages(memoMapper.toDomainEntity(data.memo), imagesMapper.toDomainEntity(data.images))
        }.ioWithMainThread()
    }

    override fun deleteMemo(memo: DomainEntity.Memo): Completable {
        return appDatabaseDao.deleteMemo(memoMapper.toDataEntity(memo)).ioWithMainThread()
    }

    override fun setMemoAndImages(memo: DomainEntity.Memo, images: List<DomainEntity.Image>): Completable {
        return io.reactivex.Completable.fromAction {
            appDatabaseDao.insertMemoAndImages(memoMapper.toDataEntity(memo), imagesMapper.toDataEntity(images))
        }.ioWithMainThread()
    }
}
