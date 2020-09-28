package com.eburg_soft.televisionbroadcasting.data.repository.mappers

import com.eburg_soft.televisionbroadcasting.core.BaseMapper
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.network.models.ProgramResponse

object ProgramMapper : BaseMapper<List<ProgramResponse>, List<ProgramEntity>> {

    override fun map(type: List<ProgramResponse>?): List<ProgramEntity> {
        val programEntities = mutableListOf<ProgramEntity>()
        type?.let { list ->
            list.forEach { programResponse ->
                programEntities.add(ProgramEntity(programResponse.id, programResponse.name))
            }
        }
        return programEntities
    }
}