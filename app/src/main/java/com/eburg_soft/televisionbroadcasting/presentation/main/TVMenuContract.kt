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

        fun showGroupsRecycler()
        fun showChannelsRecycler()
        fun showProgramsRecycler()
        fun showDaysRecycler()

        fun populateRecyclers()
    }

    abstract class Presenter : BaseContract.Presenter<View>() {

        abstract fun syncData()
//        abstract fun fetchGroupsWithChannelsFromApiIntoDb()
//        abstract fun fetchProgramsByChannelIdFromApiIntoDb(channelIdList: List<String>)
//        abstract fun fetchDaysFromApiIntoDb()

        abstract fun loadDefaultData()
        abstract fun loadGroupsFromDb():String
        abstract fun loadChannelsByGroupIdFromDb(groupId: String)
        abstract fun loadProgramsByChannelIdFromDb(channelId: String)
        abstract fun loadDaysFromDb()
        abstract fun getChannelsListOfSelectedGroupFromDb(savedGroupId: String?):String
        abstract fun getProgramsListOfSelectedChannelFromDb(savedGroupId: String, savedChannelId: String?)

        abstract fun removeAllGroups()
    }
}