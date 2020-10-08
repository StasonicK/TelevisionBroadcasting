package com.eburg_soft.televisionbroadcasting.data.repository

import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.DayEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface TVRepository {

    fun fetchGroupsFromApiToDbReturnChannelIds(): Single<Set<ChannelEntity>>

    fun fetchChannelsFromApiToDb(set: Set<ChannelEntity>): Single<List<String>>

    fun fetchProgramsFromApiToDb(id: String, channelIdList: List<String>): Completable

    fun fetchDaysFromApiToDb(): Completable

    fun getAllGroups(): Flowable<List<GroupEntity>>

    fun getChannelsByGroupId(groupId: String): Flowable<List<ChannelEntity>>

    fun getProgramsByChannelId(channelId: String): Flowable<List<ProgramEntity>>

    fun getAllDays(): Flowable<List<DayEntity>>

    fun removeAllGroups(): Completable

    fun removeAllChannels(): Completable

    fun removeAllPrograms(): Completable

    fun removeAllDays(): Completable
}