package com.eburg_soft.televisionbroadcasting.data.repository

import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface TVRepository {

    fun saveGroupsFromApiToDbReturnChannelIds(): //Completable
//            Single<MutableSet<String>>
//            Single<Map<GroupEntity, MutableList<ChannelEntity>>>
            Single<MutableSet<ChannelEntity>>

    fun saveChannelsFromApiToDb(set: MutableSet<ChannelEntity>): //Completable
            Single<MutableList<String>>

    fun saveProgramsFromApiToDb(id: String, channelIdList: MutableList<String>): Completable

    fun getAllGroups(): Flowable<List<GroupEntity>>

    fun getChannelsByGroupId(groupId: String): Flowable<List<ChannelEntity>>

    fun getProgramsByChannelId(channelId: String): Flowable<List<ProgramEntity>>

    fun removeAllGroups(): Completable

    fun removeAllChannels(): Completable

    fun removeAllPrograms(): Completable
}