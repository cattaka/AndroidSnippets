package net.cattaka.android.snippets.example.tracker_v2;

import android.os.BaseBundle;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by cattaka on 18/02/06.
 */

public class Param<T> {
    public static final Param<Long> ITEM_ID = new Param<>("item_id", BaseBundle::putLong);
    public static final Param<String> ITEM_NAME = new Param<>("item_name", BaseBundle::putString);
    public static final Param<Long> DURATION = new Param<>("duration", BaseBundle::putLong);

    String key;
    IFunc<T> putFunc;

    private Param(String key, IFunc<T> putFunc) {
        this.key = key;
        this.putFunc = putFunc;
    }

    public interface IFunc<V> {
        void put(@NonNull Bundle dest, String key, @Nullable V value);
    }
}
