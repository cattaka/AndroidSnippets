package net.cattaka.android.snippets.camera;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by cattaka on 16/11/15.
 */
public interface ICaptureRequestDescription {
    CaptureRequest setupCaptureRequest(@NonNull CameraDevice cameraDevice, @NonNull CameraCaptureSession cameraCaptureSession, @Nullable CameraCaptureSession.CaptureCallback listener, @Nullable Handler handler) throws CameraAccessException;
}
