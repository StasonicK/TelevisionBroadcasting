package com.eburg_soft.televisionbroadcasting.epoxy.controllers

import com.airbnb.epoxy.TypedEpoxyController
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.epoxy.holder.channelHolder

class ChannelController(
    private val callback: Callback
) : TypedEpoxyController<List<ChannelEntity>>() {

    interface Callback {

        fun onChannelClick(channelEntity: ChannelEntity, position: Int)
    }

    override fun buildModels(data: List<ChannelEntity>?) {
        data?.forEachIndexed { index, channelEntity ->
            channelHolder {
                id(channelEntity.id)
                channelEntity(channelEntity)
                listener { callback.onChannelClick(channelEntity, index) }
            }
        }
    }
}