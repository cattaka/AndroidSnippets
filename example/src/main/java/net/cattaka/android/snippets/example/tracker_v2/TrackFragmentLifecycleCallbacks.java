package net.cattaka.android.snippets.example.tracker_v2;

import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cattaka on 18/02/07.
 */

public class TrackFragmentLifecycleCallbacks extends FragmentManager.FragmentLifecycleCallbacks {
    Map<Fragment, Long> mStartTimes = new HashMap<>();

    @Override
    public void onFragmentStarted(FragmentManager fm, Fragment f) {
        super.onFragmentStarted(fm, f);
        if (!(f instanceof INoTrack)) {
            MyTracker.getInstance().logScreen(f);
        }
        mStartTimes.put(f, SystemClock.elapsedRealtime());
    }

    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment f) {
        super.onFragmentStopped(fm, f);
        long duration = SystemClock.elapsedRealtime() - mStartTimes.get(f);
        MyTracker.getInstance().logEvent(Event.SCREEN_SHOW, Param.DURATION, duration);
    }

    public interface INoTrack {
    }
}
