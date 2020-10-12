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

    override fun fetchGroupsFromApiIntoDbReturnChannelIds():
            Single<Set<ChannelEntity>> {
        return tvApi.getGroupsFromApi()
            .flatMap { list ->
                val map = GroupMapper.map(list)
                val groups = map.keys.toList().sortedBy { it.id }
                val channelMutableSet = mutableSetOf<ChannelEntity>()
                map.values.forEach { list1 -> channelMutableSet.addAll(list1.sortedBy { it.id }) }

                val channelSet: Set<ChannelEntity> = HashSet<ChannelEntity>(channelMutableSet)
                Timber.d("fetchGroupsFromApiToDbReturnChannelIds accomplished")
                //  insert
                groupDao.insertGroups(groups)
                    .toSingleDefault(channelSet).onErrorReturnItem(emptySet)
            }
            .subscribeOn(Schedulers.io())
    }

    override fun fetchChannelsFromApiIntoDb(set: Set<ChannelEntity>): Single<List<String>> {
        return Single.fromCallable { set }
            .flatMap { set1 ->
                val channels = mutableListOf<ChannelEntity>()
                channels.addAll(set1)
                val channelIdMutableList = mutableListOf<String>()
                channels.sortWith { p0, p1 ->   // pattern: "channel-id-x-x"
                    val cuttingPart = "channel-id-"
                    val p01 = p0!!.id.substringAfter(cuttingPart)
                    val p11 = p1!!.id.substringAfter(cuttingPart)
                    val digits0 = p01.split("-")
                    val digits1 = p11.split("-")
                    when {
                        digits0[0].toInt() > digits1[0].toInt() -> 1
                        digits0[0].toInt() == digits1[0].toInt() -> when {
                            digits0[1].toInt() > digits1[1].toInt() -> 1
                            digits0[1].toInt() == digits1[1].toInt() -> 0
                            else -> -1
                        }
                        else -> -1
                    }
                }
                channels.forEach {
                    channelIdMutableList.add(it.id)
                }
                val channelIdList: List<String> = ArrayList<String>(channelIdMutableList)
                Timber.d("fetchChannelsFromApiToDb accomplished")
                //  insert
                channelDao.insertChannels(channels)
                    .toSingleDefault(channelIdList).onErrorReturnItem(emptyList)
            }
            .subscribeOn(Schedulers.io())
    }

    override fun fetchProgramsFromApiIntoDb(id: String, channelIdList: List<String>): Completable {
        return tvApi.getProgramsFromApi(id)
            .flatMapCompletable { list ->
                val allProgramEntities = mutableListOf<ProgramEntity>()
                channelIdList.forEach { channelId ->
                    programMapper.setChannelId(channelId)
                    val programEntities = programMapper.map(list)
                    allProgramEntities.addAll(programEntities)
                }
                Timber.d("fetchProgramsFromApiToDb accomplished")
                //  insert
                programDao.insertPrograms(allProgramEntities)
            }
            .subscribeOn(Schedulers.io())
    }

    override fun fetchDaysFromApiIntoDb(): Completable {
        return Single.fromCallable { TestDataDb.generateDayEntities("01.06.2020", "14.06.2020") }
            .flatMapCompletable { days ->
                Timber.d("fetchDaysFromApiToDb accomplished")
                //  insert
                dayDao.insertDays(days)
            }
    }

    override fun getAllGroups(): Flowable<List<GroupEntity>> = groupDao.getAllGroups()
        .subscribeOn(Schedulers.io())

    override fun getChannelsByGroupId(groupId: String): Flowable<List<ChannelEntity>> =
        channelDao.getChannelsByGroupId(groupId)
            .subscribeOn(Schedulers.io())

    override fun getProgramsByChannelId(channelId: String): Flowable<List<ProgramEntity>> =
        programDao.getProgramsByChannelId(channelId)
            .subscribeOn(Schedulers.io())

    override fun getAllDays(): Flowable<List<DayEntity>> = dayDao.getAllDays()
        .subscribeOn(Schedulers.io())

    override fun getSelectedGroupById(groupId: String): Flowable<String> =
        groupDao.getSelectedGroupById(groupId)
            .subscribeOn(Schedulers.io())

    override fun getSelectedChannelById(channelId: String): Flowable<String> =
        channelDao.getSelectedChannelById(channelId)
            .subscribeOn(Schedulers.io())

//    override fun getSelectedProgram(limit: Int, clicked: Boolean): Flowable<ProgramEntity> =
//        programDao.getSelectedProgram(limit, clicked)
//            .subscribeOn(Schedulers.io())

//    override fun getSelectedDay(limit: Int, clicked: Boolean): Flowable<DayEntity> =
//        dayDao.getSelectedDayId(limit, clicked)
//            .subscribeOn(Schedulers.io())

//    override fun updateGroups(groups: List<GroupEntity>): Completable =
//        groupDao.updateGroups(groups)

//    override fun updateChannels(channels: List<ChannelEntity>): Completable =
//        channelDao.updateChannels(channels)
//
//    override fun updatePrograms(programs: List<ProgramEntity>): Completable =
//        programDao.updatePrograms(programs)
//
//    override fun updateDays(days: List<DayEntity>): Completable =
//        dayDao.updateDays(days)

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