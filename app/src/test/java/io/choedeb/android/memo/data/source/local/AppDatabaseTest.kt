package io.choedeb.android.memo.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.choedeb.android.memo.data.Image
import io.choedeb.android.memo.data.Memo
import io.choedeb.android.memo.data.MemoAndImages
import org.junit.*
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest : AutoCloseKoinTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var databaseDao: AppDatabaseDao

    private lateinit var memo: Memo
    private lateinit var images: ArrayList<Image>

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().context
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        databaseDao = database.appDatabaseDao()

        memo = Memo(memoId = 1, title = "제목1", contents = "내용1")
        images = ArrayList()
        images.add(Image(imageId = 1, image = "이미지1"))
        images.add(Image(imageId = 2, image = "이미지2"))
    }

    @After
    @Throws(IOException::class)
    fun close() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun getAllMemo() {
        databaseDao.saveMemoAndImages(memo, images)
        databaseDao.getAllMemo()
            .test()
            .assertValue {
                val newMemoList = ArrayList<MemoAndImages>()
                newMemoList.add(MemoAndImages(memo, images))

                it.size == newMemoList.size
            }
    }

    @Test
    @Throws(Exception::class)
    fun getMemo() {
        databaseDao.saveMemoAndImages(memo, images)
        databaseDao.getMemo(1)
            .test()
            .assertValue {
                it == MemoAndImages(memo, images)
            }
    }

    @Test
    @Throws(Exception::class)
    fun deleteMemo() {
        databaseDao.saveMemoAndImages(memo, images)
        databaseDao.deleteMemo(memo)
            .test()
            .assertComplete()
    }

    @Test
    @Throws(Exception::class)
    fun saveMemoAndImages_confirmForeignKeyImages() {
        databaseDao.saveMemoAndImages(memo, images)
        databaseDao.getMemo(1)
            .test()
            .assertValue {
                // 저장한 메모(Memo)의 'memoId'와 이미지의 'memoId'(foreign key)가 같은지 확인
                it.memo.memoId == memo.memoId && it.images[0].memoId == memo.memoId
            }
    }
}