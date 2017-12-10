package net.cattaka.android.snippets.example.tracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrackParam<T extends Serializable> implements Serializable {
    private static final IFunc<Boolean> BOOL = (IFunc<Boolean>) (dest, key, value) -> dest.putBoolean(key, value);
    private static final IFunc<Integer> INT = (IFunc<Integer>) (dest, key, value) -> dest.putInt(key, value);
    private static final IFunc<Long> LONG = (IFunc<Long>) (dest, key, value) -> dest.putLong(key, value);
    private static final IFunc<Double> DOUBLE = (IFunc<Double>) (dest, key, value) -> dest.putDouble(key, value);
    private static final IFunc<String> STR = (IFunc<String>) (dest, key, value) -> dest.putString(key, truncate(value, 100));
    private static final IFunc<String[]> STR_ARRAY = (IFunc<String[]>) (dest, key, value) -> {
        String str = join(Arrays.asList(value), ',', '"');
        dest.putString(key, truncate(str, 100));
    };
    private static final IFunc<int[]> INT_ARRAY = (IFunc<int[]>) (dest, key, value) -> {
        ArrayList<Integer> list = new ArrayList<>();
        for (int v : value) {
            list.add(v);
        }
        dest.putIntegerArrayList(key, list);
    };
    private static final IFunc<Serializable> OBJ = (IFunc<Serializable>) (dest, key, value) -> dest.putString(key, truncate(String.valueOf(value), 100));

    // Custom parameters
    public static final TrackParam<String> VIEW_NAME = new TrackParam<>(STR, "view_name");
    public static final TrackParam<Boolean> HAS_ERROR = new TrackParam<>(BOOL, "has_error");

    // NOTE: From FirebaseAnalytics
    public static final TrackParam<String> ACHIEVEMENT_ID = new TrackParam<>(STR, FirebaseAnalytics.Param.ACHIEVEMENT_ID);
    public static final TrackParam<Serializable> ACLID = new TrackParam<>(OBJ, FirebaseAnalytics.Param.ACLID);
    public static final TrackParam<Serializable> AFFILIATION = new TrackParam<>(OBJ, FirebaseAnalytics.Param.AFFILIATION);
    public static final TrackParam<Serializable> CAMPAIGN = new TrackParam<>(OBJ, FirebaseAnalytics.Param.CAMPAIGN);
    public static final TrackParam<String> CHARACTER = new TrackParam<>(STR, FirebaseAnalytics.Param.CHARACTER);
    public static final TrackParam<Serializable> CHECKOUT_OPTION = new TrackParam<>(OBJ, FirebaseAnalytics.Param.CHECKOUT_OPTION);
    public static final TrackParam<Integer> CHECKOUT_STEP = new TrackParam<>(INT, FirebaseAnalytics.Param.CHECKOUT_STEP);
    public static final TrackParam<Serializable> CONTENT = new TrackParam<>(OBJ, FirebaseAnalytics.Param.CONTENT);
    public static final TrackParam<String> CONTENT_TYPE = new TrackParam<>(STR, FirebaseAnalytics.Param.CONTENT_TYPE);
    public static final TrackParam<String> COUPON = new TrackParam<>(STR, FirebaseAnalytics.Param.COUPON);
    public static final TrackParam<Serializable> CP1 = new TrackParam<>(OBJ, FirebaseAnalytics.Param.CP1);
    public static final TrackParam<Serializable> CREATIVE_NAME = new TrackParam<>(OBJ, FirebaseAnalytics.Param.CREATIVE_NAME);
    public static final TrackParam<Serializable> CREATIVE_SLOT = new TrackParam<>(OBJ, FirebaseAnalytics.Param.CREATIVE_SLOT);
    public static final TrackParam<String> CURRENCY = new TrackParam<>(STR, FirebaseAnalytics.Param.CURRENCY);
    public static final TrackParam<String> DESTINATION = new TrackParam<>(STR, FirebaseAnalytics.Param.DESTINATION);
    public static final TrackParam<String> END_DATE = new TrackParam<>(STR, FirebaseAnalytics.Param.END_DATE);
    public static final TrackParam<String> FLIGHT_NUMBER = new TrackParam<>(STR, FirebaseAnalytics.Param.FLIGHT_NUMBER);
    public static final TrackParam<String> GROUP_ID = new TrackParam<>(STR, FirebaseAnalytics.Param.GROUP_ID);
    public static final TrackParam<Integer> INDEX = new TrackParam<>(INT, FirebaseAnalytics.Param.INDEX);
    public static final TrackParam<String> ITEM_BRAND = new TrackParam<>(STR, FirebaseAnalytics.Param.ITEM_BRAND);
    public static final TrackParam<String> ITEM_CATEGORY = new TrackParam<>(STR, FirebaseAnalytics.Param.ITEM_CATEGORY);
    public static final TrackParam<String> ITEM_ID = new TrackParam<>(STR, FirebaseAnalytics.Param.ITEM_ID);
    public static final TrackParam<String> ITEM_LIST = new TrackParam<>(STR, FirebaseAnalytics.Param.ITEM_LIST);
    public static final TrackParam<String> ITEM_LOCATION_ID = new TrackParam<>(STR, FirebaseAnalytics.Param.ITEM_LOCATION_ID);
    public static final TrackParam<String> ITEM_NAME = new TrackParam<>(STR, FirebaseAnalytics.Param.ITEM_NAME);
    public static final TrackParam<Serializable> ITEM_VARIANT = new TrackParam<>(OBJ, FirebaseAnalytics.Param.ITEM_VARIANT);
    public static final TrackParam<Long> LEVEL = new TrackParam<>(LONG, FirebaseAnalytics.Param.LEVEL);
    public static final TrackParam<String> LOCATION = new TrackParam<>(STR, FirebaseAnalytics.Param.LOCATION);
    public static final TrackParam<Serializable> MEDIUM = new TrackParam<>(OBJ, FirebaseAnalytics.Param.MEDIUM);
    public static final TrackParam<Long> NUMBER_OF_NIGHTS = new TrackParam<>(LONG, FirebaseAnalytics.Param.NUMBER_OF_NIGHTS);
    public static final TrackParam<Long> NUMBER_OF_PASSENGERS = new TrackParam<>(LONG, FirebaseAnalytics.Param.NUMBER_OF_PASSENGERS);
    public static final TrackParam<Long> NUMBER_OF_ROOMS = new TrackParam<>(LONG, FirebaseAnalytics.Param.NUMBER_OF_ROOMS);
    public static final TrackParam<String> ORIGIN = new TrackParam<>(STR, FirebaseAnalytics.Param.ORIGIN);
    public static final TrackParam<Double> PRICE = new TrackParam<>(DOUBLE, FirebaseAnalytics.Param.PRICE);
    public static final TrackParam<Long> QUANTITY = new TrackParam<>(LONG, FirebaseAnalytics.Param.QUANTITY);
    public static final TrackParam<Long> SCORE = new TrackParam<>(LONG, FirebaseAnalytics.Param.SCORE);
    public static final TrackParam<String> SEARCH_TERM = new TrackParam<>(STR, FirebaseAnalytics.Param.SEARCH_TERM);
    public static final TrackParam<Double> SHIPPING = new TrackParam<>(DOUBLE, FirebaseAnalytics.Param.SHIPPING);
    public static final TrackParam<String> SIGN_UP_METHOD = new TrackParam<>(STR, FirebaseAnalytics.Param.SIGN_UP_METHOD);
    public static final TrackParam<Serializable> SOURCE = new TrackParam<>(OBJ, FirebaseAnalytics.Param.SOURCE);
    public static final TrackParam<String> START_DATE = new TrackParam<>(STR, FirebaseAnalytics.Param.START_DATE);
    public static final TrackParam<Double> TAX = new TrackParam<>(DOUBLE, FirebaseAnalytics.Param.TAX);
    public static final TrackParam<Serializable> TERM = new TrackParam<>(OBJ, FirebaseAnalytics.Param.TERM);
    public static final TrackParam<String> TRANSACTION_ID = new TrackParam<>(STR, FirebaseAnalytics.Param.TRANSACTION_ID);
    public static final TrackParam<String> TRAVEL_CLASS = new TrackParam<>(STR, FirebaseAnalytics.Param.TRAVEL_CLASS);
    public static final TrackParam<Serializable> VALUE = new TrackParam<>(OBJ, FirebaseAnalytics.Param.VALUE);
    public static final TrackParam<String> VIRTUAL_CURRENCY_NAME = new TrackParam<>(STR, FirebaseAnalytics.Param.VIRTUAL_CURRENCY_NAME);

    private final IFunc<T> func;
    private final String code;

    /**
     * WARN: Do not change this public to avoid confusing.
     */
    private TrackParam(IFunc<T> func, String code) {
        this.func = func;
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }

    public void put(@NonNull Bundle dest, @Nullable T value) {
        if (value != null) {
            func.put(dest, code, value);
        } else {
            dest.remove(code);
        }
    }

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
        void put(@NonNull Bundle dest, String key, @NonNull T value);
    }
}
