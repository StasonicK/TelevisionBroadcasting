package com.eburg_soft.televisionbroadcasting.presentation.main

import com.eburg_soft.televisionbroadcasting.core.BaseContract
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity

interface TVMenuContract {
    interface View : BaseContract.View {

        fun showLoading()

        fun hideLoading()

        fun submitGroupsList(list: List<GroupEntity>)
        fun submitChannelsList(list: List<ChannelEntity>)
        fun submitProgramsList(list: List<ProgramEntity>)

        fun showNetworkErrorMessage(message: String)

        fun showGroupsRecycler(grId: String?)
        fun showChannelsRecycler(chId: String?)
        fun showProgramsRecycler(prId: String?)
    }

    abstract class Presenter : BaseContract.Presenter<View>() {

        abstract fun syncData()

        abstract fun loadGroupsFromDb()
        abstract fun loadChannelsByGroupIdFromDb(groupId: String)
        abstract fun loadProgramsByChannelIdFromDb(channelId: String)
        abstract fun removeAllGroups()
    }
}