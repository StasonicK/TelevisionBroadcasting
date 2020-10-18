package com.eburg_soft.televisionbroadcasting.customviews

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

class ItemDecoration(
    private val viewWidth: Int = 0,
    private val padding: Int = 16
//    private val viewWidth: Int = 0,
//    private val padding: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
//
//        val totalWidth = parent.width
//
//        when (parent.getChildAdapterPosition(view)) {
//            //first element
//            0 -> {
//                val firstPadding = (totalWidth - viewWidth) / 2
//                outRect.set(firstPadding, 0, padding, 0)
//            }
//            //last element
//            parent.adapter?.itemCount!! - 1 -> {
//                val lastPadding = (totalWidth - viewWidth) / 2
//                outRect.set(padding, 0, lastPadding, 0);
//            }
//            else -> {
//                outRect.set(padding, 0, padding, 0)
//            }
//        }
//        if (position == 0) {
//            var firstPadding = (totalWidth - padding - viewWidth) / 2
//            firstPadding = max(0, firstPadding)
//            outRect.set(firstPadding, 0, 0, 0)
//        }
//
//        //last element
//        if (position == parent.adapter?.itemCount!! - 1 &&
//            parent.adapter?.itemCount!! > 1
//        ) {
//            var lastPadding = (totalWidth - padding - viewWidth) / 2
//            lastPadding = max(0, lastPadding);
//            outRect.set(0, 0, lastPadding, 0);
//        }

        val screenWidthPx = screenWidth()
        val position = parent.getChildAdapterPosition(view)
        val viewWidthPx = dpToPx(view.measuredWidth)
        val paddingPx = dpToPx(padding)
        val itemCount = parent.adapter?.itemCount ?: 0
        val startMargin = if (position == 0) {
            (screenWidthPx - viewWidthPx) / 2
        } else {
            paddingPx
        }
        val endMargin = if (position == itemCount - 1) {
            (screenWidthPx - viewWidthPx) / 2
        } else {
            paddingPx
        }
        outRect.set(startMargin, 0, endMargin, 0)

        Timber.d("screenWidth: $screenWidthPx")
        Timber.d("viewWidthPx: $viewWidthPx")
        Timber.d("paddingPx: $paddingPx")
        Timber.d("position: $position")
        Timber.d("startMargin: $startMargin")
        Timber.d("endMargin: $endMargin")

//        val screenWidth = parent.width
//        val itemWidth = view.width
//        val listWidth = state.itemCount * (itemWidth + marginSize) + marginSize
//        val position = parent.getChildViewHolder(view).adapterPosition
//        if (position == 0 || position == state.itemCount - 1) {
////                if (listWidth < screenWidth) {
////                    if (position == 0 || position == state.itemCount - 1) {
////                        val padding: Int = (screenWidth - listWidth) / 2
////                        when (position) {
////                            0 -> {
////                                outRect.left = padding
////                            }
////                            state.itemCount - 1 -> {
////                                outRect.right = padding
////                            }
////                        }
////                    }
////                }
////                else {
//            val padding: Int = screenWidth / 2
//            when (position) {
//                0 -> {
//                    outRect.left = padding
//                }
//                state.itemCount - 1 -> {
//                    outRect.right = padding
//                }
//            }
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
    return (dp * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}