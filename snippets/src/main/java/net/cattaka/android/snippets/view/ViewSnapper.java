package net.cattaka.android.snippets.view;

import android.animation.ObjectAnimator;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IdRes;
import android.support.v4.widget.NestedScrollView;
import android.util.Property;
import android.util.SparseBooleanArray;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by takao on 2016/10/26.
 */
public class ViewSnapper {
    static final Handler sHandler = new Handler(Looper.getMainLooper());
    public static final int DEFAULT_STOP_VELOCITY = 250;
    public static final int DEFAULT_MAX_SCROLL_DURATION = 1000;

    public static ISnapTargetFunc withId(@IdRes int... viewIds) {
        final SparseBooleanArray viewIdSet = new SparseBooleanArray(viewIds.length);
        for (int viewId : viewIds) {
            viewIdSet.put(viewId, true);
        }
        return new ISnapTargetFunc() {
            @Override
            public boolean isSnappable(View view) {
                return viewIdSet.get(view.getId(), false);
            }
        };
    }

    public static ISnapTargetFunc withClass(final Class<?>... classes) {
        return new ISnapTargetFunc() {
            @Override
            public boolean isSnappable(View view) {
                for (Class<?> clazz : classes) {
                    if (clazz.isInstance(view)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    public static IPullAnchorFunc anchorVertical(final boolean top, final boolean bottom) {
        return new IPullAnchorFunc() {
            @Override
            public void pullAnchor(Set<Integer> dest, View view, int totalParentOffset) {
                if (top) {
                    dest.add(totalParentOffset + view.getTop());
                }
                if (bottom) {
                    dest.add(totalParentOffset + view.getBottom());
                }
            }

            @Override
            public int pullOffset(View view) {
                return view.getTop();
            }
        };
    }

    public static IPullAnchorFunc anchorHorizontal(final boolean left, final boolean right) {
        return new IPullAnchorFunc() {
            @Override
            public void pullAnchor(Set<Integer> dest, View view, int totalParentOffset) {
                if (left) {
                    dest.add(totalParentOffset + view.getLeft());
                }
                if (right) {
                    dest.add(totalParentOffset + view.getRight());
                }
            }

            @Override
            public int pullOffset(View view) {
                return view.getLeft();
            }
        };
    }

    private int mStopVelocity;
    private int mMaxScrollDuration;

    public ViewSnapper() {
        this(DEFAULT_STOP_VELOCITY, DEFAULT_MAX_SCROLL_DURATION);
    }

    public ViewSnapper(int stopVelocity, int maxScrollDuration) {
        this.mStopVelocity = stopVelocity;
        this.mMaxScrollDuration = maxScrollDuration;
    }

    public void apply(final ScrollView view, final boolean snapStart, final boolean snapEnd, final ISnapTargetFunc snapTargetFunc, final IPullAnchorFunc pullAnchorFunc) {
        apply(view, new IExtraFunc<ScrollView>() {
            @Override
            public void onStartScroll(ScrollView view) {
                view.fling(0);
            }

            @Override
            public int getScroll(ScrollView view) {
                return view.getScrollY();
            }

            @Override
            public int getSize(ScrollView view) {
                return view.getHeight();
            }

            @Override
            public float getVelocity(VelocityTracker velocityTracker) {
                return velocityTracker.getYVelocity();
            }

            @Override
            public Property<ScrollView, Integer> getScrollProperty() {
                return Property.of(ScrollView.class, int.class, "scrollY");
            }
        }, snapStart, snapEnd, snapTargetFunc, pullAnchorFunc);
    }

    public void apply(final HorizontalScrollView view, final boolean snapStart, final boolean snapEnd, final ISnapTargetFunc snapTargetFunc, final IPullAnchorFunc pullAnchorFunc) {
        apply(view, new IExtraFunc<HorizontalScrollView>() {
            @Override
            public void onStartScroll(HorizontalScrollView view) {
                view.fling(0);
            }

            @Override
            public int getScroll(HorizontalScrollView view) {
                return view.getScrollX();
            }

            @Override
            public int getSize(HorizontalScrollView view) {
                return view.getWidth();
            }

            @Override
            public float getVelocity(VelocityTracker velocityTracker) {
                return velocityTracker.getXVelocity();
            }

            @Override
            public Property<HorizontalScrollView, Integer> getScrollProperty() {
                return Property.of(HorizontalScrollView.class, int.class, "scrollX");
            }
        }, snapStart, snapEnd, snapTargetFunc, pullAnchorFunc);
    }

    public void apply(final NestedScrollView view, final boolean snapStart, final boolean snapEnd, final ISnapTargetFunc snapTargetFunc, final IPullAnchorFunc pullAnchorFunc) {
        apply(view, new IExtraFunc<NestedScrollView>() {
            @Override
            public void onStartScroll(NestedScrollView view) {
                view.fling(0);
            }

            @Override
            public int getScroll(NestedScrollView view) {
                return view.getScrollY();
            }

            @Override
            public int getSize(NestedScrollView view) {
                return view.getHeight();
            }

            @Override
            public float getVelocity(VelocityTracker velocityTracker) {
                return velocityTracker.getYVelocity();
            }

            @Override
            public Property<NestedScrollView, Integer> getScrollProperty() {
                return Property.of(NestedScrollView.class, int.class, "scrollY");
            }
        }, snapStart, snapEnd, snapTargetFunc, pullAnchorFunc);
    }

    public <V extends View> void apply(final V view, final IExtraFunc<V> extraFunc, final boolean snapStart, final boolean snapEnd, final ISnapTargetFunc snapTargetFunc, final IPullAnchorFunc pullAnchorFunc) {
        view.setOnTouchListener(new View.OnTouchListener() {
            VelocityTracker mVelocityTracker;
            ObjectAnimator mAnimator;
            Set<Integer> anchors = new HashSet<>();

            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                if (mAnimator != null) {
                    mAnimator.cancel();
                    mAnimator = null;
                }
                if (mVelocityTracker == null) {
                    mVelocityTracker = VelocityTracker.obtain();
                }
                mVelocityTracker.addMovement(motionEvent);
                mVelocityTracker.computeCurrentVelocity(1000);
                if (motionEvent.getActionMasked() == MotionEvent.ACTION_UP) {
                    final float friction = ViewConfiguration.getScrollFriction();
                    final double logFriction = Math.log(friction);
                    final float velocity = -extraFunc.getVelocity(mVelocityTracker);
                    sHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            anchors.clear();
                            pullAnchors(anchors, snapTargetFunc, pullAnchorFunc, view);

                            int currentScroll = extraFunc.getScroll(view);

                            Integer goal = null;
                            {   // Find goal
                                int maxDistance = Integer.MAX_VALUE;
                                float delta = (float) (velocity / logFriction);
                                int estimatedScroll = currentScroll - (int) delta;
                                for (int point : anchors) {
                                    if (snapStart) {
                                        int distance = Math.abs(point - estimatedScroll);
                                        if (distance < maxDistance) {
                                            maxDistance = distance;
                                            goal = point;
                                        }
                                    }
                                    if (snapEnd) {
                                        int h = extraFunc.getSize(view);
                                        int distance = Math.abs(point - h - estimatedScroll);
                                        if (distance < maxDistance) {
                                            maxDistance = distance;
                                            goal = point - h;
                                        }
                                    }
                                }
                            }
                            if (goal != null) {
                                int time = (int) (1000 * Math.log(mStopVelocity / Math.abs(velocity)) / logFriction);
                                double d1 = 1.0 + (goal - currentScroll) * logFriction / velocity;
                                if (d1 > 0) {
                                    int t2 = (int) (1000 * (1.0 / logFriction) * Math.log(d1));
                                    time = Math.min(time, t2);
                                }
                                if (time < 0 || mMaxScrollDuration < time) {
                                    time = mMaxScrollDuration;
                                }

                                if (extraFunc != null) {
                                    extraFunc.onStartScroll(view);
                                }
                                mAnimator = ObjectAnimator
                                        .ofInt(view, extraFunc.getScrollProperty(), currentScroll, goal)
                                        .setDuration(time);
                                mAnimator.setInterpolator(new DecelerateInterpolator(1.0f - friction));
                                mAnimator.start();
                            }
                        }
                    });
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                return false;
            }
        });
    }

    private void pullAnchors(Set<Integer> dest, ISnapTargetFunc snapTargetFunc, IPullAnchorFunc pullAnchorFunc, View view) {
        pullAnchors(dest, snapTargetFunc, pullAnchorFunc, view, 0);
    }

    private void pullAnchors(Set<Integer> dest, ISnapTargetFunc snapTargetFunc, IPullAnchorFunc pullAnchorFunc, View view, int offset) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                pullAnchors(dest, snapTargetFunc, pullAnchorFunc, viewGroup.getChildAt(i), offset + pullAnchorFunc.pullOffset(viewGroup));
            }
        }
        if (snapTargetFunc.isSnappable(view)) {
            pullAnchorFunc.pullAnchor(dest, view, offset);
        }
    }

    public interface ISnapTargetFunc {
        boolean isSnappable(View view);
    }

    public interface IPullAnchorFunc {
        void pullAnchor(Set<Integer> dest, View view, int offset);

        int pullOffset(View view);
    }

    public interface IExtraFunc<V extends View> {
        void onStartScroll(V view);

        int getScroll(V view);

        int getSize(V view);

        float getVelocity(VelocityTracker velocityTracker);

        Property<V, Integer> getScrollProperty();
    }
}
