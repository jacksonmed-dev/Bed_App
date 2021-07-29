package com.jacksonmed.bed.utils;
import android.graphics.Bitmap;

import android.graphics.Color;

public class Hue {

    public static Bitmap changeHue(Bitmap bmp, int hue, int width,
                                   int heightStartPercentage, int heightEndPercentage) {

        if (bmp == null) return null;
        if ((hue < 0) || (hue > 360)) return null;
        if (heightStartPercentage < 0 || heightStartPercentage > 100) return null;
        if (heightEndPercentage < 0 || heightEndPercentage > 100) return null;

        // Specify dimensions of the pixels to extract from image
        int heightStart = (int)((float) heightStartPercentage/100 * bmp.getHeight());
        int heightEnd = (int) ((float) heightEndPercentage/100 * bmp.getHeight());
        int heightDifference = heightEnd - heightStart;


        int size = width * heightDifference;
        int[] all_pixels = new int[size];
        int top = heightStart;
        int left = 0;
        int offset = 0;
        int stride = width;

        bmp.getPixels(all_pixels, offset, stride, left, top, width, heightDifference);

        int pixel = 0;
        int alpha = 0;
        float[] hsv = new float[3];

        for (int i = 0; i < size; i++) {
            pixel = all_pixels[i];
            if(pixel == 0) continue;
            alpha = Color.alpha(pixel);
            Color.colorToHSV(pixel, hsv);

            // You could specify target color including Saturation for
            // more precise results
            hsv[0] = hue;
            hsv[1] = 0.8f;

            all_pixels[i] = Color.HSVToColor(alpha, hsv);
        }

        Bitmap bitmap = bmp.copy(Bitmap.Config.ARGB_8888, true);
        bitmap.setPixels(all_pixels, offset, stride, left, top, width, heightDifference);
        return bitmap;
    }
}
