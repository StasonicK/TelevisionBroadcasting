package com.eburg_soft.televisionbroadcasting.domain.usecases

import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.DayEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.data.repository.TVRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetAllDaysUseCase @Inject constructor(private val repository: TVRepository) {

    fun execute(): Flowable<List<DayEntity>> = repository.getAllDays()
}