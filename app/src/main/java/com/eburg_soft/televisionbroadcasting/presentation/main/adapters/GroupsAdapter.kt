package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import android.view.View
import android.view.ViewGroup
import com.eburg_soft.televisionbroadcasting.R
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.extensions.changeBackgroundColor
import com.eburg_soft.televisionbroadcasting.extensions.inflate
import com.eburg_soft.televisionbroadcasting.presentation.base.BaseAdapter
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.GroupsAdapter.GroupViewHolder
import kotlinx.android.synthetic.main.item_groups_recycler.view.tv_group_name

class GroupsAdapter : BaseAdapter<GroupEntity, GroupViewHolder>(GroupsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = parent.inflate(R.layout.item_groups_recycler)
        return GroupViewHolder(view)
    }

    class GroupViewHolder(view: View) : BaseViewHolder(view) {
        init {
        }

        override fun onBind(item: Any) {
            (item as? GroupEntity)?.let {
                itemView.apply {
                    tv_group_name.text = item.name
                }
            }
        }

        override fun changeSelectedView(isSelected: Boolean) {
            if (isSelected) {
                itemView.apply {
                    tv_group_name.apply {
                        setTextColor(resources.getColor(R.color.white))
                    }
                    changeBackgroundColor(R.color.black_light)
                }
            } else {
                itemView.apply {
                    tv_group_name.apply {
                        setTextColor(resources.getColor(R.color.grey_light))
                    }
                    changeBackgroundColor(R.color.black)
                }
            }
        }
    }
}