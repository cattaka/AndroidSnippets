package net.cattaka.android.snippets.camera;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;

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
    private TextureView mTextureView;
    private Surface mSurface;
    private ISurfaceHolderListener mListener;

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
