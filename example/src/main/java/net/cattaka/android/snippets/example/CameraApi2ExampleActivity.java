package net.cattaka.android.snippets.example;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;

import net.cattaka.android.snippets.camera.Camera2Engine;
import net.cattaka.android.snippets.camera.CaptureRequestDescriptions;
import net.cattaka.android.snippets.camera.ISurfaceHolder;
import net.cattaka.android.snippets.camera.ImageReaderDescription;
import net.cattaka.android.snippets.camera.ImageReaderSurfaceHolder;
import net.cattaka.android.snippets.camera.TextureViewSurfaceHolder;

import java.nio.ByteBuffer;

/**
 * Created by cattaka on 16/11/16.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CameraApi2ExampleActivity extends AppCompatActivity implements View.OnClickListener {
    static Handler sHandler = new Handler(Looper.getMainLooper());

    Camera2Engine.ICamera2EngineListener mCamera2EngineListener = new Camera2Engine.ICamera2EngineListener() {
        @Override
        public void onSurfaceHolderPrepared(ISurfaceHolder<?> surfaceHolder, boolean success) {
            if (surfaceHolder == mImageReaderSurfaceHolder) {
                mImageReaderSurfaceHolder.getTarget().setOnImageAvailableListener(mOnImageAvailableListener, sHandler);
            }
        }

        @Override
        public void onCameraAccessException(CameraAccessException e) {
            // Handle me!
            throw new RuntimeException(e);
        }
    };

    ImageReader.OnImageAvailableListener mOnImageAvailableListener = new ImageReader.OnImageAvailableListener() {
        @Override
        public void onImageAvailable(ImageReader reader) {
            Image image = reader.acquireLatestImage();
            try {
                if (image.getFormat() == ImageFormat.JPEG) {
                    ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                    byte[] data = new byte[buffer.remaining()];
                    buffer.get(data);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    mImageView.setImageBitmap(bitmap);
                }
            } finally {
                image.close();
            }
        }
    };

    Camera2Engine mCamera2Engine;
    TextureView mTexture1View;
    TextureView mTexture2View;
    ImageView mImageView;

    TextureViewSurfaceHolder mTexture1SurfaceHolder;
    TextureViewSurfaceHolder mTexture2SurfaceHolder;
    ImageReaderSurfaceHolder mImageReaderSurfaceHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_api_2_example);

        mTexture1View = (TextureView) findViewById(R.id.texture1);
        mTexture2View = (TextureView) findViewById(R.id.texture2);
        mImageView = (ImageView) findViewById(R.id.image);

        findViewById(R.id.button_shutter).setOnClickListener(this);
        findViewById(R.id.button_front).setOnClickListener(this);
        findViewById(R.id.button_back).setOnClickListener(this);

        mTexture1SurfaceHolder = new TextureViewSurfaceHolder(mTexture1View, TextureViewSurfaceHolder.CENTER_CROP);
        mTexture2SurfaceHolder = new TextureViewSurfaceHolder(mTexture2View, TextureViewSurfaceHolder.CENTER_INSIDE);

        ImageReaderDescription imageReaderDescription = new ImageReaderDescription.Builder()
                .addImageFormats(ImageFormat.JPEG)
                .setChooseSizeMode(ImageReaderDescription.CHOOSE_SIZE_MODE_SMALLEST)
                .build();
        mImageReaderSurfaceHolder = new ImageReaderSurfaceHolder(imageReaderDescription);

        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        mCamera2Engine = new Camera2Engine(cameraManager);
        mCamera2Engine.setCamera2EngineListener(mCamera2EngineListener);
        mCamera2Engine.addSurfaceHolder(mTexture1SurfaceHolder);
        mCamera2Engine.addSurfaceHolder(mTexture2SurfaceHolder);
        mCamera2Engine.addSurfaceHolder(mImageReaderSurfaceHolder);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_shutter) {
            mCamera2Engine.startCaptureRequest(CaptureRequestDescriptions.zeroShutterLag(mImageReaderSurfaceHolder));
        } else if (v.getId() == R.id.button_front) {
            mCamera2Engine.setFacing(CameraMetadata.LENS_FACING_FRONT);
        } else if (v.getId() == R.id.button_back) {
            mCamera2Engine.setFacing(CameraMetadata.LENS_FACING_BACK);
        }
    }
}
