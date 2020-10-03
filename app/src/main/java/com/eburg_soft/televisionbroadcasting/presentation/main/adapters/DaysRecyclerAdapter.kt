package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import android.view.View
import android.view.ViewGroup
import com.eburg_soft.televisionbroadcasting.R
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.DayEntity
import com.eburg_soft.televisionbroadcasting.extensions.inflate
import com.eburg_soft.televisionbroadcasting.presentation.base.BaseRecyclerAdapter
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.DaysRecyclerAdapter.DayViewHolder
import kotlinx.android.synthetic.main.item_days_recycler.view.tv_day_date

class DaysRecyclerAdapter : BaseRecyclerAdapter<DayViewHolder, DayEntity>() {

    class DayViewHolder(view: View) : BaseViewHolder(view) {

        override fun onBind(item: Any?) {
            (item as DayEntity).let {
                itemView.apply {
                    tv_day_date.text = it.date
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = parent.inflate(R.layout.item_days_recycler)
        return DayViewHolder(view)
    }
}