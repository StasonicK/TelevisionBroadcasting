package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity

class ProgramsDiffCallback(
    private val oldList: List<ProgramEntity>,
    private val newList: List<ProgramEntity>
) : BaseDiffCallback<ProgramEntity>(oldList, newList) {

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEntity = oldList[oldItemPosition]
        val newEntity = newList[newItemPosition]
        return oldEntity.id == newEntity.id &&
                oldEntity.channelId == newEntity.channelId &&
                oldEntity.name == newEntity.name
    }
}