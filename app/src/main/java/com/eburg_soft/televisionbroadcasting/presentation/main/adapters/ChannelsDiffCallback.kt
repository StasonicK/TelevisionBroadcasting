package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import androidx.recyclerview.widget.DiffUtil
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity

class ChannelsDiffCallback : DiffUtil.ItemCallback<ChannelEntity>() {

    override fun areItemsTheSame(oldItem: ChannelEntity, newItem: ChannelEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ChannelEntity, newItem: ChannelEntity): Boolean {
        return oldItem.id == newItem.id && oldItem.groupId == newItem.groupId && oldItem.name == newItem.name && oldItem.logoUrl == newItem.logoUrl
    }
}