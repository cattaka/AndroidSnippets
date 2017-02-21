package net.cattaka.android.snippets.example.core;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import net.cattaka.android.snippets.example.BuildConfig;
import net.cattaka.android.snippets.issue.Issue212316Parrier;

/**
 * Created by cattaka on 16/07/10.
 */
public class MyApplication extends Application {
    AppComponent mAppComponent;
    Issue212316Parrier mIssue212316Parrier;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent
                .builder()
                .myModule(new MyModule(this))
                .build();

        mIssue212316Parrier = new Issue212316Parrier(this, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);
        mIssue212316Parrier.initialize();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public Issue212316Parrier getIssue212316Parrier() {
        return mIssue212316Parrier;
    }

    @VisibleForTesting
    public void setAppComponent(AppComponent appComponent) {
        mAppComponent = appComponent;
    }
}
