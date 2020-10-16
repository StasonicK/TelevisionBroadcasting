package com.eburg_soft.televisionbroadcasting.epoxy.controllers

import com.airbnb.epoxy.TypedEpoxyController
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.epoxy.holder.channelHolder

class ChannelController(
    private val channelCallback: ChannelCallback
) : TypedEpoxyController<List<ChannelEntity>>() {

    interface ChannelCallback {

        fun onChannelClick(channelEntity: ChannelEntity, position: Int)
    }

    override fun buildModels(data: List<ChannelEntity>?) {
        data?.forEachIndexed { index, channelEntity ->
            channelHolder {
                id(channelEntity.id)
                channelEntity(channelEntity)
                listener { channelCallback.onChannelClick(channelEntity, index) }
            }
        }
    }
}