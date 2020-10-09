package com.eburg_soft.televisionbroadcasting.domain.usecases

import com.eburg_soft.televisionbroadcasting.data.repository.TVRepository
import javax.inject.Inject

class UpdateProgramsUseCase @Inject constructor(private val repository: TVRepository) {

//    fun execute(programsList: List<ProgramEntity>): Completable = repository.updatePrograms(programsList)
}