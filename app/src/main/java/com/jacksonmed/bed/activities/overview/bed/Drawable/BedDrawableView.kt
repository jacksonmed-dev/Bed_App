package com.jacksonmed.bed.activities.overview.bed.Drawable

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.view.View
import androidx.core.content.ContextCompat
import com.jacksonmed.bed.R

class BedDrawableView(context: Context): View(context) {

    private var drawableRectangle: MutableList<ShapeDrawable> = mutableListOf()

    override fun onDraw(canvas: Canvas) {
        for (rectangle in drawableRectangle)
            rectangle.draw(canvas)
    }

    fun createRectangles(num: Int){
        drawableRectangle = mutableListOf()
        var baseY = 10
        val width = 300
        val height = 50
        val offset = 10

        for(i in 0 until num){
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
        var rectangle: ShapeDrawable = drawableRectangle.elementAt(index)
        var temp = rectangle.paint.color
        rectangle.getPaint().setColor(color)
        var temp2 =  rectangle.paint.color
        drawableRectangle.set(index, rectangle)
        invalidate()
    }


}