package com.eburg_soft.televisionbroadcasting.data.datasource.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface ChannelDao {

    @Query("SELECT * from ${ChannelEntity.TABLE_NAME}")
    fun getAllChannels(): Flowable<List<ChannelEntity>>

    @Query("SELECT * from ${ChannelEntity.TABLE_NAME} WHERE ${ChannelEntity.COLUMN_GROUP_ID}=:groupId")
    fun getChannelsByGroupId(groupId: String): Flowable<List<ChannelEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChannels(channels: List<ChannelEntity>): Completable

    @Query("DELETE FROM `channels`")
    fun deleteAllChannels(): Completable
}