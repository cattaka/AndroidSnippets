package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.SingleViewAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;
import net.cattaka.android.adaptertoolbox.thirdparty.MergeRecyclerAdapter;
import net.cattaka.android.snippets.example.adapter.SimpleNumberAdapter;
import net.cattaka.android.snippets.example.adapter.SimpleStringAdapter;
import net.cattaka.android.snippets.example.tracker.IScreen;
import net.cattaka.android.snippets.example.tracker.TrackAction;
import net.cattaka.android.snippets.example.tracker.TrackKey;
import net.cattaka.android.snippets.example.tracker.Tracker;

import java.util.ArrayList;
import java.util.List;

import static net.cattaka.android.snippets.example.tracker.TrackParams.toParamsMap;

/**
 * Created by cattaka on 16/05/02.
 */
public class MultiAdapterExampleActivity extends AppCompatActivity implements IScreen {

    ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> mListenerRelay = new ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder>() {
        @Override
        public void onClick(RecyclerView recyclerView, ScrambleAdapter<?> adapter, RecyclerView.ViewHolder viewHolder, View view) {
            if (recyclerView.getId() == R.id.recycler) {
                MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
                if (la.mAdapter == mStringsAdapter) {
                    String item = mStringsAdapter.getItemAt(la.mLocalPosition);
                    Tracker.getInstance().recordAction(me, TrackAction.ACTION_CLICK, toParamsMap(TrackKey.ITEM_NAME, item, TrackKey.ITEM_CATEGORY, "string"));
                    Snackbar.make(view, item + " is clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (la.mAdapter == mNumbersAdapter) {
                    Number item = mNumbersAdapter.getItemAt(la.mLocalPosition);
                    Tracker.getInstance().recordAction(me, TrackAction.ACTION_CLICK, toParamsMap(TrackKey.ITEM_NAME, String.valueOf(item), TrackKey.ITEM_CATEGORY, "number"));
                    Snackbar.make(view, item + " is clicked.", Snackbar.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public boolean onLongClick(RecyclerView recyclerView, ScrambleAdapter<?> adapter, RecyclerView.ViewHolder viewHolder, View view) {
            if (recyclerView.getId() == R.id.recycler) {
                MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
                if (la.mAdapter == mStringsAdapter) {
                    String item = mStringsAdapter.getItemAt(la.mLocalPosition);
                    Tracker.getInstance().recordAction(me, TrackAction.ACTION_LONG_CLICK, toParamsMap(TrackKey.ITEM_NAME, item, TrackKey.ITEM_CATEGORY, "string"));
                    Snackbar.make(view, item + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (la.mAdapter == mNumbersAdapter) {
                    Number item = mNumbersAdapter.getItemAt(la.mLocalPosition);
                    Tracker.getInstance().recordAction(me, TrackAction.ACTION_LONG_CLICK, toParamsMap(TrackKey.ITEM_NAME, String.valueOf(item), TrackKey.ITEM_CATEGORY, "number"));
                    Snackbar.make(view, item + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        }
    };

    ListenerRelay<SingleViewAdapter, RecyclerView.ViewHolder> mHeaderListenerRelay = new ListenerRelay<SingleViewAdapter, RecyclerView.ViewHolder>() {
        @Override
        public void onClick(RecyclerView recyclerView, SingleViewAdapter adapter, RecyclerView.ViewHolder viewHolder, View view) {
            if (recyclerView.getId() == R.id.recycler) {
                MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
                if (la.mAdapter == mStringsHeaderAdapter) {
                    Tracker.getInstance().recordAction(me, TrackAction.ACTION_CLICK, toParamsMap(TrackKey.ITEM_NAME, "string", TrackKey.ITEM_CATEGORY, "header"));
                    Snackbar.make(view, "Strings Header is clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (la.mAdapter == mNumbersHeaderAdapter) {
                    Tracker.getInstance().recordAction(me, TrackAction.ACTION_CLICK, toParamsMap(TrackKey.ITEM_NAME, "number", TrackKey.ITEM_CATEGORY, "header"));
                    Snackbar.make(view, "Numbers Header is clicked.", Snackbar.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public boolean onLongClick(RecyclerView recyclerView, SingleViewAdapter adapter, RecyclerView.ViewHolder viewHolder, View view) {
            if (recyclerView.getId() == R.id.recycler) {
                MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
                if (la.mAdapter == mStringsHeaderAdapter) {
                    Tracker.getInstance().recordAction(me, TrackAction.ACTION_LONG_CLICK, toParamsMap(TrackKey.ITEM_NAME, "string", TrackKey.ITEM_CATEGORY, "header"));
                    Snackbar.make(view, "Strings Header is long clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (la.mAdapter == mNumbersHeaderAdapter) {
                    Tracker.getInstance().recordAction(me, TrackAction.ACTION_LONG_CLICK, toParamsMap(TrackKey.ITEM_NAME, "number", TrackKey.ITEM_CATEGORY, "header"));
                    Snackbar.make(view, "Numbers Header is long clicked.", Snackbar.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        }
    };

    MultiAdapterExampleActivity me = this;
    RecyclerView mRecyclerView;
    MergeRecyclerAdapter<RecyclerView.Adapter> mMergeRecyclerAdapter;
    SingleViewAdapter mStringsHeaderAdapter;
    SimpleStringAdapter mStringsAdapter;
    SingleViewAdapter mNumbersHeaderAdapter;
    SimpleNumberAdapter mNumbersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_adapter_example);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        {   // prepare adapters
            mMergeRecyclerAdapter = new MergeRecyclerAdapter<>(this);
            {   // create strings header adapter
                mStringsHeaderAdapter = new SingleViewAdapter(this, R.layout.view_header_string);
                mStringsHeaderAdapter.setListenerRelay(mHeaderListenerRelay);
                mMergeRecyclerAdapter.addAdapter(mStringsHeaderAdapter);
            }
            {   // create strings adapter
                List<String> items = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    items.add("item " + i);
                }

                mStringsAdapter = new SimpleStringAdapter(this, items, mListenerRelay);
                mMergeRecyclerAdapter.addAdapter(mStringsAdapter);
            }
            {   // create numbers header adapter
                mNumbersHeaderAdapter = new SingleViewAdapter(this, R.layout.view_header_number);
                mNumbersHeaderAdapter.setListenerRelay(mHeaderListenerRelay);
                mMergeRecyclerAdapter.addAdapter(mNumbersHeaderAdapter);
            }
            {   // create numbers adapter
                List<Number> items = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    items.add(i);
                }
                mNumbersAdapter = new SimpleNumberAdapter(this, items, mListenerRelay);
                mMergeRecyclerAdapter.addAdapter(mNumbersAdapter);
            }
            {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                mRecyclerView.setAdapter(mMergeRecyclerAdapter);
            }
        }
    }

}
