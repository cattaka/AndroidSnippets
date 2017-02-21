package net.cattaka.android.snippets.example;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;
import net.cattaka.android.snippets.example.adapter.ActivityEntryAdapter;
import net.cattaka.android.snippets.example.data.ActivityEntry;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final List<ActivityEntry> ACTIVITY_ENTRIES = Arrays.asList(
            new ActivityEntry("Workaround of issues", null,
                    new ActivityEntry("AOSP Issue 212316", Issue212316ParrierExampleActvity.class)
            ),
            new ActivityEntry("CoordinatorLayout", null,
                    new ActivityEntry("AppBarLayout", null,
                            new ActivityEntry("With simple view", AppBarLayoutExample1Activity.class, Build.VERSION_CODES.LOLLIPOP),
                            new ActivityEntry("With RecyclerView", AppBarLayoutExample2Activity.class, Build.VERSION_CODES.LOLLIPOP)
                    )
            ),
            new ActivityEntry("Status Bar", null,
                    new ActivityEntry("Control color", ControlStatusBarColorActivity.class, Build.VERSION_CODES.LOLLIPOP)
            ),
            new ActivityEntry("Camera API", null,
                    new ActivityEntry("Multiple surface(Camera 2)", CameraApi2ExampleActivity.class, Build.VERSION_CODES.LOLLIPOP),
                    new ActivityEntry("Check camera orientation", CheckCameraOrientationActivity.class)
            ),

            new ActivityEntry("ConstraintLayout", null,
                    new ActivityEntry("Animate ConstraintLayout", AnimateConstraintLayoutActivity.class)
            ),
            new ActivityEntry("ViewSnapper", null,
                    new ActivityEntry("Snap rect to button", ViewSnapperExampleActivity.class)
            ),
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
            ),
            new ActivityEntry("GitHub", null,
                    new ActivityEntry("Repos", GitHubReposActivity.class)
            )
    );

    ListenerRelay<ActivityEntryAdapter, ActivityEntryAdapter.ViewHolder> mListenerRelay = new ListenerRelay<ActivityEntryAdapter, ActivityEntryAdapter.ViewHolder>() {
        @Override
        public void onClick(RecyclerView recyclerView, ActivityEntryAdapter adapter, ActivityEntryAdapter.ViewHolder viewHolder, View view) {
            if (recyclerView.getId() == R.id.recycler) {
                ActivityEntry entry = adapter.getItemAt(viewHolder.getAdapterPosition()).getItem();
                if (entry != null && entry.getClazz() != null) {
                    if (entry.getApiLevel() > Build.VERSION.SDK_INT) {
                        Toast.makeText(MainActivity.this, "This is for over api level " + entry.getApiLevel(), Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(MainActivity.this, entry.getClazz());
                        startActivity(intent);
                    }
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
