package com.eburg_soft.televisionbroadcasting.data.repository

import com.eburg_soft.televisionbroadcasting.data.datasource.database.TestDataDb
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.ChannelDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.DayDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.GroupDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.ProgramDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.DayEntity
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
    private val dayDao: DayDao,
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
                Timber.d("saveGroupsFromApiToDbReturnChannelIds accomplished")
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
                channelIdMutableList.sortWith { p0, p1 -> // pattern: "channel-id-x-x"
                    val cuttingPart = "channel-id-"
                    val p01 = p0!!.substringAfter(cuttingPart)
                    val p11 = p1!!.substringAfter(cuttingPart)
                    val digits0 = p01.split("-")
                    val digits1 = p11.split("-")
                    when {
                        digits0[0].toInt() > digits1[0].toInt() -> 1
                        digits0[0].toInt() == digits1[0].toInt() -> when {
                            digits0[1].toInt() > digits1[1].toInt() -> 1
                            digits0[1].toInt() == digits1[1].toInt() -> 1
                            else -> -1
                        }
                        else -> -1
                    }
                }
                val channelIdList: List<String> = ArrayList<String>(channelIdMutableList)
                Timber.d("saveChannelsFromApiToDb accomplished")
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
                }
                Timber.d("saveProgramsFromApiToDb accomplished")
                //  insert
                programDao.insertPrograms(allProgramEntities)
            }
            .subscribeOn(Schedulers.io())
    }

    override fun saveDaysFromApiToDb(): Completable {
        return Single.fromCallable { TestDataDb.generateDayEntities("01.06.2020", "14.06.2020")}
            .flatMapCompletable { days->
                Timber.d("saveDaysFromApiToDb accomplished")
                //  insert
                dayDao.insertDays(days)
            }
    }

    override fun getAllGroups(): Flowable<List<GroupEntity>> {
        return groupDao.getAllGroups()
//        return groupDao.getFirstGroup()
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

    override fun getAllDays(): Flowable<List<DayEntity>> {
        return dayDao.getAllDays()
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

    override fun removeAllDays(): Completable {
        return dayDao.deleteAllDays()
    }
}