package net.cattaka.android.snippets.example.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.cattaka.android.snippets.example.Constants;
import net.cattaka.android.snippets.example.MySQLiteOpenHelper;
import net.cattaka.android.snippets.example.retrofit.GitHubService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cattaka on 16/07/10.
 */
@Module
public class MyModule {
    private Context mContext;

    public MyModule(@NonNull Context context) {
        mContext = context;
    }

    @Provides
    public SharedPreferences createSharedPreferences() {
        return mContext.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    public MySQLiteOpenHelper createMySQLiteOpenHelper() {
        return new MySQLiteOpenHelper(mContext, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Provides
    public Retrofit createRetrofit() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://api.github.com/").build();
    }

    @Provides
    public GitHubService createGitHubService() {
        return createRetrofit().create(GitHubService.class);
    }
}