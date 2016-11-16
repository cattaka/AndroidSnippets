package net.cattaka.android.snippets.camera;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.WindowManager;

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
public class TextureViewSurfaceHolder implements ISurfaceHolder<TextureView> {
    public static final int CENTER_CROP = 6;
    public static final int CENTER_INSIDE = 7;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({CENTER_CROP, CENTER_INSIDE})
    public @interface ScaleType {
    }

    private TextureView mTextureView;
    private Surface mSurface;
    private ISurfaceHolderListener mListener;
    private int mScaleType = CENTER_CROP;

    TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
            if (mListener != null) {
                mListener.onChangeReadyForPrepare(TextureViewSurfaceHolder.this);
            }
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            if (mListener != null) {
                mListener.onChangeReadyForPrepare(TextureViewSurfaceHolder.this);
            }
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }
    };

    public TextureViewSurfaceHolder(@NonNull TextureView textureView) {
        this.mTextureView = textureView;
        mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
    }

    public TextureViewSurfaceHolder(@NonNull TextureView textureView, @ScaleType int scaleType) {
        this.mTextureView = textureView;
        mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
        mScaleType = scaleType;
    }

    @Override
    public Surface getSurface() {
        return mSurface;
    }

    @Override
    public boolean prepare(@NonNull CameraDevice device, @NonNull CameraCharacteristics characteristics) {
        StreamConfigurationMap scMap = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        Size size = (scMap != null) ? findBestPreviewSize(scMap, mTextureView.getWidth(), mTextureView.getHeight()) : null;
        if (size != null) {
            SurfaceTexture texture = mTextureView.getSurfaceTexture();
            texture.setDefaultBufferSize(size.getWidth(), size.getHeight());
            mSurface = new Surface(texture);
            adjustViewMatrix(mTextureView, size, mScaleType);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public TextureView getTarget() {
        return mTextureView;
    }

    @Override
    public boolean isReadyForPrepare() {
        return mTextureView.isAvailable();
    }

    @Override
    public void setSurfaceHolderListener(ISurfaceHolderListener listener) {
        mListener = listener;
    }

    private static void adjustViewMatrix(TextureView textureView, Size previewSize, @ScaleType int scaleType) {
        WindowManager windowManager = (WindowManager) textureView.getContext().getSystemService(Context.WINDOW_SERVICE);
        int rotation = windowManager.getDefaultDisplay().getRotation();

        int viewWidth = textureView.getWidth();
        int viewHeight = textureView.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(1.0f / (float) viewWidth, 1.0f / (float) viewHeight);
        if (Surface.ROTATION_90 == rotation) {
            matrix.postRotate(-90, 0.5f, 0.5f);
        } else if (Surface.ROTATION_180 == rotation) {
            matrix.postRotate(-180, 0.5f, 0.5f);
        } else if (Surface.ROTATION_270 == rotation) {
            matrix.postRotate(-270, 0.5f, 0.5f);
        }

        int previewWidth;
        int previewHeight;
        if (Surface.ROTATION_90 == rotation) {
            previewWidth = previewSize.getWidth();
            previewHeight = previewSize.getHeight();
        } else if (Surface.ROTATION_180 == rotation) {
            previewWidth = previewSize.getHeight();
            previewHeight = previewSize.getWidth();
        } else if (Surface.ROTATION_270 == rotation) {
            previewWidth = previewSize.getWidth();
            previewHeight = previewSize.getHeight();
        } else {
            previewWidth = previewSize.getHeight();
            previewHeight = previewSize.getWidth();
        }
        float scaleX = (float) viewWidth / previewWidth;
        float scaleY = (float) viewHeight / previewHeight;
        float scale = (scaleType == CENTER_CROP) ? Math.max(scaleX, scaleY) : Math.min(scaleX, scaleY);
        int correctWidth = (int) (previewWidth * scale);
        int correctHeight = (int) (previewHeight * scale);
        matrix.postScale(correctWidth, correctHeight);
        matrix.postTranslate((viewWidth - correctWidth) / 2, (viewHeight - correctHeight) / 2);

        textureView.setTransform(matrix);
    }

    private static Size findBestPreviewSize(@NonNull StreamConfigurationMap scMap, int width, int height) {
        List<Size> sizes = new ArrayList<>(Arrays.asList(scMap.getOutputSizes(SurfaceTexture.class)));
        for (Iterator<Size> itr = sizes.iterator(); itr.hasNext(); ) {
            Size size = itr.next();
            if (size.getWidth() < width || size.getHeight() < height) {
                itr.remove();
            }
        }
        if (sizes.size() == 0) {
            sizes = new ArrayList<>(Arrays.asList(scMap.getOutputSizes(SurfaceTexture.class)));
        }
        Collections.sort(sizes, new Comparator<Size>() {
            @Override
            public int compare(Size s1, Size s2) {
                return s2.getWidth() * s2.getHeight() - s1.getWidth() * s1.getHeight();
            }
        });
        return (sizes.size() > 0) ? sizes.get(sizes.size() - 1) : null;
    }
}
