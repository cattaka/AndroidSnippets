package net.cattaka.android.snippets.example.tracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TrackKey<T extends Serializable> implements Serializable {
    private static final IFunc<Boolean> BOOL = new IFunc<Boolean>() {
        @Override
        public void put(@NonNull Bundle dest, @NonNull String key, @Nullable Boolean value) {
            if (value != null) {
                dest.putBoolean(key, value);
            } else {
                dest.remove(key);
            }
        }

        @Override
        public void put(@NonNull Map<String, Object> dest, @NonNull String key, @Nullable Boolean value) {
            dest.put(key, value);
        }
    };

    private static final IFunc<Integer> INT = new IFunc<Integer>() {
        @Override
        public void put(@NonNull Bundle dest, @NonNull String key, @Nullable Integer value) {
            if (value != null) {
                dest.putInt(key, value);
            } else {
                dest.remove(key);
            }
        }

        @Override
        public void put(@NonNull Map<String, Object> dest, @NonNull String key, @Nullable Integer value) {
            dest.put(key, value);
        }
    };
    private static final IFunc<Long> LONG = new IFunc<Long>() {
        @Override
        public void put(@NonNull Bundle dest, @NonNull String key, @Nullable Long value) {
            if (value != null) {
                dest.putLong(key, value);
            } else {
                dest.remove(key);
            }
        }

        @Override
        public void put(@NonNull Map<String, Object> dest, @NonNull String key, @Nullable Long value) {
            dest.put(key, value);
        }
    };
    private static final IFunc<Double> DOUBLE = new IFunc<Double>() {
        @Override
        public void put(@NonNull Bundle dest, @NonNull String key, @Nullable Double value) {
            if (value != null) {
                dest.putDouble(key, value);
            } else {
                dest.remove(key);
            }
        }

        @Override
        public void put(@NonNull Map<String, Object> dest, @NonNull String key, @Nullable Double value) {
            dest.put(key, value);
        }
    };
    private static final IFunc<String> STR = new IFunc<String>() {
        @Override
        public void put(@NonNull Bundle dest, @NonNull String key, @Nullable String value) {
            dest.putString(key, truncate(value, 100));
        }

        @Override
        public void put(@NonNull Map<String, Object> dest, @NonNull String key, @Nullable String value) {
            dest.put(key, value);
        }
    };
    private static final IFunc<String[]> STR_ARRAY = new IFunc<String[]>() {
        @Override
        public void put(@NonNull Bundle dest, @NonNull String key, @Nullable String[] value) {
            String str = (value != null) ? join(Arrays.asList(value), ',', '"') : null;
            dest.putString(key, truncate(str, 100));
        }

        @Override
        public void put(@NonNull Map<String, Object> dest, @NonNull String key, @Nullable String[] value) {
            dest.put(key, value);
        }
    };
    private static final IFunc<int[]> INT_ARRAY = new IFunc<int[]>() {
        @Override
        public void put(@NonNull Bundle dest, @NonNull String key, @Nullable int[] value) {
            if (value != null) {
                ArrayList<Integer> list = new ArrayList<>();
                for (int v : value) {
                    list.add(v);
                }
                dest.putIntegerArrayList(key, list);
            } else {
                dest.remove(key);
            }
        }

        @Override
        public void put(@NonNull Map<String, Object> dest, @NonNull String key, @Nullable int[] value) {
            dest.put(key, value);
        }
    };
    private static final IFunc<Serializable> OBJ = new IFunc<Serializable>() {
        @Override
        public void put(@NonNull Bundle dest, @NonNull String key, @Nullable Serializable value) {
            dest.putString(key, truncate(String.valueOf(value), 100));
        }

        @Override
        public void put(@NonNull Map<String, Object> dest, @NonNull String key, @Nullable Serializable value) {
            dest.put(key, String.valueOf(value));
        }
    };


    // NOTE: From FirebaseAnalytics
    public static final TrackKey<String> ACHIEVEMENT_ID = new TrackKey<>(STR, FirebaseAnalytics.Param.ACHIEVEMENT_ID);
    public static final TrackKey<Serializable> ACLID = new TrackKey<>(OBJ, FirebaseAnalytics.Param.ACLID);
    public static final TrackKey<Serializable> AFFILIATION = new TrackKey<>(OBJ, FirebaseAnalytics.Param.AFFILIATION);
    public static final TrackKey<Serializable> CAMPAIGN = new TrackKey<>(OBJ, FirebaseAnalytics.Param.CAMPAIGN);
    public static final TrackKey<String> CHARACTER = new TrackKey<>(STR, FirebaseAnalytics.Param.CHARACTER);
    public static final TrackKey<Serializable> CHECKOUT_OPTION = new TrackKey<>(OBJ, FirebaseAnalytics.Param.CHECKOUT_OPTION);
    public static final TrackKey<Integer> CHECKOUT_STEP = new TrackKey<>(INT, FirebaseAnalytics.Param.CHECKOUT_STEP);
    public static final TrackKey<Serializable> CONTENT = new TrackKey<>(OBJ, FirebaseAnalytics.Param.CONTENT);
    public static final TrackKey<String> CONTENT_TYPE = new TrackKey<>(STR, FirebaseAnalytics.Param.CONTENT_TYPE);
    public static final TrackKey<String> COUPON = new TrackKey<>(STR, FirebaseAnalytics.Param.COUPON);
    public static final TrackKey<Serializable> CP1 = new TrackKey<>(OBJ, FirebaseAnalytics.Param.CP1);
    public static final TrackKey<Serializable> CREATIVE_NAME = new TrackKey<>(OBJ, FirebaseAnalytics.Param.CREATIVE_NAME);
    public static final TrackKey<Serializable> CREATIVE_SLOT = new TrackKey<>(OBJ, FirebaseAnalytics.Param.CREATIVE_SLOT);
    public static final TrackKey<String> CURRENCY = new TrackKey<>(STR, FirebaseAnalytics.Param.CURRENCY);
    public static final TrackKey<String> DESTINATION = new TrackKey<>(STR, FirebaseAnalytics.Param.DESTINATION);
    public static final TrackKey<String> END_DATE = new TrackKey<>(STR, FirebaseAnalytics.Param.END_DATE);
    public static final TrackKey<String> FLIGHT_NUMBER = new TrackKey<>(STR, FirebaseAnalytics.Param.FLIGHT_NUMBER);
    public static final TrackKey<String> GROUP_ID = new TrackKey<>(STR, FirebaseAnalytics.Param.GROUP_ID);
    public static final TrackKey<Integer> INDEX = new TrackKey<>(INT, FirebaseAnalytics.Param.INDEX);
    public static final TrackKey<String> ITEM_BRAND = new TrackKey<>(STR, FirebaseAnalytics.Param.ITEM_BRAND);
    public static final TrackKey<String> ITEM_CATEGORY = new TrackKey<>(STR, FirebaseAnalytics.Param.ITEM_CATEGORY);
    public static final TrackKey<String> ITEM_ID = new TrackKey<>(STR, FirebaseAnalytics.Param.ITEM_ID);
    public static final TrackKey<String> ITEM_LIST = new TrackKey<>(STR, FirebaseAnalytics.Param.ITEM_LIST);
    public static final TrackKey<String> ITEM_LOCATION_ID = new TrackKey<>(STR, FirebaseAnalytics.Param.ITEM_LOCATION_ID);
    public static final TrackKey<String> ITEM_NAME = new TrackKey<>(STR, FirebaseAnalytics.Param.ITEM_NAME);
    public static final TrackKey<Serializable> ITEM_VARIANT = new TrackKey<>(OBJ, FirebaseAnalytics.Param.ITEM_VARIANT);
    public static final TrackKey<Long> LEVEL = new TrackKey<>(LONG, FirebaseAnalytics.Param.LEVEL);
    public static final TrackKey<String> LOCATION = new TrackKey<>(STR, FirebaseAnalytics.Param.LOCATION);
    public static final TrackKey<Serializable> MEDIUM = new TrackKey<>(OBJ, FirebaseAnalytics.Param.MEDIUM);
    public static final TrackKey<Long> NUMBER_OF_NIGHTS = new TrackKey<>(LONG, FirebaseAnalytics.Param.NUMBER_OF_NIGHTS);
    public static final TrackKey<Long> NUMBER_OF_PASSENGERS = new TrackKey<>(LONG, FirebaseAnalytics.Param.NUMBER_OF_PASSENGERS);
    public static final TrackKey<Long> NUMBER_OF_ROOMS = new TrackKey<>(LONG, FirebaseAnalytics.Param.NUMBER_OF_ROOMS);
    public static final TrackKey<String> ORIGIN = new TrackKey<>(STR, FirebaseAnalytics.Param.ORIGIN);
    public static final TrackKey<Double> PRICE = new TrackKey<>(DOUBLE, FirebaseAnalytics.Param.PRICE);
    public static final TrackKey<Long> QUANTITY = new TrackKey<>(LONG, FirebaseAnalytics.Param.QUANTITY);
    public static final TrackKey<Long> SCORE = new TrackKey<>(LONG, FirebaseAnalytics.Param.SCORE);
    public static final TrackKey<String> SEARCH_TERM = new TrackKey<>(STR, FirebaseAnalytics.Param.SEARCH_TERM);
    public static final TrackKey<Double> SHIPPING = new TrackKey<>(DOUBLE, FirebaseAnalytics.Param.SHIPPING);
    public static final TrackKey<String> SIGN_UP_METHOD = new TrackKey<>(STR, FirebaseAnalytics.Param.SIGN_UP_METHOD);
    public static final TrackKey<Serializable> SOURCE = new TrackKey<>(OBJ, FirebaseAnalytics.Param.SOURCE);
    public static final TrackKey<String> START_DATE = new TrackKey<>(STR, FirebaseAnalytics.Param.START_DATE);
    public static final TrackKey<Double> TAX = new TrackKey<>(DOUBLE, FirebaseAnalytics.Param.TAX);
    public static final TrackKey<Serializable> TERM = new TrackKey<>(OBJ, FirebaseAnalytics.Param.TERM);
    public static final TrackKey<String> TRANSACTION_ID = new TrackKey<>(STR, FirebaseAnalytics.Param.TRANSACTION_ID);
    public static final TrackKey<String> TRAVEL_CLASS = new TrackKey<>(STR, FirebaseAnalytics.Param.TRAVEL_CLASS);
    public static final TrackKey<Serializable> VALUE = new TrackKey<>(OBJ, FirebaseAnalytics.Param.VALUE);
    public static final TrackKey<String> VIRTUAL_CURRENCY_NAME = new TrackKey<>(STR, FirebaseAnalytics.Param.VIRTUAL_CURRENCY_NAME);

    public final IFunc<T> func;
    public final String firebaseCode;

    /**
     * WARN: Do not change this public to avoid confusing.
     */
    private TrackKey(IFunc<T> func, String firebaseCode) {
        this.func = func;
        this.firebaseCode = firebaseCode;
    }

    @Override
    public String toString() {
        return firebaseCode;
    }

    // Utility methods

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

    // NOTE: 引数がたくさんのものが必要なら足してください

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

    // NOTE: 引数がたくさんのものが必要なら足してください

    public static String truncate(@Nullable String src, int length) {
        return (src != null && src.length() > length) ? src.substring(0, length) : src;
    }

    @Nullable
    public static String join(@NonNull List<String> src, char delim, char bracket) {
        if (src == null || src.size() == 0) {
            return null;
        }
        String bracketStr = "" + bracket;
        String bracketStr2 = "" + bracket + bracket;
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String s : src) {
            if (first) {
                first = false;
            } else {
                sb.append(delim);
            }
            if (s == null) {
                // none
            } else if (s.length() == 0) {
                sb.append(bracket);
                sb.append(bracket);
            } else if (s.indexOf(delim) != -1 || s.indexOf(bracket) != -1) {
                sb.append(bracket);
                sb.append(s.replace(bracketStr, bracketStr2));
                sb.append(bracket);
            } else {
                sb.append(s);
            }
        }
        return sb.toString();
    }

    public interface IFunc<T> extends Serializable {
        void put(@NonNull Bundle dest, String key, @Nullable T value);

        void put(@NonNull Map<String, Object> dest, String key, @Nullable T value);
    }
}
