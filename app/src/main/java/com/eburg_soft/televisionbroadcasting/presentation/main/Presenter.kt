package com.eburg_soft.televisionbroadcasting.presentation.main

import com.eburg_soft.televisionbroadcasting.domain.usecases.GetAllGroupsUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetChannelsByGroupIdUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetProgramsByChannelIdUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.RemoveAllGroupsUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.SaveGroupsAndChannelsFromApiToDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.SaveProgramsFromApiToDbUseCase
import javax.inject.Inject

class TVMenuPresenter @Inject constructor(
    private val getAllGroupsUseCase: GetAllGroupsUseCase,
    private val getChannelsByGroupIdUseCase: GetChannelsByGroupIdUseCase,
    private val getProgramsByChannelIdUseCase: GetProgramsByChannelIdUseCase,
    private val saveGroupsAndChannelsFromApiToDbUseCase: SaveGroupsAndChannelsFromApiToDbUseCase,
    private val saveProgramsFromApiToDbUseCase: SaveProgramsFromApiToDbUseCase,
    private val removeAllGroupsUseCase: RemoveAllGroupsUseCase
) : TVMenuContract.Presenter() {

    override fun syncData() {
        TODO("Not yet implemented")
    }

    override fun loadGroupsFromDb() {
        TODO("Not yet implemented")
    }

    override fun loadChannelsByGroupIdFromDb() {
        TODO("Not yet implemented")
    }

    override fun loadProgramsByChannelIdFromDb() {
        TODO("Not yet implemented")
    }
}