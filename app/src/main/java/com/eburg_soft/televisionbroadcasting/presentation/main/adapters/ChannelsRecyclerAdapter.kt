package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import android.view.View
import android.view.ViewGroup
import com.eburg_soft.televisionbroadcasting.R
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.extensions.inflate
import com.eburg_soft.televisionbroadcasting.presentation.base.BaseRecyclerAdapter
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.ChannelsRecyclerAdapter.ChannelViewHolder
import kotlinx.android.synthetic.main.item_channels_recycler.view.tv_channel_name

class ChannelsRecyclerAdapter : BaseRecyclerAdapter<ChannelViewHolder, ChannelEntity>() {

    class ChannelViewHolder(view: View) : BaseViewHolder(view) {

        override fun onBind(item: Any?) {
            (item as ChannelEntity).let {
                itemView.apply {
                    tv_channel_name.text = item.name
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = parent.inflate(R.layout.item_channels_recycler)
        return ChannelViewHolder(view)
    }

//    override fun updateAdapter(updatedList: List<ChannelEntity>) {
//        val result = DiffUtil.calculateDiff(ChannelsDiffCallback(list, updatedList))
//        list.addAll(updatedList)
//        result.dispatchUpdatesTo(this)
//    }
}