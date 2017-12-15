package net.cattaka.android.snippets.example;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import net.cattaka.android.snippets.example.databinding.ActivityPickFromGooglePhotosBinding;
import net.cattaka.android.snippets.example.tracker.IScreen;
import net.cattaka.android.snippets.example.tracker.TrackEvent;
import net.cattaka.android.snippets.example.tracker.Tracker;

import static net.cattaka.android.snippets.example.tracker.TrackParam.VIEW_NAME;
import static net.cattaka.android.snippets.example.tracker.TrackParamBundle.toParamsMap;

/**
 * Created by cattaka on 17/12/15.
 */

public class PickFromGooglePhotosActivity extends AppCompatActivity implements IScreen {
    static final int REQUEST_CODE_PICK = 1;

    ActivityPickFromGooglePhotosBinding mBinding;

    String GOOGLE_PHOTOS_PACKAGE_NAME = "com.google.android.apps.photos";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_pick_from_google_photos);
        mBinding.setActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK) {
            if (resultCode == RESULT_OK) {
                Uri resultUri = (data != null ? data.getData() : null);
                if (resultUri != null) {
                    mBinding.image.setImageURI(resultUri);
                }
            }
        }
    }

    public void onClickPickNormal(View view) {
        Tracker.getInstance().recordAction(this, TrackEvent.ACTION_CLICK, toParamsMap(VIEW_NAME, "pick_normal"));
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (Build.VERSION.SDK_INT >= 19) {
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_PICK);
    }

    public void onClickPickFromPhotos(View view) {
        Tracker.getInstance().recordAction(this, TrackEvent.ACTION_CLICK, toParamsMap(VIEW_NAME, "pick_from_photos"));
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (Build.VERSION.SDK_INT >= 19) {
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        intent.setPackage(GOOGLE_PHOTOS_PACKAGE_NAME);
        startActivityForResult(intent, REQUEST_CODE_PICK);
    }
}
