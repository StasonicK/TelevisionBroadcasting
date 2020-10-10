package com.eburg_soft.televisionbroadcasting.presentation.main

import com.eburg_soft.televisionbroadcasting.core.BaseContract
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.DayEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity

interface TVMenuContract {
    interface View : BaseContract.View {

        fun showLoading()

        fun hideLoading()

        fun submitGroupsList(list: List<GroupEntity>)
        fun submitChannelsList(list: List<ChannelEntity>)
        fun submitProgramsList(list: List<ProgramEntity>)
        fun submitDaysList(list: List<DayEntity>)

        fun saveSelectedGroupId(groupId: String)

        fun saveSelectedChannelId(channelId: String)

        fun saveSelectedProgramId(channelId: String)

        fun saveSelectedDayId(dayId: String)

        fun showNetworkErrorMessage(message: String)

        fun initGroupsRecycler()
        fun initChannelsRecycler()
        fun initProgramsRecycler()
        fun initDaysRecycler()

        fun populateGroupsRecycler()
        fun populateChannelsRecycler()
        fun populateProgramsRecycler()
        fun populateDaysRecycler()
    }

    abstract class Presenter : BaseContract.Presenter<View>() {

        abstract fun syncData()
        abstract fun fetchGroupsFromApiIntoDb()
        abstract fun fetchChannelsByChannelSetFromApiIntoDb(channelSet: Set<ChannelEntity>)
        abstract fun fetchProgramsByChannelIdFromApiIntoDb(channelIdList: List<String>)
        abstract fun fetchDaysFromApiIntoDb()

        abstract fun loadGroupsFromDb(savedGroupId: String)
        abstract fun loadChannelsByGroupIdFromDb(savedGroupId: String, savedChannelId: String)
        abstract fun loadProgramsByChannelIdFromDb(savedChannelId: String, savedProgramId: String)
        abstract fun loadDaysFromDb()

        abstract fun getRandomGroupId()
        abstract fun getRandomChannelIdByGroupId(groupId: String)
        abstract fun getRandomProgramIdByGroupId(channelId: String)
        abstract fun getRandomDayId()

//        abstract fun getChannelsListOfSelectedGroupFromDb(savedGroupId: String): String
//        abstract fun getProgramsListOfSelectedChannelFromDb(savedGroupId: String, savedChannelId: String)

        abstract fun removeAllGroups()
    }
}