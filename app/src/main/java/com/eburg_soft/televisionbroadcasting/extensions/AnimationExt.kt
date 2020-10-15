package com.eburg_soft.televisionbroadcasting.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView

fun ViewGroup.elevate(isUp: Boolean) {
    if (isUp) {
        scaleTo(1.2f)
    } else {
        scaleTo(1f)
    }
}

private fun ViewGroup.scaleTo(scale: Float) {
    getScaleObjectAnimator(scale, scale).apply {
        duration = 500
        start()
    }
}

private fun ViewGroup.getScaleObjectAnimator(x: Float, y: Float): ObjectAnimator {
    val scaleX1 = PropertyValuesHolder.ofFloat(View.SCALE_X, x)
    val scaleY1 = PropertyValuesHolder.ofFloat(View.SCALE_Y, y)
    return ObjectAnimator.ofPropertyValuesHolder(this, scaleX1, scaleY1)
}

fun View.elevate(isUp: Boolean) {
    if (isUp) {
        scaleTo(1.2f)
//        if (this is CardView) {
//            this.clipToOutline = false
//        }
    } else {
        scaleTo(1f)
    }
}

private fun View.scaleTo(scale: Float) {
    getScaleObjectAnimator(scale, scale).apply {
        duration = 500
        start()
    }
}

private fun View.getScaleObjectAnimator(x: Float, y: Float): ObjectAnimator {
    val scaleX1 = PropertyValuesHolder.ofFloat(View.SCALE_X, x)
    val scaleY1 = PropertyValuesHolder.ofFloat(View.SCALE_Y, y)
    return ObjectAnimator.ofPropertyValuesHolder(this, scaleX1, scaleY1)
}

private fun ObjectAnimator.animatorEndListener(listenerEnd: (() -> Unit)) {
    addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            listenerEnd()
        }
    })
}