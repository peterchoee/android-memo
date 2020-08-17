package io.choedeb.android.memo.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.choedeb.android.memo.domain.entity.DomainEntity
import io.choedeb.android.memo.domain.usecase.DeleteMemoUseCase
import io.choedeb.android.memo.domain.usecase.GetMemoUseCase
import io.choedeb.android.memo.presentation.entity.PresentationEntity
import io.choedeb.android.memo.presentation.mapper.toPresentationMemoAndImages
import io.choedeb.android.memo.presentation.ui.base.ui.BaseViewModel
import io.choedeb.android.memo.presentation.util.DateFormatUtil
import io.choedeb.android.memo.presentation.util.SingleLiveEvent

class DetailViewModel(
    private val getMemoUseCase: GetMemoUseCase,
    private val deleteMemoUseCase: DeleteMemoUseCase
) : BaseViewModel() {

    private val _updateAtText = MutableLiveData<String>()
    val updateAtText: LiveData<String> = _updateAtText

    private val _titleText = MutableLiveData<String>()
    val titleText: LiveData<String> = _titleText

    private val _contentsText = MutableLiveData<String>()
    val contentsText: LiveData<String> = _contentsText

    private val _imageList = MutableLiveData<List<PresentationEntity.Image>>()
    val imageList: LiveData<List<PresentationEntity.Image>> = _imageList

    private val _isImageVisible = MutableLiveData<Boolean>()
    val isImageVisible: LiveData<Boolean> = _isImageVisible

    val imageClick = SingleLiveEvent<String>()

    val completeDelete = SingleLiveEvent<Void>()

    val showMessage = SingleLiveEvent<Void>()

    init {
        _isImageVisible.value = false
    }

    fun getMemoDetail(memoId: Long) {
        addDisposable(getMemoUseCase.execute(memoId)
            .map(DomainEntity.MemoAndImages::toPresentationMemoAndImages)
            .subscribe({ data ->
                if (data != null) {
                    _updateAtText.value = DateFormatUtil.compareFormatDate(data.memo.updateAt)
                    _titleText.value = data.memo.title
                    _contentsText.value = data.memo.contents
                    _imageList.value = data.images
                    _isImageVisible.value = true
                }
            }, {
                Logger.d(it.message)
                showMessage.call()
            })
        )
    }

    fun onDeleteClicked(memoId: Long) {
        addDisposable(deleteMemoUseCase.execute(DomainEntity.Memo(memoId = memoId))
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