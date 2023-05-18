package com.ralphevmanzano.movies.shared.utils

import android.view.View
import androidx.core.view.isVisible

fun View.show() {
    if (isVisible) return
    visibility = View.VISIBLE
}

fun View.hide() {
    if (!isVisible) return
    visibility = View.GONE
}

fun View.invisible() {
    if (visibility == View.INVISIBLE) return
    visibility = View.INVISIBLE
}