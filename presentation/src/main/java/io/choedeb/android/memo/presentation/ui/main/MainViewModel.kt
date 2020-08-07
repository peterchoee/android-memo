package io.choedeb.android.memo.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import io.choedeb.android.memo.domain.usecase.MemoUseCase
import io.choedeb.android.memo.presentation.entity.PresentationEntity
import io.choedeb.android.memo.presentation.ui.base.ui.BaseViewModel
import io.choedeb.android.memo.presentation.util.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(
    private val memoUseCase: MemoUseCase
) : BaseViewModel() {

    val fabClick = SingleLiveEvent<Void>()
    val memoClick = SingleLiveEvent<Long>()

    private val _memoList = MutableLiveData<List<PresentationEntity.MemoAndImages>>()
    val memoList: LiveData<List<PresentationEntity.MemoAndImages>> = _memoList

    private val _memoCount = MutableLiveData<Int>()
    val memoCount: LiveData<Int> = _memoCount

    private val _isFabVisible = MutableLiveData<Boolean>()
    val isFabVisible: LiveData<Boolean> = _isFabVisible

    val showMessage = SingleLiveEvent<Boolean>()


    fun getMemos() {
        addDisposable(memoUseCase.getMemos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isNotEmpty()) {
                    _memoList.value = it
                    _memoCount.value = it.size
                } else {
                    val sampleMemoList = listOf(
                        PresentationEntity.MemoAndImages(
                            PresentationEntity.Memo(
                                -1,
                                "아직 작성한 메모가 없어요. \uD83D\uDE0D",
                                "하단의 버튼(+)을 누르면 메모를 작성할 수 있습니다. " +
                                        "메모를 작성하면 샘플 메모는 삭제됩니다."
                            )
                        ))
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