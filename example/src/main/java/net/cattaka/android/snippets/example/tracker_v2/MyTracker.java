package net.cattaka.android.snippets.example.tracker_v2;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;

import net.cattaka.android.snippets.example.tracker.TrackActivityLifecycleCallbacks;

/**
 * Created by cattaka on 18/02/06.
 */

public class MyTracker {
    private static MyTracker INSTANCE = new MyTracker();

    public static MyTracker getInstance() {
        return INSTANCE;
    }

    private Context mContext;
    private FirebaseAnalytics mFirebaseAnalytics;
    private TrackActivityLifecycleCallbacks mTrackActivityLifecycleCallbacks;

    public void initialize(@NonNull Application context) {
        mContext = context;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

        TrackActivityLifecycleCallbacks mTrackActivityLifecycleCallbacks = new TrackActivityLifecycleCallbacks();
        context.registerActivityLifecycleCallbacks(mTrackActivityLifecycleCallbacks);
    }

    public <V> void logEvent(
            Event event
    ) {
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.logEvent(event.key, bundle);
    }

    public <V> void logEvent(
            Event event,
            Param<V> k, V v
    ) {
        Bundle bundle = new Bundle();
        k.putFunc.put(bundle, k.key, v);
        mFirebaseAnalytics.logEvent(event.key, bundle);
    }

    public <V1, V2> void logEvent(
            Event event,
            Param<V1> k1, V1 v1,
            Param<V2> k2, V2 v2
    ) {
        Bundle bundle = new Bundle();
        k1.putFunc.put(bundle, k1.key, v1);
        k2.putFunc.put(bundle, k2.key, v2);
        mFirebaseAnalytics.logEvent(event.key, bundle);
    }

    public <V1, V2, V3> void logEvent(
            Event event,
            Param<V1> k1, V1 v1,
            Param<V2> k2, V2 v2,
            Param<V3> k3, V3 v3
    ) {
        Bundle bundle = new Bundle();
        k1.putFunc.put(bundle, k1.key, v1);
        k2.putFunc.put(bundle, k2.key, v2);
        k3.putFunc.put(bundle, k3.key, v3);
        mFirebaseAnalytics.logEvent(event.key, bundle);
    }

    public <V1, V2, V3, V4, V5, V6> void logEvent(
            Event event,
            Param<V1> k1, V1 v1,
            Param<V2> k2, V2 v2,
            Param<V3> k3, V3 v3,
            Param<V4> k4, V4 v4,
            Param<V5> k5, V5 v5,
            Param<V6> k6, V6 v6
    ) {
        Bundle bundle = new Bundle();
        k1.putFunc.put(bundle, k1.key, v1);
        k2.putFunc.put(bundle, k2.key, v2);
        k3.putFunc.put(bundle, k3.key, v3);
        k4.putFunc.put(bundle, k4.key, v4);
        k5.putFunc.put(bundle, k5.key, v5);
        k6.putFunc.put(bundle, k6.key, v6);
        mFirebaseAnalytics.logEvent(event.key, bundle);
    }

    private void example() {
        long id = 123;
        String name = "hoge";
        MyTracker.getInstance().logEvent(
                Event.SELECT_CONTENT,
                Param.ITEM_ID, id,
                Param.ITEM_NAME, name);

        Log.d("test", Event.CLICK.toString());

        // ほげ


        Bundle bundle = new Bundle();
        bundle.putLong("item_id", id);
        bundle.putString("item_name", name);
        mFirebaseAnalytics.logEvent("select_content", bundle);

    }

    @MainThread
    public void logScreen(@NonNull Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity != null) {
            mFirebaseAnalytics.setCurrentScreen(activity, fragment.getClass().getSimpleName(), null);
        }
    }
}
