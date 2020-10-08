package com.eburg_soft.televisionbroadcasting.domain.usecases

import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import com.eburg_soft.televisionbroadcasting.data.repository.TVRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetProgramsByChannelIdFromDbUseCase @Inject constructor(private val repository: TVRepository) {

    fun execute(channelId: String): Flowable<List<ProgramEntity>> = repository.getProgramsByChannelId(channelId)
}