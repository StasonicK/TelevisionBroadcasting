package com.eburg_soft.televisionbroadcasting.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.ViewGroup

fun ViewGroup.elevate(isUp: Boolean) {
    if (isUp) {
        scaleTo(1.2f) {
        }
    } else {
        scaleTo(1f) {
        }
    }
}

private fun ViewGroup.scaleTo(scale: Float, listenerEnd: () -> Unit) {
    getScaleObjectAnimator(scale, scale).apply {
//        animatorEndListener { listenerEnd() }
        duration = 200
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
        scaleTo(1.2f) {
        }
    } else {
        scaleTo(1f) {
        }
    }
}

private fun View.scaleTo(scale: Float, listenerEnd: () -> Unit) {
    getScaleObjectAnimator(scale, scale).apply {
//        animatorEndListener { listenerEnd() }
        duration = 200
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