package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.DayEntity
import com.eburg_soft.televisionbroadcasting.presentation.base.BaseDiffCallback

class DaysDiffCallback : BaseDiffCallback<DayEntity>() {

    override fun areContentsTheSame(oldItem: DayEntity, newItem: DayEntity): Boolean {
        return oldItem.id == newItem.id && oldItem.date == newItem.date
    }
}