package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity

class ChannelsDiffCallback(
    private val oldList: List<ChannelEntity>,
    private val newList: List<ChannelEntity>
) : BaseDiffCallback<ChannelEntity>(oldList, newList) {

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEntity = oldList[oldItemPosition]
        val newEntity = newList[newItemPosition]
        return oldEntity.id == newEntity.id &&
                oldEntity.groupId == newEntity.groupId &&
                oldEntity.name == newEntity.name &&
                oldEntity.logoUrl == newEntity.logoUrl
    }
}