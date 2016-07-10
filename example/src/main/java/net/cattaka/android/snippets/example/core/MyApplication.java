package net.cattaka.android.snippets.example.core;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

/**
 * Created by cattaka on 16/07/10.
 */
public class MyApplication extends Application {
    AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent
                .builder()
                .myModule(new MyModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    @VisibleForTesting
    public void setAppComponent(AppComponent appComponent) {
        mAppComponent = appComponent;
    }
}
