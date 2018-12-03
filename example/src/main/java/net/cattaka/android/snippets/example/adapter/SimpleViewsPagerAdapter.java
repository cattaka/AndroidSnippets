package net.cattaka.android.snippets.example.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.cattaka.android.snippets.example.fragment.SimpleAppBarLayoutFragment;
import net.cattaka.android.snippets.example.fragment.SimpleRecyclerViewFragment;

public class SimpleViewsPagerAdapter extends FragmentPagerAdapter {
    int mNum;

    public SimpleViewsPagerAdapter(FragmentManager fm, int num) {
        super(fm);
        mNum = num;
    }

    @Override
    public Fragment getItem(int i) {
        if (i % 2 == 0) {
            return new SimpleRecyclerViewFragment();
        } else {
            return new SimpleAppBarLayoutFragment();
        }
    }

    @Override
    public int getCount() {
        return mNum;
    }
}
