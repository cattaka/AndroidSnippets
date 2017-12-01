package net.cattaka.android.snippets.example.tracker;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;

import net.cattaka.android.snippets.example.BuildConfig;

import java.util.Locale;

/**
 * Created by cattaka on 17/12/01.
 */

public class Tracker {
    static final String TAG_SCREEN = "TrackScreen";
    static final String TAG_ACTION = "TrackAction";


    private static Tracker INSTANCE = new Tracker();

    public static Tracker getInstance() {
        return INSTANCE;
    }

    private Context mContext;
    private FirebaseAnalytics mFirebaseAnalytics;
    private TrackActivityLifecycleCallbacks mTrackActivityLifecycleCallbacks;

    public void initialize(@NonNull Application context) {
        mContext = context;
        if (BuildConfig.ENABLE_GOOGLE_SERVICES) {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        }

        TrackActivityLifecycleCallbacks mTrackActivityLifecycleCallbacks = new TrackActivityLifecycleCallbacks();
        context.registerActivityLifecycleCallbacks(mTrackActivityLifecycleCallbacks);
    }

    public void recordScreen(@Nullable IScreen screen) {
        if (BuildConfig.ENABLE_GOOGLE_SERVICES) {
            if (screen instanceof Activity) {
                String screenName = screen.getScreenName();
                mFirebaseAnalytics.setCurrentScreen((Activity) screen, screenName, null);
            } else if (screen instanceof Fragment) {
                Activity activity = ((Fragment) screen).getActivity();
                mFirebaseAnalytics.setCurrentScreen(activity, screen.getScreenName(), null);
            } else {
                String screenName = (screen != null) ? screen.getScreenName() : null;
                mFirebaseAnalytics.setCurrentScreen(null, screenName, null);
            }
        }
        if (BuildConfig.DEBUG) {
            String screenName = (screen != null) ? screen.getScreenName() : null;
            Log.d(TAG_SCREEN, String.format(Locale.ROOT, "screen=%s", screenName));
        }
    }
}
