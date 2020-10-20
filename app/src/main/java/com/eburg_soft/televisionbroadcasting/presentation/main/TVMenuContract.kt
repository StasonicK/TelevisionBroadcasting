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

        fun initGroupsRecycler()
        fun initChannelsRecycler()
        fun initProgramsRecycler()
        fun initDaysRecycler()

        fun submitGroupsList(list: List<GroupEntity>?)
        fun submitChannelsList(list: List<ChannelEntity>?)
        fun submitProgramsList(list: List<ProgramEntity>?)
        fun submitDaysList(list: List<DayEntity>?)

        fun saveSelectedGroupId(groupId: String)
        fun saveSelectedChannelId(channelId: String)
        fun saveSelectedProgramId(programId: String)
        fun saveSelectedDayId(dayId: String)

        fun showNetworkErrorMessage(message: String)

        fun populateGroupsRecycler()
        fun populateChannelsRecycler()
        fun populateProgramsRecycler()
        fun populateDaysRecycler()

        fun setChannelsRecyclerVisibility(isVisible: Boolean)
    }

    abstract class Presenter : BaseContract.Presenter<View>() {

        abstract fun syncData()
        abstract fun fetchGroupsFromApiIntoDb()
        abstract fun fetchChannelsByChannelSetFromApiIntoDb(channelSet: Set<ChannelEntity>)
        abstract fun fetchProgramsByChannelIdFromApiIntoDb(channelIdList: List<String>)
        abstract fun fetchDaysFromApiIntoDb()

        abstract fun verifySelectedIdAndLoadGroupsFromDb(selectedGroupId: String?)
        abstract fun verifySelectedIdAndLoadChannelsFromDb(selectedGroupId: String, selectedChannelId: String?)
        abstract fun verifySelectedIdAndLoadProgramsFromDb(selectedChannelId: String, selectedProgramId: String?)
        abstract fun verifySelectedIdAndLoadDaysFromDb(selectedDayId: String?)

        abstract fun loadGroupsFromDb()
        abstract fun loadChannelsByGroupIdFromDb(savedGroupId: String)
        abstract fun loadProgramsByChannelIdFromDb(savedChannelId: String)
        abstract fun loadDaysFromDb(selectedDayId: String)

        abstract fun getRandomGroupIdAndLoadGroupsFromDb()
        abstract fun getRandomChannelIdAndLoadChannelsByGroupId(groupId: String)
        abstract fun getRandomProgramIdByChannelIdAndLoadProgramsByChannelId(channelId: String)
        abstract fun getRandomDayId()

        abstract fun removeAllGroups()
        abstract fun setSelectedGroupView(
            previousPosition: Pair<Int, GroupEntity?>,
            selectedPosition: Pair<Int, GroupEntity?>,
            currentList: List<GroupEntity>
        )

        abstract fun setSelectedChannelView(
            previousPosition: Pair<Int, ChannelEntity?>,
            selectedPosition: Pair<Int, ChannelEntity?>,
            currentList: List<ChannelEntity>
        )

        abstract fun setSelectedProgramView(
            previousPosition: Pair<Int, ProgramEntity?>,
            selectedPosition: Pair<Int, ProgramEntity?>,
            currentList: List<ProgramEntity>
        )

        abstract fun setSelectedDayView(
            previousPosition: Pair<Int, DayEntity?>,
            selectedPosition: Pair<Int, DayEntity?>,
            currentList: List<DayEntity>
        )
    }
}