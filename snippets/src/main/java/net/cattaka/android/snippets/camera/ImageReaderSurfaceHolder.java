package net.cattaka.android.snippets.camera;

import android.annotation.TargetApi;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.ImageReader;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.Surface;

/**
 * Created by cattaka on 16/11/15.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ImageReaderSurfaceHolder implements ISurfaceHolder<ImageReader> {
    private ImageReaderDescription mImageReaderDescription;
    private ImageReader mImageReader;
    private ISurfaceHolderListener mListener;

    public ImageReaderSurfaceHolder(@NonNull ImageReaderDescription imageReaderDescription) {
        mImageReaderDescription = imageReaderDescription;
    }

    @Override
    public Surface getSurface() {
        return (mImageReader != null) ? mImageReader.getSurface() : null;
    }

    @Override
    public boolean prepare(@NonNull CameraDevice device, @NonNull CameraCharacteristics characteristics) {
        StreamConfigurationMap scMap = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        mImageReader = (scMap != null) ? mImageReaderDescription.findPrefferedImageReader(scMap) : null;
        return mImageReader != null;
    }

    @Override
    public ImageReader getTarget() {
        return mImageReader;
    }

    @Override
    public boolean isReadyForPrepare() {
        return true;
    }

    @Override
    public void setSurfaceHolderListener(ISurfaceHolderListener listener) {
        mListener = listener;
    }
}
