package com.eburg_soft.televisionbroadcasting.data.repository

import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.ChannelDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.GroupDao
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
    private val tvApi: TVApi,
    private val programMapper: ProgramMapper
) : TVRepository {

    private val emptySet: Set<ChannelEntity> = mutableSetOf()
    private val emptyList: List<String> = mutableListOf()

    override fun saveGroupsFromApiToDbReturnChannelIds():
            Single<Set<ChannelEntity>> {
        return tvApi.getGroupsFromApi()
            .flatMap { list ->
                val map = GroupMapper.map(list)
                val groups = map.keys.toList().sortedBy { it.id }
                val channelMutableSet = mutableSetOf<ChannelEntity>()
                map.values.forEach { list1 -> channelMutableSet.addAll(list1.sortedBy { it.id }) }

                val channelSet: Set<ChannelEntity> = HashSet<ChannelEntity>(channelMutableSet)
                Timber.d("groups: $groups")
                //  insert
                groupDao.insertGroups(groups)
                    .toSingleDefault(channelSet).onErrorReturnItem(emptySet)
            }
            .subscribeOn(Schedulers.io())
    }

    override fun saveChannelsFromApiToDb(set: Set<ChannelEntity>): Single<List<String>> {
        return Single.fromCallable { set }
            .flatMap { set1 ->
                val channels = mutableListOf<ChannelEntity>()
                channels.addAll(set1)
                val channelIdMutableList = mutableListOf<String>()
                channels.forEach {
                    channelIdMutableList.add(it.id)
                }
                val channelIdList: List<String> = ArrayList<String>(channelIdMutableList)
                Timber.d("channels: $channels")
                //  insert
                channelDao.insertChannels(channels)
                    .toSingleDefault(channelIdList).onErrorReturnItem(emptyList)
            }
            .subscribeOn(Schedulers.io())
    }

    override fun saveProgramsFromApiToDb(id: String, channelIdList: List<String>): Completable {
        return tvApi.getProgramsFromApi(id)
            .flatMapCompletable { list ->
                val allProgramEntities = mutableListOf<ProgramEntity>()
                channelIdList.forEach { channelId ->
                    programMapper.setChannelId(channelId)
                    val programEntities = programMapper.map(list)
                    allProgramEntities.addAll(programEntities)
                    Timber.d("programs: $programEntities")
                }
                Timber.d("all programs: $allProgramEntities")
                //  insert
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