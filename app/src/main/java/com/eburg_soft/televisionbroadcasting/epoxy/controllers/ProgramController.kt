package com.eburg_soft.televisionbroadcasting.epoxy.controllers

import com.airbnb.epoxy.TypedEpoxyController
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import com.eburg_soft.televisionbroadcasting.epoxy.holder.programHolder

class ProgramController(
    private val callback: Callback
) : TypedEpoxyController<List<ProgramEntity>>() {

    interface Callback {

        fun onProgramClick(programEntity: ProgramEntity, position: Int)
    }

    override fun buildModels(data: List<ProgramEntity>?) {
        data?.forEachIndexed { index, programEntity ->
            programHolder {
                id(programEntity.id)
                programEntity(programEntity)
                listener { callback.onProgramClick(programEntity, index) }
            }
        }
    }
}