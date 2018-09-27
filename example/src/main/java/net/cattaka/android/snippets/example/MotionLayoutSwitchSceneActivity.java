package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.constraint.motion.MotionLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import net.cattaka.android.snippets.example.tracker.IScreen;

import java.util.Locale;

/**
 * Created by takao on 2016/10/28.
 */

public class MotionLayoutSwitchSceneActivity extends AppCompatActivity implements View.OnClickListener, IScreen {
    private MotionLayoutSwitchSceneActivity me = this;
    private MotionLayout mMotionLayout;
    private int mDirtyHackForSceneId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_layout_switch_scene);

        // Find views
        mMotionLayout = findViewById(R.id.motionLayout);
        findViewById(R.id.button_center).setOnClickListener(this);
        findViewById(R.id.button_top).setOnClickListener(this);
        findViewById(R.id.button_start_bottom).setOnClickListener(this);
        findViewById(R.id.button_end_bottom).setOnClickListener(this);

        mMotionLayout.setShowPaths(true);
    }

    @Override
    public void onClick(View view) {

        Log.d("cattaka", String.format(Locale.ROOT, "%d:%f", mMotionLayout.getCurrentState(), mMotionLayout.getProgress()));
        if (view.getId() == R.id.button_center) {
            switchToState(R.id.scene_c);
        } else if (view.getId() == R.id.button_top) {
            switchToState(R.id.scene_t);
        } else if (view.getId() == R.id.button_start_bottom) {
            switchToState(R.id.scene_sb);
        } else if (view.getId() == R.id.button_end_bottom) {
            switchToState(R.id.scene_eb);
        }
    }

    private void switchToState(int nextSceneId) {
        // FIXME: In some case, getCurrentState() returns -1
        int lastSceneId = mMotionLayout.getCurrentState();
        if (lastSceneId == -1) {
            lastSceneId = mDirtyHackForSceneId;
        } else {
            mDirtyHackForSceneId = lastSceneId;
        }
        mMotionLayout.setTransition(lastSceneId, nextSceneId);
        mMotionLayout.setProgress(0);
    }

}
