package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import android.view.View
import android.view.ViewGroup
import com.eburg_soft.televisionbroadcasting.R
import com.eburg_soft.televisionbroadcasting.core.Constants
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.extensions.inflate
import com.eburg_soft.televisionbroadcasting.presentation.base.BaseAdapter
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.ChannelsAdapter.ChannelViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_channels_recycler.view.img_channel_icon
import kotlinx.android.synthetic.main.item_channels_recycler.view.tv_channel_name

class ChannelsAdapter : BaseAdapter<ChannelEntity, ChannelViewHolder>(ChannelsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val view = parent.inflate(R.layout.item_channels_recycler)
        return ChannelViewHolder(view)
    }

    class ChannelViewHolder(view: View) : BaseViewHolder(view) {
        init {
        }

        override fun onBind(item: Any) {
            (item as? ChannelEntity)?.let {
                itemView.apply {
                    tv_channel_name.text = item.name
                    Picasso.get()
                        .load(Constants.BASE_URL + item.logoUrl)
                        .placeholder(R.drawable.old_tv_white_32)
                        .error(R.drawable.old_tv_white_32)
                        .fit()
                        .into(img_channel_icon)

                }
            }
        }

        override fun changeSelectedView(isSelected: Boolean) {
            if (isSelected) {
                itemView.apply {
                    tv_channel_name.visibility = View.VISIBLE
                    img_channel_icon.setBackgroundColor(resources.getColor(R.color.blue_light))
                }
            } else {
                itemView.apply {
                    tv_channel_name.visibility = View.INVISIBLE

                    img_channel_icon.setBackgroundColor(resources.getColor(R.color.black))
                }
            }
        }
    }
}