package io.choedeb.android.memo.presentation.ui.write.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.choedeb.android.memo.presentation.R
import io.choedeb.android.memo.presentation.databinding.RowWriteImageListBinding
import io.choedeb.android.memo.presentation.entity.PresentationEntity
import io.choedeb.android.memo.presentation.ui.write.WriteViewModel

class WriteImageRecyclerAdapter(
    var images: List<PresentationEntity.Image>?,
    val viewModel: WriteViewModel
): RecyclerView.Adapter<WriteImageRecyclerAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_write_image_list, parent, false)
        )
    }

    override fun getItemCount(): Int = images!!.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.binding.image = images!![position]
        holder.binding.viewModel = viewModel
        holder.binding.index = position
    }

    class ImageViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding: RowWriteImageListBinding = DataBindingUtil.bind(view)!!
    }
}