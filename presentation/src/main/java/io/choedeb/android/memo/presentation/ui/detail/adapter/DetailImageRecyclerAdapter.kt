package io.choedeb.android.memo.presentation.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.choedeb.android.memo.R
import io.choedeb.android.memo.data.Image
import io.choedeb.android.memo.databinding.RowDetailImageListBinding
import io.choedeb.android.memo.presentation.ui.detail.DetailViewModel

class DetailImageRecyclerAdapter(
    var images: List<Image>?,
    val viewModel: DetailViewModel
): RecyclerView.Adapter<DetailImageRecyclerAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_detail_image_list, parent, false)
        )
    }

    override fun getItemCount(): Int = images!!.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.binding.image = images!![position]
        holder.binding.viewModel = viewModel
    }

    class ImageViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding: RowDetailImageListBinding = DataBindingUtil.bind(view)!!
    }
}