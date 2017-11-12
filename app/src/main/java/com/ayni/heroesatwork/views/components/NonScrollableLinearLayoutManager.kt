package com.ayni.heroesatwork.views.components

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.LinearLayoutManager

class NonScrollableLinearLayoutManager: LinearLayoutManager {
    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout)

    override fun canScrollVertically(): Boolean {
        return false
    }
}