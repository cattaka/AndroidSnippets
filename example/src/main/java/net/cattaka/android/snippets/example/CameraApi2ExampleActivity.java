package net.cattaka.android.snippets.example;

import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.TextureView;

import net.cattaka.android.snippets.camera.Camera2Engine;
import net.cattaka.android.snippets.camera.CaptureRequestDescriptions;
import net.cattaka.android.snippets.camera.TextureViewSurfaceHolder;

/**
 * Created by cattaka on 16/11/16.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CameraApi2ExampleActivity extends AppCompatActivity {

    Camera2Engine mCamera2Engine;
    TextureView mTexture1View;
    TextureView mTexture2View;

    TextureViewSurfaceHolder mTexture1SurfaceHolder;
    TextureViewSurfaceHolder mTexture2SurfaceHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_api_2_example);

        mTexture1View = (TextureView) findViewById(R.id.texture1);
        mTexture2View = (TextureView) findViewById(R.id.texture2);

        mTexture1SurfaceHolder = new TextureViewSurfaceHolder(mTexture1View);
        mTexture2SurfaceHolder = new TextureViewSurfaceHolder(mTexture2View);

        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        mCamera2Engine = new Camera2Engine(cameraManager);
        mCamera2Engine.addSurfaceHolder(mTexture1SurfaceHolder);
        mCamera2Engine.addSurfaceHolder(mTexture2SurfaceHolder);
        mCamera2Engine.startCaptureRequest(CaptureRequestDescriptions.preview(mTexture1SurfaceHolder, mTexture2SurfaceHolder));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCamera2Engine.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCamera2Engine.release();
    }
}
