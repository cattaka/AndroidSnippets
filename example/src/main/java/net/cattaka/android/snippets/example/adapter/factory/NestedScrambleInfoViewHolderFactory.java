package net.cattaka.android.snippets.example.adapter.factory;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ForwardingListener;
import net.cattaka.android.snippets.example.R;
import net.cattaka.android.snippets.example.data.NestedScrambleInfo;

/**
 * Created by cattaka on 16/05/16.
 */
public class NestedScrambleInfoViewHolderFactory extends ScrambleAdapter.AbsViewHolderFactory<NestedScrambleInfoViewHolderFactory.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ScrambleAdapter<?> adapter, ViewGroup parent, ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder> forwardingListener) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nested_scramble, parent, false);

        {   // Hack height
            int h = parent.getHeight();
            if (h > 0) {
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = h / 3;
                view.setLayoutParams(params);
            }
        }

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ScrambleAdapter<?> adapter, ViewHolder holder, int position, Object object) {
        NestedScrambleInfo item = (NestedScrambleInfo) object;

        ScrambleAdapter<Object> nextedAdapter = new ScrambleAdapter<Object>(holder.itemView.getContext(), item.getItems(), item.getListenerRelay(), item.getViewHolderFactories());
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setAdapter(nextedAdapter);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler);
        }
    }

    @Override
    public boolean isAssignable(Object object) {
        return object instanceof NestedScrambleInfo;
    }
}
