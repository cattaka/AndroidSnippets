package net.cattaka.android.snippets.example.tracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Wrapper for params to send multiple destination.
 */
public class TrackParamBundle implements Serializable {
    private Map<TrackParam, Object> map = new LinkedHashMap<>();

    public TrackParamBundle() {
    }

    public TrackParamBundle(@Nullable TrackParamBundle orig) {
        if (orig != null) {
            this.map.putAll(orig.map);
        }
    }

    public static <
            T1 extends Serializable
            >
    TrackParamBundle toParamsMapWithExtras(
            @Nullable TrackParamBundle extras,
            @NonNull TrackParam<T1> k1, @Nullable T1 v1
    ) {
        TrackParamBundle ret = new TrackParamBundle(extras);
        ret.put(k1, v1);
        return ret;
    }

    public static <
            T1 extends Serializable,
            T2 extends Serializable
            >
    TrackParamBundle toParamsMapWithExtras(
            @Nullable TrackParamBundle extras,
            @NonNull TrackParam<T1> k1, @Nullable T1 v1,
            @NonNull TrackParam<T2> k2, @Nullable T2 v2
    ) {
        TrackParamBundle ret = new TrackParamBundle(extras);
        ret.put(k1, v1);
        ret.put(k2, v2);
        return ret;
    }

    public static <
            T1 extends Serializable,
            T2 extends Serializable,
            T3 extends Serializable
            >
    TrackParamBundle toParamsMapWithExtras(
            @Nullable TrackParamBundle extras,
            @NonNull TrackParam<T1> k1, @Nullable T1 v1,
            @NonNull TrackParam<T2> k2, @Nullable T2 v2,
            @NonNull TrackParam<T3> k3, @Nullable T3 v3
    ) {
        TrackParamBundle ret = new TrackParamBundle(extras);
        ret.put(k1, v1);
        ret.put(k2, v2);
        ret.put(k3, v3);
        return ret;
    }

    public static <
            T1 extends Serializable,
            T2 extends Serializable,
            T3 extends Serializable,
            T4 extends Serializable
            >
    TrackParamBundle toParamsMapWithExtras(
            @Nullable TrackParamBundle extras,
            @NonNull TrackParam<T1> k1, @Nullable T1 v1,
            @NonNull TrackParam<T2> k2, @Nullable T2 v2,
            @NonNull TrackParam<T3> k3, @Nullable T3 v3,
            @NonNull TrackParam<T4> k4, @Nullable T4 v4
    ) {
        TrackParamBundle ret = new TrackParamBundle(extras);
        ret.put(k1, v1);
        ret.put(k2, v2);
        ret.put(k3, v3);
        ret.put(k4, v4);
        return ret;
    }

    public static <
            T1 extends Serializable,
            T2 extends Serializable,
            T3 extends Serializable,
            T4 extends Serializable,
            T5 extends Serializable
            >
    TrackParamBundle toParamsMapWithExtras(
            @Nullable TrackParamBundle extras,
            @NonNull TrackParam<T1> k1, @Nullable T1 v1,
            @NonNull TrackParam<T2> k2, @Nullable T2 v2,
            @NonNull TrackParam<T3> k3, @Nullable T3 v3,
            @NonNull TrackParam<T4> k4, @Nullable T4 v4,
            @NonNull TrackParam<T5> k5, @Nullable T5 v5
    ) {
        TrackParamBundle ret = new TrackParamBundle(extras);
        ret.put(k1, v1);
        ret.put(k2, v2);
        ret.put(k3, v3);
        ret.put(k4, v4);
        ret.put(k5, v5);
        return ret;
    }

    public static <
            T1 extends Serializable,
            T2 extends Serializable,
            T3 extends Serializable,
            T4 extends Serializable,
            T5 extends Serializable,
            T6 extends Serializable
            >
    TrackParamBundle toParamsMapWithExtras(
            @Nullable TrackParamBundle extras,
            @NonNull TrackParam<T1> k1, @Nullable T1 v1,
            @NonNull TrackParam<T2> k2, @Nullable T2 v2,
            @NonNull TrackParam<T3> k3, @Nullable T3 v3,
            @NonNull TrackParam<T4> k4, @Nullable T4 v4,
            @NonNull TrackParam<T5> k5, @Nullable T5 v5,
            @NonNull TrackParam<T6> k6, @Nullable T6 v6
    ) {
        TrackParamBundle ret = new TrackParamBundle(extras);
        ret.put(k1, v1);
        ret.put(k2, v2);
        ret.put(k3, v3);
        ret.put(k4, v4);
        ret.put(k5, v5);
        ret.put(k6, v6);
        return ret;
    }

    public static <
            T1 extends Serializable,
            T2 extends Serializable,
            T3 extends Serializable,
            T4 extends Serializable,
            T5 extends Serializable,
            T6 extends Serializable,
            T7 extends Serializable
            >
    TrackParamBundle toParamsMapWithExtras(
            @Nullable TrackParamBundle extras,
            @NonNull TrackParam<T1> k1, @Nullable T1 v1,
            @NonNull TrackParam<T2> k2, @Nullable T2 v2,
            @NonNull TrackParam<T3> k3, @Nullable T3 v3,
            @NonNull TrackParam<T4> k4, @Nullable T4 v4,
            @NonNull TrackParam<T5> k5, @Nullable T5 v5,
            @NonNull TrackParam<T6> k6, @Nullable T6 v6,
            @NonNull TrackParam<T7> k7, @Nullable T7 v7
    ) {
        TrackParamBundle ret = new TrackParamBundle(extras);
        ret.put(k1, v1);
        ret.put(k2, v2);
        ret.put(k3, v3);
        ret.put(k4, v4);
        ret.put(k5, v5);
        ret.put(k6, v6);
        ret.put(k7, v7);
        return ret;
    }

    public static <
            T1 extends Serializable
            >
    TrackParamBundle toParamsMap(
            @NonNull TrackParam<T1> k1, @Nullable T1 v1
    ) {
        TrackParamBundle ret = new TrackParamBundle();
        ret.put(k1, v1);
        return ret;
    }

    public static <
            T1 extends Serializable,
            T2 extends Serializable
            >
    TrackParamBundle toParamsMap(
            @NonNull TrackParam<T1> k1, @Nullable T1 v1,
            @NonNull TrackParam<T2> k2, @Nullable T2 v2
    ) {
        TrackParamBundle ret = new TrackParamBundle();
        ret.put(k1, v1);
        ret.put(k2, v2);
        return ret;
    }

    public static <
            T1 extends Serializable,
            T2 extends Serializable,
            T3 extends Serializable
            >
    TrackParamBundle toParamsMap(
            @NonNull TrackParam<T1> k1, @Nullable T1 v1,
            @NonNull TrackParam<T2> k2, @Nullable T2 v2,
            @NonNull TrackParam<T3> k3, @Nullable T3 v3
    ) {
        TrackParamBundle ret = new TrackParamBundle();
        ret.put(k1, v1);
        ret.put(k2, v2);
        ret.put(k3, v3);
        return ret;
    }

    public static <
            T1 extends Serializable,
            T2 extends Serializable,
            T3 extends Serializable,
            T4 extends Serializable
            >
    TrackParamBundle toParamsMap(
            @NonNull TrackParam<T1> k1, @Nullable T1 v1,
            @NonNull TrackParam<T2> k2, @Nullable T2 v2,
            @NonNull TrackParam<T3> k3, @Nullable T3 v3,
            @NonNull TrackParam<T4> k4, @Nullable T4 v4
    ) {
        TrackParamBundle ret = new TrackParamBundle();
        ret.put(k1, v1);
        ret.put(k2, v2);
        ret.put(k3, v3);
        ret.put(k4, v4);
        return ret;
    }

    public static <
            T1 extends Serializable,
            T2 extends Serializable,
            T3 extends Serializable,
            T4 extends Serializable,
            T5 extends Serializable
            >
    TrackParamBundle toParamsMap(
            @NonNull TrackParam<T1> k1, @Nullable T1 v1,
            @NonNull TrackParam<T2> k2, @Nullable T2 v2,
            @NonNull TrackParam<T3> k3, @Nullable T3 v3,
            @NonNull TrackParam<T4> k4, @Nullable T4 v4,
            @NonNull TrackParam<T5> k5, @Nullable T5 v5
    ) {
        TrackParamBundle ret = new TrackParamBundle();
        ret.put(k1, v1);
        ret.put(k2, v2);
        ret.put(k3, v3);
        ret.put(k4, v4);
        ret.put(k5, v5);
        return ret;
    }

    public static <
            T1 extends Serializable,
            T2 extends Serializable,
            T3 extends Serializable,
            T4 extends Serializable,
            T5 extends Serializable,
            T6 extends Serializable
            >
    TrackParamBundle toParamsMap(
            @NonNull TrackParam<T1> k1, @Nullable T1 v1,
            @NonNull TrackParam<T2> k2, @Nullable T2 v2,
            @NonNull TrackParam<T3> k3, @Nullable T3 v3,
            @NonNull TrackParam<T4> k4, @Nullable T4 v4,
            @NonNull TrackParam<T5> k5, @Nullable T5 v5,
            @NonNull TrackParam<T6> k6, @Nullable T6 v6
    ) {
        TrackParamBundle ret = new TrackParamBundle();
        ret.put(k1, v1);
        ret.put(k2, v2);
        ret.put(k3, v3);
        ret.put(k4, v4);
        ret.put(k5, v5);
        ret.put(k6, v6);
        return ret;
    }

    public static <
            T1 extends Serializable,
            T2 extends Serializable,
            T3 extends Serializable,
            T4 extends Serializable,
            T5 extends Serializable,
            T6 extends Serializable,
            T7 extends Serializable
            >
    TrackParamBundle toParamsMap(
            @NonNull TrackParam<T1> k1, @Nullable T1 v1,
            @NonNull TrackParam<T2> k2, @Nullable T2 v2,
            @NonNull TrackParam<T3> k3, @Nullable T3 v3,
            @NonNull TrackParam<T4> k4, @Nullable T4 v4,
            @NonNull TrackParam<T5> k5, @Nullable T5 v5,
            @NonNull TrackParam<T6> k6, @Nullable T6 v6,
            @NonNull TrackParam<T7> k7, @Nullable T7 v7
    ) {
        TrackParamBundle ret = new TrackParamBundle();
        ret.put(k1, v1);
        ret.put(k2, v2);
        ret.put(k3, v3);
        ret.put(k4, v4);
        ret.put(k5, v5);
        ret.put(k6, v6);
        ret.put(k7, v7);
        return ret;
    }

    public <T extends Serializable> void put(@NonNull TrackParam<T> k, @Nullable T v) {
        this.map.put(k, v);
    }

    public void putAll(@Nullable TrackParamBundle src) {
        if (src != null) {
            for (Map.Entry<TrackParam, Object> entry : map.entrySet()) {
                this.map.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @NonNull
    public Bundle toFirebaseBundle() {
        Bundle ret = new Bundle();
        for (Map.Entry entry : map.entrySet()) {
            //noinspection unchecked
            putParam(ret, entry);
        }
        return ret;
    }

    private <T extends Serializable> void putParam(Bundle ret, Map.Entry<TrackParam<T>, T> entry) {
        entry.getKey().put(ret, entry.getValue());
    }


    @Override
    public String toString() {
        return String.valueOf(map);
    }
}
