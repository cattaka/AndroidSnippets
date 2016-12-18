package net.cattaka.android.snippets.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.media.Image;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.Surface;

import java.nio.ByteBuffer;

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static Bitmap decodeYuv420(@NonNull Image image) {
        ByteBuffer buffer;
        int rowStride;
        int pixelStride;
        int width = image.getWidth();
        int height = image.getHeight();
        int offset = 0;

        Image.Plane[] planes = image.getPlanes();
        byte[] data = new byte[image.getWidth() * image.getHeight() * ImageFormat.getBitsPerPixel(ImageFormat.YUV_420_888) / 8];
        byte[] rowData = new byte[planes[0].getRowStride()];

        for (int i = 0; i < planes.length; i++) {
            buffer = planes[i].getBuffer();
            rowStride = planes[i].getRowStride();
            pixelStride = planes[i].getPixelStride();
            int w = (i == 0) ? width : width / 2;
            int h = (i == 0) ? height : height / 2;
            for (int row = 0; row < h; row++) {
                int bytesPerPixel = ImageFormat.getBitsPerPixel(ImageFormat.YUV_420_888) / 8;
                if (pixelStride == bytesPerPixel) {
                    int length = w * bytesPerPixel;
                    buffer.get(data, offset, length);

                    if (h - row != 1) {
                        buffer.position(buffer.position() + rowStride - length);
                    }
                    offset += length;
                } else {

                    if (h - row == 1) {
                        buffer.get(rowData, 0, width - pixelStride + 1);
                    } else {
                        buffer.get(rowData, 0, rowStride);
                    }

                    for (int col = 0; col < w; col++) {
                        data[offset++] = rowData[col * pixelStride];
                    }
                }
            }
        }
        return decodeYuv420sp(data, width, height);
    }

    public static Bitmap decodeYuv420sp(byte[] in, int w, int h) {
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        int[] rgb = new int[w * h];
        final int frameSize = w * h;
        for (int j = 0, yp = 0; j < h; j++) {
            int uvp = frameSize + (j >> 1) * w, u = 0, v = 0;
            for (int i = 0; i < w; i++, yp++) {
                int y = (0xff & ((int) in[yp])) - 16;
                if (y < 0) y = 0;
                if ((i & 1) == 0) {
                    v = (0xff & in[uvp++]) - 128;
                    u = (0xff & in[uvp++]) - 128;
                }
                int max = (1 << 18) - 1;
                int y1192 = 1192 * y;
                int r = Math.max(0, Math.min(y1192 + 1634 * v, max));
                int g = Math.max(0, Math.min(y1192 - 833 * v - 400 * u, max));
                int b = Math.max(0, Math.min(y1192 + 2066 * u, max));
                rgb[yp] = 0xff000000
                        | ((r << 6) & 0xff0000)
                        | ((g >> 2) & 0xff00)
                        | ((b >> 10) & 0xff);
            }
        }
        bitmap.setPixels(rgb, 0, w, 0, 0, w, h);
        return bitmap;
    }
}
