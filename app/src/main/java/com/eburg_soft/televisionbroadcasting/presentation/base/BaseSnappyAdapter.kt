package com.eburg_soft.televisionbroadcasting.presentation.base

import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.eburg_soft.televisionbroadcasting.customviews.SnappyAdapter
import com.eburg_soft.televisionbroadcasting.presentation.base.BaseSnappyAdapter.BaseViewHolder

abstract class BaseSnappyAdapter<Item : Any> : SnappyAdapter<BaseViewHolder>() {

    private val dataList = arrayListOf<Item>()

    fun setData(list: List<Item>?) {
        dataList.clear()
        list?.let { dataList.addAll(it) }
        this.notifyDataSetChanged()
    }

    protected var selectedItemPosition = -1
    protected var itemPosition = -1

    private var onClick: OnClick? = null

    private var onTouch: OnTouch? = null

    interface OnClick {

        fun onClick(item0: Any?, positionItem0: Int, item1: Any?, positionItem1: Int)
    }

    interface OnTouch {

        fun onTouch(isTouching: Boolean)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int, isAtTheCenter: Boolean) {
        holder.apply {
            bind(dataList[position])

        }

        if (isAtTheCenter) holder.changeSelectedView(true) else holder.changeSelectedView(false)

        holder.onClick = onClick
        holder.onTouch = onTouch

//        if (selectedItemPosition == position) viewHolder.changeSelectedView(true) else viewHolder.changeSelectedView(false)

        holder.itemView.apply {
//            setOnClickListener {
//                val previousItemPosition = selectedItemPosition
//                selectedItemPosition = position
//                val selectedItem = if (selectedItemPosition != -1) getItemAt(selectedItemPosition) else null
//                val previousItem = if (previousItemPosition != -1) getItemAt(previousItemPosition) else null
//                onClick?.onClick(
//                    previousItem,
//                    previousItemPosition,
//                    selectedItem,
//                    selectedItemPosition
//                )
//
//                if (snapper.snappedPosition!=this@BaseSnappyAdapter.)
//            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    override fun onSnapedFromCenter(p0: BaseViewHolder) {
        TODO("Not yet implemented")
    }

    fun getItemAt(position: Int) = dataList[position]

    class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        var onClick: OnClick? = null
        var onTouch: OnTouch? = null
        var item: Any? = null
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

                if (snapper.snappedPosition != adapterPosition) {
                    snapp
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

        protected abstract fun onBind(item: Any)

        abstract fun changeSelectedView(isSelected: Boolean)

        fun bind(item: Any) {
            this.item = item

            onBind(item)
        }
    }
}