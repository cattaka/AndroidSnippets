package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;
import net.cattaka.android.snippets.example.adapter.MyTreeItemAdapter;
import net.cattaka.android.snippets.example.data.MyTreeItem;
import net.cattaka.android.snippets.example.utils.ExampleDataGenerator;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cattaka on 16/05/21.
 */
public class TreeItemAdapterExampleActivity extends AppCompatActivity {
    private ListenerRelay<MyTreeItemAdapter, MyTreeItemAdapter.ViewHolder> mListenerRelay = new ListenerRelay<MyTreeItemAdapter, MyTreeItemAdapter.ViewHolder>() {
        @Override
        public void onClick(RecyclerView recyclerView, MyTreeItemAdapter adapter, MyTreeItemAdapter.ViewHolder viewHolder, View view) {
            if (recyclerView.getId() == R.id.recycler) {
                MyTreeItem item = adapter.getItemAt(viewHolder.getAdapterPosition()).getItem();
                Snackbar.make(view, "Clicked: " + item.getText(), Snackbar.LENGTH_SHORT).show();
            }
        }

        @Override
        public boolean onLongClick(RecyclerView recyclerView, MyTreeItemAdapter adapter, MyTreeItemAdapter.ViewHolder viewHolder, View view) {
            if (recyclerView.getId() == R.id.recycler) {
                MyTreeItem item = adapter.getItemAt(viewHolder.getAdapterPosition()).getItem();
                Snackbar.make(view, "Long clicked: " + item.getText(), Snackbar.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }
    };


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
            adapter.setListenerRelay(mListenerRelay);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(adapter);
        }
    }
}
