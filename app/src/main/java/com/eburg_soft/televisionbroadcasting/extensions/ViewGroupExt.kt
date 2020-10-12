package com.eburg_soft.televisionbroadcasting.extensions

import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun ViewGroup.changeBackgroundColor(@ColorRes color: Int) {
    this.setBackgroundColor(ContextCompat.getColor(context, color))
}