package net.cattaka.android.snippets.example.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class GradientOvalDrawable extends Drawable {
    private int mAlpha = 255;
    private int mStrokeWidth = 1;
    private int mFromColor = Color.GREEN;
    private int mToColor = Color.BLUE;
    private float mDegree = 0;
    private Paint mPaint = new Paint();

    public GradientOvalDrawable() {
        updatePaint();
    }

    private void updatePaint() {
        mPaint.setAlpha(mAlpha);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setShader(new LinearGradient(
                -1,
                0,
                1,
                0,
                mFromColor,
                mToColor,
                Shader.TileMode.MIRROR));
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.save();

        Rect r = canvas.getClipBounds();
        float strokeWidth = (float) mStrokeWidth / (float) Math.min(r.width(), r.height());
        mPaint.setStrokeWidth(strokeWidth);
        canvas.translate(r.centerX(), r.centerY());
        canvas.scale(r.width() / 2f, r.height() / 2f);
        canvas.rotate(mDegree);
        canvas.drawCircle(0f, 0f, 1f - strokeWidth / 2f, mPaint);

        canvas.restore();
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        // TODO
    }

    @Override
    public int getOpacity() {
        if (mAlpha <= 0) {
            return PixelFormat.TRANSPARENT;
        } else {
            return PixelFormat.TRANSLUCENT;
        }
    }

    @Override
    public int getAlpha() {
        return mAlpha;
    }

    @Override
    public void setAlpha(int alpha) {
        if (mAlpha != alpha) {
            mAlpha = alpha;
            updatePaint();
            invalidateSelf();
        }
    }

    public int getStrokeWidth() {
        return mStrokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        if (mStrokeWidth != strokeWidth) {
            mStrokeWidth = strokeWidth;
            updatePaint();
            invalidateSelf();
        }
    }

    public int getFromColor() {
        return mFromColor;
    }

    public void setFromColor(int fromColor) {
        if (mFromColor != fromColor) {
            mFromColor = fromColor;
            updatePaint();
            invalidateSelf();
        }
    }

    public int getToColor() {
        return mToColor;
    }

    public void setToColor(int toColor) {
        if (mToColor != toColor) {
            mToColor = toColor;
            updatePaint();
            invalidateSelf();
        }
    }

    public float getDegree() {
        return mDegree;
    }

    public void setDegree(float degree) {
        if (mDegree != degree) {
            mDegree = degree;
            invalidateSelf();
        }
    }
}
