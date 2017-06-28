package net.cattaka.android.snippets.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cattaka on 2017/06/28.
 * <p>
 * Only for support-library over 26.0.0
 */
public class NestedCoordinatorLayout extends CoordinatorLayout {
    private final NestedScrollingChildHelper mScrollingChildHelper;
    private final int[] mParentOffsetInWindow = new int[2];

    public NestedCoordinatorLayout(Context context) {
        super(context);
        mScrollingChildHelper = new NestedScrollingChildHelper(this);
        mScrollingChildHelper.setNestedScrollingEnabled(true);
    }

    public NestedCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScrollingChildHelper = new NestedScrollingChildHelper(this);
        mScrollingChildHelper.setNestedScrollingEnabled(true);
    }

    public NestedCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScrollingChildHelper = new NestedScrollingChildHelper(this);
        mScrollingChildHelper.setNestedScrollingEnabled(true);
    }

    // NestedScrollingParent
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @Nullable int[] consumed, int type) {
        if (!mScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, mParentOffsetInWindow, type)) {
            super.onNestedPreScroll(target, dx, dy, consumed, type);
        }
    }

    // NestedScrollingParent
    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        boolean handled = super.onStartNestedScroll(child, target, axes, type);
        handled |= mScrollingChildHelper.startNestedScroll(axes, type);
        return handled;
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        super.onStopNestedScroll(target, type);
        mScrollingChildHelper.stopNestedScroll();
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        if (!mScrollingChildHelper.startNestedScroll(axes, type)) {
            super.onNestedScrollAccepted(child, target, axes, type);
        }
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        if (!mScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, mParentOffsetInWindow, type)) {
            super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        }
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (!mScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, mParentOffsetInWindow)) {
            super.onNestedPreScroll(target, dx, dy, consumed);
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return mScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed)
                || super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return mScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY)
                || super.onNestedPreFling(target, velocityX, velocityY);
    }

    @Override
    public int getNestedScrollAxes() {
        return super.getNestedScrollAxes();
    }
}
