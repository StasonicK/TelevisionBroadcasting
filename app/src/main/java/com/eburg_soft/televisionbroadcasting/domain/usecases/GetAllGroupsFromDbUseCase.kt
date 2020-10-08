package com.eburg_soft.televisionbroadcasting.domain.usecases

import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.data.repository.TVRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetAllGroupsUseCase @Inject constructor(private val repository: TVRepository) {

    fun execute(): Flowable<List<GroupEntity>> = repository.getAllGroups()
}