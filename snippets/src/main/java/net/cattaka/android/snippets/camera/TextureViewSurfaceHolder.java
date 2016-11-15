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
import java.util.List;

/**
 * Created by cattaka on 16/11/15.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class TextureViewSurfaceHolder implements ISurfaceHolder<TextureView> {
    private TextureView mTextureView;
    private Surface mSurface;

    public TextureViewSurfaceHolder(@NonNull TextureView textureView) {
        this.mTextureView = textureView;
    }

    @Override
    public Surface getSurface() {
        return mSurface;
    }

    @Override
    public boolean prepare(@NonNull CameraDevice device, @NonNull CameraCharacteristics characteristics) {
        StreamConfigurationMap scMap = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        Size size = (scMap != null) ? findBestPreviewSize(scMap) : null;
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

    private static Size findBestPreviewSize(@NonNull StreamConfigurationMap scMap) {
        List<Size> size = new ArrayList<>(Arrays.asList(scMap.getOutputSizes(SurfaceTexture.class)));
        Collections.sort(size, new Comparator<Size>() {
            @Override
            public int compare(Size s1, Size s2) {
                return s2.getWidth() * s2.getHeight() - s1.getWidth() * s1.getHeight();
            }
        });
        return (size.size() > 0) ? size.get(0) : null;
    }
}
