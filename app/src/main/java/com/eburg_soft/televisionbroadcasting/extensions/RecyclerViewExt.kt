package com.eburg_soft.televisionbroadcasting.extensions

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.State

fun RecyclerView.centerItemsInLinearLayout(itemSize: Int, marginSize: Int = 0) {
    this.addItemDecoration(
        object : ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
                val viewHolderWidth = parent.width
                val itemWidth = resources.getDimension(itemSize).toInt()
                val realWidth = state.itemCount * (itemWidth + marginSize) + marginSize
                if (realWidth < viewHolderWidth) {
                    val position = parent.getChildViewHolder(view).adapterPosition
                    if (position == 0 || position == state.itemCount - 1) {
                        val padding: Int = (viewHolderWidth - realWidth) / 2
                        when (position) {
                            0 -> {
                                outRect.left = padding
                            }
                            state.itemCount - 1 -> {
                                outRect.right = padding
                            }
                        }
                    }
                }
            }
        }
    )
}