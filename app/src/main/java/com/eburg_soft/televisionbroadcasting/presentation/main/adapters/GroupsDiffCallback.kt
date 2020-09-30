package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import androidx.recyclerview.widget.DiffUtil
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity

class GroupsDiffCallback : DiffUtil.ItemCallback<GroupEntity>() {

    override fun areItemsTheSame(oldItem: GroupEntity, newItem: GroupEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: GroupEntity, newItem: GroupEntity): Boolean {
        return oldItem.id == newItem.id && oldItem.name == newItem.name
    }
}