package com.eburg_soft.televisionbroadcasting.domain.usecases

import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.repository.TVRepository
import io.reactivex.Single
import javax.inject.Inject

class FetchChannelsFromApiToDbUseCase @Inject constructor(private val repository: TVRepository) {

    fun execute(set: Set<ChannelEntity>): Single<List<String>> = repository.fetchChannelsFromApiToDb(set)
}