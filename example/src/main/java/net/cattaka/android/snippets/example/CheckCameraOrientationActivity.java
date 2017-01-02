package net.cattaka.android.snippets.example;

import android.content.Context;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.hardware.Camera;
import android.hardware.camera2.CameraMetadata;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.WindowManager;

import net.cattaka.android.snippets.example.databinding.AndroidCheckCameraOrientationBinding;

/**
 * Created by cattaka on 17/01/02.
 */

public class CheckCameraOrientationActivity extends AppCompatActivity {
    AndroidCheckCameraOrientationBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.android_check_camera_orientation);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mBinding.setActivityOrientation(getActivityOrientation());
        mBinding.setDisplayOrientation(getDisplayOrientation());
        mBinding.setCameraOrientation(getCameraOrientation());
    }

    private String getActivityOrientation() {
        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                return "ORIENTATION_LANDSCAPE";
            case Configuration.ORIENTATION_PORTRAIT:
                return "ORIENTATION_PORTRAIT";
            case Configuration.ORIENTATION_SQUARE:
                return "ORIENTATION_SQUARE";
            case Configuration.ORIENTATION_UNDEFINED:
            default: {
                return "ORIENTATION_UNDEFINED";
            }
        }
    }

    private String getDisplayOrientation() {
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int rotation = windowManager.getDefaultDisplay().getRotation();
        switch (rotation) {
            case Surface.ROTATION_0:
                return "ROTATION_0";
            case Surface.ROTATION_90:
                return "ROTATION_90";
            case Surface.ROTATION_180:
                return "ROTATION_180";
            case Surface.ROTATION_270:
                return "ROTATION_270";
            default:
                return "ROTATION_UNDEFINED";
        }
    }

    private String getCameraOrientation() {
        Camera.CameraInfo info = new Camera.CameraInfo();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Camera.getCameraInfo(CameraMetadata.LENS_FACING_BACK, info);
        } else {
            Camera.getCameraInfo(1, info);
        }
        return String.valueOf(info.orientation);
    }
}
