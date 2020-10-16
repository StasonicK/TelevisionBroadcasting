package com.eburg_soft.televisionbroadcasting.epoxy.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.eburg_soft.televisionbroadcasting.R
import com.eburg_soft.televisionbroadcasting.core.Constants
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.epoxy.KotlinEpoxyHolder
import com.squareup.picasso.Picasso

@EpoxyModelClass(layout = R.layout.item_channel)
abstract class ChannelHolder : EpoxyModelWithHolder<ChannelHolder.ViewHolder>() {

    @EpoxyAttribute
    lateinit var channelEntity: ChannelEntity

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var listener: (ChannelEntity) -> Unit

    override fun bind(holder: ViewHolder) {
        holder.channelName.text = channelEntity.name
        Picasso.get()
            .load(Constants.BASE_URL + channelEntity.logoUrl)
            .placeholder(R.drawable.old_tv_white_32)
            .error(R.drawable.old_tv_white_32)
            .fit()
            .into(holder.channelIcon)

        holder.container.setOnClickListener { listener.invoke(channelEntity) }
    }

    override fun unbind(holder: ViewHolder) {
        holder.container.setOnClickListener(null)
    }

    class ViewHolder : KotlinEpoxyHolder() {

        val container by bind<View>(R.id.container_channel)
        val channelName by bind<TextView>(R.id.tv_channel_name)
        val channelIcon by bind<ImageView>(R.id.img_channel_icon)
        val channelIconBackground by bind<ImageView>(R.id.linear_channel_img_background)
    }
}