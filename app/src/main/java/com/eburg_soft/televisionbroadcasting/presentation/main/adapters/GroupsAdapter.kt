package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.eburg_soft.televisionbroadcasting.R
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.extensions.inflate
import com.eburg_soft.televisionbroadcasting.presentation.base.BaseAdapter
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.GroupsAdapter.GroupViewHolder
import kotlinx.android.synthetic.main.item_groups_recycler.view.tv_group_name

class GroupsAdapter : BaseAdapter<GroupEntity, GroupViewHolder>(GroupsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = parent.inflate(R.layout.item_groups_recycler)
        if (parent.isFocusable) {

        }
        return GroupViewHolder(view)
    }



    class GroupViewHolder(view: View) : BaseViewHolder(view) {
        init {
            itemView.setOnClickListener {
                onClick?.onClick(item, it)
            }
//            itemView.setOnFocusChangeListener { view, b ->
//                if (b) {
//                    val anim = AnimationUtils.loadAnimation(itemView.context, R.anim.scale_in_program)
//                    view.startAnimation(anim)
//                    anim.fillAfter = true
//                } else {
//                    val anim = AnimationUtils.loadAnimation(itemView.context, R.anim.scale_out_program)
//                    view.startAnimation(anim)
//                    anim.fillAfter = true
//                }
//            }
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