package com.eburg_soft.televisionbroadcasting.customviews

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper

class ProgramsScrollListener(
    private val snapHelper: SnapHelper,
    private val callback: ProgramCallback
)  : RecyclerView.OnScrollListener() {
    interface ProgramCallback {

        fun onProgramsPositionChanged(position: Int)
    }
    private var snapPosition = RecyclerView.NO_POSITION

     override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        dispatchPositionChange(recyclerView)
    }

    private fun dispatchPositionChange(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager ?: return
        val snapView = snapHelper.findSnapView(layoutManager) ?: return
        val snapPosition = layoutManager.getPosition(snapView)
        val snapPositionChanged = this.snapPosition != snapPosition
        if (snapPositionChanged) {
            callback.onProgramsPositionChanged(snapPosition)
            this.snapPosition = snapPosition
        }
    }
}