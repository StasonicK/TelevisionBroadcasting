package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eburg_soft.televisionbroadcasting.R
import com.eburg_soft.televisionbroadcasting.customviews.SnappyAdapter
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import com.eburg_soft.televisionbroadcasting.extensions.changeBackgroundColor
import com.eburg_soft.televisionbroadcasting.extensions.elevate
import com.eburg_soft.televisionbroadcasting.extensions.inflate
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.ProgramsSnappyAdapter.ProgramViewHolder
import kotlinx.android.synthetic.main.item_program.view.linear_program_description
import kotlinx.android.synthetic.main.item_program.view.tv_country_and_year_of_production
import kotlinx.android.synthetic.main.item_program.view.tv_genre
import kotlinx.android.synthetic.main.item_program.view.tv_program_name

class ProgramsSnappyAdapter : SnappyAdapter<ProgramViewHolder>() {

    inner class ProgramViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var onClick: OnClick? = null
        var onTouch: OnTouch? = null
        var item: ProgramEntity? = null
        private var rect: Rect? = null
        private var isTouching: Boolean = false

        init {

            view.setOnClickListener {
                val previousItemPosition = selectedItemPosition
                selectedItemPosition = position
                val selectedItem = if (selectedItemPosition != -1) getItemAt(selectedItemPosition) else null
                val previousItem = if (previousItemPosition != -1) getItemAt(previousItemPosition) else null
                onClick?.onClick(
                    previousItem,
                    previousItemPosition,
                    selectedItem,
                    selectedItemPosition
                )

                if (getSnapper()?.getSnappedPosition() !== adapterPosition) {
                    getSnapper()?.smoothSnapToPosition(adapterPosition)
                }
            }
            view.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_MOVE || event.action == MotionEvent.ACTION_UP) {
                    // Construct a rect of the view's bounds
                    rect = Rect(v.left, v.top, v.right, v.bottom)

                    if (!rect?.contains(v.left + event.x.toInt(), v.top + event.y.toInt())!!) {
                        // User moved outside bounds
                        isTouching = false
                        onTouch?.onTouch(isTouching)
                    } else {
                        isTouching = true
                        onTouch?.onTouch(isTouching)
                    }
                }
                false
            }
        }

        fun changeSelectedView(isSelected: Boolean) {
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

        fun bind(item: ProgramEntity) {
            this.item = item
            this.itemView.apply {
                tv_program_name.text = item.name
            }
        }
    }

    private val dataList = arrayListOf<ProgramEntity>()

    fun setData(list: List<ProgramEntity>?) {
        this.dataList.clear()
        list?.let { this.dataList.addAll(it) }
        this.notifyDataSetChanged()
    }

    var selectedItemPosition = -1
    var itemPosition = -1

    private var onClick: OnClick? = null

    private var onTouch: OnTouch? = null

    interface OnClick {

        fun onClick(item0: Any?, positionItem0: Int, item1: Any?, positionItem1: Int)
    }

    interface OnTouch {

        fun onTouch(isTouching: Boolean)
    }

    override fun getItemCount(): Int = dataList.size

    fun getItemAt(position: Int) = dataList[position]

    override fun onBindViewHolder(vh: ProgramViewHolder, position: Int, isAtTheCenter: Boolean) {

        if (isAtTheCenter) {
            itemPosition = selectedItemPosition
            selectedItemPosition = position
            vh.changeSelectedView(true)
        } else {
            vh.changeSelectedView(false)
        }

        vh.onClick = onClick
        vh.onTouch = onTouch
    }

    override fun onSnapedFromCenter(vh: ProgramViewHolder) {
        vh.changeSelectedView(false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        val view = parent.inflate(R.layout.item_day)
        return ProgramViewHolder(view)
    }
}