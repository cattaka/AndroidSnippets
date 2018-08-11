package net.cattaka.android.snippets.example.adapter.factory;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ForwardingListener;
import net.cattaka.android.snippets.example.R;
import net.cattaka.android.snippets.example.data.Repo;

/**
 * Created by cattaka on 16/07/10.
 */
public class RepoViewHolderFactory extends ScrambleAdapter.AbsViewHolderFactory<RepoViewHolderFactory.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ScrambleAdapter<?> adapter, @NonNull ViewGroup parent, @NonNull ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder> forwardingListener) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.openButton.setOnClickListener(forwardingListener);
        holder.ownerButton.setOnClickListener(forwardingListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScrambleAdapter<?> adapter, @NonNull ViewHolder holder, int position, @Nullable Object object) {
        Repo item = (Repo) object;

        holder.nameText.setText(item.getName());
        holder.urlText.setText(item.getUrl());
    }

    @Override
    public boolean isAssignable(Object object) {
        return object instanceof Repo;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView nameText;
        public final TextView urlText;
        public final View openButton;
        public final View ownerButton;

        public ViewHolder(View itemView) {
            super(itemView);
            nameText = (TextView) itemView.findViewById(R.id.text_name);
            urlText = (TextView) itemView.findViewById(R.id.text_url);
            openButton = itemView.findViewById(R.id.button_open);
            ownerButton = itemView.findViewById(R.id.button_owner);
        }
    }
}
