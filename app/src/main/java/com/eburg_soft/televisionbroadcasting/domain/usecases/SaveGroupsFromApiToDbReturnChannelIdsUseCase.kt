package com.eburg_soft.televisionbroadcasting.domain.usecases

import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.repository.TVRepository
import io.reactivex.Single
import javax.inject.Inject

class SaveGroupsFromApiToDbReturnChannelIdsUseCase @Inject constructor(private val repository: TVRepository) {

    fun execute(): Single<Set<ChannelEntity>> = repository.saveGroupsFromApiToDbReturnChannelIds()
}