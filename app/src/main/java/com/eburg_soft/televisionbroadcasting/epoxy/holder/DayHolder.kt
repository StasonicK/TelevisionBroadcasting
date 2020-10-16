package com.eburg_soft.televisionbroadcasting.epoxy.holder

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.eburg_soft.televisionbroadcasting.R
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.DayEntity
import com.eburg_soft.televisionbroadcasting.epoxy.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.item_day)
abstract class DayHolder : EpoxyModelWithHolder<DayHolder.ViewHolder>() {

    @EpoxyAttribute
    lateinit var dayEntity: DayEntity

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var listener: (DayEntity) -> Unit

    override fun bind(holder: ViewHolder) {
        holder.dayDate.text = dayEntity.date
        holder.container.setOnClickListener { listener.invoke(dayEntity) }
    }

    override fun unbind(holder: ViewHolder) {
        holder.container.setOnClickListener(null)
    }

    class ViewHolder : KotlinEpoxyHolder() {

        val container by bind<View>(R.id.container_day)
        val dayDate by bind<TextView>(R.id.tv_day_date)
    }
}