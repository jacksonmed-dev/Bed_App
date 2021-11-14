package com.jacksonmed.bed.activities.overview.bed.inflatable

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.view.View

class BedDrawableView(context: Context, inflatableRegions: Int): View(context) {
    private var inflatableRegions = inflatableRegions
    private var drawableRectangle: MutableList<ShapeDrawable> = mutableListOf()

    override fun onDraw(canvas: Canvas) {
        for (rectangle in drawableRectangle)
            rectangle.draw(canvas)
    }

    fun createRectangles(){
        drawableRectangle = mutableListOf()
        var baseY = 10
        val width = 300
        val height = 50
        val offset = 10

        for(i in 0 until inflatableRegions){
            val drawable: ShapeDrawable = run {
                val x = 10
                val y = baseY
                ShapeDrawable(RectShape()).apply {
                    // If the color isn't set, the shape uses black as the default.
                    paint.color = 0xff74AC23.toInt()
                    // If the bounds aren't set, the shape can't be drawn.
                    setBounds(x, y, x + width, y + height)
                }
            }
            drawableRectangle.add(drawable)
            baseY+= height + offset
        }
        invalidate()
    }

    fun changeRectColor(index: Int, color: Int){
        if (drawableRectangle.size == 0) return
        val rectangle: ShapeDrawable = drawableRectangle.elementAt(index)
        rectangle.paint.color = color
        drawableRectangle.set(index, rectangle)
        invalidate()
    }


}