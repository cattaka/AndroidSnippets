package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import net.cattaka.android.snippets.example.tracker.IScreen;

public class MotionLayoutStretchableSurfacesActivity extends AppCompatActivity implements IScreen {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_layout_stretchable_surface);
    }
}
