package io.choedeb.android.memo.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.choedeb.android.memo.data.Image
import io.choedeb.android.memo.data.Memo
import io.choedeb.android.memo.data.source.MemoDataSource
import io.choedeb.android.memo.presentation.ui.base.BaseViewModel
import io.choedeb.android.memo.util.DateFormatUtil
import io.choedeb.android.memo.util.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailViewModel(
    private val memoDataSource: MemoDataSource
) : BaseViewModel() {

    private val _updateAtText = MutableLiveData<String>()
    val updateAtText: LiveData<String> = _updateAtText

    private val _titleText = MutableLiveData<String>()
    val titleText: LiveData<String> = _titleText

    private val _contentsText = MutableLiveData<String>()
    val contentsText: LiveData<String> = _contentsText

    private val _imageList = MutableLiveData<List<Image>>()
    val imageList: LiveData<List<Image>> = _imageList

    private val _isImageVisible = MutableLiveData<Boolean>()
    val isImageVisible: LiveData<Boolean> = _isImageVisible

    val imageClick = SingleLiveEvent<String>()

    val completeDelete = SingleLiveEvent<Void>()

    val showMessage = SingleLiveEvent<Boolean>()

    init {
        _isImageVisible.value = false
    }

    fun getMemoDetail(memoId: Long) {
        addDisposable(memoDataSource.getMemo(memoId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Logger.d(it.toString())
                _updateAtText.value = DateFormatUtil.compareFormatDate(it.memo.updateAt)
                _titleText.value = it.memo.title
                _contentsText.value = it.memo.contents
                _imageList.value = it.images
                _isImageVisible.value = true
            }, {
                Logger.d(it.message)
                showMessage.call()
            })
        )
    }

    fun onDeleteClicked(memoId: Long) {
        addDisposable(memoDataSource.deleteMemo(Memo(memoId = memoId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                completeDelete.call()
            }, {
                Logger.d(it.message)
                showMessage.call()
            })
        )
    }

    fun onImageClicked(imageUri: String) {
        imageClick.value = imageUri
    }
}