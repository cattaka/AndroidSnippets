package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;
import net.cattaka.android.snippets.example.adapter.factory.CodeLableViewHolderFactory;
import net.cattaka.android.snippets.example.adapter.factory.SimpleNumberViewHolderFactory;
import net.cattaka.android.snippets.example.adapter.factory.SimpleStringViewHolderFactory;
import net.cattaka.android.snippets.example.data.OrdinalLabel;
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
public class ScrambleAdapterExampleActivity extends AppCompatActivity implements IScreen {

    ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> mListenerRelay = new ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder>() {
        @Override
        public void onClick(RecyclerView recyclerView, ScrambleAdapter adapter, RecyclerView.ViewHolder viewHolder, View view) {
            if (recyclerView.getId() == R.id.recycler) {
                if (viewHolder instanceof SimpleStringViewHolderFactory.ViewHolder) {
                    String item = (String) adapter.getItemAt(viewHolder.getAdapterPosition());
                    Tracker.getInstance().recordAction(me, TrackEvent.ACTION_CLICK, toParamsMap(TrackParam.ITEM_NAME, item, TrackParam.ITEM_CATEGORY, "string"));
                    Snackbar.make(viewHolder.itemView, "String " + item + " is clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (viewHolder instanceof SimpleNumberViewHolderFactory.ViewHolder) {
                    Number item = (Number) adapter.getItemAt(viewHolder.getAdapterPosition());
                    Tracker.getInstance().recordAction(me, TrackEvent.ACTION_CLICK, toParamsMap(TrackParam.ITEM_NAME, String.valueOf(item), TrackParam.ITEM_CATEGORY, "number"));
                    Snackbar.make(viewHolder.itemView, "Number " + item + " is clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (viewHolder instanceof CodeLableViewHolderFactory.ViewHolder) {
                    OrdinalLabel item = (OrdinalLabel) adapter.getItemAt(viewHolder.getAdapterPosition());
                    String text = item.getLabel(getResources()) + "(" + item.getCode() + ")";
                    if (view.getId() == R.id.text_code) {
                        Tracker.getInstance().recordAction(me, TrackEvent.ACTION_CLICK, toParamsMap(TrackParam.ITEM_NAME, item.getCode(), TrackParam.ITEM_CATEGORY, "ordinal_label", TrackParam.VIEW_NAME, "text_code"));
                        Snackbar.make(viewHolder.itemView, "The code of " + text + " is clicked.", Snackbar.LENGTH_SHORT).show();
                    } else if (view.getId() == R.id.text_label) {
                        Tracker.getInstance().recordAction(me, TrackEvent.ACTION_CLICK, toParamsMap(TrackParam.ITEM_NAME, item.getCode(), TrackParam.ITEM_CATEGORY, "ordinal_label", TrackParam.VIEW_NAME, "text_label"));
                        Snackbar.make(viewHolder.itemView, "The label of " + text + " is clicked.", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public boolean onLongClick(RecyclerView recyclerView, ScrambleAdapter adapter, RecyclerView.ViewHolder viewHolder, View view) {
            if (recyclerView.getId() == R.id.recycler) {
                if (viewHolder instanceof SimpleStringViewHolderFactory.ViewHolder) {
                    String item = (String) adapter.getItemAt(viewHolder.getAdapterPosition());
                    Tracker.getInstance().recordAction(me, TrackEvent.ACTION_LONG_CLICK, toParamsMap(TrackParam.ITEM_NAME, item, TrackParam.ITEM_CATEGORY, "string"));
                    Snackbar.make(view, "String " + item + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (viewHolder instanceof SimpleNumberViewHolderFactory.ViewHolder) {
                    Number item = (Number) adapter.getItemAt(viewHolder.getAdapterPosition());
                    Tracker.getInstance().recordAction(me, TrackEvent.ACTION_LONG_CLICK, toParamsMap(TrackParam.ITEM_NAME, String.valueOf(item), TrackParam.ITEM_CATEGORY, "number"));
                    Snackbar.make(view, "Number " + item + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (viewHolder instanceof CodeLableViewHolderFactory.ViewHolder) {
                    OrdinalLabel item = (OrdinalLabel) adapter.getItemAt(viewHolder.getAdapterPosition());
                    String text = item.getLabel(getResources()) + "(" + item.getCode() + ")";
                    if (view.getId() == R.id.text_code) {
                        Tracker.getInstance().recordAction(me, TrackEvent.ACTION_LONG_CLICK, toParamsMap(TrackParam.ITEM_NAME, item.getCode(), TrackParam.ITEM_CATEGORY, "ordinal_label", TrackParam.VIEW_NAME, "text_code"));
                        Snackbar.make(view, "The code of " + text + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                    } else if (view.getId() == R.id.text_label) {
                        Tracker.getInstance().recordAction(me, TrackEvent.ACTION_LONG_CLICK, toParamsMap(TrackParam.ITEM_NAME, item.getCode(), TrackParam.ITEM_CATEGORY, "ordinal_label", TrackParam.VIEW_NAME, "text_label"));
                        Snackbar.make(view, "The label of " + text + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
            return false;
        }
    };

    ScrambleAdapterExampleActivity me = this;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scramble_adapter_example);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        { // set adapter
            List<Object> items = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                switch (i % 4) {
                    case 0:
                        items.add(i);
                        break;
                    case 1:
                        items.add(String.valueOf(i));
                        break;
                    case 2:
                        OrdinalLabel[] ols = OrdinalLabel.values();
                        items.add(ols[(i / 4) % ols.length]);
                    default:
                        items.add(new Object());
                        break;
                }
            }
            ScrambleAdapter<Object> adapter = new ScrambleAdapter<>(this, items,
                    mListenerRelay,
                    new SimpleStringViewHolderFactory(),
                    new SimpleNumberViewHolderFactory(),
                    new CodeLableViewHolderFactory(getResources())
            );
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(adapter);
        }
    }
}
