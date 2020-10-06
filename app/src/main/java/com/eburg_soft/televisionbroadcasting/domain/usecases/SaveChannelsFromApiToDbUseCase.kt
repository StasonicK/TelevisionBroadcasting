package com.eburg_soft.televisionbroadcasting.domain.usecases

import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.repository.TVRepository
import io.reactivex.Single
import javax.inject.Inject

class SaveChannelsFromApiToDbUseCase @Inject constructor(private val repository: TVRepository) {

    fun execute(set: MutableSet<ChannelEntity>): Single<MutableList<String>> = repository.saveChannelsFromApiToDb(set)
}