package com.eburg_soft.televisionbroadcasting.customviews

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.State
import kotlin.math.max

/**
 * Class is based on the ItemDecoration example: https://gist.github.com/mandybess/dada043b2e20bf3f9da4
 * @param startPadding
 * @param endPadding
 * [.startPadding] and [.endPadding] are final and required on initialization
 * because  [android.support.v7.widget.RecyclerView.ItemDecoration] are drawn
 * before the adapter's child views so you cannot rely on the child view measurements
 * to determine padding as the two are connascent
 *
 * see {@see [](https://en.wikipedia.org/wiki/Connascence_(computer_programming))
 */
class CustomItemDecoration(
    private val startPadding: Int = 0,
    private val endPadding: Int = 0
) : ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
        val totalWidth = parent.width

        //first element
        if (parent.getChildAdapterPosition(view) == 0) {
            var firstPadding = (totalWidth - startPadding - view.width) / 2
            firstPadding = max(0, firstPadding)
            outRect.set(firstPadding, 0, 0, 0)
        }

        //last element
        if (parent.getChildAdapterPosition(view) == parent.adapter!!.itemCount - 1 &&
            parent.adapter!!.itemCount > 1
        ) {
            var lastPadding = (totalWidth - endPadding - view.width) / 2
            lastPadding = max(0, lastPadding)
            outRect.set(0, 0, lastPadding, 0)
        }
    }
}