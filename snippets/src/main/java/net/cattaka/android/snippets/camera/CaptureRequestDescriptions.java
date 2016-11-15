package net.cattaka.android.snippets.camera;

import android.annotation.TargetApi;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Surface;

/**
 * Created by cattaka on 16/11/15.
 */

public class CaptureRequestDescriptions {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static ICaptureRequestDescription preview(Surface... surfaces) {
        return new PreviewCaptureRequestDescription(surfaces);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static ICaptureRequestDescription zeroShutterLag(Surface... surfaces) {
        return new ZeroShutterLagCaptureRequestDescription(surfaces);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static class PreviewCaptureRequestDescription implements ICaptureRequestDescription {
        private Surface[] mSurfaces;

        public PreviewCaptureRequestDescription(Surface... surfaces) {
            mSurfaces = surfaces;
        }

        @Override
        public CaptureRequest setupCaptureRequest(@NonNull CameraDevice cameraDevice, @NonNull CameraCaptureSession cameraCaptureSession, @Nullable CameraCaptureSession.CaptureCallback captureCallback, @Nullable Handler handler) throws CameraAccessException {
            CaptureRequest.Builder builder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            builder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            builder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            for (Surface surface : mSurfaces) {
                builder.addTarget(surface);
            }
            CaptureRequest captureRequest = builder.build();
            cameraCaptureSession.setRepeatingRequest(captureRequest, captureCallback, handler);
            return captureRequest;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static class ZeroShutterLagCaptureRequestDescription implements ICaptureRequestDescription {
        private Surface[] mSurfaces;

        public ZeroShutterLagCaptureRequestDescription(Surface... surfaces) {
            mSurfaces = surfaces;
        }

        @Override
        public CaptureRequest setupCaptureRequest(@NonNull CameraDevice cameraDevice, @NonNull CameraCaptureSession cameraCaptureSession, @Nullable CameraCaptureSession.CaptureCallback captureCallback, @Nullable Handler handler) throws CameraAccessException {
            CaptureRequest.Builder builder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_ZERO_SHUTTER_LAG);
            for (Surface surface : mSurfaces) {
                builder.addTarget(surface);
            }
            CaptureRequest captureRequest = builder.build();
            cameraCaptureSession.capture(captureRequest, captureCallback, handler);
            return captureRequest;
        }
    }
}
