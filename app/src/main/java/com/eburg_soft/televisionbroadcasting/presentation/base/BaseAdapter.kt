package com.eburg_soft.televisionbroadcasting.presentation.base

import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<Item : Any, VH : BaseAdapter.BaseViewHolder>(
    diff: BaseDiffCallback<Item>
) : ListAdapter<Item, VH>(diff) {

    private var onClick: OnClick? = null

    private var onTouch: OnTouch? = null

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))

        holder.onClick = onClick
        holder.onTouch = onTouch
    }

    fun setOnClick(click: (Any?, View) -> Unit) {
        onClick = object : OnClick {
            override fun onClick(item: Any?, view: View) {
                click(item, view)
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

        fun onClick(item: Any?, view: View)
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
            view.setOnClickListener {
                onClick?.onClick(item, it)
            }

            view.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_MOVE || event.action == MotionEvent.ACTION_UP) {
                    // Construct a rect of the view's bounds
                    rect = Rect(v.left, v.top, v.right, v.bottom)
//                    rect = Rect()

                    if (!rect?.contains(v.left + event.x.toInt(), v.top + event.y.toInt())!!) {
                        // User moved outside bounds
                        isTouching = false
                        onTouch?.onTouch(isTouching)
                    } else {
                        isTouching = true
                        onTouch?.onTouch(isTouching)
                    }
                }
//                if (event.action === MotionEvent.ACTION_MOVE) {
//                    if (!rect?.contains(v.left + event.x.toInt(), v.top + event.y.toInt())!!) {
//                        // User moved outside bounds
//                        isTouching = false
//                        onTouch?.onTouch(isTouching)
//                    }
//                }
                false
            }
        }

        protected abstract fun onBind(item: Any)

        fun bind(item: Any) {
            this.item = item

            onBind(item)
        }

//        private var outRect: Rect = Rect()
//        private var location = IntArray(2)
//
//        private fun isViewInBounds(view: View, x: Int, y: Int): Boolean {
//            view.getDrawingRect(outRect)
//            view.getLocationOnScreen(location)
//            outRect.offset(location[0], location[1])
//            return outRect.contains(x, y)
//        }
    }
}