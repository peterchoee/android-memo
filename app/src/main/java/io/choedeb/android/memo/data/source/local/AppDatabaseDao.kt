package io.choedeb.android.memo.data.source.local

import androidx.room.*
import io.choedeb.android.memo.data.Image
import io.choedeb.android.memo.data.Memo
import io.choedeb.android.memo.data.MemoAndImages
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface AppDatabaseDao {

    @Query("SELECT * FROM memos ORDER BY updateAt DESC")
    fun getAllMemo(): Single<List<MemoAndImages>>

    @Query("SELECT * FROM memos WHERE memoId = :memoId")
    fun getMemo(memoId : Long): Single<MemoAndImages>

    @Delete
    fun deleteMemo(memo: Memo): Completable

    @Transaction
    fun saveMemoAndImages(memo: Memo, images: List<Image>) {

        val memoId = saveNewMemo(memo)

        for (i in images.indices) {
            images[i].memoId = memoId
            images[i].order = i + 1
        }

        saveNewImages(images)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNewMemo(memo: Memo): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNewImages(images: List<Image>)
}