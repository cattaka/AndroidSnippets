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
public class TrackParams implements Serializable {
    private Map<TrackKey, Object> map = new LinkedHashMap<>();

    public TrackParams() {
    }

    public TrackParams(@Nullable TrackParams orig) {
        if (orig != null) {
            this.map.putAll(orig.map);
        }
    }

    public static <
            T1 extends Serializable
            >
    TrackParams toParamsMapWithExtras(
            @Nullable TrackParams extras,
            @NonNull TrackKey<T1> k1, @Nullable T1 v1
    ) {
        TrackParams ret = new TrackParams(extras);
        ret.put(k1, v1);
        return ret;
    }

    public static <
            T1 extends Serializable,
            T2 extends Serializable
            >
    TrackParams toParamsMapWithExtras(
            @Nullable TrackParams extras,
            @NonNull TrackKey<T1> k1, @Nullable T1 v1,
            @NonNull TrackKey<T2> k2, @Nullable T2 v2
    ) {
        TrackParams ret = new TrackParams(extras);
        ret.put(k1, v1);
        ret.put(k2, v2);
        return ret;
    }

    public static <
            T1 extends Serializable,
            T2 extends Serializable,
            T3 extends Serializable
            >
    TrackParams toParamsMapWithExtras(
            @Nullable TrackParams extras,
            @NonNull TrackKey<T1> k1, @Nullable T1 v1,
            @NonNull TrackKey<T2> k2, @Nullable T2 v2,
            @NonNull TrackKey<T3> k3, @Nullable T3 v3
    ) {
        TrackParams ret = new TrackParams(extras);
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
    TrackParams toParamsMapWithExtras(
            @Nullable TrackParams extras,
            @NonNull TrackKey<T1> k1, @Nullable T1 v1,
            @NonNull TrackKey<T2> k2, @Nullable T2 v2,
            @NonNull TrackKey<T3> k3, @Nullable T3 v3,
            @NonNull TrackKey<T4> k4, @Nullable T4 v4
    ) {
        TrackParams ret = new TrackParams(extras);
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
    TrackParams toParamsMapWithExtras(
            @Nullable TrackParams extras,
            @NonNull TrackKey<T1> k1, @Nullable T1 v1,
            @NonNull TrackKey<T2> k2, @Nullable T2 v2,
            @NonNull TrackKey<T3> k3, @Nullable T3 v3,
            @NonNull TrackKey<T4> k4, @Nullable T4 v4,
            @NonNull TrackKey<T5> k5, @Nullable T5 v5
    ) {
        TrackParams ret = new TrackParams(extras);
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
    TrackParams toParamsMapWithExtras(
            @Nullable TrackParams extras,
            @NonNull TrackKey<T1> k1, @Nullable T1 v1,
            @NonNull TrackKey<T2> k2, @Nullable T2 v2,
            @NonNull TrackKey<T3> k3, @Nullable T3 v3,
            @NonNull TrackKey<T4> k4, @Nullable T4 v4,
            @NonNull TrackKey<T5> k5, @Nullable T5 v5,
            @NonNull TrackKey<T6> k6, @Nullable T6 v6
    ) {
        TrackParams ret = new TrackParams(extras);
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
    TrackParams toParamsMapWithExtras(
            @Nullable TrackParams extras,
            @NonNull TrackKey<T1> k1, @Nullable T1 v1,
            @NonNull TrackKey<T2> k2, @Nullable T2 v2,
            @NonNull TrackKey<T3> k3, @Nullable T3 v3,
            @NonNull TrackKey<T4> k4, @Nullable T4 v4,
            @NonNull TrackKey<T5> k5, @Nullable T5 v5,
            @NonNull TrackKey<T6> k6, @Nullable T6 v6,
            @NonNull TrackKey<T7> k7, @Nullable T7 v7
    ) {
        TrackParams ret = new TrackParams(extras);
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
    TrackParams toParamsMap(
            @NonNull TrackKey<T1> k1, @Nullable T1 v1
    ) {
        TrackParams ret = new TrackParams();
        ret.put(k1, v1);
        return ret;
    }

    public static <
            T1 extends Serializable,
            T2 extends Serializable
            >
    TrackParams toParamsMap(
            @NonNull TrackKey<T1> k1, @Nullable T1 v1,
            @NonNull TrackKey<T2> k2, @Nullable T2 v2
    ) {
        TrackParams ret = new TrackParams();
        ret.put(k1, v1);
        ret.put(k2, v2);
        return ret;
    }

    public static <
            T1 extends Serializable,
            T2 extends Serializable,
            T3 extends Serializable
            >
    TrackParams toParamsMap(
            @NonNull TrackKey<T1> k1, @Nullable T1 v1,
            @NonNull TrackKey<T2> k2, @Nullable T2 v2,
            @NonNull TrackKey<T3> k3, @Nullable T3 v3
    ) {
        TrackParams ret = new TrackParams();
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
    TrackParams toParamsMap(
            @NonNull TrackKey<T1> k1, @Nullable T1 v1,
            @NonNull TrackKey<T2> k2, @Nullable T2 v2,
            @NonNull TrackKey<T3> k3, @Nullable T3 v3,
            @NonNull TrackKey<T4> k4, @Nullable T4 v4
    ) {
        TrackParams ret = new TrackParams();
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
    TrackParams toParamsMap(
            @NonNull TrackKey<T1> k1, @Nullable T1 v1,
            @NonNull TrackKey<T2> k2, @Nullable T2 v2,
            @NonNull TrackKey<T3> k3, @Nullable T3 v3,
            @NonNull TrackKey<T4> k4, @Nullable T4 v4,
            @NonNull TrackKey<T5> k5, @Nullable T5 v5
    ) {
        TrackParams ret = new TrackParams();
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
    TrackParams toParamsMap(
            @NonNull TrackKey<T1> k1, @Nullable T1 v1,
            @NonNull TrackKey<T2> k2, @Nullable T2 v2,
            @NonNull TrackKey<T3> k3, @Nullable T3 v3,
            @NonNull TrackKey<T4> k4, @Nullable T4 v4,
            @NonNull TrackKey<T5> k5, @Nullable T5 v5,
            @NonNull TrackKey<T6> k6, @Nullable T6 v6
    ) {
        TrackParams ret = new TrackParams();
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
    TrackParams toParamsMap(
            @NonNull TrackKey<T1> k1, @Nullable T1 v1,
            @NonNull TrackKey<T2> k2, @Nullable T2 v2,
            @NonNull TrackKey<T3> k3, @Nullable T3 v3,
            @NonNull TrackKey<T4> k4, @Nullable T4 v4,
            @NonNull TrackKey<T5> k5, @Nullable T5 v5,
            @NonNull TrackKey<T6> k6, @Nullable T6 v6,
            @NonNull TrackKey<T7> k7, @Nullable T7 v7
    ) {
        TrackParams ret = new TrackParams();
        ret.put(k1, v1);
        ret.put(k2, v2);
        ret.put(k3, v3);
        ret.put(k4, v4);
        ret.put(k5, v5);
        ret.put(k6, v6);
        ret.put(k7, v7);
        return ret;
    }

    public <T extends Serializable> void put(@NonNull TrackKey<T> k, @Nullable T v) {
        this.map.put(k, v);
    }

    public void putAll(@Nullable TrackParams src) {
        if (src != null) {
            for (Map.Entry<TrackKey, Object> entry : map.entrySet()) {
                this.map.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @NonNull
    public Bundle toFirebaseBundle() {
        Bundle ret = new Bundle();
        for (Map.Entry<TrackKey, Object> entry : map.entrySet()) {
            TrackKey k = entry.getKey();
            Object v = entry.getValue();
            //noinspection unchecked
            k.func.put(ret, k.firebaseCode, v);
        }
        return ret;
    }

    @Override
    public String toString() {
        return String.valueOf(map);
    }
}
