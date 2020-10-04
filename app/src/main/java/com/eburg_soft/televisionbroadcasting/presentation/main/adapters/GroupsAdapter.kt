package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import android.view.View
import android.view.ViewGroup
import com.eburg_soft.televisionbroadcasting.R
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.extensions.inflate
import com.eburg_soft.televisionbroadcasting.presentation.base.BaseAdapter
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.GroupsAdapter.GroupViewHolder
import kotlinx.android.synthetic.main.item_groups_recycler.view.tv_group_name

class GroupsAdapter : BaseAdapter<GroupEntity, GroupViewHolder>(GroupsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = parent.inflate(R.layout.item_channels_recycler)
        return GroupViewHolder(view)
    }

    class GroupViewHolder(view: View) : BaseViewHolder(view) {
        init {
            itemView.setOnClickListener {
                onClick?.onClick(item, it)
            }
        }

        override fun onBind(item: Any) {
            (item as? GroupEntity)?.let {
                itemView.apply {
                    tv_group_name.text = item.name
                }
            }
        }
    }
}