package com.eburg_soft.televisionbroadcasting.presentation.base

import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<Item : Any, VH : BaseAdapter.BaseViewHolder>(
    diff: BaseDiffCallback<Item>
) : ListAdapter<Item, VH>(diff) {

    private var selectedItemPosition = -1
    private var itemPosition = -1

    private var onClick: OnClick? = null

    private var onTouch: OnTouch? = null

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))

        holder.onClick = onClick
        holder.onTouch = onTouch

        if (selectedItemPosition == position) holder.changeSelectedView(true) else holder.changeSelectedView(false)

        holder.itemView.apply {
            setOnClickListener {
                val previousItemPosition = selectedItemPosition
                selectedItemPosition = position
                val selectedItem = if (selectedItemPosition != -1) getItem(selectedItemPosition) else null
                val previousItem = if (previousItemPosition != -1) getItem(previousItemPosition) else null
                onClick?.onClick(
                    previousItem,
                    previousItemPosition,
                    selectedItem,
                    selectedItemPosition
                )

//                notifyItemChanged(previousItemPosition)
//                notifyItemChanged(position)
            }
        }
//        itemPosition = position
    }

    //    fun setOnClick(click: (Any?, View) -> Unit) {
    fun setOnClick(click: (Any?, Int, Any?, Int) -> Unit) {
        onClick = object : OnClick {
            //            override fun onClick(item: Any?, view: View) {
//                click(item, view)
            override fun onClick(item0: Any?, positionItem0: Int, item1: Any?, positionItem1: Int) {
                click(item0, positionItem0, item1, positionItem1)
            }
        }
    }

    fun setOnTouch(touch: (Boolean) -> Unit) {
        onTouch = object : OnTouch {
            override fun onTouch(isTouching: Boolean) {
                touch(isTouching)
            }
        }
    }

    interface OnClick {

        fun onClick(item0: Any?, positionItem0: Int, item1: Any?, positionItem1: Int)
    }

    interface OnTouch {

        fun onTouch(isTouching: Boolean)
    }

    abstract class BaseViewHolder(protected val view: View) : RecyclerView.ViewHolder(view) {

        var onClick: OnClick? = null
        var onTouch: OnTouch? = null
        var item: Any? = null
        private var rect: Rect? = null
        private var isTouching: Boolean = false

        init {
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