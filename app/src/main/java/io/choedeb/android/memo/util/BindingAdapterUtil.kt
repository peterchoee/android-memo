package io.choedeb.android.memo.util

import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.choedeb.android.memo.R
import io.choedeb.android.memo.data.Image
import io.choedeb.android.memo.data.MemoAndImages
import io.choedeb.android.memo.ui.detail.DetailViewModel
import io.choedeb.android.memo.ui.detail.adapter.DetailImageRecyclerAdapter
import io.choedeb.android.memo.ui.main.MainViewModel
import io.choedeb.android.memo.ui.main.adapter.MainMemoRecyclerAdapter
import io.choedeb.android.memo.ui.write.WriteViewModel
import io.choedeb.android.memo.ui.write.adapter.WriteImageRecyclerAdapter

object BindingAdapterUtil {

    @JvmStatic
    @BindingAdapter("onScrollListener")
    fun setOnScrollListener(recyclerView: RecyclerView, listener: RecyclerView.OnScrollListener) {
        recyclerView.addOnScrollListener(listener)
    }

    @JvmStatic
    @BindingAdapter("textChangedListener")
    fun bindTextWatcher(editText: EditText, textWatcher: TextWatcher?) {
        editText.addTextChangedListener(textWatcher)
    }

    @JvmStatic
    @BindingAdapter(value = ["items", "viewModel"])
    fun setMemoItems(view: RecyclerView, memoList: List<MemoAndImages>?, viewModel: MainViewModel) {
        view.adapter?.run {
            if (this is MainMemoRecyclerAdapter) {
                this.memos = memoList
                this.notifyDataSetChanged()
            }
        } ?: run {
            MainMemoRecyclerAdapter(memoList, viewModel).apply {
                view.adapter = this
            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["items", "viewModel"])
    fun setDetailItems(view: RecyclerView, imageList: List<Image>?, viewModel: DetailViewModel) {
        view.adapter?.run {
            if (this is DetailImageRecyclerAdapter) {
                this.images = imageList
                this.notifyDataSetChanged()
            }
        } ?: run {
            DetailImageRecyclerAdapter(imageList, viewModel).apply {
                view.adapter = this
            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["items", "viewModel"])
    fun setWriteItems(view: RecyclerView, imageList: List<Image>?, viewModel: WriteViewModel) {
        view.adapter?.run {
            if (this is WriteImageRecyclerAdapter) {
                this.images = imageList
                this.notifyDataSetChanged()
            }
        } ?: run {
            WriteImageRecyclerAdapter(imageList, viewModel).apply {
                view.adapter = this
            }
        }
    }

    @JvmStatic
    @BindingAdapter("image")
    fun setImage(view: ImageView, image: String?) {
        Glide.with(view.context)
            .load(image)
            .error(R.drawable.img_image_error)
            .into(view)
    }
}