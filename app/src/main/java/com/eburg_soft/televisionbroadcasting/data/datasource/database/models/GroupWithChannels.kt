package com.eburg_soft.televisionbroadcasting.data.datasource.database.models

import androidx.room.Embedded
import androidx.room.Relation

data class GroupWithChannels(
    @Embedded
    val group: GroupEntity,
    @Relation(
        parentColumn = GroupEntity.COLUMN_ID,
        entityColumn = ChannelEntity.COLUMN_GROUP_ID
    )
    val channels: List<ChannelEntity>
)