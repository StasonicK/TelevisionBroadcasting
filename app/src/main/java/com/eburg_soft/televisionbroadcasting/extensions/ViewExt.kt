package com.eburg_soft.televisionbroadcasting.extensions

import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun View.changeBackgroundColor(@ColorRes color: Int) {
    this.setBackgroundColor(ContextCompat.getColor(context, color))
}

fun View.disableClipOnParents() {
    if (this.parent == null) {
        return
    }
    if (this is ViewGroup) {
        this.clipChildren = false
    }
    if (this.parent is View) {
        (this.parent as View).disableClipOnParents()
    }
}