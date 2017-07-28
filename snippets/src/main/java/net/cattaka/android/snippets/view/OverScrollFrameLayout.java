package net.cattaka.android.snippets.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 2017/06/28.
 */

public class OverScrollFrameLayout extends FrameLayout implements NestedScrollingParent {
    private IOnReleaseOverScrollListener mOnReleaseOverScrollListener;
    private float mTranslateX;
    private float mTranslateY;
    private Animator mAnimator;
    private boolean mOverScrollTop = true;
    private boolean mOverScrollBottom = true;
    private boolean mOverScrollLeft = true;
    private boolean mOverScrollRight = true;
    private float mOverScrollRate = 0.5f;

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
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }
        return true;
    }

    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int nestedScrollAxes) {
        super.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    public void onStopNestedScroll(@NonNull View target) {
        super.onStopNestedScroll(target);
        final float dx = mTranslateX;
        final float dy = mTranslateY;
        mTranslateX = 0;
        mTranslateY = 0;

        boolean consumed = false;
        if (mOnReleaseOverScrollListener != null) {
            consumed = mOnReleaseOverScrollListener.onReleaseOverScroll(this, dx, dy);
        }
        if (!consumed) {
            List<Animator> animators = new ArrayList<>();
            if (getTranslationX() != 0) {
                animators.add(ObjectAnimator.ofFloat(this, TRANSLATION_X, getTranslationX(), 0f));
            }
            if (getTranslationY() != 0) {
                animators.add(ObjectAnimator.ofFloat(this, TRANSLATION_Y, getTranslationY(), 0f));
            }
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(animators);
            animatorSet.setInterpolator(new AccelerateInterpolator());
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (mOnReleaseOverScrollListener != null) {
                        mOnReleaseOverScrollListener.onReleaseAnimationEnd(OverScrollFrameLayout.this, dx, dy);
                    }
                    mAnimator = null;
                }
            });
            mAnimator = animatorSet;
            mAnimator.start();
        }
    }

    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if ((dxUnconsumed < 0 && mOverScrollLeft) || (dxUnconsumed > 0 && mOverScrollRight)) {
            mTranslateX -= dxUnconsumed;
            setTranslationX(mTranslateX * mOverScrollRate);
        }
        if ((dyUnconsumed < 0 && mOverScrollTop) || (dyUnconsumed > 0 && mOverScrollBottom)) {
            mTranslateY -= dyUnconsumed;
            setTranslationY(mTranslateY * mOverScrollRate);
        }
    }

    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed) {
        super.onNestedPreScroll(target, dx, dy, consumed);
        if (mTranslateX != 0 && dx != 0) {
            float old = mTranslateX;
            mTranslateX -= dx;
            if (old > 0 ^ mTranslateX > 0) {
                consumed[0] = (int) -mTranslateX;
                mTranslateX = 0;
            } else {
                consumed[0] = dx;
            }
            setTranslationX(mTranslateX * mOverScrollRate);
        }
        if (mTranslateY != 0 && dy != 0) {
            float old = mTranslateY;
            mTranslateY -= dy;
            if (old > 0 ^ mTranslateY > 0) {
                consumed[1] = (int) -mTranslateY;
                mTranslateY = 0;
            } else {
                consumed[1] = dy;
            }
            setTranslationY(mTranslateY * mOverScrollRate);
        }
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

    public boolean isOverScrollTop() {
        return mOverScrollTop;
    }

    public void setOverScrollTop(boolean overScrollTop) {
        mOverScrollTop = overScrollTop;
    }

    public boolean isOverScrollBottom() {
        return mOverScrollBottom;
    }

    public void setOverScrollBottom(boolean overScrollBottom) {
        mOverScrollBottom = overScrollBottom;
    }

    public boolean isOverScrollLeft() {
        return mOverScrollLeft;
    }

    public void setOverScrollLeft(boolean overScrollLeft) {
        mOverScrollLeft = overScrollLeft;
    }

    public boolean isOverScrollRight() {
        return mOverScrollRight;
    }

    public void setOverScrollRight(boolean overScrollRight) {
        mOverScrollRight = overScrollRight;
    }

    public float getOverScrollRate() {
        return mOverScrollRate;
    }

    public void setOverScrollRate(float overScrollRate) {
        mOverScrollRate = overScrollRate;
    }

    public interface IOnReleaseOverScrollListener {
        boolean onReleaseOverScroll(@NonNull OverScrollFrameLayout view, float dx, float dy);

        void onReleaseAnimationEnd(@NonNull OverScrollFrameLayout view, float dx, float dy);
    }
}
