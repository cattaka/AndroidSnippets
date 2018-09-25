package net.cattaka.android.snippets.example;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;
import net.cattaka.android.snippets.example.adapter.ActivityEntryAdapter;
import net.cattaka.android.snippets.example.data.ActivityEntry;
import net.cattaka.android.snippets.example.tracker.IScreen;
import net.cattaka.android.snippets.example.tracker.TrackParam;
import net.cattaka.android.snippets.example.tracker.Tracker;

import java.util.Arrays;
import java.util.List;

import static net.cattaka.android.snippets.example.tracker.TrackEvent.ACTION_CLICK;
import static net.cattaka.android.snippets.example.tracker.TrackParamBundle.toParamsMap;

public class MainActivity extends AppCompatActivity implements IScreen {
    private static final List<ActivityEntry> ACTIVITY_ENTRIES = Arrays.asList(
            new ActivityEntry("MotionLayout", null,
                    new ActivityEntry("Stretchable Surfaces", MotionLayoutStretchableSurfacesActivity.class, Build.VERSION_CODES.JELLY_BEAN_MR2),
                    new ActivityEntry("Switch Scene", MotionLayoutSwitchSceneActivity.class, Build.VERSION_CODES.JELLY_BEAN_MR2),
                    new ActivityEntry("Progress", MotionLayoutProgressActivity.class, Build.VERSION_CODES.JELLY_BEAN_MR2),
                    new ActivityEntry("Morph", MotionLayoutMorphActivity.class, Build.VERSION_CODES.JELLY_BEAN_MR2)
            ),
            new ActivityEntry("With Google Applications", null,
                    new ActivityEntry("Pick from Google Photos", PickFromGooglePhotosActivity.class)
            ),
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
                        Tracker.getInstance().recordAction(me, ACTION_CLICK, toParamsMap(TrackParam.ITEM_NAME, entry.getLabel(), TrackParam.HAS_ERROR, true));
                        Toast.makeText(MainActivity.this, "This is for over api level " + entry.getApiLevel(), Toast.LENGTH_SHORT).show();
                    } else {
                        Tracker.getInstance().recordAction(me, ACTION_CLICK, toParamsMap(TrackParam.ITEM_NAME, entry.getLabel(), TrackParam.HAS_ERROR, false));
                        Intent intent = new Intent(MainActivity.this, entry.getClazz());
                        startActivity(intent);
                    }
                }
            }
        }
    };

    ActivityEntryAdapter.IActivityEntryAdapterListener mActivityEntryAdapterListener = new ActivityEntryAdapter.IActivityEntryAdapterListener() {
        @Override
        public void onItemOpenChanged(@NonNull ActivityEntry item, boolean open) {
            Tracker.getInstance().recordAction(me, ACTION_CLICK, toParamsMap(TrackParam.VALUE, open));
        }
    };

    MainActivity me = this;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        ActivityEntryAdapter adapter = new ActivityEntryAdapter(this, ACTIVITY_ENTRIES);
        adapter.setListener(mActivityEntryAdapterListener);
        adapter.setListenerRelay(mListenerRelay);

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}
