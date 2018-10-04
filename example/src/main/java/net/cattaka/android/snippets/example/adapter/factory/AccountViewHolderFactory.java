package net.cattaka.android.snippets.example.adapter.factory;

import android.accounts.Account;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ForwardingListener;
import net.cattaka.android.snippets.example.R;

public class AccountViewHolderFactory extends ScrambleAdapter.AbsViewHolderFactory<AccountViewHolderFactory.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ScrambleAdapter<?> adapter, @NonNull ViewGroup parent, @NonNull ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder> forwardingListener) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(forwardingListener);
        vh.buttonEdit.setOnClickListener(forwardingListener);
        vh.buttonInfo.setOnClickListener(forwardingListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ScrambleAdapter adapter, @NonNull ViewHolder holder, int position, Object object) {
        Account item = (Account) object;
        holder.textLabel.setText(item != null ? item.name : null);
    }

    @Override
    public boolean isAssignable(Object object) {
        return object instanceof Account;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textLabel;
        View buttonEdit;
        View buttonInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            textLabel = itemView.findViewById(R.id.text_label);
            buttonEdit = itemView.findViewById(R.id.button_edit);
            buttonInfo = itemView.findViewById(R.id.button_info);
        }
    }
}
