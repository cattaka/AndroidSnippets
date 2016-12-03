package net.cattaka.android.snippets;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cattaka on 16/12/04.
 */

public class CoordinatorLayoutUtils {

    @Nullable
    public static CoordinatorLayout.Behavior pickBehavior(@NonNull View contentsView) {
        ViewGroup.LayoutParams lp = contentsView.getLayoutParams();
        if (lp instanceof CoordinatorLayout.LayoutParams) {
            return ((CoordinatorLayout.LayoutParams) lp).getBehavior();
        } else {
            return null;
        }
    }

    @Nullable
    public static <T extends CoordinatorLayout.Behavior> T pickBehavior(@NonNull View contentsView, @NonNull Class<T> clazz) {
        CoordinatorLayout.Behavior behavior = pickBehavior(contentsView);
        return clazz.isInstance(behavior) ? clazz.cast(behavior) : null;
    }
}
