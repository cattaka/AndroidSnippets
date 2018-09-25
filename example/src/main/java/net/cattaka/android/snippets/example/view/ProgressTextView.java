package net.cattaka.android.snippets.example.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.util.Locale;

public class ProgressTextView extends AppCompatTextView {
    private float mProgress;

    public ProgressTextView(Context context) {
        super(context);
        init();
    }

    public ProgressTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setProgress(0);
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {
        if (mProgress != progress) {
            mProgress = progress;
            setText(String.format(Locale.ROOT, "%1.3f", mProgress));
        }
    }
}
