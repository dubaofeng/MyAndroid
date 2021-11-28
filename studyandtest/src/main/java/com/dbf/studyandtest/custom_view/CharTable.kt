package com.dbf.studyandtest.custom_view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.compose.ui.graphics.Paint
import java.time.format.DecimalStyle

/**
 *Created by dbf on 2021/11/7
 *describe:
 */
class CharTable : View {
    constructor(context: Context?, attributeSet: AttributeSet? = null, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr)
    private val paint by lazy { Paint().also { } }
    private val paint2 = Paint()
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }
}