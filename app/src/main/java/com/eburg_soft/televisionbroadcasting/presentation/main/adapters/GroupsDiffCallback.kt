package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity

class GroupsDiffCallback(
    private val oldList: List<GroupEntity>,
    private val newList: List<GroupEntity>
) : BaseDiffCallback<GroupEntity>(oldList, newList) {

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEntity = oldList[oldItemPosition]
        val newEntity = newList[newItemPosition]
        return oldEntity.id == newEntity.id &&
                oldEntity.name == newEntity.name
    }
}