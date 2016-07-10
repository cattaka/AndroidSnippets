package net.cattaka.android.snippets.example.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.test.RenamingDelegatingContext;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import net.cattaka.android.snippets.example.Constants;
import net.cattaka.android.snippets.example.MySQLiteOpenHelper;
import net.cattaka.android.snippets.example.core.MyModule;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

/**
 * Created by cattaka on 16/07/10.
 */
@Module
public class TestMyModule extends MyModule {
    private Context mContext;
    // テスト時にアクセスできるようにpublicにしておく
    public RenamingDelegatingContext mRenamingDelegatingContext;
    public MockWebServer mMockWebServer;

    public SharedPreferences mSharedPreferences;
    public MySQLiteOpenHelper mMySQLiteOpenHelper;
    public Retrofit mRetrofit;

    public TestMyModule(@NonNull Context context) {
        super(context);
        mContext = context;
        mRenamingDelegatingContext = new RenamingDelegatingContext(mContext, "test_");
        mMockWebServer = new MockWebServer();
        mMockWebServer.setDispatcher(new AssetsDispatcher());

        mSharedPreferences = mContext.getSharedPreferences("test_" + Constants.PREF_NAME, Context.MODE_PRIVATE);
        mMySQLiteOpenHelper = new MySQLiteOpenHelper(mRenamingDelegatingContext, Constants.DB_NAME, null, Constants.DB_VERSION);
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        mRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(mMockWebServer.url("/").toString()).build();
    }

    public void reset() {
        // 手段を問わず、保存されているデータを消す
        createSharedPreferences().edit().clear().apply();
        mRenamingDelegatingContext.deleteDatabase(Constants.DB_NAME);
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
}

