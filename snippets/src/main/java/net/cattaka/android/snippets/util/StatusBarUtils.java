package net.cattaka.android.snippets.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;

/**
 * Created by takao on 2016/12/12.
 */
public class StatusBarUtils {
    public static void onCreate(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    public static void setStatusBarColor(@Nullable Activity activity, int color) {
        Window window = (activity != null) ? activity.getWindow() : null;
        if (window == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(color);
        }
    }

    public static int getStatusBarColorFromStyle(@NonNull Context context) {
        Resources.Theme theme = context.getTheme();
        TypedValue typedValue = new TypedValue();
        theme.resolveAttribute(android.R.attr.statusBarColor, typedValue, true);
        return typedValue.data;
    }
}
