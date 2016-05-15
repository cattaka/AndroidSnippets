package net.cattaka.android.snippets.example.data;

import android.support.v7.widget.RecyclerView;

import net.cattaka.android.snippets.adapter.ScrambleAdapter;
import net.cattaka.android.snippets.adapter.listener.ListenerRelay;

import java.util.List;

/**
 * Created by cattaka on 16/05/16.
 */
public class NestedScrambleInfo {
    private RecyclerView.LayoutManager layoutManager;
    private List<ScrambleAdapter.AbsViewHolderFactory<? extends RecyclerView.ViewHolder>> viewHolderFactories;
    private ListenerRelay<ScrambleAdapter<Object>, RecyclerView.ViewHolder> listenerRelay;
    private List<Object> items;

    public NestedScrambleInfo() {
    }

    public NestedScrambleInfo(RecyclerView.LayoutManager layoutManager, List<ScrambleAdapter.AbsViewHolderFactory<? extends RecyclerView.ViewHolder>> viewHolderFactories, ListenerRelay<ScrambleAdapter<Object>, RecyclerView.ViewHolder> listenerRelay, List<Object> items) {
        this.layoutManager = layoutManager;
        this.viewHolderFactories = viewHolderFactories;
        this.listenerRelay = listenerRelay;
        this.items = items;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public List<ScrambleAdapter.AbsViewHolderFactory<? extends RecyclerView.ViewHolder>> getViewHolderFactories() {
        return viewHolderFactories;
    }

    public void setViewHolderFactories(List<ScrambleAdapter.AbsViewHolderFactory<? extends RecyclerView.ViewHolder>> viewHolderFactories) {
        this.viewHolderFactories = viewHolderFactories;
    }

    public ListenerRelay<ScrambleAdapter<Object>, RecyclerView.ViewHolder> getListenerRelay() {
        return listenerRelay;
    }

    public void setListenerRelay(ListenerRelay<ScrambleAdapter<Object>, RecyclerView.ViewHolder> listenerRelay) {
        this.listenerRelay = listenerRelay;
    }

    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }
}
