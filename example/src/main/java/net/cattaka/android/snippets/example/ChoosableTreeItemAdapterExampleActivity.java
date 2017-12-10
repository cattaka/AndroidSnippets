package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.cattaka.android.snippets.example.adapter.ChoosableMyTreeItemAdapter;
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
public class ChoosableTreeItemAdapterExampleActivity extends AppCompatActivity implements IScreen {
    ChoosableMyTreeItemAdapter.IChoosableMyTreeItemAdapterListener mChoosableMyTreeItemAdapterListener = new ChoosableMyTreeItemAdapter.IChoosableMyTreeItemAdapterListener() {
        @Override
        public void onItemOpenChanged(@NonNull MyTreeItem item, boolean open) {
            Tracker.getInstance().recordAction(me, TrackEvent.ACTION_CLICK, toParamsMap(TrackParam.ITEM_NAME, item.getText(), TrackParam.VIEW_NAME, "open", TrackParam.VALUE, String.valueOf(open)));
        }

        @Override
        public void onItemCheckChanged(@NonNull MyTreeItem item, boolean checked) {
            Tracker.getInstance().recordAction(me, TrackEvent.ACTION_CLICK, toParamsMap(TrackParam.ITEM_NAME, item.getText(), TrackParam.VIEW_NAME, "check", TrackParam.VALUE, String.valueOf(checked)));
        }
    };

    ChoosableTreeItemAdapterExampleActivity me = this;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosable_tree_item_adapter);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        { // set adapter
            List<MyTreeItem> items = ExampleDataGenerator.generateMyTreeItem(Arrays.asList(5, 3, 2), 0);
            ChoosableMyTreeItemAdapter adapter = new ChoosableMyTreeItemAdapter(this, items);
            adapter.setListener(mChoosableMyTreeItemAdapterListener);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(adapter);
        }
    }
}
