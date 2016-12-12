package net.cattaka.android.snippets.example.data;

import java.util.Locale;

/**
 * Created by takao on 2016/12/12.
 */

public class ColorItem {
    private int color;

    public ColorItem() {

    }

    public ColorItem(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%08X", color);
    }
}
