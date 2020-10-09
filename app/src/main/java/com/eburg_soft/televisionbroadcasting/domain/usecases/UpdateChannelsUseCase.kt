package com.eburg_soft.televisionbroadcasting.domain.usecases

import com.eburg_soft.televisionbroadcasting.data.repository.TVRepository
import javax.inject.Inject

class UpdateChannelsUseCase @Inject constructor(private val repository: TVRepository) {

//    fun execute(channelsList: List<ChannelEntity>): Completable = repository.updateChannels(channelsList)
}