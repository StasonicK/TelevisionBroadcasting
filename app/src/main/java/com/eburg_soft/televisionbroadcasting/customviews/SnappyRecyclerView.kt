package com.eburg_soft.televisionbroadcasting.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.eburg_soft.televisionbroadcasting.customviews.SnappyAdapter.MSnapper
import com.eburg_soft.televisionbroadcasting.customviews.SnappyAdapter.Snapper
import com.eburg_soft.televisionbroadcasting.customviews.SnappyRecyclerView.Behavior.NOTIFY_ON_IDLE_AND_NO_POSITION
import com.eburg_soft.televisionbroadcasting.customviews.SnappyRecyclerView.Behavior.NOTIFY_ON_SCROLL

class SnappyRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
    RecyclerView(context, attrs, defStyle), Snapper, MSnapper {

    private var mSnapHelper = LinearSnapHelper()
    private var mBehavior = NOTIFY_ON_IDLE_AND_NO_POSITION
    private var mAdapter: SnappyAdapter<*>? = null
    private var mIsPaddingSet = false

    /**
     * @return true if padding has been aplied and items centered
     */
    var isPaddingApplied = false
        private set
    private var mIsScrollEnabled = false
    private var mItemSize = 0
    private var mEdgeMargin = 0
    private var mCurentPosition = NO_POSITION
    fun setCustomSnapHelper(snapHelper: LinearSnapHelper) {
        mSnapHelper.attachToRecyclerView(null)
        mSnapHelper = snapHelper
        mSnapHelper.attachToRecyclerView(this)
    }

    /**
     * Sets required padding to place items in the center
     *
     * @param itemSize   height (if orientation is vertical) or weight (if orientation is horizontal) of the item
     * @param edgeMargin edge margin (e.g. left for vertical and top for horizontal orientation) of the item
     * @param position   initial position of the centered item (usually position = 0)
     */
    fun setCenteringPadding(itemSize: Int, edgeMargin: Int, position: Int) {
        clipChildren = false
        clipToPadding = false
        mItemSize = itemSize
        mEdgeMargin = edgeMargin
        mCurentPosition = position
        mIsPaddingSet = true
    }

    /**
     * Resets required padding to place items in the center
     *
     * @param itemSize   height (if orientation is vertical) or weight (if orientation is horizontal) of the item
     * @param edgeMargin edge margin (e.g. left for vertical and top for horizontal orientation) of the item
     * @param position   initial position of the centered item (usually position = 0)
     */
    fun resetCenteringPadding(itemSize: Int, edgeMargin: Int, position: Int) {
        setCenteringPadding(itemSize, edgeMargin, position)
        isPaddingApplied = false
        requestLayout()
    }

    /**
     * Sets required padding to place items in the center. Using view to obtain params
     *
     * @param target   the view that is identical to item view
     * @param position initial position of the centered item (usually position = 0)
     */
    fun setCenteringPadding(target: View, position: Int) {
        target.visibility = INVISIBLE
        target.post(Runnable {
            if (layoutManager == null) return@Runnable
            when ((layoutManager as LinearLayoutManager?)!!.orientation) {
                HORIZONTAL -> {
                    mItemSize = target.width
                    mEdgeMargin = (target.layoutParams as android.widget.FrameLayout.LayoutParams).leftMargin
                }
                VERTICAL -> {
                    mItemSize = target.height
                    mEdgeMargin = (target.layoutParams as android.widget.FrameLayout.LayoutParams).topMargin
                }
            }
            clipChildren = false
            clipToPadding = false
            mCurentPosition = position
            mIsPaddingSet = true
            target.visibility = GONE
        })
    }

    /**
     * Resets required padding to place items in the center. Using view to obtain params
     *
     * @param target   the view that is identical to item view
     * @param position initial position of the centered item (usually position = 0)
     */
    fun resetCenteringPadding(target: View, position: Int) {
        target.visibility = INVISIBLE
        target.post(object : Runnable {
            override fun run() {
                if (layoutManager == null) return
                when ((layoutManager as LinearLayoutManager?)!!.orientation) {
                    HORIZONTAL -> {
                        mItemSize = target.width
                        mEdgeMargin = (target.layoutParams as android.widget.FrameLayout.LayoutParams).leftMargin
                    }
                    VERTICAL -> {
                        mItemSize = target.height
                        mEdgeMargin = (target.layoutParams as android.widget.FrameLayout.LayoutParams).topMargin
                    }
                }
                clipChildren = false
                clipToPadding = false
                mCurentPosition = position
                mIsPaddingSet = true
                target.visibility = GONE
                isPaddingApplied = false
                requestLayout()
            }
        })
    }

    override fun setLayoutManager(layout: LayoutManager?) {
        super.setLayoutManager(layout)
        if (mIsScrollEnabled) enableSnapListener()
    }

    private fun enableSnapListener() {
        mIsScrollEnabled = false
        enableSnapListener(mBehavior)
    }

    /**
     * Enables scroll listener that will notify on snapped item change
     */
    fun enableSnapListener(behavior: Behavior) {
        if (mIsScrollEnabled) return
        mIsScrollEnabled = true
        mBehavior = behavior
        if (layoutManager == null) return
        val isVertical = (layoutManager as LinearLayoutManager?)
            .getOrientation() == VERTICAL
        when (behavior) {
            NOTIFY_ON_SCROLL -> addOnScrollListener(object : OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dx != 0 || dy != 0) onItemCentered()
                }
            })
            else -> addOnScrollListener(object : OnScrollListener() {
                var hasBeenDragged = false
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    when (newState) {
                        SCROLL_STATE_IDLE -> {
                            hasBeenDragged = false
                            onItemCentered()
                        }
                        SCROLL_STATE_DRAGGING -> if (mBehavior == NOTIFY_ON_IDLE_AND_NO_POSITION) hasBeenDragged =
                            true
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dx == 0 && dy == 0) return
                    if (hasBeenDragged && mCurentPosition != NO_POSITION) {
                        hasBeenDragged = false
                        onNoItemCentered()
                    } else if (isVertical) {
                        if (!canScrollVertically(1) || !canScrollVertically(-1)) recyclerView.stopScroll()
                    } else if (!canScrollHorizontally(1) || !canScrollHorizontally(-1)) recyclerView.stopScroll()
                }
            })
        }
    }

    private fun onItemCentered() {
        if (layoutManager == null) return
        val target = mSnapHelper.findSnapView(layoutManager) ?: return
        val position = layoutManager!!.getPosition(target)
        if (mCurentPosition != position) {
            mCurentPosition = position
            mAdapter!!.onNewItemCentered(mCurentPosition)
        }
    }

    private fun onNoItemCentered() {
        mCurentPosition = NO_POSITION
        mAdapter!!.onNewItemCentered(mCurentPosition)
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        if (adapter == null) super.setAdapter(null) else try {
            mAdapter = adapter as SnappyAdapter<*>?
            mAdapter!!.setSnapper(this)
            mAdapter!!.setMSnapper(this)
            super.setAdapter(mAdapter)
        } catch (ex: Throwable) {
            super.setAdapter(null)
            ex.printStackTrace()
            Toast.makeText(context, "Error: cannot set adapter", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * @return current snapped position (position of the center item)
     */
    override fun getSnappedPosition(): Int {
        if (mIsScrollEnabled) return mCurentPosition
        if (layoutManager == null) return NO_POSITION
        val target = mSnapHelper.findSnapView(layoutManager)
            ?: return NO_POSITION
        return layoutManager!!.getPosition(target)
    }

    /**
     * @return saved current snapped position (position of the center item)
     */
    override fun getCurrentPosition(): Int {
        return mCurentPosition
    }

    /**
     * @return true if layout of the snapped item should be updated
     */
    override fun notifyOnSnap(): Boolean {
        return mBehavior != NOTIFY_ON_SCROLL
    }

    /**
     * Smothly scrolls to position
     *
     * @param position position of the item that will be placed in the center
     */
    override fun smoothSnapToPosition(position: Int) {
        if (snappedPosition == position) return
        if (layoutManager == null || mAdapter == null) return
        if (mBehavior == NOTIFY_ON_IDLE_AND_NO_POSITION
            && mCurentPosition != NO_POSITION
        ) onNoItemCentered()
        smoothSnapTo(position)
    }

    private fun smoothSnapTo(position: Int) {
        var target = layoutManager!!.findViewByPosition(position)
        if (target != null) {
            val dists = mSnapHelper.calculateDistanceToFinalSnap(layoutManager!!, target) ?: return
            smoothScrollBy(dists[0], dists[1])
        } else {
            target = mSnapHelper.findSnapView(layoutManager)
            if (target == null) return
            val dists = mSnapHelper.calculateDistanceToFinalSnap(layoutManager!!, target) ?: return
            scrollBy(dists[0], dists[1])
            val index = layoutManager!!.getPosition(target)
            val params = target.layoutParams as LayoutParams
            val span: Int
            val delta = position - index.toFloat()
            when ((layoutManager as LinearLayoutManager?)!!.orientation) {
                HORIZONTAL -> {
                    span = target.width + params.leftMargin + params.rightMargin
                    smoothScrollBy((span * delta).toInt(), 0)
                }
                VERTICAL -> {
                    span = target.height + params.topMargin + params.bottomMargin
                    smoothScrollBy(0, (span * delta).toInt())
                }
            }
        }
    }

    /**
     * Smothly scrolls to position by pixels
     */
    override fun smoothSnapBy(dx: Int, dy: Int) {
        if (mAdapter == null) return
        if (mBehavior == NOTIFY_ON_IDLE_AND_NO_POSITION
            && mCurentPosition != NO_POSITION
        ) onNoItemCentered()
        smoothScrollBy(dx, dy)
    }

    /**
     * Instantly scrolls to position
     */
    override fun snapToPosition(position: Int) {
        if (snappedPosition == position) return
        snapTo(position)
        if (mIsScrollEnabled) {
            mCurentPosition = position
            mAdapter!!.onNewItemCentered(mCurentPosition)
        }
    }

    private fun snapTo(position: Int) {
        var target = layoutManager!!.findViewByPosition(position)
        if (target == null) {
            scrollToPosition(position)
            target = layoutManager!!.findViewByPosition(position)
            if (target == null) return
        }
        val dists = mSnapHelper.calculateDistanceToFinalSnap(layoutManager!!, target) ?: return
        scrollBy(dists[0], dists[1])
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        setCenteringPadding()
    }

    private fun setCenteringPadding() {
        if (layoutManager == null) return
        if (!isPaddingApplied && mIsPaddingSet) {
            isPaddingApplied = true
            val edgeSpacing = (width - mItemSize) / 2 - mEdgeMargin
            when ((layoutManager as LinearLayoutManager?)!!.orientation) {
                HORIZONTAL -> setPadding(edgeSpacing, 0, edgeSpacing, 0)
                VERTICAL -> setPadding(0, edgeSpacing, 0, edgeSpacing)
            }
            var snapTo = 0
            if (mCurentPosition != NO_POSITION) snapTo = mCurentPosition
            snapTo(snapTo)
        }
    }

    enum class Behavior {
        /**
         * Adapter will be notified on state idle with position of the centered item
         * and state dragging with position = -1
         *
         *
         * onBindViewHolder, onSnapedFromCenter and Callback's onItemCentered will be called
         */
        NOTIFY_ON_IDLE_AND_NO_POSITION,

        /**
         * Adapter will be notified only on state idle with position of the centered item
         *
         *
         * onBindViewHolder, onSnapedFromCenter and Callback's onItemCentered will be called
         */
        NOTIFY_ON_IDLE,

        /**
         * Adapter will be notified with position of the centered item even while dragging
         *
         *
         * Only Callback's onItemCentered will be called
         */
        NOTIFY_ON_SCROLL
    }

    init {
        mSnapHelper.attachToRecyclerView(this)
    }
}