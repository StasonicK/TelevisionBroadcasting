package com.eburg_soft.televisionbroadcasting.extensions

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.State
import com.eburg_soft.televisionbroadcasting.presentation.base.BaseAdapter
import com.eburg_soft.televisionbroadcasting.presentation.base.BaseAdapter.BaseViewHolder

fun RecyclerView.centerListInLinearLayout(itemSize: Int, marginSize: Int = 0) {
    this.addItemDecoration(
        object : ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
                val screenWidth = parent.width
                val itemWidth = resources.getDimension(itemSize).toInt()
                val listWidth = state.itemCount * (itemWidth + marginSize) + marginSize
                val position = parent.getChildViewHolder(view).adapterPosition
//                if (listWidth < screenWidth) {
//                    if (position == 0 || position == state.itemCount - 1) {
//                        val padding: Int = (screenWidth - listWidth) / 2
//                        when (position) {
//                            0 -> {
//                                outRect.left = padding
//                            }
//                            state.itemCount - 1 -> {
//                                outRect.right = padding
//                            }
//                        }
//                    }
//                }
//                else {
                    if (position == 0 || position == state.itemCount - 1) {
                        val padding: Int = screenWidth / 2
                        when (position) {
                            0 -> {
                                outRect.left = padding
                            }
                            state.itemCount - 1 -> {
                                outRect.right = padding
                            }
                        }
                    }
//                }
            }
        }
    )
}

// implementation of method that is called from OnScrollListener
fun RecyclerView.selectMiddleItem(
    recyclerView: RecyclerView,
    context: Context,
    adapter: BaseAdapter<out Any, out BaseViewHolder>
) {
    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
    val firstVisibleIndex = layoutManager.findFirstVisibleItemPosition()
    val lastVisibleIndex = layoutManager.findLastVisibleItemPosition()
    val visibleIndexes = listOf(firstVisibleIndex..lastVisibleIndex).flatten()

    for (i in visibleIndexes) {
        val vh = recyclerView.findViewHolderForLayoutPosition(i)
        if (vh?.itemView == null) {
            continue
        }
        val location = IntArray(2)
        vh.itemView.getLocationOnScreen(location)
        val x = location[0]
        val halfWidth = vh.itemView.width * .5
        val rightSide = x + halfWidth
        val leftSide = x - halfWidth

        val displayMetrics = context.resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels / displayMetrics.density
        val isInMiddle = screenWidth * .5 in leftSide..rightSide
        if (isInMiddle) {
            // "i" is your middle index and implement selecting it as you want
            adapter.selectItemAtIndex(i)
            return
        }
    }
}

//fun RecyclerView.setItemToCenter(){
//    val layoutManager = (this.layoutManager) as LinearLayoutManager
//    val totalVisibleItems = layoutManager.findLastVisibleItemPosition() - layoutManager.findFirstVisibleItemPosition()
//    val centeredItemPosition = totalVisibleItems / 2
////        recycler_group_list.smoothScrollToPosition(position)
//    this.scrollY = centeredItemPosition
//}

//fun getRecyclerviewDate() {
//    recyclerViewDate = findViewById(id.recyclerViewDay) as RecyclerView
//    val vtoDate: ViewTreeObserver = recyclerViewDate.getViewTreeObserver()
//    vtoDate.addOnPreDrawListener(object : OnPreDrawListener {
//        override fun onPreDraw(): Boolean {
//            recyclerViewDate.getViewTreeObserver().removeOnPreDrawListener(this)
//            finalWidthDate = recyclerViewDate.getMeasuredWidth()
//            itemWidthDate = getResources().getDimension(dimen.item_dob_width)
//            paddingDate = (finalWidthDate - itemWidthDate) / 2
//            firstItemWidthDate = paddingDate
//            allPixelsDate = 0
//            val dateLayoutManager = LinearLayoutManager(getApplicationContext())
//            dateLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
//            recyclerViewDate.setLayoutManager(dateLayoutManager)
//            recyclerViewDate.addOnScrollListener(object : OnScrollListener() {
//                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                    super.onScrollStateChanged(recyclerView, newState)
//                    synchronized(this) {
//                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                            calculatePositionAndScrollDate(recyclerView)
//                        }
//                    }
//                }
//
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    super.onScrolled(recyclerView, dx, dy)
//                    allPixelsDate += dx
//                }
//            })
//            if (labelerDates == null) labelerDates = ArrayList()
//            labelerDates.addAll(genLabelerDate(currentMonth, currentYear))
//            dateAdapter = DateAdapter(labelerDates, firstItemWidthDate as Int)
//            recyclerViewDate.setAdapter(dateAdapter)
//            return true
//        }
//    })
//}