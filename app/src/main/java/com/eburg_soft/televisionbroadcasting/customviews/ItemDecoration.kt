package com.eburg_soft.televisionbroadcasting.customviews

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max

class ItemDecoration(
//    private val paddingPx: Int = dpToPx(12),
//    private val viewWidthPx: Int = dpToPx(120),
    private val startPadding: Int = 0,
    private val endPadding: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
//        val position = parent.getChildAdapterPosition(view)
//        val itemCount = parent.adapter?.itemCount ?: 0
//        val startMargin = if (position == 0) {
//            (screenWidth() - viewWidthPx) / 2
//        } else {
//            paddingPx
//        }
//        val endMargin = if (position == itemCount - 1) {
//            (screenWidth() - viewWidthPx) / 2
//        } else {
//            paddingPx
//        }
//        outRect.set(startMargin, 0, endMargin, 0)

        val totalWidth = parent.width

        //first element
        if (parent.getChildAdapterPosition(view) === 0) {
            var firstPadding: Int = (totalWidth - startPadding) / 2
            firstPadding = max(0, firstPadding)
            outRect[firstPadding, 0, 0] = 0
        }

        //last element
        if (parent.getChildAdapterPosition(view) === parent.adapter!!.itemCount - 1 &&
            parent.adapter!!.itemCount > 1
        ) {
            var lastPadding: Int = (totalWidth - endPadding) / 2
            lastPadding = max(0, lastPadding)
            outRect[0, 0, lastPadding] = 0
        }
    }
}

fun screenWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}

fun screenHeight(): Int {
    return Resources.getSystem().displayMetrics.heightPixels
}

fun dpToPx(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}