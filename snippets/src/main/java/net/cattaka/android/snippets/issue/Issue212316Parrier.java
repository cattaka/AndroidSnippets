package net.cattaka.android.snippets.issue;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * To parry BUG of Android N. https://code.google.com/p/android/issues/detail?id=212316
 * <p>
 * Created by cattaka on 2017/01/12.
 */
public class Issue212316Parrier {
    public static final String DEFAULT_NAME = "Issue212316Parrier";
    private static final String KEY_STORED_BUNDLE_ID = "net.cattaka.android.snippets.issue.Issue212316Parrier.KEY_STORED_BUNDLE_ID";

    private String mName;
    private Context mContext;
    private String mAppVersionName;
    private int mAppVersionCode;
    private SharedPreferences mPreferences;
    private File mDirForStoredBundle;

    public Issue212316Parrier(Context context, String appVersionName, int appVersionCode) {
        this(context, appVersionName, appVersionCode, DEFAULT_NAME);
    }

    public Issue212316Parrier(Context context, String appVersionName, int appVersionCode, String name) {
        mName = name;
        mContext = context;
        mAppVersionName = appVersionName;
        mAppVersionCode = appVersionCode;
    }

    public void initialize() {
        mPreferences = mContext.getSharedPreferences(mName, Context.MODE_PRIVATE);

        File cacheDir = mContext.getCacheDir();
        mDirForStoredBundle = new File(cacheDir, mName);
        if (!mDirForStoredBundle.exists()) {
            mDirForStoredBundle.mkdirs();
        }

        long lastStoredBundleId = 1;
        boolean needReset = true;
        String fingerPrint = (Build.FINGERPRINT != null) ? Build.FINGERPRINT : "";
        needReset = !fingerPrint.equals(mPreferences.getString("deviceFingerprint", null))
                || !mAppVersionName.equals(mPreferences.getString("appVersionName", null))
                || (mAppVersionCode != mPreferences.getInt("appVersionCode", 0));
        lastStoredBundleId = mPreferences.getLong("lastStoredBundleId", 1);

        if (needReset) {
            clearDirForStoredBundle();

            mPreferences.edit()
                    .putString("deviceFingerprint", Build.FINGERPRINT)
                    .putString("appVersionName", mAppVersionName)
                    .putInt("appVersionCode", mAppVersionCode)
                    .putLong("lastStoredBundleId", lastStoredBundleId)
                    .apply();
        }
    }

    /**
     * Call this from {@link android.app.Activity#onCreate(Bundle)}, {@link android.app.Activity#onRestoreInstanceState(Bundle)} or {@link android.app.Activity#onPostCreate(Bundle)}
     */
    public void restoreSaveInstanceState(@Nullable Bundle savedInstanceState, boolean deleteStoredBundle) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (savedInstanceState != null && savedInstanceState.containsKey(KEY_STORED_BUNDLE_ID)) {
                long storedBundleId = savedInstanceState.getLong(KEY_STORED_BUNDLE_ID);
                File storedBundleFile = new File(mDirForStoredBundle, storedBundleId + ".bin");
                Bundle storedBundle = loadBundle(storedBundleFile);
                if (storedBundle != null) {
                    savedInstanceState.putAll(storedBundle);
                }
                if (deleteStoredBundle && storedBundleFile.exists()) {
                    storedBundleFile.delete();
                }
            }
        }
    }

    /**
     * Call this from {@link android.app.Activity#onSaveInstanceState(Bundle)}
     */
    public void saveInstanceState(Bundle outState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (outState != null) {
                long nextStoredBundleId = mPreferences.getLong("lastStoredBundleId", 1) + 1;
                mPreferences.edit().putLong("lastStoredBundleId", nextStoredBundleId).apply();
                File storedBundleFile = new File(mDirForStoredBundle, nextStoredBundleId + ".bin");
                saveBundle(outState, storedBundleFile);
                outState.clear();
                outState.putLong(KEY_STORED_BUNDLE_ID, nextStoredBundleId);
            }
        }
    }

    private void saveBundle(@NonNull Bundle bundle, @NonNull File storedBundleFile) {
        byte[] blob = marshall(bundle);
        OutputStream out = null;
        try {
            out = new GZIPOutputStream(new FileOutputStream(storedBundleFile));
            out.write(blob);
            out.flush();
            out.close();
        } catch (IOException e) {
            // ignore
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    @Nullable
    private Bundle loadBundle(File storedBundleFile) {
        byte[] blob = null;
        InputStream in = null;
        try {
            in = new GZIPInputStream(new FileInputStream(storedBundleFile));
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            int n;
            byte[] buffer = new byte[1024];
            while ((n = in.read(buffer)) > -1) {
                bout.write(buffer, 0, n);   // Don't allow any extra bytes to creep in, final write
            }
            bout.close();
            blob = bout.toByteArray();
        } catch (IOException e) {
            // ignore
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }

        try {
            return (blob != null) ? (Bundle) unmarshall(blob) : null;
        } catch (Exception e) {
            return null;
        }
    }

    private void clearDirForStoredBundle() {
        for (File file : mDirForStoredBundle.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".bin")) {
                file.delete();
            }
        }
    }


    @NonNull
    private static <T extends Parcelable> byte[] marshall(@NonNull final T object) {
        Parcel p1 = Parcel.obtain();
        p1.writeValue(object);

        byte[] data = p1.marshall();
        p1.recycle();
        return data;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    private static <T extends Parcelable> T unmarshall(@NonNull byte[] bytes) {
        Parcel p2 = Parcel.obtain();
        p2.unmarshall(bytes, 0, bytes.length);
        p2.setDataPosition(0);
        T result = (T) p2.readValue(Issue212316Parrier.class.getClassLoader());
        p2.recycle();
        return result;
    }
}
