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

//    companion object {
//
//        var selectedItemPosition = -1
//        var itemPosition = -1
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = parent.inflate(R.layout.item_groups_recycler)
        return GroupViewHolder(view)
    }

    class GroupViewHolder(view: View) : BaseViewHolder(view) {

        init {
//            if (selectedItemPosition == position) this.changeSelectedView(true) else this.changeSelectedView(false)

//            itemView.setOnClickListener {
//                onClick?.onClick(item, it)
//
//                val previousItemPosition = selectedItemPosition
//                selectedItemPosition = position
//
//                val list = currentList
//                list.add(previousItemPosition, getItem(previousItemPosition))
//                list.add(selectedItemPosition, getItem(selectedItemPosition))
//                list.add(holder.adapterPosition, getItem(holder.adapterPosition))
//                submitList(list)
//
//                itemPosition = position
//            }
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
                    changeBackgroundColor(R.color.black_transparent)
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