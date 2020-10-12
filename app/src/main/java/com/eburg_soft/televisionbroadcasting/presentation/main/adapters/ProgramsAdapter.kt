package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import android.view.View
import android.view.ViewGroup
import com.eburg_soft.televisionbroadcasting.R
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import com.eburg_soft.televisionbroadcasting.extensions.inflate
import com.eburg_soft.televisionbroadcasting.presentation.base.BaseAdapter
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.ProgramsAdapter.ProgramViewHolder
import kotlinx.android.synthetic.main.item_programs_recycler.view.tv_program_name

class ProgramsAdapter : BaseAdapter<ProgramEntity, ProgramViewHolder>(ProgramsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        val view = parent.inflate(R.layout.item_programs_recycler)
        return ProgramViewHolder(view)
    }

    class ProgramViewHolder(view: View) : BaseViewHolder(view) {
        init {
            itemView.setOnClickListener {
                onClick?.onClick(item, it)
            }
        }

        override fun onBind(item: Any) {
            (item as? ProgramEntity)?.let {
                itemView.apply {
                    tv_program_name.text = item.name
                }
            }
        }

//        override fun changeSelectedView(isSelected: Boolean) {
//            TODO("Not yet implemented")
//        }
    }
}