package net.cattaka.android.snippets.example.tracker;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by cattaka on 17/12/01.
 */

public class TrackActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    private static final int EVENT_CHECK_FOREGROUND = 1;
    private static final int INTERVAL_LEAVE_SCREEN_MSEC = 3000;

    private static Handler sHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TrackActivityLifecycleCallbacks target = (TrackActivityLifecycleCallbacks) msg.obj;
            if (msg.what == EVENT_CHECK_FOREGROUND) {
                target.checkForeground();
            }
        }
    };

    private boolean mForeground = false;

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        // no-op
    }

    @Override
    public void onActivityStarted(Activity activity) {
        // no-op
    }

    @Override
    public void onActivityResumed(Activity activity) {
        mForeground = true;
        sHandler.removeMessages(EVENT_CHECK_FOREGROUND, this);
        IScreen screen = (activity instanceof IScreen) ? ((IScreen) activity) : IScreen.ofScreen(activity);

        if (screen.useAutoTrack()) {
            Tracker.getInstance().recordScreen(screen);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        mForeground = false;
        sHandler.removeMessages(EVENT_CHECK_FOREGROUND, this);
        sHandler.sendMessageDelayed(
                sHandler.obtainMessage(EVENT_CHECK_FOREGROUND, this),
                INTERVAL_LEAVE_SCREEN_MSEC
        );
    }

    @Override
    public void onActivityStopped(Activity activity) {
        // no-op
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        // no-op
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        // no-op
    }

    private void checkForeground() {
        if (!mForeground) {
            Tracker.getInstance().recordScreen(null);
        }
    }
}
