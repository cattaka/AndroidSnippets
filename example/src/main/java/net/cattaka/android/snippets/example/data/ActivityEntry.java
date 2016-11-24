package net.cattaka.android.snippets.example.data;

import android.app.Activity;

import net.cattaka.android.adaptertoolbox.data.ITreeItem;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cattaka on 16/05/22.
 */
public class ActivityEntry implements ITreeItem<ActivityEntry> {
    private String label;
    private Class<? extends Activity> clazz;
    private List<ActivityEntry> children;
    private int apiLevel;

    public ActivityEntry(String label, Class<? extends Activity> clazz, ActivityEntry... children) {
        this.label = label;
        this.clazz = clazz;
        this.children = Arrays.asList(children);
        this.apiLevel = 1;
    }

    public ActivityEntry(String label, Class<? extends Activity> clazz, int apiLevel, ActivityEntry... children) {
        this.label = label;
        this.clazz = clazz;
        this.children = Arrays.asList(children);
        this.apiLevel = apiLevel;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Class<? extends Activity> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends Activity> clazz) {
        this.clazz = clazz;
    }

    @Override
    public List<ActivityEntry> getChildren() {
        return children;
    }

    public void setChildren(List<ActivityEntry> children) {
        this.children = children;
    }

    public int getApiLevel() {
        return apiLevel;
    }
}
