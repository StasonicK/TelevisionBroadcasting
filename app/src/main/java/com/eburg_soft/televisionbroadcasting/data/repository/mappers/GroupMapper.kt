package com.eburg_soft.televisionbroadcasting.data.repository.mappers

import com.eburg_soft.televisionbroadcasting.core.BaseMapper
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.network.models.GroupResponse

object GroupMapper : BaseMapper<List<GroupResponse>, Map<GroupEntity, MutableList<ChannelEntity>>> {

    override fun map(type: List<GroupResponse>?): Map<GroupEntity, MutableList<ChannelEntity>> {
        val groupAndChannelMap = mutableMapOf<GroupEntity, MutableList<ChannelEntity>>()
        var groupEntity: GroupEntity

        type?.let { groupResponses ->
            //  move to each GroupResponses from list
            groupResponses.forEach { groupResponse ->
                //  create a GroupEntity
                groupEntity = GroupEntity(groupResponse.id, groupResponse.name)
                val channelEntities = arrayListOf<ChannelEntity>()
                //  move to each ChannelResponses of list
                groupResponse.channelResponses.forEach { channelResponse ->
                    //  add new ChannelEntity to the list
                    channelEntities.add(
                        //  create a ChannelEntity
                        ChannelEntity(
                            channelResponse.id,
                            groupEntity.id,
                            channelResponse.name,
                            channelResponse.logoUrl
                        )
                    )
                }
                //  put created objects to the map
                groupAndChannelMap[groupEntity] = channelEntities
            }
        }

        return groupAndChannelMap
    }
}