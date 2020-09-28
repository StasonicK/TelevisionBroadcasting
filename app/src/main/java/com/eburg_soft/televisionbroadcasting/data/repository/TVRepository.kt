package com.eburg_soft.televisionbroadcasting.data.repository

import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface TVRepository {

    fun saveGroupsAndChannelsFromApiToDb(): Completable

    fun saveProgramsFromApiToDb(id:String): Completable

    fun getAllGroups(): Flowable<List<GroupEntity>>

    fun getChannelsById(id: String): Flowable<List<ChannelEntity>>

    fun getProgramsById(id: String): Flowable<List<ProgramEntity>>

    fun removeAllGroups(): Completable

    fun removeAllChannels(): Completable

    fun removeAllPrograms(): Completable
}