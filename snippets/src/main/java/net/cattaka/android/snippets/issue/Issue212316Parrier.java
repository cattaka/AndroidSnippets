package net.cattaka.android.snippets.issue;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;

/**
 * To parry BUG of Android N. https://code.google.com/p/android/issues/detail?id=212316
 * <p>
 * Created by cattaka on 2017/01/12.
 */
public class Issue212316Parrier {
    public static final String DEFAULT_DB_NAME = "Issue212316Parrier.db";
    private static final String KEY_STORED_BUNDLE_ID = "net.cattaka.android.snippets.issue.Issue212316Parrier.KEY_STORED_BUNDLE_ID";

    private String mDbName;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private String mAppVersionName;
    private int mAppVersionCode;

    public Issue212316Parrier(Context context, String appVersionName, int appVersionCode) {
        this(context, appVersionName, appVersionCode, DEFAULT_DB_NAME);
    }

    public Issue212316Parrier(Context context, String appVersionName, int appVersionCode, String dbName) {
        mDbName = dbName;
        mContext = context;
        mAppVersionName = appVersionName;
        mAppVersionCode = appVersionCode;
    }

    public void initialize() {
        File cacheDir = mContext.getCacheDir();
        File dbFile = new File(cacheDir, mDbName);

        mDatabase = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS installation(id INTEGER PRIMARY KEY AUTOINCREMENT,deviceFingerprint TEXT,appVersionName TEXT,appVersionCode INTEGER,lastStoredBundleId INTEGER)");
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS storedBundle(id INTEGER PRIMARY KEY AUTOINCREMENT,blob BLOB)");

        long lastStoredBundleId = 1;
        boolean needReset = true;
        Cursor cursor = mDatabase.query("installation", new String[]{"id", "deviceFingerprint", "appVersionName", "appVersionCode", "lastStoredBundleId"}, "id=1", null, null, null, null);
        try {
            if (cursor.moveToNext()) {
                needReset = !Build.FINGERPRINT.equals(cursor.getString(1))
                        || !mAppVersionName.equals(cursor.getString(2))
                        || (mAppVersionCode != cursor.getInt(3));
                lastStoredBundleId = cursor.getLong(4);
            }
        } finally {
            cursor.close();
        }
        if (needReset) {
            mDatabase.delete("installation", null, null);
            mDatabase.delete("storedBundle", null, null);

            ContentValues values = new ContentValues();
            values.put("id", 1);
            values.put("deviceFingerprint", Build.FINGERPRINT);
            values.put("appVersionName", mAppVersionName);
            values.put("appVersionCode", mAppVersionCode);
            values.put("lastStoredBundleId", lastStoredBundleId);
            mDatabase.insert("installation", null, values);
        }
    }

    public void dispose() {
        if (mDatabase != null) {
            mDatabase.close();
            mDatabase = null;
        }
    }

    /**
     * Call this from {@link android.app.Activity#onCreate(Bundle)}, {@link android.app.Activity#onRestoreInstanceState(Bundle)} or {@link android.app.Activity#onPostCreate(Bundle)}
     */
    public void restoreSaveInstanceState(@Nullable Bundle savedInstanceState, boolean deleteStoredBundle) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (savedInstanceState != null && savedInstanceState.containsKey(KEY_STORED_BUNDLE_ID)) {
                long id = savedInstanceState.getLong(KEY_STORED_BUNDLE_ID);
                Bundle storedBundle = loadBundle(id, deleteStoredBundle);
                if (storedBundle != null) {
                    savedInstanceState.putAll(storedBundle);
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
                long id = saveBundle(outState);
                outState.clear();
                outState.putLong(KEY_STORED_BUNDLE_ID, id);
            }
        }
    }

    private long saveBundle(@NonNull Bundle bundle) {
        byte[] blob = marshall(bundle);
        mDatabase.beginTransaction();
        long nextStoredBundleId = 1;
        try {
            {   // get nextStoredBundleId
                Cursor cursor = mDatabase.query("installation", new String[]{"lastStoredBundleId"}, "id=1", null, null, null, null);
                try {
                    if (cursor.moveToNext()) {
                        nextStoredBundleId = cursor.getLong(0) + 1;
                    }
                } finally {
                    cursor.close();
                }
                ContentValues values = new ContentValues();
                values.put("id", 1);
                values.put("lastStoredBundleId", nextStoredBundleId);
                mDatabase.update("installation", values, "id=1", null);
            }
            {   // insert storedBundle
                ContentValues values = new ContentValues();
                values.put("id", nextStoredBundleId);
                values.put("blob", blob);
                mDatabase.insert("storedBundle", null, values);
            }
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
        return nextStoredBundleId;
    }

    @Nullable
    private Bundle loadBundle(long storedBundleId, boolean deleteStoredBundle) {
        byte[] blob = null;
        mDatabase.beginTransaction();
        try {
            Cursor cursor = mDatabase.query("storedBundle", new String[]{"id", "blob"}, "id=?", new String[]{String.valueOf(storedBundleId)}, null, null, null);
            try {
                if (cursor.moveToNext()) {
                    blob = cursor.getBlob(1);
                }
            } finally {
                cursor.close();
            }
            if (deleteStoredBundle) {
                mDatabase.delete("storedBundle", "id=?", new String[]{String.valueOf(storedBundleId)});
            }
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }

        try {
            return (blob != null) ? (Bundle) unmarshall(blob) : null;
        } catch (Exception e) {
            return null;
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
