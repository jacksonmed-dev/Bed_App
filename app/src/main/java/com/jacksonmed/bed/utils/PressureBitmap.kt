package com.jacksonmed.bed.utils

import android.graphics.Bitmap
import android.graphics.Matrix
import java.lang.Exception

class PressureBitmap {
    fun pressureMap(pixelValues: IntArray, dimX: Int, dimY: Int): Bitmap {
        var bmp: Bitmap = Bitmap.createBitmap(dimX, dimY, Bitmap.Config.ARGB_8888)
        bmp.setPixels(pixelValues, 0, dimX, 0, 0, dimX, dimY);
        return bmp
    }

    fun scaleBitmap(bitmap: Bitmap, scaleFactor: Float): Bitmap? {
        var newBitmap: Bitmap? = null
        try {

            //width = 500 and height = 500
            val width = bitmap.width
            val height = bitmap.height
            val newWidth = (width * scaleFactor).toInt()
            val newHeight = (height * scaleFactor).toInt()

            // create matrix for the manipulation
            val matrix = Matrix()

            // resize the bit map 2.4f, 2.4f
            matrix.postScale(scaleFactor, scaleFactor)

            // recreate the new Bitmap
            newBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false)
//            newBitmap = Bitmap.createBitmap(bitmap, 0, 0, newWidth, newHeight, matrix, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return newBitmap
    }
}