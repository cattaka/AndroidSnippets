package net.cattaka.android.snippets.example.tracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by cattaka on 17/11/10.
 */ // NOTE: 外部にmapを晒すと型の整合性を壊す恐れが有るのラップしている
public class TrackParams implements Serializable {
    private Map<TrackKey, Object> map = new LinkedHashMap<>();

    public TrackParams() {
    }

    public TrackParams(@Nullable TrackParams orig) {
        if (orig != null) {
            this.map.putAll(orig.map);
        }
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
        return "TrackParams{" +
                "map=" + map +
                '}';
    }
}
