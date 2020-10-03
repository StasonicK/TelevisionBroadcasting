package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import androidx.recyclerview.widget.DiffUtil

abstract class BaseDiffCallback<V>(
    private val oldList: List<V>,
    private val newList: List<V>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}