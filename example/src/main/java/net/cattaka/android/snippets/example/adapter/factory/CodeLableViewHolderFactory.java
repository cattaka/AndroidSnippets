package net.cattaka.android.snippets.example.adapter.factory;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ForwardingListener;
import net.cattaka.android.adaptertoolbox.data.ICodeLabel;
import net.cattaka.android.snippets.example.R;

/**
 * Created by cattaka on 16/05/02.
 */
public class CodeLableViewHolderFactory extends ScrambleAdapter.AbsViewHolderFactory<CodeLableViewHolderFactory.ViewHolder> {
    Resources mResources;

    public CodeLableViewHolderFactory(Resources resources) {
        mResources = resources;
    }

    @Override
    public ViewHolder onCreateViewHolder(ScrambleAdapter<?> adapter, ViewGroup parent, ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder> forwardingListener) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_code_label, parent, false);
        ViewHolder vh = new ViewHolder(view);
        vh.codeText.setOnClickListener(forwardingListener);
        vh.codeText.setOnLongClickListener(forwardingListener);
        vh.labelText.setOnClickListener(forwardingListener);
        vh.labelText.setOnLongClickListener(forwardingListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(ScrambleAdapter adapter, ViewHolder holder,
                                 int position, Object object) {
        ICodeLabel item = (ICodeLabel) object;

        holder.codeText.setText(item.getCode());
        holder.labelText.setText(item.getLabel(mResources));
    }

    @Override
    public boolean isAssignable(Object object) {
        return object instanceof ICodeLabel;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView codeText;
        TextView labelText;

        public ViewHolder(View itemView) {
            super(itemView);
            codeText = (TextView) itemView.findViewById(R.id.text_code);
            labelText = (TextView) itemView.findViewById(R.id.text_label);
        }
    }
}
