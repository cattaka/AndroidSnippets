package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import net.cattaka.android.snippets.CoordinatorLayoutUtils;

/**
 * Created by cattaka on 16/12/03.
 */

public class AppBarLayoutExample1Activity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    CoordinatorLayout mCoordinatorLayout;
    AppBarLayout mAppBarLayout;
    View mExpandedView;
    NestedScrollView mScrollView;
    TextView mContentsView;
    CheckBox mScrollCheck;
    CheckBox mEnterAlwaysCheck;
    CheckBox mEnterAlwaysCollapsedCheck;
    CheckBox mExitUntilCollapsedCheck;
    CheckBox mSnapCheck;
    CheckBox mScrollViewMatchParentCheck;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_bar_layout_example_1);

        // Find views
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.layout_coordinator);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.layout_app_bar);
        mExpandedView = findViewById(R.id.view_expanded);
        mScrollView = (NestedScrollView) findViewById(R.id.view_scroll);
        mContentsView = (TextView) findViewById(R.id.view_contents);
        mScrollCheck = (CheckBox) findViewById(R.id.check_scroll);
        mEnterAlwaysCheck = (CheckBox) findViewById(R.id.check_enter_always);
        mEnterAlwaysCollapsedCheck = (CheckBox) findViewById(R.id.check_enter_always_collapsed);
        mExitUntilCollapsedCheck = (CheckBox) findViewById(R.id.check_exit_until_collapsed);
        mSnapCheck = (CheckBox) findViewById(R.id.check_snap);
        mScrollViewMatchParentCheck = (CheckBox) findViewById(R.id.check_scroll_view_match_parent);

        // Bind event handlers
        findViewById(R.id.button_add_contents).setOnClickListener(this);
        findViewById(R.id.button_subtract_contents).setOnClickListener(this);
        findViewById(R.id.button_add_overlap_top).setOnClickListener(this);
        findViewById(R.id.button_subtract_overlap_top).setOnClickListener(this);
        mScrollCheck.setOnCheckedChangeListener(this);
        mEnterAlwaysCheck.setOnCheckedChangeListener(this);
        mEnterAlwaysCollapsedCheck.setOnCheckedChangeListener(this);
        mExitUntilCollapsedCheck.setOnCheckedChangeListener(this);
        mSnapCheck.setOnCheckedChangeListener(this);
        mScrollViewMatchParentCheck.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshViews();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_add_contents) {
            int h = mContentsView.getHeight() + getResources().getDimensionPixelSize(R.dimen.control_diff);
            mContentsView.setHeight(h);
        } else if (v.getId() == R.id.button_subtract_contents) {
            int h = Math.max(0, mContentsView.getHeight() - getResources().getDimensionPixelSize(R.dimen.control_diff));
            mContentsView.setHeight(h);
        } else if (v.getId() == R.id.button_add_overlap_top) {
            AppBarLayout.ScrollingViewBehavior behavior = CoordinatorLayoutUtils.pickBehavior(mScrollView, AppBarLayout.ScrollingViewBehavior.class);
            if (behavior != null) {
                int ot = Math.max(0, behavior.getOverlayTop() + getResources().getDimensionPixelSize(R.dimen.control_diff));
                behavior.setOverlayTop(ot);
                mCoordinatorLayout.requestLayout();
            }
        } else if (v.getId() == R.id.button_subtract_overlap_top) {
            AppBarLayout.ScrollingViewBehavior behavior = CoordinatorLayoutUtils.pickBehavior(mScrollView, AppBarLayout.ScrollingViewBehavior.class);
            if (behavior != null) {
                int ot = Math.max(0, behavior.getOverlayTop() - getResources().getDimensionPixelSize(R.dimen.control_diff));
                behavior.setOverlayTop(ot);
                mCoordinatorLayout.requestLayout();
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.check_scroll) {
            applyScrollFlags();
        } else if (buttonView.getId() == R.id.check_enter_always) {
            applyScrollFlags();
        } else if (buttonView.getId() == R.id.check_enter_always_collapsed) {
            applyScrollFlags();
        } else if (buttonView.getId() == R.id.check_exit_until_collapsed) {
            applyScrollFlags();
        } else if (buttonView.getId() == R.id.check_snap) {
            applyScrollFlags();
        } else if (buttonView.getId() == R.id.check_scroll_view_match_parent) {
            ViewGroup.LayoutParams lp = mScrollView.getLayoutParams();
            lp.height = isChecked ? ViewGroup.LayoutParams.MATCH_PARENT : ViewGroup.LayoutParams.WRAP_CONTENT;
            mScrollView.setLayoutParams(lp);
        }
    }

    private void refreshViews() {
        AppBarLayout.LayoutParams lp = (AppBarLayout.LayoutParams) mExpandedView.getLayoutParams();
        int flags = lp.getScrollFlags();
        mScrollCheck.setChecked((flags & AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL) != 0);
        mEnterAlwaysCheck.setChecked((flags & AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS) != 0);
        mEnterAlwaysCollapsedCheck.setChecked((flags & AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED) != 0);
        mExitUntilCollapsedCheck.setChecked((flags & AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED) != 0);
        mSnapCheck.setChecked((flags & AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP) != 0);
    }

    private void applyScrollFlags() {
        int flags = 0;
        flags |= mScrollCheck.isChecked() ? AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL : 0;
        flags |= mEnterAlwaysCheck.isChecked() ? AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS : 0;
        flags |= mEnterAlwaysCollapsedCheck.isChecked() ? AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED : 0;
        flags |= mExitUntilCollapsedCheck.isChecked() ? AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED : 0;
        flags |= mSnapCheck.isChecked() ? AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP : 0;

        AppBarLayout.LayoutParams lp = (AppBarLayout.LayoutParams) mExpandedView.getLayoutParams();
        lp.setScrollFlags(flags);
        mExpandedView.setLayoutParams(lp);
        mCoordinatorLayout.requestLayout();
    }
}
