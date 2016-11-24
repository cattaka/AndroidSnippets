package net.cattaka.android.snippets.camera;

import android.annotation.TargetApi;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.ImageReader;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Size;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Created by cattaka on 16/11/15.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ImageReaderDescription {
    public static final int CHOOSE_SIZE_MODE_SMALLEST = 1;
    public static final int CHOOSE_SIZE_MODE_BIGGEST = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({CHOOSE_SIZE_MODE_SMALLEST, CHOOSE_SIZE_MODE_BIGGEST})
    public @interface ChooseSizeMode {
    }

    private List<Integer> imageFormats;
    private int minWidth = 0;
    private int maxWidth = Integer.MAX_VALUE;
    private int minHeight = 0;
    private int maxHeight = Integer.MAX_VALUE;
    private int chooseSizeMode = CHOOSE_SIZE_MODE_SMALLEST;
    private int maxImages = 1;
    private boolean fallbackIfNoMatchSize = true;

    @Nullable
    public ImageReader findPrefferedImageReader(@NonNull StreamConfigurationMap scMap) {
        Integer imageFormat = null;
        {
            for (Integer format : imageFormats) {
                for (int supportedFormat : scMap.getOutputFormats()) {
                    if (format != null && format == supportedFormat) {
                        imageFormat = format;
                        break;
                    }
                }
            }
            if (imageFormat == null) {
                return null;
            }
        }

        List<Size> sizes = new ArrayList<>(Arrays.asList(scMap.getOutputSizes(imageFormat)));
        for (Iterator<Size> itr = sizes.iterator(); itr.hasNext(); ) {
            Size s = itr.next();
            if (s.getWidth() < minWidth || maxWidth < s.getWidth() || s.getHeight() < minHeight || maxHeight < s.getHeight()) {
                itr.remove();
            }
        }
        if (sizes.size() == 0 && fallbackIfNoMatchSize) {
            sizes.addAll(Arrays.asList(scMap.getOutputSizes(imageFormat)));
        }
        if (sizes.size() > 0) {
            Collections.sort(sizes, new Comparator<Size>() {
                @Override
                public int compare(Size s1, Size s2) {
                    return s1.getWidth() * s1.getHeight() - s2.getWidth() * s2.getHeight();
                }
            });
            Size size;
            if (chooseSizeMode == CHOOSE_SIZE_MODE_SMALLEST) {
                size = sizes.get(0);
            } else {
                size = sizes.get(sizes.size() - 1);
            }
            return ImageReader.newInstance(size.getWidth(), size.getHeight(), imageFormat, maxImages);
        } else {
            return null;
        }
    }

    public List<Integer> getImageFormats() {
        return imageFormats;
    }

    public void setImageFormats(List<Integer> imageFormats) {
        this.imageFormats = imageFormats;
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

    public int getMaxImages() {
        return maxImages;
    }

    public void setMaxImages(int maxImages) {
        this.maxImages = maxImages;
    }

    public boolean isFallbackIfNoMatchSize() {
        return fallbackIfNoMatchSize;
    }

    public void setFallbackIfNoMatchSize(boolean fallbackIfNoMatchSize) {
        this.fallbackIfNoMatchSize = fallbackIfNoMatchSize;
    }

    public static class Builder {
        private List<Integer> imageFormats = new ArrayList<>();
        private int minWidth = 0;
        private int maxWidth = Integer.MAX_VALUE;
        private int minHeight = 0;
        private int maxHeight = Integer.MAX_VALUE;
        private int chooseSizeMode = CHOOSE_SIZE_MODE_SMALLEST;
        private int maxImages = 1;
        private boolean fallbackIfNoMatchSize = true;

        public Builder() {
        }

        @NonNull
        public ImageReaderDescription build() {
            ImageReaderDescription target = new ImageReaderDescription();
            target.setImageFormats(imageFormats);
            target.setMinWidth(minWidth);
            target.setMaxWidth(maxWidth);
            target.setMinHeight(minHeight);
            target.setMaxHeight(maxHeight);
            target.setMaxImages(maxImages);
            target.setFallbackIfNoMatchSize(fallbackIfNoMatchSize);
            return target;
        }

        public Builder addImageFormats(Integer imageFormat) {
            imageFormats.add(imageFormat);
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

        public Builder setMaxImages(int maxImages) {
            this.maxImages = maxImages;
            return this;
        }

        public Builder setFallbackIfNoMatchSize(boolean fallbackIfNoMatchSize) {
            this.fallbackIfNoMatchSize = fallbackIfNoMatchSize;
            return this;
        }
    }
}
