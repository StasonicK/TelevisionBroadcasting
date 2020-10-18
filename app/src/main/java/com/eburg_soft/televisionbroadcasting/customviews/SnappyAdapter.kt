package com.eburg_soft.televisionbroadcasting.customviews

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

abstract class SnappyAdapter<VH : ViewHolder?> : Adapter<VH>() {

    protected var snapper: Snapper? = null
    private var mSnapper: MSnapper? = null
    private var mCallback: Callback? = null
    private var mCenteredVh: VH? = null
    fun setSnapper(snapper: Snapper?) {
        this.snapper = snapper
    }

    fun setMSnapper(snapper: MSnapper?) {
        mSnapper = snapper
    }

    fun setCallback(callback: Callback?) {
        mCallback = callback
    }

    fun onNewItemCentered(position: Int) {
        if (mSnapper!!.notifyOnSnap()) {
            if (mCenteredVh != null) {
                onSnapedFromCenter(mCenteredVh)
                mCenteredVh = null
            }
            if (position != RecyclerView.NO_POSITION) notifyItemChanged(position)
        } else if (mCallback != null) mCallback!!.onItemCentered(position)
    }

    override fun onBindViewHolder(vh: VH, i: Int) {
        if (mSnapper == null) return
        if (i == mSnapper!!.currentPosition && mSnapper!!.notifyOnSnap()) {
            mCenteredVh = vh
            onBindViewHolder(vh, i, true)
            if (mCallback != null) mCallback!!.onItemCentered(i)
        } else onBindViewHolder(vh, i, false)
    }

    /**
     * Similar to onBindViewHolder(@NonNull VH vh, int position)
     * If centered item is different from the other, layout of the item should be binded with
     * respect of @param isAtTheCenter
     *
     *
     * provides with @param isAtTheCenter only if enableSnapListener of recyclerview had been called,
     * otherwise isAtTheCenter will always be false
     *
     * @param isAtTheCenter true if item is at the center
     */
    protected abstract fun onBindViewHolder(vh: VH, position: Int, isAtTheCenter: Boolean)

    /**
     * Notifies when the item that had been at the center moved.
     * If centered item is different from the other, here layout of the item should be updated
     * using @param vh
     *
     *
     * fires only if enableSnapListener of recyclerview had been called
     */
    protected abstract fun onSnapedFromCenter(vh: VH)
    interface Snapper {

        val snappedPosition: Int
        fun snapToPosition(position: Int)
        fun smoothSnapToPosition(position: Int)
        fun smoothSnapBy(dx: Int, dy: Int)
    }

    interface Callback {

        fun onItemCentered(position: Int)
    }

    interface MSnapper {

        val currentPosition: Int
        fun notifyOnSnap(): Boolean
    }
}