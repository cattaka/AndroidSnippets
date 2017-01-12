package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import net.cattaka.android.snippets.example.core.MyApplication;

/**
 * https://code.google.com/p/android/issues/detail?id=212316
 * <p>
 * Created by takao on 2017/01/12.
 */
public class Issue212316ParrierExampleActvity extends AppCompatActivity {
    private TextView mTextView;
    private int mOnStartCount = 0;
    private byte[] mBlob;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ((MyApplication) getApplication()).getIssue212316Parrier().restoreSaveInstanceState(savedInstanceState, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_issue_212316_parrier_example);
        if (savedInstanceState != null) {
            mOnStartCount = savedInstanceState.getInt("onStartCount");
            mBlob = savedInstanceState.getByteArray("blob");
        } else {
            mBlob = new byte[1 << 23]; // 8MiB
        }

        // find view
        mTextView = (TextView) findViewById(R.id.text);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mOnStartCount++;
        mTextView.setText("onStart count : " + mOnStartCount);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("onStartCount", mOnStartCount);
        outState.putByteArray("blob", mBlob);

        ((MyApplication) getApplication()).getIssue212316Parrier().saveInstanceState(outState);
    }
}
