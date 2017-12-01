package net.cattaka.android.snippets.example.tracker;

import android.app.Activity;
import android.support.annotation.NonNull;

/**
 * Created by cattaka on 17/12/01.
 */

public interface IScreen {
    @NonNull
    default String getScreenName() {
        return this.getClass().getSimpleName();
    }

    default boolean useAutoTrack() {
        return true;
    }

    static IScreen ofScreen(@NonNull Activity o) {
        return new IScreen() {
            @NonNull
            @Override
            public String getScreenName() {
                return o.getClass().getSimpleName();
            }
        };
    }
}
