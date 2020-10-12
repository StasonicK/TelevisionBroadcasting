package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.presentation.base.BaseDiffCallback

class ChannelsDiffCallback : BaseDiffCallback<ChannelEntity>() {

    override fun areContentsTheSame(oldItem: ChannelEntity, newItem: ChannelEntity): Boolean {
        return oldItem.id == newItem.id && oldItem.groupId == newItem.groupId && oldItem.name == newItem.name && oldItem.logoUrl == newItem.logoUrl
    }
}