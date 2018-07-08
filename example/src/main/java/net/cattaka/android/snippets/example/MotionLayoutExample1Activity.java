package net.cattaka.android.snippets.example;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import net.cattaka.android.snippets.animator.LayoutAnimatorHelper;
import net.cattaka.android.snippets.example.tracker.IScreen;
import net.cattaka.android.snippets.example.tracker.TrackEvent;
import net.cattaka.android.snippets.example.tracker.TrackParam;
import net.cattaka.android.snippets.example.tracker.Tracker;

import static net.cattaka.android.snippets.example.tracker.TrackParamBundle.toParamsMap;

/**
 * Created by takao on 2016/10/28.
 */

public class MotionLayoutExample1Activity extends AppCompatActivity implements View.OnClickListener, IScreen {
    private MotionLayoutExample1Activity me = this;
    private View mSpaceBase;
    private View mBlueView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_layout_example_1);

        // Find views
        mSpaceBase = findViewById(R.id.space_base);
        mBlueView = findViewById(R.id.view_blue);

        // Bind event handlers
        findViewById(R.id.button_normal).setOnClickListener(this);
        findViewById(R.id.button_vertical).setOnClickListener(this);
        findViewById(R.id.button_horizontal).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_normal) {
            Tracker.getInstance().recordAction(me, TrackEvent.SELECTED, toParamsMap(TrackParam.VIEW_NAME, "button_normal"));
            int goalWidth = getResources().getDimensionPixelSize(R.dimen.image_small);
            int goalHeight = goalWidth;
            int goalTop = 0;
            LayoutAnimatorHelper helper = new LayoutAnimatorHelper(mBlueView);
            ObjectAnimator.ofInt(helper, LayoutAnimatorHelper.WIDTH, helper.getWidth(), goalWidth).start();
            ObjectAnimator.ofInt(helper, LayoutAnimatorHelper.HEIGHT, helper.getHeight(), goalHeight).start();
            ObjectAnimator.ofInt(helper, LayoutAnimatorHelper.TOP_MARGIN, helper.getTopMargin(), goalTop).start();
        } else if (view.getId() == R.id.button_vertical) {
            Tracker.getInstance().recordAction(me, TrackEvent.SELECTED, toParamsMap(TrackParam.VIEW_NAME, "button_vertical"));
            int goalWidth = ((ViewGroup) mBlueView.getParent()).getWidth() - mSpaceBase.getWidth();
            int goalHeight = goalWidth * 2;
            int goalTop = 0;
            LayoutAnimatorHelper helper = new LayoutAnimatorHelper(mBlueView);
            ObjectAnimator.ofInt(helper, LayoutAnimatorHelper.WIDTH, helper.getWidth(), goalWidth).start();
            ObjectAnimator.ofInt(helper, LayoutAnimatorHelper.HEIGHT, helper.getHeight(), goalHeight).start();
            ObjectAnimator.ofInt(helper, LayoutAnimatorHelper.TOP_MARGIN, helper.getTopMargin(), goalTop).start();
        } else if (view.getId() == R.id.button_horizontal) {
            Tracker.getInstance().recordAction(me, TrackEvent.SELECTED, toParamsMap(TrackParam.VIEW_NAME, "button_horizontal"));
            int goalWidth = ((ViewGroup) mBlueView.getParent()).getWidth();
            int goalHeight = goalWidth / 2;
            int goalTop = mSpaceBase.getHeight();
            LayoutAnimatorHelper helper = new LayoutAnimatorHelper(mBlueView);
            ObjectAnimator.ofInt(helper, LayoutAnimatorHelper.WIDTH, helper.getWidth(), goalWidth).start();
            ObjectAnimator.ofInt(helper, LayoutAnimatorHelper.HEIGHT, helper.getHeight(), goalHeight).start();
            ObjectAnimator.ofInt(helper, LayoutAnimatorHelper.TOP_MARGIN, helper.getTopMargin(), goalTop).start();
        }
    }
}
