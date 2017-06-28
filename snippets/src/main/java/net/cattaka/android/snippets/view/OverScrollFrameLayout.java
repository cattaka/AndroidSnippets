package net.cattaka.android.snippets.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;

/**
 * Created by cattaka on 2017/06/28.
 */

public class OverScrollFrameLayout extends FrameLayout implements NestedScrollingParent {
    public static final int DURATION_RETURN = 100;

    private IOnReleaseOverScrollListener mOnReleaseOverScrollListener;

    public OverScrollFrameLayout(@NonNull Context context) {
        super(context);
        initialize();
    }

    public OverScrollFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public OverScrollFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public OverScrollFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize();
    }

    private void initialize() {
        ViewCompat.setNestedScrollingEnabled(this, true);
    }

    public void setOnReleaseOverScrollListener(IOnReleaseOverScrollListener onReleaseOverScrollListener) {
        mOnReleaseOverScrollListener = onReleaseOverScrollListener;
    }

    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int nestedScrollAxes) {
        return true;
    }

    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int nestedScrollAxes) {
        super.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    public void onStopNestedScroll(@NonNull View target) {
        super.onStopNestedScroll(target);
        final float dx = getTranslationX();
        final float dy = getTranslationY();
        boolean consumed = false;
        if (mOnReleaseOverScrollListener != null) {
            consumed = mOnReleaseOverScrollListener.onReleaseOverScroll(this, dx, dy);
        }
        if (!consumed) {
            ViewPropertyAnimator animator = animate().translationX(0).translationY(0).setDuration(DURATION_RETURN);
            animator.setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (mOnReleaseOverScrollListener != null) {
                        mOnReleaseOverScrollListener.onReleaseAnimationEnd(OverScrollFrameLayout.this, dx, dy);
                    }
                }
            });
            animator.start();
        }
    }

    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dxUnconsumed != 0) {
            setTranslationX(getTranslationX() - (float) dxUnconsumed / 2f);
        }
        if (dyUnconsumed != 0) {
            setTranslationY(getTranslationY() - (float) dyUnconsumed / 2f);
        }
    }

    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed) {
        super.onNestedPreScroll(target, dx, dy, consumed);
    }

    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(target, velocityX, velocityY);
    }

    public int getNestedScrollAxes() {
        return super.getNestedScrollAxes();
    }

    public interface IOnReleaseOverScrollListener {
        boolean onReleaseOverScroll(@NonNull OverScrollFrameLayout view, float dx, float dy);

        void onReleaseAnimationEnd(@NonNull OverScrollFrameLayout view, float dx, float dy);
    }
}
