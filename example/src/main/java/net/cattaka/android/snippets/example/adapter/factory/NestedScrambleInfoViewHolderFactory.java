package net.cattaka.android.snippets.example.adapter.factory;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.cattaka.android.snippets.adapter.AdapterConverter;
import net.cattaka.android.snippets.adapter.ScrambleAdapter;
import net.cattaka.android.snippets.adapter.listener.ForwardingListener;
import net.cattaka.android.snippets.example.R;
import net.cattaka.android.snippets.example.data.NestedScrambleInfo;

/**
 * Created by cattaka on 16/05/16.
 */
public class NestedScrambleInfoViewHolderFactory extends ScrambleAdapter.AbsViewHolderFactory<NestedScrambleInfoViewHolderFactory.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ScrambleAdapter<Object> adapter, ViewGroup parent, ForwardingListener<ScrambleAdapter<Object>, RecyclerView.ViewHolder> forwardingListener) {
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
    public void onBindViewHolder(ScrambleAdapter adapter, ViewHolder holder, int position, Object object) {
        NestedScrambleInfo item = (NestedScrambleInfo) object;

        ScrambleAdapter nextedAdapter = new ScrambleAdapter(holder.itemView.getContext(), item.getItems(), item.getListenerRelay(), item.getViewHolderFactories());
        holder.recyclerView.setLayoutManager(item.getLayoutManager());
        holder.recyclerView.setAdapter(nextedAdapter);
    }

    public static class ViewHolder extends AdapterConverter.ViewHolder {
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
