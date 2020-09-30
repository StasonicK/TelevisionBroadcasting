package com.eburg_soft.televisionbroadcasting.domain.usecases

import com.eburg_soft.televisionbroadcasting.data.repository.TVRepository
import io.reactivex.Single
import javax.inject.Inject

class SaveGroupsAndChannelsFromApiToDbReturnIdsUseCase @Inject constructor(private val repository: TVRepository) {

    fun execute(): Single<ArrayList<String>> = repository.saveGroupsAndChannelsFromApiToDbReturnIds()
}