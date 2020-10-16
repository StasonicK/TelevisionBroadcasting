package com.eburg_soft.televisionbroadcasting.epoxy.controllers

import com.airbnb.epoxy.TypedEpoxyController
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.epoxy.holder.groupHolder

class GroupController(
    private val groupCallback: GroupCallback
) : TypedEpoxyController<List<GroupEntity>>() {

    interface GroupCallback {

        fun onGroupClick(groupEntity: GroupEntity, position: Int)
    }

    override fun buildModels(data: List<GroupEntity>?) {
        data?.forEachIndexed { index, groupEntity ->
            groupHolder {
                id(groupEntity.id)
                groupEntity(groupEntity)
                listener { groupCallback.onGroupClick(groupEntity, index) }
            }
        }
    }
}