package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;
import net.cattaka.android.snippets.example.adapter.MyTreeItemAdapter;
import net.cattaka.android.snippets.example.data.MyTreeItem;
import net.cattaka.android.snippets.example.tracker.IScreen;
import net.cattaka.android.snippets.example.tracker.TrackEvent;
import net.cattaka.android.snippets.example.tracker.TrackParam;
import net.cattaka.android.snippets.example.tracker.Tracker;
import net.cattaka.android.snippets.example.utils.ExampleDataGenerator;

import java.util.Arrays;
import java.util.List;

import static net.cattaka.android.snippets.example.tracker.TrackParamBundle.toParamsMap;

/**
 * Created by cattaka on 16/05/21.
 */
public class TreeItemAdapterExampleActivity extends AppCompatActivity implements IScreen {
    private ListenerRelay<MyTreeItemAdapter, MyTreeItemAdapter.ViewHolder> mListenerRelay = new ListenerRelay<MyTreeItemAdapter, MyTreeItemAdapter.ViewHolder>() {
        @Override
        public void onClick(RecyclerView recyclerView, MyTreeItemAdapter adapter, MyTreeItemAdapter.ViewHolder viewHolder, View view) {
            if (recyclerView.getId() == R.id.recycler) {
                MyTreeItem item = adapter.getItemAt(viewHolder.getAdapterPosition()).getItem();
                Tracker.getInstance().recordAction(me, TrackEvent.ACTION_CLICK, toParamsMap(TrackParam.ITEM_NAME, item.getText(), TrackParam.VIEW_NAME, "body"));
                Snackbar.make(view, "Clicked: " + item.getText(), Snackbar.LENGTH_SHORT).show();
            }
        }

        @Override
        public boolean onLongClick(RecyclerView recyclerView, MyTreeItemAdapter adapter, MyTreeItemAdapter.ViewHolder viewHolder, View view) {
            if (recyclerView.getId() == R.id.recycler) {
                MyTreeItem item = adapter.getItemAt(viewHolder.getAdapterPosition()).getItem();
                Tracker.getInstance().recordAction(me, TrackEvent.ACTION_LONG_CLICK, toParamsMap(TrackParam.ITEM_NAME, item.getText(), TrackParam.VIEW_NAME, "body"));
                Snackbar.make(view, "Long clicked: " + item.getText(), Snackbar.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }
    };

    MyTreeItemAdapter.IMyTreeItemAdapterListener mMyTreeItemAdapterListener = new MyTreeItemAdapter.IMyTreeItemAdapterListener() {
        @Override
        public void onItemOpenChanged(@NonNull MyTreeItem item, boolean open) {
            Tracker.getInstance().recordAction(me, TrackEvent.ACTION_CLICK, toParamsMap(TrackParam.ITEM_NAME, item.getText(), TrackParam.VIEW_NAME, "open", TrackParam.VALUE, String.valueOf(open)));
        }
    };

    TreeItemAdapterExampleActivity me = this;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosable_tree_item_adapter);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        { // set adapter
            List<MyTreeItem> items = ExampleDataGenerator.generateMyTreeItem(Arrays.asList(5, 3, 2), 0);
            MyTreeItemAdapter adapter = new MyTreeItemAdapter(this, items);
            adapter.setListener(mMyTreeItemAdapterListener);
            adapter.setListenerRelay(mListenerRelay);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(adapter);
        }
    }
}
