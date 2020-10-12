package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.presentation.base.BaseDiffCallback

class GroupsDiffCallback : BaseDiffCallback<GroupEntity>() {

    override fun areContentsTheSame(oldItem: GroupEntity, newItem: GroupEntity): Boolean {
        return oldItem.id == newItem.id && oldItem.name == newItem.name
    }
}