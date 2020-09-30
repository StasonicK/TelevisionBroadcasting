package com.eburg_soft.televisionbroadcasting.data.repository.mappers

import com.eburg_soft.televisionbroadcasting.core.BaseMapper
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.network.models.ProgramResponse
import javax.inject.Inject

class ProgramMapper @Inject constructor() : BaseMapper<List<ProgramResponse>, List<ProgramEntity>> {

    private var channelId = ""

    fun setChannelId(id: String) {
        channelId = id
    }

    override fun map(type: List<ProgramResponse>?): List<ProgramEntity> {
        val programEntities = mutableListOf<ProgramEntity>()
        type?.let { list ->
            list.forEach { programResponse ->
                programEntities.add(ProgramEntity(programResponse.id, channelId, programResponse.name))
            }
        }
        return programEntities
    }
}