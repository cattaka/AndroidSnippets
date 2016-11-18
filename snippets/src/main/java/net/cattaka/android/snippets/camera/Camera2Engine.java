package net.cattaka.android.snippets.camera;

import android.annotation.TargetApi;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.Surface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cattaka on 16/11/15.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class Camera2Engine {
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    private CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice cameraDevice) {
            mCameraDevice = cameraDevice;
            goNext();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            mCameraDevice = null;
        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int i) {

        }
    };

    private CameraCaptureSession.StateCallback mSessionStateCallback = new CameraCaptureSession.StateCallback() {
        @Override
        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
            if (mCameraDevice != null) {
                mCameraCaptureSession = cameraCaptureSession;
                goNext();
            }
        }

        @Override
        public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

        }
    };

    private CameraCaptureSession.CaptureCallback mCaptureCallback = new CameraCaptureSession.CaptureCallback() {
        @Override
        public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
            super.onCaptureCompleted(session, request, result);
        }

        @Override
        public void onCaptureBufferLost(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull Surface target, long frameNumber) {
            super.onCaptureBufferLost(session, request, target, frameNumber);
        }

        @Override
        public void onCaptureSequenceAborted(@NonNull CameraCaptureSession session, int sequenceId) {
            super.onCaptureSequenceAborted(session, sequenceId);
        }

        @Override
        public void onCaptureSequenceCompleted(@NonNull CameraCaptureSession session, int sequenceId, long frameNumber) {
            super.onCaptureSequenceCompleted(session, sequenceId, frameNumber);
            popCaptureRequestDescription(sequenceId);
        }

        @Override
        public void onCaptureFailed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureFailure failure) {
            super.onCaptureFailed(session, request, failure);
        }

        @Override
        public void onCaptureProgressed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureResult partialResult) {
            super.onCaptureProgressed(session, request, partialResult);
        }

        @Override
        public void onCaptureStarted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, long timestamp, long frameNumber) {
            super.onCaptureStarted(session, request, timestamp, frameNumber);
        }

        private void popCaptureRequestDescription(int sequenceId) {
            ICaptureRequestDescription description = mCaptureRequestDescriptionMap.get(sequenceId);
            if (description != null && description.isOneShot()) {
                mCaptureRequestDescriptions.remove(description);
                mCaptureRequest = null;
            }
            goNext();
        }
    };

    private ISurfaceHolder.ISurfaceHolderListener mSurfaceHolderListener = new ISurfaceHolder.ISurfaceHolderListener() {
        @Override
        public void onChangeReadyForPrepare(ISurfaceHolder<?> target) {
            goNext();
        }
    };

    private CameraManager mCameraManager;
    private int mFacing = CameraMetadata.LENS_FACING_BACK;
    private List<ISurfaceHolder<?>> mSurfaceHolders = new ArrayList<>();
    private List<ISurfaceHolder<?>> mPreparedSurfaceHolders = new ArrayList<>();
    private ICamera2EngineListener mCamera2EngineListener;
    private List<ICaptureRequestDescription> mCaptureRequestDescriptions = new ArrayList<>();

    private boolean mRunning;

    private CameraDevice mCameraDevice;
    private CameraCharacteristics mCharacteristics;
    private CameraCaptureSession mCameraCaptureSession;
    private CaptureRequest mCaptureRequest;
    private boolean mRequireRecreateCaptureRequest;
    private Map<Integer, ICaptureRequestDescription> mCaptureRequestDescriptionMap = new HashMap<>();
    private int mSensorOrientation;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Camera2Engine(CameraManager cameraManager) {
        mCameraManager = cameraManager;
    }

    private void goNext() {
        if (mRunning) {
            for (ISurfaceHolder surfaceHolder : mSurfaceHolders) {
                if (!surfaceHolder.isReadyForPrepare()) {
                    return;
                }
            }
            if (mCameraDevice == null) {
                try {
                    String cameraId = findCameraId(mCameraManager, mFacing);
                    if (cameraId == null) {
                        return;
                    }
                    mCharacteristics = mCameraManager.getCameraCharacteristics(cameraId);
                    Integer sensorOrientation = mCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
                    if (sensorOrientation != null) {
                        mSensorOrientation = sensorOrientation;
                    }
                    //noinspection MissingPermission
                    mCameraManager.openCamera(cameraId, mStateCallback, sHandler);
                } catch (CameraAccessException e) {
                    if (mCamera2EngineListener != null) {
                        mCamera2EngineListener.onCameraAccessException(e);
                    }
                }
                return;
            }
            if (mCameraCaptureSession == null) {
                mPreparedSurfaceHolders.clear();
                List<Surface> surfaces = new ArrayList<>();
                for (ISurfaceHolder surfaceHolder : mSurfaceHolders) {
                    boolean success = surfaceHolder.prepare(mCameraDevice, mCharacteristics);
                    if (success) {
                        mPreparedSurfaceHolders.add(surfaceHolder);
                        surfaces.add(surfaceHolder.getSurface());
                    }
                    if (mCamera2EngineListener != null) {
                        mCamera2EngineListener.onSurfaceHolderPrepared(surfaceHolder, success);
                    }
                }
                try {
                    mCameraDevice.createCaptureSession(surfaces, mSessionStateCallback, sHandler);
                } catch (CameraAccessException e) {
                    if (mCamera2EngineListener != null) {
                        mCamera2EngineListener.onCameraAccessException(e);
                    }
                }
                return;
            }
            if (mRequireRecreateCaptureRequest && mCaptureRequest != null) {
                mCaptureRequest = null;
                try {
                    mCameraCaptureSession.abortCaptures();
                } catch (CameraAccessException e) {
                    if (mCamera2EngineListener != null) {
                        mCamera2EngineListener.onCameraAccessException(e);
                    }
                }
            }
            if (mCaptureRequest == null && mCaptureRequestDescriptions.size() > 0) {
                mRequireRecreateCaptureRequest = false;
                ICaptureRequestDescription description = mCaptureRequestDescriptions.get(mCaptureRequestDescriptions.size() - 1);
                try {
                    Pair<Integer, CaptureRequest> pair = description.setupCaptureRequest(mCameraDevice, mCameraCaptureSession, mCaptureCallback, sHandler);
                    mCaptureRequest = pair.second;
                    mCaptureRequestDescriptionMap.put(pair.first, description);
                } catch (CameraAccessException e) {
                    if (mCamera2EngineListener != null) {
                        mCamera2EngineListener.onCameraAccessException(e);
                    }
                }
                return;
            }
        } else {
            mCaptureRequest = null;
            if (mCameraCaptureSession != null) {
                mCameraCaptureSession.close();
                mCameraCaptureSession = null;
            }
            if (mCameraDevice != null) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
        }
    }

    private static String findCameraId(CameraManager cameraManager, int facing) throws CameraAccessException {
        String[] cameraIds = cameraManager.getCameraIdList();
        for (String cameraId : cameraIds) {
            CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraId);
            Integer t = characteristics.get(CameraCharacteristics.LENS_FACING);
            if (t != null && t == facing) {
                return cameraId;
            }
        }
        return (cameraIds.length > 0) ? cameraIds[0] : null;
    }

    public void start() {
        mRunning = true;
        goNext();
    }

    public void release() {
        mRunning = false;
        goNext();
    }

    public boolean isRunning() {
        return mRunning;
    }

    public int getFacing() {
        return mFacing;
    }

    public void setFacing(int facing) {
        if (mFacing != facing) {
            mFacing = facing;
            boolean oldRunning = mRunning;
            release();
            if (oldRunning) {
                start();
            }
        }
    }

    public int getSensorOrientation() {
        return mSensorOrientation;
    }

    public void addSurfaceHolder(ISurfaceHolder<?> surfaceHolder) {
        mSurfaceHolders.add(surfaceHolder);
        surfaceHolder.setSurfaceHolderListener(mSurfaceHolderListener);
    }

    public void startCaptureRequest(ICaptureRequestDescription description) {
        mRequireRecreateCaptureRequest = true;
        mCaptureRequestDescriptions.add(description);
        goNext();
    }

    public void setCamera2EngineListener(ICamera2EngineListener camera2EngineListener) {
        mCamera2EngineListener = camera2EngineListener;
    }

    public interface ICamera2EngineListener {
        void onSurfaceHolderPrepared(ISurfaceHolder<?> surfaceHolder, boolean success);

        void onCameraAccessException(CameraAccessException e);
    }
}
