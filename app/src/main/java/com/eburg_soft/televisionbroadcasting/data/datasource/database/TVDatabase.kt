package com.eburg_soft.televisionbroadcasting.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.ChannelDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.DayDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.GroupDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.GroupWithChannelsDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.ProgramDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.DayEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupWithChannels
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity

@Database(
    entities = [GroupEntity::class, ChannelEntity::class, ProgramEntity::class, DayEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TVDatabase : RoomDatabase() {

    abstract fun groupDao(): GroupDao
    abstract fun channelDao(): ChannelDao
    abstract fun groupWithChannelsDao(): GroupWithChannelsDao
    abstract fun programDao(): ProgramDao
    abstract fun dayDao(): DayDao
}