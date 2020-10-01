package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import androidx.recyclerview.widget.DiffUtil
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.DayEntity

class DaysDiffCallback : DiffUtil.ItemCallback<DayEntity>() {

    override fun areItemsTheSame(oldItem: DayEntity, newItem: DayEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DayEntity, newItem: DayEntity): Boolean {
        return oldItem.id == newItem.id && oldItem.date == newItem.date
    }
}