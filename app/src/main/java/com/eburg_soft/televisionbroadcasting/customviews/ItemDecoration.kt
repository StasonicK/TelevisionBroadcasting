package com.eburg_soft.televisionbroadcasting.customviews

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.State

/**
 * @param startPadding
 * @param endPadding
 * [.startPadding] and [.endPadding] are final and required on initialization
 * because  [android.support.v7.widget.RecyclerView.ItemDecoration] are drawn
 * before the adapter's child views so you cannot rely on the child view measurements
 * to determine padding as the two are connascent
 *
 * see {@see [](https://en.wikipedia.org/wiki/Connascence_(computer_programming))
 */
class ItemDecoration(
    private val startPadding: Int,
    private val endPadding: Int
) : ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
        val totalWidth = parent.width

        //first element
        if (parent.getChildAdapterPosition(view) == 0) {
            var firstPadding = (totalWidth - startPadding) / 2
            firstPadding = Math.max(0, firstPadding)
            outRect.set(firstPadding, 0, 0, 0)
        }

        //last element
        if (parent.getChildAdapterPosition(view) == parent.adapter!!.itemCount - 1 &&
            parent.adapter!!.itemCount > 1
        ) {
            var lastPadding = (totalWidth - endPadding) / 2
            lastPadding = Math.max(0, lastPadding)
            outRect.set(0, 0, lastPadding, 0)
        }
    }
}