package net.cattaka.android.snippets.example.utils;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;

/**
 * Created by cattaka on 17/02/19.
 */

public class RelayForEnterAlwaysCollapsed {
    public static RecyclerView.OnScrollListener apply(@NonNull RecyclerView target, @NonNull CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout appBarLayout) {
        ForRecyclerView listener = new ForRecyclerView(coordinatorLayout, appBarLayout);
        target.addOnScrollListener(listener);
        return listener;
    }

    public static class ForRecyclerView extends RecyclerView.OnScrollListener {
        CoordinatorLayout mCoordinatorLayout;
        AppBarLayout mAppBarLayout;
        long mLastTime;
        float mLastVelX;
        float mLastVelY;

        public ForRecyclerView(@NonNull CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout appBarLayout) {
            mCoordinatorLayout = coordinatorLayout;
            mAppBarLayout = appBarLayout;
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
                AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
                if (behavior != null) {
                    behavior.onNestedFling(mCoordinatorLayout, mAppBarLayout, null, mLastVelX, mLastVelY, false);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            long t = SystemClock.elapsedRealtime();
            if (t > mLastTime) {
                float dt = (float) (t - mLastTime) / 1000f;
                mLastVelX = (float) dx / dt;
                mLastVelY = (float) dy / dt;
            }
            mLastTime = t;
        }
    }
}
