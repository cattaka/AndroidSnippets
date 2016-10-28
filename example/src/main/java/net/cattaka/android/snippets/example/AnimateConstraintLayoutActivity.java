package net.cattaka.android.snippets.example;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import net.cattaka.android.snippets.animator.LayoutAnimatorHelper;

/**
 * Created by takao on 2016/10/28.
 */

public class AnimateConstraintLayoutActivity extends AppCompatActivity implements View.OnClickListener {
    private View mSpaceBase;
    private View mBlueView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate_constraint_layout);

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
            int goalWidth = getResources().getDimensionPixelSize(R.dimen.image_small);
            int goalHeight = goalWidth;
            int goalTop = 0;
            LayoutAnimatorHelper helper = new LayoutAnimatorHelper(mBlueView);
            ObjectAnimator.ofInt(helper, LayoutAnimatorHelper.WIDTH, helper.getWidth(), goalWidth).start();
            ObjectAnimator.ofInt(helper, LayoutAnimatorHelper.HEIGHT, helper.getHeight(), goalHeight).start();
            ObjectAnimator.ofInt(helper, LayoutAnimatorHelper.TOP_MARGIN, helper.getTopMargin(), goalTop).start();
        } else if (view.getId() == R.id.button_vertical) {
            int goalWidth = ((ViewGroup) mBlueView.getParent()).getWidth() - mSpaceBase.getWidth();
            int goalHeight = goalWidth * 2;
            int goalTop = 0;
            LayoutAnimatorHelper helper = new LayoutAnimatorHelper(mBlueView);
            ObjectAnimator.ofInt(helper, LayoutAnimatorHelper.WIDTH, helper.getWidth(), goalWidth).start();
            ObjectAnimator.ofInt(helper, LayoutAnimatorHelper.HEIGHT, helper.getHeight(), goalHeight).start();
            ObjectAnimator.ofInt(helper, LayoutAnimatorHelper.TOP_MARGIN, helper.getTopMargin(), goalTop).start();
        } else if (view.getId() == R.id.button_horizontal) {
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
