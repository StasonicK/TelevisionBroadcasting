package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.eburg_soft.televisionbroadcasting.R
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.extensions.inflate
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.GroupsRecyclerAdapter.GroupViewHolder
import kotlinx.android.synthetic.main.item_groups_recycler.view.tv_group_name

class GroupsRecyclerAdapter : BaseRecyclerAdapter<GroupViewHolder, GroupEntity>() {

    class GroupViewHolder(view: View) : BaseViewHolder(view) {

        override fun onBind(item: Any?) {
            (item as GroupEntity).let {
                itemView.apply {
                    tv_group_name.text = it.name
                }
            }
        }
    }

//    override fun updateAdapter(updatedList: List<GroupEntity>) {
//        val result = DiffUtil.calculateDiff(GroupDiffCallback(list, updatedList))
//        list.clear()
//        list.addAll(updatedList)
//        result.dispatchUpdatesTo(this)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = parent.inflate(R.layout.item_groups_recycler)
        return GroupViewHolder(view)
    }
}