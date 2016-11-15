package net.cattaka.android.snippets.camera;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/11/15.
 */
public class CaptureTarget {
    public static final int CHOOSE_SIZE_MODE_SMALLEST = 1;
    public static final int CHOOSE_SIZE_MODE_BIGGEST = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({CHOOSE_SIZE_MODE_SMALLEST, CHOOSE_SIZE_MODE_BIGGEST})
    public @interface ChooseSizeMode {
    }

    private List<Integer> supportedImageFormats;
    private int minWidth = 0;
    private int maxWidth = Integer.MAX_VALUE;
    private int minHeight = 0;
    private int maxHeight = Integer.MAX_VALUE;
    private int chooseSizeMode = CHOOSE_SIZE_MODE_SMALLEST;

    public List<Integer> getSupportedImageFormats() {
        return supportedImageFormats;
    }

    public void setSupportedImageFormats(List<Integer> supportedImageFormats) {
        this.supportedImageFormats = supportedImageFormats;
    }

    public int getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    @ChooseSizeMode
    public int getChooseSizeMode() {
        return chooseSizeMode;
    }

    public void setChooseSizeMode(@ChooseSizeMode int chooseSizeMode) {
        this.chooseSizeMode = chooseSizeMode;
    }

    public static class Builder {
        private List<Integer> supportedImageFormats = new ArrayList<>();
        private int minWidth = 0;
        private int maxWidth = Integer.MAX_VALUE;
        private int minHeight = 0;
        private int maxHeight = Integer.MAX_VALUE;
        private int chooseSizeMode = CHOOSE_SIZE_MODE_SMALLEST;

        public Builder() {
        }

        public CaptureTarget build() {
            CaptureTarget target = new CaptureTarget();
            target.setSupportedImageFormats(supportedImageFormats);
            target.setMinWidth(minWidth);
            target.setMaxWidth(maxWidth);
            target.setMinHeight(minHeight);
            target.setMaxHeight(maxHeight);
            return target;
        }

        public Builder setSupportedImageFormats(Integer supportedImageFormat) {
            supportedImageFormats.add(supportedImageFormat);
            return this;
        }

        public Builder setMinWidth(int minWidth) {
            this.minWidth = minWidth;
            return this;
        }

        public Builder setMaxWidth(int maxWidth) {
            this.maxWidth = maxWidth;
            return this;
        }

        public Builder setMinHeight(int minHeight) {
            this.minHeight = minHeight;
            return this;
        }

        public Builder setMaxHeight(int maxHeight) {
            this.maxHeight = maxHeight;
            return this;
        }

        public Builder setChooseSizeMode(@ChooseSizeMode int chooseSizeMode) {
            this.chooseSizeMode = chooseSizeMode;
            return this;
        }
    }
}
