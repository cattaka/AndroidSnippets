package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

import net.cattaka.android.snippets.example.drawable.GradientOvalDrawable;
import net.cattaka.android.snippets.example.tracker.IScreen;

public class GradientOvalDrawableActivity extends AppCompatActivity implements IScreen {
    SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.seek_alpha: {
                    mGradientOvalDrawable.setAlpha(progress);
                    break;
                }
                case R.id.seek_degree: {
                    mGradientOvalDrawable.setDegree(progress);
                    break;
                }
                case R.id.seek_stroke_width: {
                    float f = (float) progress / (float) seekBar.getMax();
                    int w = (int) (f * mImageView.getWidth() / 2);
                    mGradientOvalDrawable.setStrokeWidth(w);
                    break;
                }
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // no-op
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // no-op
        }
    };

    View mImageView;
    SeekBar mAlphaSeek;
    SeekBar mStrokeWidthSeek;
    SeekBar mSeekDegree;

    GradientOvalDrawable mGradientOvalDrawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradient_oval_example);

        mGradientOvalDrawable = new GradientOvalDrawable();
        mImageView = findViewById(R.id.image);
        mAlphaSeek = findViewById(R.id.seek_alpha);
        mStrokeWidthSeek = findViewById(R.id.seek_stroke_width);
        mSeekDegree = findViewById(R.id.seek_degree);

        mImageView.setBackgroundDrawable(mGradientOvalDrawable);
        mAlphaSeek.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mStrokeWidthSeek.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mSeekDegree.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
    }
}
