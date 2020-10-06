package com.eburg_soft.televisionbroadcasting.data.repository

import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface TVRepository {

    fun saveGroupsFromApiToDbReturnChannelIds(): Single<Set<ChannelEntity>>

    fun saveChannelsFromApiToDb(set: Set<ChannelEntity>): Single<List<String>>

    fun saveProgramsFromApiToDb(id: String, channelIdList: List<String>): Completable

    fun getAllGroups(): Flowable<List<GroupEntity>>

    fun getChannelsByGroupId(groupId: String): Flowable<List<ChannelEntity>>

    fun getProgramsByChannelId(channelId: String): Flowable<List<ProgramEntity>>

    fun removeAllGroups(): Completable

    fun removeAllChannels(): Completable

    fun removeAllPrograms(): Completable
}