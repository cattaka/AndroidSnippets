package net.cattaka.android.snippets.example.tracker;

import android.app.Activity;
import android.support.annotation.NonNull;

/**
 * Created by cattaka on 17/12/01.
 */

public interface IScreen {
    @NonNull
    String getScreenName();

    boolean useAutoTrack();

    static IScreen ofScreen(@NonNull Activity o) {
        return new IScreen() {
            @NonNull
            @Override
            public String getScreenName() {
                return o.getClass().getSimpleName();
            }

            @Override
            public boolean useAutoTrack() {
                return true;
            }
        };
    }
}
