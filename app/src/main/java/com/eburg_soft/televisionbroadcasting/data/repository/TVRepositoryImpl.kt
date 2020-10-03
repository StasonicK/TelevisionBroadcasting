package com.eburg_soft.televisionbroadcasting.data.repository

import com.eburg_soft.televisionbroadcasting.core.Constants
import com.eburg_soft.televisionbroadcasting.core.TVNetworkDataSource
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.ChannelDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.GroupDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.ProgramDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import com.eburg_soft.televisionbroadcasting.data.repository.mappers.GroupMapper
import com.eburg_soft.televisionbroadcasting.data.repository.mappers.ProgramMapper
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

class TVRepositoryImpl @Inject constructor(
    private val groupDao: GroupDao,
    private val channelDao: ChannelDao,
    private val programDao: ProgramDao,
    @Named(Constants.PRODUCTIVE_VERSION) private val tvNetworkDataSourceImpl: TVNetworkDataSource,
    private val programMapper: ProgramMapper
) : TVRepository {

    override fun saveGroupsAndChannelsFromApiToDbReturnIds(): Single<ArrayList<String>> {
        return tvNetworkDataSourceImpl.getGroupsAndChannelsFromApi()
            .flatMap { list ->
                val map = GroupMapper.map(list)
                val groups = map.keys.toList().sortedBy { it.id }
                val channels = mutableListOf<ChannelEntity>()
                map.values.forEach { list1 -> channels.addAll(list1.sortedBy { it.id }) }
                //  insert
                groupDao.insertGroups(groups)
                channelDao.insertChannels(channels)
                val ids: ArrayList<String> = ArrayList()
                channels.forEach { channelEntity ->
                    ids.add(channelEntity.id.substringAfterLast("-"))
                }
                Single.just((ids))
            }
            .subscribeOn(Schedulers.io())
    }

    override fun saveProgramsFromApiToDb(id: String, channelId: String): Completable {
        return tvNetworkDataSourceImpl.getProgramsFromApi(id)
            .flatMapCompletable { list ->
                programMapper.setChannelId(channelId)
                val programEntities = programMapper.map(list)
                programDao.insertPrograms(programEntities)
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