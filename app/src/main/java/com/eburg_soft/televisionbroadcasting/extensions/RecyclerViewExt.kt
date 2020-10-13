package com.eburg_soft.televisionbroadcasting.extensions

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.State
import timber.log.Timber

fun RecyclerView.centerItemsInLinearLayout(itemSize: Int, marginSize: Int = 0) {
    this.addItemDecoration(
        object : ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
                val viewHolderWidth = parent.measuredWidth
                Timber.d("viewHolderWidth $this: $viewHolderWidth")
                val itemWidth = resources.getDimension(itemSize).toInt()
                Timber.d("itemWidth $this: $itemWidth")
                val realWidth = state.itemCount * (itemWidth + marginSize) + marginSize
                Timber.d("realWidth $this: $realWidth")
                if (realWidth < viewHolderWidth) {
                    val position = parent.getChildViewHolder(view).adapterPosition
                    if (position == 0 || position == state.itemCount - 1) {
                        val padding: Int = (viewHolderWidth - realWidth) / 2
                        Timber.d("padding $this: $padding")
                        when (position) {
                            0 -> {
                                outRect.left = padding // state.itemCount
                            }
                            state.itemCount - 1 -> {
                                outRect.right = padding // state.itemCount
                            }
                        }
                    }
                }
            }
        }
    )
}