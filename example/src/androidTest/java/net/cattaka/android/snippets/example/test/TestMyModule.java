package net.cattaka.android.snippets.example.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.cattaka.android.snippets.example.Constants;
import net.cattaka.android.snippets.example.MySQLiteOpenHelper;
import net.cattaka.android.snippets.example.core.MyModule;
import net.cattaka.android.snippets.example.retrofit.GitHubService;

import java.io.IOException;

import dagger.Module;
import dagger.Provides;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cattaka on 16/07/10.
 */
@Module
public class TestMyModule extends MyModule {
    public static final String TEST_PREF_NAME = "test_" + Constants.PREF_NAME;
    public static final String TEST_DB_NAME = "test_" + Constants.DB_NAME;

    private Context mContext;
    // テスト時にアクセスできるようにpublicにしておく
    public MockWebServer mMockWebServer;

    public SharedPreferences mSharedPreferences;
    public MySQLiteOpenHelper mMySQLiteOpenHelper;
    public Retrofit mRetrofit;
    public GitHubService mGitHubService;

    public TestMyModule(@NonNull Context context) {
        super(context);
        mContext = context;
        mMockWebServer = new MockWebServer();
        mMockWebServer.setDispatcher(new AssetsDispatcher());

        mSharedPreferences = mContext.getSharedPreferences(TEST_PREF_NAME, Context.MODE_PRIVATE);
        mMySQLiteOpenHelper = new MySQLiteOpenHelper(mContext, TEST_DB_NAME, null, Constants.DB_VERSION);
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        mRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(mMockWebServer.url("/").toString()).build();
        mGitHubService = mRetrofit.create(GitHubService.class);
    }

    public void reset() {
        // 手段を問わず、保存されているデータを消す
        createSharedPreferences().edit().clear().apply();
        mContext.deleteDatabase(TEST_DB_NAME);
    }

    public void shutdown() throws IOException {
        mMockWebServer.shutdown();
    }

    @Provides
    public SharedPreferences createSharedPreferences() {
        return mSharedPreferences;
    }

    @Provides
    public MySQLiteOpenHelper createMySQLiteOpenHelper() {
        return mMySQLiteOpenHelper;
    }

    @Provides
    public Retrofit createRetrofit() {
        return mRetrofit;
    }

    @Provides

    @Override
    public GitHubService createGitHubService() {
        return mGitHubService;
    }
}

