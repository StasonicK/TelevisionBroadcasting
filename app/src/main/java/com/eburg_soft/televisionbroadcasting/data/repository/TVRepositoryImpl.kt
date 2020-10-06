package com.eburg_soft.televisionbroadcasting.data.repository

import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.ChannelDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.GroupDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.GroupWithChannelsDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.ProgramDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.network.TVApi
import com.eburg_soft.televisionbroadcasting.data.repository.mappers.GroupMapper
import com.eburg_soft.televisionbroadcasting.data.repository.mappers.ProgramMapper
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class TVRepositoryImpl @Inject constructor(
    private val groupDao: GroupDao,
    private val channelDao: ChannelDao,
    private val programDao: ProgramDao,
    private val groupWithChannelsDao: GroupWithChannelsDao,
    private val tvApi: TVApi,
    private val programMapper: ProgramMapper
) : TVRepository {

    private val emptySet: MutableSet<ChannelEntity> = mutableSetOf()
    private val emptyList: MutableList<String> = mutableListOf()

    override fun saveGroupsFromApiToDbReturnChannelIds():
            Single<MutableSet<ChannelEntity>> {
        return tvApi.getGroupsFromApi()
            .flatMap { list ->
                val map = GroupMapper.map(list)
                val groups = map.keys.toList().sortedBy { it.id }
                val channels = mutableSetOf<ChannelEntity>()
                map.values.forEach { list1 -> channels.addAll(list1.sortedBy { it.id }) }
                Timber.d("groups: $groups")
                //  insert
                groupDao.insertGroups(groups)
                    .toSingleDefault(channels).onErrorReturnItem(emptySet)
            }
            .subscribeOn(Schedulers.io())
    }

    override fun saveChannelsFromApiToDb(set: MutableSet<ChannelEntity>): Single<MutableList<String>> {
        return Single.fromCallable { set }
            .flatMap { set1 ->
                val channels = mutableListOf<ChannelEntity>()
                channels.addAll(set1)
                val channelIdList = mutableListOf<String>()
                channels.forEach {
                    channelIdList.add(it.id)
                }
                channelDao.insertChannels(channels)
                    .toSingleDefault(channelIdList).onErrorReturnItem(emptyList)
            }
            .subscribeOn(Schedulers.io())
    }

    override fun saveProgramsFromApiToDb(id: String, channelIdList: MutableList<String>): Completable {
        return tvApi.getProgramsFromApi(id)
            .flatMapCompletable { list ->
                val allProgramEntities = mutableListOf<ProgramEntity>()
                channelIdList.forEach { channelId ->
                    programMapper.setChannelId(channelId)
                    val programEntities = programMapper.map(list)
                    allProgramEntities.addAll(programEntities)
                }
                programDao.insertPrograms(allProgramEntities)
            }
            .subscribeOn(Schedulers.io())
    }

    override fun getAllGroups(): Flowable<List<GroupEntity>> {
        return groupDao.getAllGroups()
            .subscribeOn(Schedulers.io())
    }

    override fun getChannelsByGroupId(groupId: String): Flowable<List<ChannelEntity>> {
        return channelDao.getChannelsByGroupId(groupId)
            .subscribeOn(Schedulers.io())
    }

    override fun getProgramsByChannelId(channelId: String): Flowable<List<ProgramEntity>> {
        return programDao.getProgramsByChannelId(channelId)
            .subscribeOn(Schedulers.io())
    }

    override fun removeAllGroups(): Completable {
        return groupDao.deleteAllGroups()
            .subscribeOn(Schedulers.io())
    }

    override fun removeAllChannels(): Completable {
        return channelDao.deleteAllChannels()
            .subscribeOn(Schedulers.io())
    }

    override fun removeAllPrograms(): Completable {
        return programDao.deleteAllPrograms()
            .subscribeOn(Schedulers.io())
    }
}