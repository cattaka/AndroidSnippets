package net.cattaka.android.snippets.camera;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.support.annotation.NonNull;
import android.view.Surface;

/**
 * Created by cattaka on 16/11/15.
 */
public interface ISurfaceHolder<T> {
    Surface getSurface();

    boolean prepare(@NonNull CameraDevice device, @NonNull CameraCharacteristics characteristics);

    T getTarget();
}
