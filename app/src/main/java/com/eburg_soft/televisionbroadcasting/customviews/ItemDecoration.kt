package com.eburg_soft.televisionbroadcasting.customviews

import android.content.res.Resources
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

class ItemDecoration(
//    private val paddingPx: Int = dpToPx(0),
//    private val viewWidthPx: Int = dpToPx(168),
//    private val startPadding: Int = 0,
//    private val endPadding: Int = 0
    private val margin: Int = 8,
    private val viewWidth: Int = 80
) : RecyclerView.ItemDecoration() {

    private val screenWidthPx = screenWidth()
    private var marginPx = dpToPx(margin)
    private var viewWidthPx = dpToPx(viewWidth)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val position = parent.getChildAdapterPosition(view)
//       val marginPx = dpToPx(padding)
//        val viewWidthPx = dpToPx(viewWidth)
//        val viewWidthPx = dpToPx(view.width)
        val itemCount = parent.adapter?.itemCount ?: 0
        val startMargin = if (position == 0) {
//            paddingPx
//            (screenWidthPx - viewWidthPx) / 2 + paddingPx
            (screenWidthPx - viewWidthPx) / 2
        } else {
            marginPx
        }
        val endMargin = if (position == itemCount - 1) {
//            paddingPx
//            (screenWidthPx - viewWidthPx) / 2 + paddingPx
            (screenWidthPx - viewWidthPx) / 2
        } else {
            marginPx
        }
        Timber.d("screenWidth: $screenWidthPx")
        Timber.d("viewWidth: $viewWidth")
        Timber.d("viewWidthPx: $viewWidthPx")
        Timber.d("padding: $margin")
        Timber.d("paddingPx: $marginPx")
        Timber.d("position: $position")
        Timber.d("startMargin: $startMargin")
        Timber.d("endMargin: $endMargin")
        Timber.d("/////////////////////////")
        outRect.set(startMargin, 0, endMargin, 0)

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

//        val totalWidth = parent.width
//
//        //first element
//        if (parent.getChildAdapterPosition(view) === 0) {
//            var firstPadding: Int = (totalWidth - startPadding) / 2
//            firstPadding = Math.max(0, firstPadding)
//            outRect[firstPadding, 0, 0] = 0
//        }
//
//        //last element
//        if (parent.getChildAdapterPosition(view) === parent.adapter!!.itemCount - 1 &&
//            parent.adapter!!.itemCount > 1
//        ) {
//            var lastPadding: Int = (totalWidth - endPadding) / 2
//            lastPadding = Math.max(0, lastPadding)
//            outRect[0, 0, lastPadding] = 0
//        }
    }
}

fun screenWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}

fun screenHeight(): Int {
    return Resources.getSystem().displayMetrics.heightPixels
}

fun dpToPx(dp: Int): Int {
//    return (dp * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), Resources.getSystem().displayMetrics)
        .toInt()
}