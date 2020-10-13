package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import android.view.View
import android.view.ViewGroup
import com.eburg_soft.televisionbroadcasting.R
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.DayEntity
import com.eburg_soft.televisionbroadcasting.extensions.changeBackgroundColor
import com.eburg_soft.televisionbroadcasting.extensions.inflate
import com.eburg_soft.televisionbroadcasting.presentation.base.BaseAdapter
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.DaysAdapter.DayViewHolder
import kotlinx.android.synthetic.main.item_days_recycler.view.tv_day_date

class DaysAdapter : BaseAdapter<DayEntity, DayViewHolder>(DaysDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = parent.inflate(R.layout.item_days_recycler)
        return DayViewHolder(view)
    }

    class DayViewHolder(view: View) : BaseViewHolder(view) {
        init {
//            itemView.setOnClickListener {
//                onClick?.onClick(item, it)
//            }
        }

        override fun onBind(item: Any) {
            (item as? DayEntity)?.let {
                itemView.apply {
                    tv_day_date.text = item.date
                }
            }
        }

        override fun changeSelectedView(isSelected: Boolean) {
            if (isSelected) {
                itemView.apply {
                    tv_day_date.setTextColor(resources.getColor(R.color.white))
                    changeBackgroundColor(R.color.blue)
                }
            } else {
                itemView.apply {
                    tv_day_date.setTextColor(resources.getColor(R.color.grey_light))
                    changeBackgroundColor(R.color.black)
                }
            }
        }
    }
}