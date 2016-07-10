package net.cattaka.android.snippets.example.core;

import android.content.SharedPreferences;
import dagger.Component;
import net.cattaka.android.snippets.example.MySQLiteOpenHelper;
import net.cattaka.android.snippets.example.GitHubReposActivity;
import retrofit2.Retrofit;

/**
 * Created by cattaka on 16/07/10.
 */
@Component(modules = MyModule.class)
public interface AppComponent {
    void inject(GitHubReposActivity activity);

    SharedPreferences getSharedPreferences();

    MySQLiteOpenHelper getMySQLiteOpenHelper();

    Retrofit getRetrofit();
}
