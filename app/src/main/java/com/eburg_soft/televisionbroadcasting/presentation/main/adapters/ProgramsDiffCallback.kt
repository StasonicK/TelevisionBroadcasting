package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import com.eburg_soft.televisionbroadcasting.presentation.base.BaseDiffCallback

class ProgramsDiffCallback : BaseDiffCallback<ProgramEntity>() {

    override fun areContentsTheSame(oldItem: ProgramEntity, newItem: ProgramEntity): Boolean {
        return oldItem.id == newItem.id && oldItem.channelId == newItem.channelId && oldItem.name == newItem.name
    }
}