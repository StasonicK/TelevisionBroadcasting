package com.eburg_soft.televisionbroadcasting.data.repository

import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.GroupDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.ProgramDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.network.TVNetworkDataSource
import com.eburg_soft.televisionbroadcasting.data.repository.mappers.GroupMapper
import com.eburg_soft.televisionbroadcasting.data.repository.mappers.ProgramMapper
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TVRepositoryImpl @Inject constructor(
    private val groupDao: GroupDao,
    private val programDao: ProgramDao,
    private val tvNetworkDataSource: TVNetworkDataSource
) : TVRepository {

    override fun saveGroupsAndChannelsFromApiToDb(): Completable {
        return tvNetworkDataSource.getGroupsAndChannelsFromApi()
            .map { it.data }
            .flatMapCompletable { list ->
                val map = GroupMapper.map(list)
                val groups = map.keys.toList().sortedBy { it.id }
                val channels = mutableListOf<ChannelEntity>()
                map.values.forEach { list1 -> channels.addAll(list1.sortedBy { it.id }) }
                //  insert
                groupDao.insertGroups(groups)
                groupDao.insertChannels(channels)
            }
            .subscribeOn(Schedulers.io())
    }

    override fun saveProgramsFromApiToDb(id: String): Completable {
        return tvNetworkDataSource.getProgramsFromApi(id)
            .map { it.data }
            .flatMapCompletable { list ->
                val programEntities = ProgramMapper.map(list)
                programDao.insertPrograms(programEntities)
            }
            .subscribeOn(Schedulers.io())
    }

    override fun getAllGroups(): Flowable<List<GroupEntity>> {
        return groupDao.getAllGroups()
            .subscribeOn(Schedulers.io())
    }

    override fun getChannelsById(id: String): Flowable<List<ChannelEntity>> {
        return groupDao.getChannelByGroupId(id)
            .subscribeOn(Schedulers.io())
    }

    override fun getProgramsById(id: String): Flowable<List<ProgramEntity>> {
        return programDao.getProgramsById(id)
            .subscribeOn(Schedulers.io())
    }

    override fun removeAllGroups(): Completable {
      return  groupDao.deleteAllGroups()
            .subscribeOn(Schedulers.io())
    }

    override fun removeAllChannels(): Completable {
        return  groupDao.deleteAllChannels()
            .subscribeOn(Schedulers.io())
    }

    override fun removeAllPrograms(): Completable {
        return  programDao.deleteAllPrograms()
            .subscribeOn(Schedulers.io())
    }
}