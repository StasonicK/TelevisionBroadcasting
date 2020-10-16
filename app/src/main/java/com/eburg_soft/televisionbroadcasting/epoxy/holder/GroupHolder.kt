package com.eburg_soft.televisionbroadcasting.epoxy.holder

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.eburg_soft.televisionbroadcasting.R
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.epoxy.KotlinEpoxyHolder
import com.eburg_soft.televisionbroadcasting.epoxy.holder.GroupHolder.ViewHolder

@EpoxyModelClass(layout = R.layout.item_group)
abstract class GroupHolder : EpoxyModelWithHolder<ViewHolder>() {

    @EpoxyAttribute
    lateinit var groupEntity: GroupEntity

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var listener: (GroupEntity) -> Unit

    override fun bind(holder: ViewHolder) {
        holder.groupName.text = groupEntity.name
        holder.container.setOnClickListener { listener.invoke(groupEntity) }
    }

    override fun unbind(holder: ViewHolder) {
        holder.container.setOnClickListener(null)
    }

    class ViewHolder : KotlinEpoxyHolder() {

        val container by bind<View>(R.id.container_group)
        val groupName by bind<TextView>(R.id.tv_group_name)
    }
}