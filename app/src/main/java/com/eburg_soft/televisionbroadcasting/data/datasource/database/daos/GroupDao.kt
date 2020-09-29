package com.eburg_soft.televisionbroadcasting.data.datasource.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface GroupDao {

//    @Transaction
//    @Query("SELECT * from `groups`")
//    fun getAllGroups(): Flowable<List<GroupWithChannels>>
//    fun getGroupWithChannels(): Flowable<List<GroupWithChannels>>

    @Query("SELECT * from `groups`")
    fun getAllGroups(): Flowable<List<GroupEntity>>

//    @Query("SELECT * from ${ChannelEntity.TABLE_NAME}")
//    fun getAllChannels(): Flowable<List<ChannelEntity>>

//    @Query("SELECT * from `channels` WHERE ${ChannelEntity.COLUMN_GROUP_ID}=:groupId")
//    fun getChannelByGroupId(groupId: String): Single<List<GroupWithChannels>>
//    fun getChannelByGroupId(groupId: String): Flowable<List<ChannelEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGroups(groups: List<GroupEntity>): Completable

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertChannels(channels: List<ChannelEntity>): Completable

    @Query("DELETE FROM `groups`")
    fun deleteAllGroups(): Completable

//    @Query("DELETE FROM ${ChannelEntity.TABLE_NAME}")
//    fun deleteAllChannels(): Completable
}