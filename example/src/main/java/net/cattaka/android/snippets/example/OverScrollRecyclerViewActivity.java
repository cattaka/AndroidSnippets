package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.snippets.example.adapter.factory.SimpleStringViewHolderFactory;

import java.util.ArrayList;

/**
 * Created by cattaka on 16/12/03.
 */

public class OverScrollRecyclerViewActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;

    ScrambleAdapter<String> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_scroll_recycler_view);

        // Find views
        mRecyclerView = (RecyclerView) findViewById(R.id.view_recycler);

        {   // Setup mRecyclerView
            mAdapter = new ScrambleAdapter<>(this, new ArrayList<String>(), null, new SimpleStringViewHolderFactory());
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(mAdapter);
            for (int i = 0; i < 30; i++) {
                mAdapter.getItems().add("Item : " + mAdapter.getItems().size());
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
