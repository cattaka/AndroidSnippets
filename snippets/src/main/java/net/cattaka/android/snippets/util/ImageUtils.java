package net.cattaka.android.snippets.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.view.Surface;

/**
 * Created by takao on 2016/11/18.
 */

public class ImageUtils {
    @Nullable
    public static Bitmap rotateBitmap(@Nullable Bitmap in, int screenRotation, int sensorOrientation) {
        if (in == null) {
            return null;
        }
        int screenDegrees = 0;
        switch (screenRotation) {
            case Surface.ROTATION_0:
                screenDegrees = 0;
                break;
            case Surface.ROTATION_90:
                screenDegrees = 90;
                break;
            case Surface.ROTATION_180:
                screenDegrees = 180;
                break;
            case Surface.ROTATION_270:
                screenDegrees = 270;
                break;
        }
        int validatedDegree = (sensorOrientation / 90) * 90;
        int degree = (-screenDegrees + validatedDegree) % 360;
        if (degree < 0) {
            degree += 360;
        }

        Bitmap out;
        if ((degree % 180) == 0) {
            out = Bitmap.createBitmap(in.getWidth(), in.getHeight(), in.getConfig());
            Canvas canvas = new Canvas(out);
            canvas.rotate(degree, out.getWidth() / 2, out.getHeight() / 2);
            canvas.drawBitmap(in, 0, 0, null);
        } else {
            out = Bitmap.createBitmap(in.getHeight(), in.getWidth(), in.getConfig());
            Canvas canvas = new Canvas(out);
            canvas.rotate(degree, out.getWidth() / 2, out.getHeight() / 2);
            canvas.drawBitmap(in, 0, 0, null);
        }
        return out;
    }
}
