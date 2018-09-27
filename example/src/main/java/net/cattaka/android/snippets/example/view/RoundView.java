package net.cattaka.android.snippets.example.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class RoundView extends View {
    private float mProgress;

    public RoundView(Context context) {
        super(context);
    }

    public RoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RoundView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void draw(Canvas canvas) {
        int w = this.getWidth();
        int h = this.getHeight();
        float radius = Math.min(w / 2, h / 2) * (1f - mProgress);

        Path clipPath = new Path();
        canvas.save();
        clipPath.addRoundRect(
                new RectF(0, 0, w, h),
                radius,
                radius,
                Path.Direction.CW
        );
        canvas.clipPath(clipPath);
        super.draw(canvas);
        canvas.restore();
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {
        if (mProgress != progress) {
            mProgress = progress;
            invalidate();
        }
    }
}
