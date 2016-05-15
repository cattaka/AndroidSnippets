package net.cattaka.android.snippets.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.snippets.adapter.listener.ForwardingListener;
import net.cattaka.android.snippets.adapter.listener.ListenerRelay;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by takao on 2016/05/10.
 */
public class ScrambleAdapter<T> extends AbsScrambleAdapter<
        ScrambleAdapter<T>,
        RecyclerView.ViewHolder,
        ForwardingListener<ScrambleAdapter<T>, RecyclerView.ViewHolder>,
        RecyclerView.ViewHolder,
        ForwardingListener<ScrambleAdapter<T>, RecyclerView.ViewHolder>,
        ListenerRelay<ScrambleAdapter<T>, ? super RecyclerView.ViewHolder>> {
    private Context mContext;
    private List<T> mItems;
    private ListenerRelay<ScrambleAdapter<T>, RecyclerView.ViewHolder> mListenerRelay = new ListenerRelay<>();

    public static <T> ScrambleAdapter<T> newInstance(Context context, List<T> items, ListenerRelay<ScrambleAdapter<T>, RecyclerView.ViewHolder> listenerRelay, IViewHolderFactory<ScrambleAdapter<T>, RecyclerView.ViewHolder, ForwardingListener<ScrambleAdapter<T>, RecyclerView.ViewHolder>, ?, ?, ListenerRelay<ScrambleAdapter<T>, ? super RecyclerView.ViewHolder>>... viewHolderFactories) {
        return new ScrambleAdapter<T>(context, items, listenerRelay, Arrays.asList(viewHolderFactories));
    }

    public ScrambleAdapter(Context context, List<T> items, ListenerRelay<ScrambleAdapter<T>, RecyclerView.ViewHolder> listenerRelay, List<? extends IViewHolderFactory<ScrambleAdapter<T>, RecyclerView.ViewHolder, ForwardingListener<ScrambleAdapter<T>, RecyclerView.ViewHolder>, ?, ?, ListenerRelay<ScrambleAdapter<T>, ? super RecyclerView.ViewHolder>>> iViewHolderFactories) {
        super(listenerRelay, iViewHolderFactories);
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public RecyclerView.ViewHolder createNullViewHolder() {
        View view = new View(mContext);
        view.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public ForwardingListener<ScrambleAdapter<T>, RecyclerView.ViewHolder> createNullForwardingListener() {
        return new ForwardingListener<>();
    }

    @Override
    public ScrambleAdapter<T> getSelf() {
        return this;
    }


    @Override
    public T getItemAt(int position) {
        return mItems.get(position);
    }

    public List<T> getItems() {
        return mItems;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public abstract static class AbsViewHolderFactory<VH extends RecyclerView.ViewHolder> implements IViewHolderFactory<ScrambleAdapter<Object>, RecyclerView.ViewHolder, ForwardingListener<ScrambleAdapter<Object>, RecyclerView.ViewHolder>, VH, ForwardingListener<ScrambleAdapter<Object>, RecyclerView.ViewHolder>, ListenerRelay<ScrambleAdapter<Object>, ? super RecyclerView.ViewHolder>> {
        @Override
        public ForwardingListener<ScrambleAdapter<Object>, RecyclerView.ViewHolder> createForwardingListener() {
            return new ForwardingListener<>();
        }
    }
}
