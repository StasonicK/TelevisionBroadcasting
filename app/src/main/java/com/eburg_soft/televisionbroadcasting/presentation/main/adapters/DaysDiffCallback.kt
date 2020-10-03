package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.DayEntity
import com.eburg_soft.televisionbroadcasting.presentation.base.BaseDiffCallback

class DaysDiffCallback(
    private val oldList: List<DayEntity>,
    private val newList: List<DayEntity>
) : BaseDiffCallback<DayEntity>(oldList, newList) {

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEntity = oldList[oldItemPosition]
        val newEntity = newList[newItemPosition]
        return oldEntity.id == newEntity.id &&
                oldEntity.date == newEntity.date
    }
}