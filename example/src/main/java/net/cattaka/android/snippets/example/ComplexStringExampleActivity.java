package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;
import net.cattaka.android.snippets.example.adapter.ComplexStringAdapter;
import net.cattaka.android.snippets.example.tracker.IScreen;
import net.cattaka.android.snippets.example.tracker.TrackEvent;
import net.cattaka.android.snippets.example.tracker.TrackParam;
import net.cattaka.android.snippets.example.tracker.Tracker;

import java.util.ArrayList;
import java.util.List;

import static net.cattaka.android.snippets.example.tracker.TrackParamBundle.toParamsMap;

/**
 * Created by cattaka on 16/05/02.
 */
public class ComplexStringExampleActivity extends AppCompatActivity implements IScreen {

    ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> mListenerRelay = new ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder>() {
        @Override
        public void onClick(RecyclerView recyclerView, ScrambleAdapter<?> adapter, RecyclerView.ViewHolder viewHolder, View view) {
            if (recyclerView.getId() == R.id.recycler) {
                String item = (String) adapter.getItemAt(viewHolder.getAdapterPosition());
                switch (view.getId()) {
                    case R.id.text: {
                        Tracker.getInstance().recordAction(me, TrackEvent.ACTION_CLICK, toParamsMap(TrackParam.ITEM_NAME, item, TrackParam.VIEW_NAME, "text"));
                        Snackbar.make(view, item + " is clicked.(Text)", Snackbar.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.button_a: {
                        Tracker.getInstance().recordAction(me, TrackEvent.ACTION_CLICK, toParamsMap(TrackParam.ITEM_NAME, item, TrackParam.VIEW_NAME, "button_a"));
                        Snackbar.make(view, item + " is clicked.(A)", Snackbar.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.button_b: {
                        Tracker.getInstance().recordAction(me, TrackEvent.ACTION_CLICK, toParamsMap(TrackParam.ITEM_NAME, item, TrackParam.VIEW_NAME, "button_b"));
                        Snackbar.make(view, item + " is clicked.(B)", Snackbar.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        }

        @Override
        public boolean onLongClick(RecyclerView recyclerView, ScrambleAdapter<?> adapter, RecyclerView.ViewHolder viewHolder, View view) {
            if (recyclerView.getId() == R.id.recycler) {
                String item = (String) adapter.getItemAt(viewHolder.getAdapterPosition());
                switch (view.getId()) {
                    case R.id.text: {
                        Tracker.getInstance().recordAction(me, TrackEvent.ACTION_LONG_CLICK, toParamsMap(TrackParam.ITEM_NAME, item, TrackParam.VIEW_NAME, "text"));
                        Snackbar.make(view, item + " is long clicked.(Text)", Snackbar.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.button_a: {
                        Tracker.getInstance().recordAction(me, TrackEvent.ACTION_LONG_CLICK, toParamsMap(TrackParam.ITEM_NAME, item, TrackParam.VIEW_NAME, "button_a"));
                        Snackbar.make(view, item + " is long clicked.(A)", Snackbar.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.button_b: {
                        Tracker.getInstance().recordAction(me, TrackEvent.ACTION_LONG_CLICK, toParamsMap(TrackParam.ITEM_NAME, item, TrackParam.VIEW_NAME, "button_b"));
                        Snackbar.make(view, item + " is long clicked.(B)", Snackbar.LENGTH_SHORT).show();
                        break;
                    }
                }
                return true;
            }
            return false;
        }
    };

    ComplexStringExampleActivity me = this;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_string_example);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        { // set adapter
            List<String> items = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                items.add("item " + i);
            }
            ComplexStringAdapter adapter = new ComplexStringAdapter(this, items, mListenerRelay);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(adapter);
        }
    }
}
