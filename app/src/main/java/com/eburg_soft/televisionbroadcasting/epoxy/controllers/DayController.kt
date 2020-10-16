package com.eburg_soft.televisionbroadcasting.epoxy.controllers

import com.airbnb.epoxy.TypedEpoxyController
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.DayEntity
import com.eburg_soft.televisionbroadcasting.epoxy.holder.dayHolder

class DayController(
    private val callback: Callback
) : TypedEpoxyController<List<DayEntity>>() {

    interface Callback {

        fun onDayClick(dayEntity: DayEntity, position: Int)
    }

    override fun buildModels(data: List<DayEntity>?) {
        data?.forEachIndexed { index, dayEntity ->
            dayHolder {
                id(dayEntity.id)
                dayEntity(dayEntity)
                listener { callback.onDayClick(dayEntity, index) }
            }
        }
    }
}