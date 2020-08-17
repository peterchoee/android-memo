package io.choedeb.android.memo.presentation.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.choedeb.android.memo.presentation.R
import io.choedeb.android.memo.presentation.databinding.RowMainMemoListBinding
import io.choedeb.android.memo.presentation.entity.PresentationEntity
import io.choedeb.android.memo.presentation.ui.main.MainViewModel

class MainMemoRecyclerAdapter(
    var memos: List<PresentationEntity.MemoAndImages>?,
    val viewModel: MainViewModel
): RecyclerView.Adapter<MainMemoRecyclerAdapter.MemoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        return MemoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_main_memo_list, parent,false)
        )
    }

    override fun getItemCount(): Int = if (memos != null) memos!!.size else 0

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.binding.item = memos!![position]
        holder.binding.viewModel = viewModel
    }

    class MemoViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding: RowMainMemoListBinding = DataBindingUtil.bind(view)!!
    }
}