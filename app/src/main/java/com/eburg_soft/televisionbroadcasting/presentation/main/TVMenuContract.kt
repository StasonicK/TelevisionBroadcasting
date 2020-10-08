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

        fun submitGroupList(list: List<GroupEntity>)
        fun submitChannelList(list: List<ChannelEntity>)
        fun submitProgramList(list: List<ProgramEntity>)
        fun submitDaysList(list: List<DayEntity>)

        fun submitDefaultGroupId(defaultGroupId: String)
        fun submitDefaultChannelId(defaultChannelId: String)

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

        abstract fun loadAllData()
        abstract fun loadGroupsFromDb()
        abstract fun loadChannelsByGroupIdFromDb(groupId: String)
        abstract fun loadProgramsByChannelIdFromDb(channelId: String)
        abstract fun loadDaysFromDb()

        abstract fun removeAllGroups()

    }
}