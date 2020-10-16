package com.eburg_soft.televisionbroadcasting.epoxy.holder

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.eburg_soft.televisionbroadcasting.R
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import com.eburg_soft.televisionbroadcasting.epoxy.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.item_program)
abstract class ProgramHolder : EpoxyModelWithHolder<ProgramHolder.ViewHolder>() {

    @EpoxyAttribute
    lateinit var programEntity: ProgramEntity

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var listener: (ProgramEntity) -> Unit

    override fun bind(holder: ViewHolder) {
        holder.programName.text = programEntity.name
        holder.container.setOnClickListener { listener.invoke(programEntity) }
    }

    override fun unbind(holder: ViewHolder) {
        holder.container.setOnClickListener(null)
    }

    class ViewHolder : KotlinEpoxyHolder() {

        val container by bind<View>(R.id.card_program)
        val programName by bind<TextView>(R.id.tv_program_name)
    }
}