package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import android.view.View
import android.view.ViewGroup
import com.eburg_soft.televisionbroadcasting.R
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import com.eburg_soft.televisionbroadcasting.extensions.changeBackgroundColor
import com.eburg_soft.televisionbroadcasting.extensions.elevate
import com.eburg_soft.televisionbroadcasting.extensions.inflate
import com.eburg_soft.televisionbroadcasting.presentation.base.BaseAdapter
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.ProgramsAdapter.ProgramViewHolder
import kotlinx.android.synthetic.main.item_program.view.linear_program_description
import kotlinx.android.synthetic.main.item_program.view.tv_country_and_year_of_production
import kotlinx.android.synthetic.main.item_program.view.tv_genre
import kotlinx.android.synthetic.main.item_program.view.tv_program_name

class ProgramsAdapter : BaseAdapter<ProgramEntity, ProgramViewHolder>(ProgramsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        val view = parent.inflate(R.layout.item_program)
        return ProgramViewHolder(view)
    }

    class ProgramViewHolder(view: View) : BaseViewHolder(view) {
        init {
        }

        override fun onBind(item: Any) {
            (item as? ProgramEntity)?.let {
                itemView.apply {
                    tv_program_name.text = item.name
                }
            }
        }

        override fun changeSelectedView(isSelected: Boolean) {
            if (isSelected) {
                itemView.apply {
                    linear_program_description.changeBackgroundColor(R.color.blue)
                    tv_country_and_year_of_production.setTextColor(resources.getColor(R.color.blue_light))
                    tv_genre.setTextColor(resources.getColor(R.color.blue_light))
                    elevate(true)
//                    clipToOutline = false
                }
            } else {
                itemView.apply {
                    linear_program_description.changeBackgroundColor(R.color.black)
                    tv_country_and_year_of_production.setTextColor(resources.getColor(R.color.grey_light))
                    tv_genre.setTextColor(resources.getColor(R.color.grey_light))
                    elevate(false)
                }
            }
        }
    }
}