package net.cattaka.android.snippets.example;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import net.cattaka.android.snippets.CoordinatorLayoutUtils;
import net.cattaka.android.snippets.example.tracker.IScreen;

/**
 * Created by cattaka on 2018/12/03
 */

public class BottomSheetBehaviorOpenBySwipeUpActivity extends AppCompatActivity implements IScreen {
    static String TAG = "BottomSheetBehaviorOpenBySwipeUpActivity";

    CoordinatorLayout mCoordinatorLayout;
    View mBottomSheetView;
    View mTouchDelegateView;

    BottomSheetBehavior mBottomSheetBehavior;

    OnGestureListenerImpl mOnGestureListener;

    class OnGestureListenerImpl implements GestureDetector.OnGestureListener, View.OnTouchListener {
        GestureDetector gestureDetector;
        boolean isScrolling = false;

        public OnGestureListenerImpl(@NonNull Context context) {
            gestureDetector = new GestureDetector(context, this);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                if (isScrolling) {
                    isScrolling = false;
                    mCoordinatorLayout.onStopNestedScroll(mBottomSheetView, ViewCompat.TYPE_TOUCH);
                }
            }
            return gestureDetector.onTouchEvent(event);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            // no-op
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (!isScrolling) {
                isScrolling = true;
                mCoordinatorLayout.onStartNestedScroll(mBottomSheetView, mBottomSheetView, ViewCompat.SCROLL_AXIS_VERTICAL, ViewCompat.TYPE_TOUCH);
            }
            mCoordinatorLayout.onNestedPreScroll(mBottomSheetView, (int) distanceX, (int) distanceY, new int[2], ViewCompat.TYPE_TOUCH);
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return mBottomSheetBehavior.onNestedFling(mCoordinatorLayout, mBottomSheetView, mBottomSheetView, velocityX, velocityY, false);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_behavior_open_by_swipe_up);

        mOnGestureListener = new OnGestureListenerImpl(this);

        // Find views
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.layout_coordinator);
        mBottomSheetView = findViewById(R.id.view_bottom_sheet);
        mTouchDelegateView = findViewById(R.id.touch_delegate);
        mTouchDelegateView.setOnTouchListener(mOnGestureListener);

        // Bind event handlers
        mBottomSheetBehavior = CoordinatorLayoutUtils.pickBehavior(mBottomSheetView, BottomSheetBehavior.class);
        mBottomSheetBehavior.setHideable(true);
    }
}
