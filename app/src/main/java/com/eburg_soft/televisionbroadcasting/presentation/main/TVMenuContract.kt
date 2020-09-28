package com.eburg_soft.televisionbroadcasting.presentation.main

import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity

interface TVMenuContract {
    interface View {

        fun showLoading()

        fun hideLoading()

        fun submitGroupsList(list: List<GroupEntity>)
        fun submitChannelsList(list: List<ChannelEntity>)
        fun submitProgramsList(list: List<ProgramEntity>)

        fun showNetworkErrorMessage(message: String)

        fun openGroupsList(groupEntity: GroupEntity)
        fun openChannelsList(channelEntity: ChannelEntity)
        fun openProgramsList(program: ProgramEntity)
    }

    abstract class TVMenuPresenter {

        abstract fun syncData()

        abstract fun loadGroupsFromDb()
        abstract fun loadChannelsFromDb()
        abstract fun loadProgramsFromDb()
    }
}