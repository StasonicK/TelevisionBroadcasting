package com.eburg_soft.televisionbroadcasting.data.datasource.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import io.reactivex.Completable

@Dao
abstract class GroupWithChannelsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract  fun insertGroups(groups: List<GroupEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   abstract fun insertChannels(channels: List<ChannelEntity>): Completable

    @Transaction
  open  fun insertGroupsAndChannels(groups: List<GroupEntity>,channels: List<ChannelEntity>){
        insertGroups(groups)
        insertChannels(channels)
    }
}