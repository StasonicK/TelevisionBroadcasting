package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import android.view.View
import android.view.ViewGroup
import com.eburg_soft.televisionbroadcasting.R
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import com.eburg_soft.televisionbroadcasting.extensions.inflate
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.ProgramsRecyclerAdapter.ProgramViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_programs_recycler.view.img_program
import kotlinx.android.synthetic.main.item_programs_recycler.view.tv_program_name

class ProgramsRecyclerAdapter : BaseRecyclerAdapter<ProgramViewHolder, ProgramEntity>() {

    class ProgramViewHolder(view: View) : BaseViewHolder(view) {

        override fun onBind(item: Any?) {
            (item as ProgramEntity).let {
                itemView.apply {
                    tv_program_name.text = it.name
                    Picasso.get()
                        .load(R.drawable.old_tv_white_256)
                        .into(img_program)
                }
            }
        }
    }

//    override fun updateAdapter(updatedList: List<ProgramEntity>) {
//        val result = DiffUtil.calculateDiff(ProgramDiffCallback(list, updatedList))
//        list.addAll(updatedList)
//        result.dispatchUpdatesTo(this)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = parent.inflate(R.layout.item_programs_recycler)
        return ProgramViewHolder(view)
    }
}