package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import androidx.recyclerview.widget.DiffUtil
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.DayEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity

class ProgramsDiffCallback : DiffUtil.ItemCallback<ProgramEntity>() {

    override fun areItemsTheSame(oldItem: ProgramEntity, newItem: ProgramEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ProgramEntity, newItem: ProgramEntity): Boolean {
        return oldItem.id == newItem.id && oldItem.channelId == newItem.channelId && oldItem.name == newItem.name
    }
}