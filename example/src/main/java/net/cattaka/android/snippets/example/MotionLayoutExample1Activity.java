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
    private View mBlueView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_layout_example_1);

        // Find views
        mMotionLayout = findViewById(R.id.motionLayout);
        mBlueView = findViewById(R.id.view_blue);

        findViewById(R.id.button_normal).setOnClickListener(this);
        findViewById(R.id.button_vertical).setOnClickListener(this);
        findViewById(R.id.button_horizontal).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_normal: {
//                mMotionLayout.setState(R.id.state_normal, mMotionLayout.getWidth(), mMotionLayout.getHeight());
                mMotionLayout.transitionToState(R.id.state_normal);
//                mMotionLayout.setTransition(R.id.state_vertical, R.id.state_normal);
//                mMotionLayout.transitionToEnd();
                break;
            }
            case R.id.button_vertical: {
//                mMotionLayout.setState(R.id.state_vertical, mMotionLayout.getWidth(), mMotionLayout.getHeight());
                mMotionLayout.transitionToState(R.id.state_vertical);
//                mMotionLayout.setTransition(R.id.state_vertical, R.id.state_normal);
//                mMotionLayout.transitionToStart();
                break;
            }
            case R.id.button_horizontal: {
//                mMotionLayout.setState(R.id.state_horizontal, mMotionLayout.getWidth(), mMotionLayout.getHeight());
                mMotionLayout.transitionToState(R.id.state_horizontal);
//                mMotionLayout.setTransition(R.id.state_vertical, R.id.state_horizontal);
//                mMotionLayout.transitionToEnd();
                break;
            }
        }
    }
}
