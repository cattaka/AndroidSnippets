package net.cattaka.android.snippets.example.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import net.cattaka.android.snippets.adapter.ScrambleAdapter;
import net.cattaka.android.snippets.adapter.listener.ForwardingListener;
import net.cattaka.android.snippets.adapter.listener.ListenerRelay;
import net.cattaka.android.snippets.example.adapter.factory.MyInfoViewHolderFactory;
import net.cattaka.android.snippets.example.data.MyInfo;

import java.util.List;

/**
 * Created by cattaka on 16/05/16.
 */
public class MyInfoAdapter extends ScrambleAdapter<MyInfo> {
    @SuppressWarnings("unchecked")
    public MyInfoAdapter(Context context, List<MyInfo> items, ListenerRelay<ScrambleAdapter<MyInfo>, RecyclerView.ViewHolder> listenerRelay) {
        super(context, items, listenerRelay, castViewHolderFactories(new MyInfoViewHolderFactory()));
    }
}
