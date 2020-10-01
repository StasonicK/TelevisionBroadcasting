package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import android.view.View
import android.view.ViewGroup
import com.eburg_soft.televisionbroadcasting.R
import com.eburg_soft.televisionbroadcasting.core.BaseAdapter
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import com.eburg_soft.televisionbroadcasting.extensions.inflate
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.ProgramsAdapter.ProgramViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_programs_recycler.view.img_program
import kotlinx.android.synthetic.main.item_programs_recycler.view.tv_program_name

class ProgramsAdapter : BaseAdapter<ProgramEntity, ProgramViewHolder>(ProgramsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        val view = parent.inflate(R.layout.item_programs_recycler)
        return ProgramViewHolder(view)
    }

    class ProgramViewHolder(view: View) : BaseViewHolder(view) {

        override fun onBind(item: Any) {
            (item as? ProgramEntity)?.let {
                itemView.apply {
                    tv_program_name.text = item.name
                    Picasso.get()
                        .load(R.drawable.old_tv_white_256)
                        .fit()
                        .into(img_program)
                }
            }
        }
    }
}