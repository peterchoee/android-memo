package io.choedeb.android.memo.data.local

import androidx.room.*
import io.choedeb.android.memo.data.entity.DataEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface AppDatabaseDao {

    @Query("SELECT * FROM memos ORDER BY updateAt DESC")
    fun selectMemos(): Single<List<DataEntity.MemoAndImages>>

    @Query("SELECT * FROM memos WHERE memoId = :memoId")
    fun selectMemo(memoId : Long): Single<DataEntity.MemoAndImages>

    @Delete
    fun deleteMemo(memo: DataEntity.Memo): Completable

    @Transaction
    fun insertMemoAndImages(memo: DataEntity.Memo, images: List<DataEntity.Image>) {

        val memoId = insertMemo(memo)

        for (i in images.indices) {
            images[i].memoId = memoId
            images[i].order = i + 1
        }

        insertImages(images)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMemo(memo: DataEntity.Memo): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImages(images: List<DataEntity.Image>)
}