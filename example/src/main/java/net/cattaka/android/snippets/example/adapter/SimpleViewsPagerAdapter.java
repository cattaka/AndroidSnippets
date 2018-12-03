package net.cattaka.android.snippets.example.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.cattaka.android.snippets.example.fragment.SimpleAppBarLayoutFragment;
import net.cattaka.android.snippets.example.fragment.SimpleRecyclerViewFragment;
import net.cattaka.android.snippets.example.fragment.SimpleSwipeRefreshLayoutFragment;

public class SimpleViewsPagerAdapter extends FragmentPagerAdapter {
    int mNum;

    public SimpleViewsPagerAdapter(FragmentManager fm, int num) {
        super(fm);
        mNum = num;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i % 3) {
            case 0: {
                return new SimpleRecyclerViewFragment();
            }
            case 1: {
                return new SimpleSwipeRefreshLayoutFragment();
            }
            case 2:
            default: {
                return new SimpleAppBarLayoutFragment();
            }
        }
    }

    @Override
    public int getCount() {
        return mNum;
    }
}
