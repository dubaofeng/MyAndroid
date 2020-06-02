package com.dbf.common.refreshlistview

import android.view.View
import android.view.ViewGroup

object Utils {

    fun measureView(view: View) {
        var lp: ViewGroup.LayoutParams? = view.layoutParams
        if (lp == null) {
            lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            var viewWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width)
            var lpHeight = lp.height
            var viewHeightSpec = if (lpHeight > 0) {
                View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY)
            } else {
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            }
            view.measure(viewWidthSpec, viewHeightSpec)
        }

    }
}