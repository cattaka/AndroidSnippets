package net.cattaka.android.snippets.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;
import net.cattaka.android.snippets.example.adapter.ActivityEntryAdapter;
import net.cattaka.android.snippets.example.data.ActivityEntry;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final List<ActivityEntry> ACTIVITY_ENTRIES = Arrays.asList(
            new ActivityEntry("Spinner", null,
                    new ActivityEntry("CodeLabelAdapter", CodeLabelExampleActivity.class)
            ),
            new ActivityEntry("RecyclerView", null,
                    new ActivityEntry("Simple String", SimpleStringExampleActivity.class),
                    new ActivityEntry("Complex String", ComplexStringExampleActivity.class),
                    new ActivityEntry("Header and footer", RecyclerViewHeaderExampleActivity.class),
                    new ActivityEntry("Multi adapter", MultiAdapterExampleActivity.class)
            ),
            new ActivityEntry("ScrambleAdapter", null,
                    new ActivityEntry("ScrambleAdapter", ScrambleAdapterExampleActivity.class),
                    new ActivityEntry("Operatable list", OperatableListExampleActivity.class),
                    new ActivityEntry("Nested ScrambleAdapter", NestedScrambleAdapterExampleActivity.class),
                    new ActivityEntry("Multi adapter", MultiAdapterExampleActivity.class)
            ),
            new ActivityEntry("Tree", null,
                    new ActivityEntry("Simple tree", TreeItemAdapterExampleActivity.class),
                    new ActivityEntry("Choosable tree", ChoosableTreeItemAdapterExampleActivity.class)
            )
    );

    ListenerRelay<ActivityEntryAdapter, ActivityEntryAdapter.ViewHolder> mListenerRelay = new ListenerRelay<ActivityEntryAdapter, ActivityEntryAdapter.ViewHolder>() {
        @Override
        public void onClick(RecyclerView recyclerView, ActivityEntryAdapter adapter, ActivityEntryAdapter.ViewHolder viewHolder, View view) {
            if (recyclerView.getId() == R.id.recycler) {
                ActivityEntry entry = adapter.getItemAt(viewHolder.getAdapterPosition()).getItem();
                if (entry != null && entry.getClazz() != null) {
                    Intent intent = new Intent(MainActivity.this, entry.getClazz());
                    startActivity(intent);
                }
            }
        }
    };

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        ActivityEntryAdapter adapter = new ActivityEntryAdapter(this, ACTIVITY_ENTRIES);
        adapter.setListenerRelay(mListenerRelay);

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}
