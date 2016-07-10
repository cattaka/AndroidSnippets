package net.cattaka.android.snippets.example.test;

import android.content.res.AssetManager;
import android.support.test.InstrumentationRegistry;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by cattaka on 16/07/10.
 */
public class AssetsDispatcher extends Dispatcher {
    AssetManager mAssetManager; // テスト側のContextのAssetManagerが想定

    public AssetsDispatcher() {
        mAssetManager = InstrumentationRegistry.getContext().getAssets();
    }

    @Override
    public MockResponse dispatch(final RecordedRequest request) throws InterruptedException {
        String assetFilename = "http_testdata/" + request.getMethod() + request.getPath().replace('/', '_');
        try {
            String body = readAssetAsString(assetFilename);
            return new MockResponse().setBody(body).setResponseCode(200);
        } catch (IOException e) {
            // assets/http_testdata/に見つからないので404を返す
            return new MockResponse().setResponseCode(404);
        }
    }

    private String readAssetAsString(String assetFilename) throws IOException {
        InputStream in = mAssetManager.open(assetFilename);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buf = new byte[1 << 12];
        int r;
        while ((r = in.read(buf)) > 0) {
            bout.write(buf, 0, r);
        }
        return new String(bout.toByteArray());
    }
}
