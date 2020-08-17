package io.choedeb.android.memo.presentation.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import io.choedeb.android.memo.domain.entity.DomainEntity
import io.choedeb.android.memo.domain.usecase.GetMemosUseCase
import io.choedeb.android.memo.presentation.R
import io.choedeb.android.memo.presentation.entity.PresentationEntity
import io.choedeb.android.memo.presentation.mapper.toPresentationMemoAndImageList
import io.choedeb.android.memo.presentation.ui.base.ui.BaseViewModel
import io.choedeb.android.memo.presentation.util.SingleLiveEvent

class MainViewModel(
    private val context: Context,
    private val getMemosUseCase: GetMemosUseCase
) : BaseViewModel() {

    private val _memoList = MutableLiveData<List<PresentationEntity.MemoAndImages>>()
    val memoList: LiveData<List<PresentationEntity.MemoAndImages>> = _memoList

    private val _memoCount = MutableLiveData<Int>()
    val memoCount: LiveData<Int> = _memoCount

    private val _isFabVisible = MutableLiveData<Boolean>()
    val isFabVisible: LiveData<Boolean> = _isFabVisible

    val fabClick = SingleLiveEvent<Void>()

    val memoClick = SingleLiveEvent<Long>()

    val showMessage = SingleLiveEvent<Boolean>()

    fun getMemos() {
        addDisposable(getMemosUseCase.execute()
            .map(List<DomainEntity.MemoAndImages>::toPresentationMemoAndImageList)
            .subscribe({ data ->
                if (data.isNotEmpty()) {
                    _memoList.value = data
                    _memoCount.value = data.size
                } else {
                    // when data(memos) is empty or null
                    val sampleMemoList = listOf(
                        PresentationEntity.MemoAndImages(
                            PresentationEntity.Memo(
                                -1,
                                context.getString(R.string.text_empty_title),
                                context.getString(R.string.text_empty_contents)
                            )))
                    _memoList.value = sampleMemoList
                    _memoCount.value = 0
                }
            }, {
                Logger.d(it.message)
                showMessage.call()
            })
        )
    }

    fun onFabClicked() {
        fabClick.call()
    }

    fun onMemoClicked(memoId: Long) {
        memoClick.value = memoId
    }

    var setAddOnScrollListener = object: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            _isFabVisible.value = dy <= 0
        }
    }
}