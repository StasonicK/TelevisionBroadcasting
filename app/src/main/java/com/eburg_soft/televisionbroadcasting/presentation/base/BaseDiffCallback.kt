package com.eburg_soft.televisionbroadcasting.presentation.base

import androidx.recyclerview.widget.DiffUtil

abstract class BaseDiffCallback<T> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}