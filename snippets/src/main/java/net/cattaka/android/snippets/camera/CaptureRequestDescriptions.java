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
import android.util.Pair;

/**
 * Created by cattaka on 16/11/15.
 */

public class CaptureRequestDescriptions {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static ICaptureRequestDescription preview(ISurfaceHolder... surfaceHolders) {
        return new PreviewCaptureRequestDescription(surfaceHolders);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static ICaptureRequestDescription zeroShutterLag(ISurfaceHolder... surfaceHolders) {
        return new ZeroShutterLagCaptureRequestDescription(surfaceHolders);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static class PreviewCaptureRequestDescription implements ICaptureRequestDescription {
        private ISurfaceHolder[] mSurfaceHolders;

        public PreviewCaptureRequestDescription(ISurfaceHolder... surfaceHolders) {
            mSurfaceHolders = surfaceHolders;
        }

        @Override
        public Pair<Integer, CaptureRequest> setupCaptureRequest(@NonNull CameraDevice cameraDevice, @NonNull CameraCaptureSession cameraCaptureSession, @Nullable CameraCaptureSession.CaptureCallback captureCallback, @Nullable Handler handler) throws CameraAccessException {
            CaptureRequest.Builder builder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            builder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            builder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            for (ISurfaceHolder surfaceHolder : mSurfaceHolders) {
                builder.addTarget(surfaceHolder.getSurface());
            }
            CaptureRequest captureRequest = builder.build();
            int id = cameraCaptureSession.setRepeatingRequest(captureRequest, captureCallback, handler);
            return new Pair<>(id, captureRequest);
        }

        @Override
        public boolean isOneShot() {
            return false;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static class ZeroShutterLagCaptureRequestDescription implements ICaptureRequestDescription {
        private ISurfaceHolder[] mSurfaceHolderss;

        public ZeroShutterLagCaptureRequestDescription(ISurfaceHolder... surfaceHolderss) {
            mSurfaceHolderss = surfaceHolderss;
        }

        @Override
        public Pair<Integer, CaptureRequest> setupCaptureRequest(@NonNull CameraDevice cameraDevice, @NonNull CameraCaptureSession cameraCaptureSession, @Nullable CameraCaptureSession.CaptureCallback captureCallback, @Nullable Handler handler) throws CameraAccessException {
            CaptureRequest.Builder builder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            for (ISurfaceHolder surfaceHolder : mSurfaceHolderss) {
                builder.addTarget(surfaceHolder.getSurface());
            }
            CaptureRequest captureRequest = builder.build();
            int id = cameraCaptureSession.capture(captureRequest, captureCallback, handler);
            return new Pair<>(id, captureRequest);
        }

        @Override
        public boolean isOneShot() {
            return true;
        }
    }
}
