package com.eburg_soft.televisionbroadcasting.domain.usecases

import com.eburg_soft.televisionbroadcasting.data.repository.TVRepository
import io.reactivex.Completable
import javax.inject.Inject

class FetchDaysFromApiIntoDbUseCase @Inject constructor(private val repository: TVRepository) {

    fun execute(): Completable = repository.fetchDaysFromApiIntoDb()
}