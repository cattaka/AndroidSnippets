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
import net.cattaka.android.snippets.example.adapter.SimpleStringAdapter;
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
public class RecyclerViewHeaderExampleActivity extends AppCompatActivity implements IScreen {

    ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> mListenerRelay = new ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder>() {
        @Override
        public void onClick(RecyclerView recyclerView, ScrambleAdapter<?> adapter, RecyclerView.ViewHolder viewHolder, View view) {
            if (recyclerView.getId() == R.id.recycler) {
                MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
                String item = mItemsAdapter.getItemAt(la.mLocalPosition);
                Tracker.getInstance().recordAction(me, TrackEvent.ACTION_CLICK, toParamsMap(TrackParam.ITEM_NAME, item, TrackParam.ITEM_CATEGORY, "items"));
                Snackbar.make(view, item + " is clicked.", Snackbar.LENGTH_SHORT).show();
            }
        }

        @Override
        public boolean onLongClick(RecyclerView recyclerView, ScrambleAdapter<?> adapter, RecyclerView.ViewHolder viewHolder, View view) {
            if (recyclerView.getId() == R.id.recycler) {
                MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
                String item = mItemsAdapter.getItemAt(la.mLocalPosition);
                Tracker.getInstance().recordAction(me, TrackEvent.ACTION_LONG_CLICK, toParamsMap(TrackParam.ITEM_NAME, item, TrackParam.ITEM_CATEGORY, "items"));
                Snackbar.make(view, item + " is long clicked.", Snackbar.LENGTH_SHORT).show();
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
                if (la.mAdapter == mHeaderAdapter) {
                    Tracker.getInstance().recordAction(me, TrackEvent.ACTION_CLICK, toParamsMap(TrackParam.ITEM_NAME, "header", TrackParam.ITEM_CATEGORY, "header"));
                    Snackbar.make(view, "Header is clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (la.mAdapter == mFooterAdapter) {
                    Tracker.getInstance().recordAction(me, TrackEvent.ACTION_CLICK, toParamsMap(TrackParam.ITEM_NAME, "footer", TrackParam.ITEM_CATEGORY, "footer"));
                    Snackbar.make(view, "Footer is clicked.", Snackbar.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public boolean onLongClick(RecyclerView recyclerView, SingleViewAdapter adapter, RecyclerView.ViewHolder viewHolder, View view) {
            if (recyclerView.getId() == R.id.recycler) {
                MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
                if (la.mAdapter == mHeaderAdapter) {
                    Tracker.getInstance().recordAction(me, TrackEvent.ACTION_LONG_CLICK, toParamsMap(TrackParam.ITEM_NAME, "header", TrackParam.ITEM_CATEGORY, "header"));
                    Snackbar.make(view, "Header is long clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (la.mAdapter == mFooterAdapter) {
                    Tracker.getInstance().recordAction(me, TrackEvent.ACTION_LONG_CLICK, toParamsMap(TrackParam.ITEM_NAME, "footer", TrackParam.ITEM_CATEGORY, "footer"));
                    Snackbar.make(view, "Footer is long clicked.", Snackbar.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        }
    };

    RecyclerViewHeaderExampleActivity me = this;
    RecyclerView mRecyclerView;
    MergeRecyclerAdapter<RecyclerView.Adapter> mMergeRecyclerAdapter;
    SingleViewAdapter mHeaderAdapter;
    SimpleStringAdapter mItemsAdapter;
    SingleViewAdapter mFooterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_header_example);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        {   // prepare adapters
            mMergeRecyclerAdapter = new MergeRecyclerAdapter<>(this);
            {   // create header adapter
                mHeaderAdapter = new SingleViewAdapter(this, R.layout.view_header);
                mHeaderAdapter.setListenerRelay(mHeaderListenerRelay);
                mMergeRecyclerAdapter.addAdapter(mHeaderAdapter);
            }
            {   // create items adapter
                List<String> items = new ArrayList<>();
                for (int i = 0; i < 30; i++) {
                    items.add("item " + i);
                }

                mItemsAdapter = new SimpleStringAdapter(this, items, mListenerRelay);
                mMergeRecyclerAdapter.addAdapter(mItemsAdapter);
            }
            {   // create footer adapter
                mFooterAdapter = new SingleViewAdapter(this, R.layout.view_footer);
                mFooterAdapter.setListenerRelay(mHeaderListenerRelay);
                mMergeRecyclerAdapter.addAdapter(mFooterAdapter);
            }
            {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                mRecyclerView.setAdapter(mMergeRecyclerAdapter);
            }
        }
    }
}
