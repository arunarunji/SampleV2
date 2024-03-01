package com.example.sample.presentation.listView

import androidx.recyclerview.widget.DiffUtil
import com.example.sample.domain.model.Crypto

class MyDiffUtil(
    private val oldList: List<Crypto>,
    private val newList: List<Crypto>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}