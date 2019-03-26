package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import net.cattaka.android.snippets.example.adapter.SimpleViewsPagerAdapter;
import net.cattaka.android.snippets.example.tracker.IScreen;

import biz.laenger.android.vpbs.BottomSheetUtils;
import biz.laenger.android.vpbs.ViewPagerBottomSheetBehavior;

/**
 * Created by cattaka on 2018/12/03
 */

public class ViewPagerInBottomSheetExampleActivity extends AppCompatActivity implements IScreen, View.OnClickListener {

    ViewPager mViewPager;
    SimpleViewsPagerAdapter mAdapter;
    ViewPagerBottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_in_bottom_sheet_example);

        // Find views
        mViewPager = findViewById(R.id.view_pager);

        findViewById(R.id.button_show_bottom_sheet).setOnClickListener(this);

        mBottomSheetBehavior = ViewPagerBottomSheetBehavior.from(mViewPager);
        mBottomSheetBehavior.setHideable(true);

        mAdapter = new SimpleViewsPagerAdapter(getSupportFragmentManager(), 3);
        mViewPager.setAdapter(mAdapter);

        BottomSheetUtils.setupViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_show_bottom_sheet) {
            mBottomSheetBehavior.setState(ViewPagerBottomSheetBehavior.STATE_EXPANDED);
        }
    }
}
