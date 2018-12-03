package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.snippets.CoordinatorLayoutUtils;
import net.cattaka.android.snippets.example.adapter.factory.SimpleStringViewHolderFactory;
import net.cattaka.android.snippets.example.tracker.IScreen;
import net.cattaka.android.snippets.example.tracker.Tracker;

import java.util.ArrayList;

import static net.cattaka.android.snippets.example.tracker.TrackEvent.ACTION_CLICK;
import static net.cattaka.android.snippets.example.tracker.TrackParam.VALUE;
import static net.cattaka.android.snippets.example.tracker.TrackParam.VIEW_NAME;
import static net.cattaka.android.snippets.example.tracker.TrackParamBundle.toParamsMap;

/**
 * Created by cattaka on 2018/12/03
 */

public class BottomSheetBehaviorExampleActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, IScreen {
    CoordinatorLayout mCoordinatorLayout;
    View mExpandedView;
    RecyclerView mRecyclerView;

    CheckBox mFitToContentsCheck;
    CheckBox mHideableCheck;
    CheckBox mSkipCollapsedCheck;
    CheckBox mScrollViewMatchParentCheck;

    ScrambleAdapter<String> mAdapter;
    int mCounter;
    BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_behavior_example);

        // Find views
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.layout_coordinator);
        mExpandedView = findViewById(R.id.view_expanded);
        mRecyclerView = (RecyclerView) findViewById(R.id.view_recycler);
        mFitToContentsCheck = (CheckBox) findViewById(R.id.check_fit_to_contents);
        mHideableCheck = (CheckBox) findViewById(R.id.check_hideable);
        mSkipCollapsedCheck = (CheckBox) findViewById(R.id.check_skip_collapsed);
        mScrollViewMatchParentCheck = (CheckBox) findViewById(R.id.check_scroll_view_match_parent);

        // Bind event handlers
        findViewById(R.id.button_add_contents).setOnClickListener(this);
        findViewById(R.id.button_subtract_contents).setOnClickListener(this);
        findViewById(R.id.button_show_bottom_sheet).setOnClickListener(this);
        findViewById(R.id.button_hide_bottom_sheet).setOnClickListener(this);
        mFitToContentsCheck.setOnCheckedChangeListener(this);
        mHideableCheck.setOnCheckedChangeListener(this);
        mSkipCollapsedCheck.setOnCheckedChangeListener(this);
        mScrollViewMatchParentCheck.setOnCheckedChangeListener(this);

        {   // Setup mRecyclerView
            mAdapter = new ScrambleAdapter<>(this, new ArrayList<String>(), null, new SimpleStringViewHolderFactory());
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(mAdapter);
            for (int i = 0; i < 10; i++) {
                mAdapter.getItems().add("Item : " + mAdapter.getItems().size());
            }
        }

        mBottomSheetBehavior = CoordinatorLayoutUtils.pickBehavior(mRecyclerView, BottomSheetBehavior.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshViews();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_add_contents) {
            Tracker.getInstance().recordAction(this, ACTION_CLICK, toParamsMap(VIEW_NAME, "button_add_contents"));
            mAdapter.getItems().add("Item : " + mAdapter.getItems().size());
            mAdapter.notifyItemInserted(mAdapter.getItems().size());
        } else if (v.getId() == R.id.button_subtract_contents) {
            Tracker.getInstance().recordAction(this, ACTION_CLICK, toParamsMap(VIEW_NAME, "button_subtract_contents"));
            if (mAdapter.getItems().size() > 0) {
                mAdapter.getItems().remove(mAdapter.getItems().size() - 1);
                mAdapter.notifyItemRemoved(mAdapter.getItems().size());
            }
        } else if (v.getId() == R.id.button_show_bottom_sheet) {
            Tracker.getInstance().recordAction(this, ACTION_CLICK, toParamsMap(VIEW_NAME, "button_add_overlap_top"));
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else if (v.getId() == R.id.button_hide_bottom_sheet) {
            Tracker.getInstance().recordAction(this, ACTION_CLICK, toParamsMap(VIEW_NAME, "button_subtract_overlap_top"));
            if (mBottomSheetBehavior.isHideable()) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            } else {
                Toast.makeText(BottomSheetBehaviorExampleActivity.this, "BottomSheetBehavior do not have hidable flag", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.check_fit_to_contents) {
            Tracker.getInstance().recordAction(this, ACTION_CLICK, toParamsMap(VIEW_NAME, "check_fit_to_contents", VALUE, isChecked));
            applyScrollFlags();
        } else if (buttonView.getId() == R.id.check_hideable) {
            Tracker.getInstance().recordAction(this, ACTION_CLICK, toParamsMap(VIEW_NAME, "check_hideable", VALUE, isChecked));
            applyScrollFlags();
        } else if (buttonView.getId() == R.id.check_skip_collapsed) {
            Tracker.getInstance().recordAction(this, ACTION_CLICK, toParamsMap(VIEW_NAME, "check_skip_collapsed", VALUE, isChecked));
            applyScrollFlags();
        } else if (buttonView.getId() == R.id.check_scroll_view_match_parent) {
            Tracker.getInstance().recordAction(this, ACTION_CLICK, toParamsMap(VIEW_NAME, "check_scroll_view_match_parent", VALUE, isChecked));
            ViewGroup.LayoutParams lp = mRecyclerView.getLayoutParams();
            lp.height = isChecked ? ViewGroup.LayoutParams.MATCH_PARENT : ViewGroup.LayoutParams.WRAP_CONTENT;
            mRecyclerView.setLayoutParams(lp);
        }
    }

    private void refreshViews() {
        mFitToContentsCheck.setChecked(mBottomSheetBehavior.isFitToContents());
        mHideableCheck.setChecked(mBottomSheetBehavior.isHideable());
        mSkipCollapsedCheck.setChecked(mBottomSheetBehavior.getSkipCollapsed());
    }

    private void applyScrollFlags() {
        mBottomSheetBehavior.setFitToContents(mFitToContentsCheck.isChecked());
        mBottomSheetBehavior.setHideable(mHideableCheck.isChecked());
        mBottomSheetBehavior.setSkipCollapsed(mSkipCollapsedCheck.isChecked());
        mCoordinatorLayout.requestLayout();
    }
}
