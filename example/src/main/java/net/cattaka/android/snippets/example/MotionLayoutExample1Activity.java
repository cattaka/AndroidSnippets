package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.constraint.motion.MotionLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import net.cattaka.android.snippets.example.tracker.IScreen;

/**
 * Created by takao on 2016/10/28.
 */

public class MotionLayoutExample1Activity extends AppCompatActivity implements View.OnClickListener, IScreen {
    private MotionLayoutExample1Activity me = this;
    private MotionLayout mMotionLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_layout_example_1);

        // Find views
        mMotionLayout = findViewById(R.id.motionLayout);
        findViewById(R.id.button_top).setOnClickListener(this);
        findViewById(R.id.button_start_bottom).setOnClickListener(this);
        findViewById(R.id.button_end_bottom).setOnClickListener(this);

        mMotionLayout.setShowPaths(true);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_top) {
            mMotionLayout.setTransition(R.id.scene_center, R.id.scene_top);
            mMotionLayout.setProgress(0);
        } else if (view.getId() == R.id.button_start_bottom) {
            mMotionLayout.setTransition(R.id.scene_center, R.id.scene_start_bottom);
            mMotionLayout.setProgress(0);
        } else if (view.getId() == R.id.button_end_bottom) {
            mMotionLayout.setTransition(R.id.scene_center, R.id.scene_end_bottom);
            mMotionLayout.setProgress(0);
        }
    }
}
