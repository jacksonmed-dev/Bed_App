package com.jacksonmed.bed.activities.overview.bed.Drawable

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.View

class BedDrawableView(context: Context): View(context) {

    private var drawableRectangle: MutableList<ShapeDrawable> = mutableListOf()

    init {
        createRectangles(5)
    }

    override fun onDraw(canvas: Canvas) {
        for (rectangle in drawableRectangle)
            rectangle.draw(canvas)
    }

    fun createRectangles(num: Int){
        var baseY = 10
        for(i in 0 until num){
            val drawable: ShapeDrawable = run {
                val x = 10
                val y = baseY
                val width = 300
                val height = 50

                ShapeDrawable(OvalShape()).apply {
                    // If the color isn't set, the shape uses black as the default.
                    paint.color = 0xff74AC23.toInt()
                    // If the bounds aren't set, the shape can't be drawn.
                    setBounds(x, y, x + width, y + height)
                }
            }
            drawableRectangle.add(drawable)
            baseY+=50
        }
    }

    fun changeRectColor(index: Int){
        var rectangle: ShapeDrawable = drawableRectangle.elementAt(index)
        var temp = rectangle.paint.color
        rectangle.getPaint().setColor(0xffffffff.toInt())
        var temp2 =  rectangle.paint.color
        drawableRectangle.set(index, rectangle)
        invalidate()
    }


}