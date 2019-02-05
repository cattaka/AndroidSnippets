package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.snippets.CoordinatorLayoutUtils;
import net.cattaka.android.snippets.example.tracker.IScreen;

/**
 * Created by cattaka on 2018/12/03
 */

public class BottomSheetBehaviorOpenBySwipeUpActivity extends AppCompatActivity implements IScreen {
    CoordinatorLayout mCoordinatorLayout;
    View mBottomSheetView;

    ScrambleAdapter<String> mAdapter;
    BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_behavior_open_by_swipe_up);

        // Find views
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.layout_coordinator);
        mBottomSheetView = findViewById(R.id.view_bottom_sheet);

        // Bind event handlers

        mBottomSheetBehavior = CoordinatorLayoutUtils.pickBehavior(mBottomSheetView, BottomSheetBehavior.class);
        mBottomSheetBehavior.setHideable(true);
    }
}
