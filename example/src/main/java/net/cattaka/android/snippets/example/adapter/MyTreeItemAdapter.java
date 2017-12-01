package net.cattaka.android.snippets.example.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.Space;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import net.cattaka.android.adaptertoolbox.adapter.AbsChoosableTreeItemAdapter;
import net.cattaka.android.snippets.example.R;
import net.cattaka.android.snippets.example.data.MyTreeItem;

import java.util.List;

/**
 * Created by cattaka on 16/05/21.
 */
public class MyTreeItemAdapter extends AbsChoosableTreeItemAdapter<
        MyTreeItemAdapter,
        MyTreeItemAdapter.ViewHolder,
        MyTreeItem,
        MyTreeItemAdapter.WrappedItem
        > {
    public static ITreeItemAdapterRef<MyTreeItemAdapter, ViewHolder, MyTreeItem, WrappedItem> REF = new ITreeItemAdapterRef<MyTreeItemAdapter, ViewHolder, MyTreeItem, WrappedItem>() {
        @NonNull
        @Override
        public Class<MyTreeItem> getItemClass() {
            return MyTreeItem.class;
        }

        @NonNull
        @Override
        public MyTreeItemAdapter createAdapter(@NonNull Context context, @NonNull List<MyTreeItem> items) {
            return new MyTreeItemAdapter(context, items);
        }

        @Override
        public WrappedItem createWrappedItem(int level, MyTreeItem item, WrappedItem parent) {
            return new WrappedItem(level, item, parent);
        }
    };

    private IMyTreeItemAdapterListener mListener;

    public MyTreeItemAdapter(Context context, List<MyTreeItem> items) {
        super(context, items, REF);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_my_tree_item, parent, false);
        ViewHolder holder = new ViewHolder(view, this);

        holder.openedCheck.setOnClickListener(holder);

        holder.itemView.setOnClickListener(getForwardingListener(parent));
        holder.itemView.setOnLongClickListener(getForwardingListener(parent));

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WrappedItem wrappedItem = getItemAt(position);
        MyTreeItem item = wrappedItem.getItem();

        {
            ViewGroup.LayoutParams params = holder.levelSpace.getLayoutParams();
            params.width = wrappedItem.level * getContext().getResources().getDimensionPixelSize(R.dimen.element_spacing_large);
            holder.levelSpace.setLayoutParams(params);
        }
        {
            boolean hasChildren = (wrappedItem.children != null && wrappedItem.children.size() > 0);
            holder.openedCheck.setVisibility(hasChildren ? View.VISIBLE : View.INVISIBLE);
        }

        holder.openedCheck.setChecked(wrappedItem.isOpened());
        holder.labelText.setText(item.getText());
    }


    public void setListener(IMyTreeItemAdapterListener mListener) {
        this.mListener = mListener;
    }

    private void onItemOpenChanged(@NonNull MyTreeItem item, boolean open) {
        if (mListener != null) {
            mListener.onItemOpenChanged(item, open);
        }
    }

    public static class WrappedItem extends AbsChoosableTreeItemAdapter.WrappedItem<WrappedItem, MyTreeItem> {
        public WrappedItem(int level, MyTreeItem item, WrappedItem parent) {
            super(level, item, parent);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyTreeItemAdapter adapter;
        Space levelSpace;
        CompoundButton openedCheck;
        TextView labelText;

        public ViewHolder(View itemView, MyTreeItemAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            levelSpace = (Space) itemView.findViewById(R.id.space_level);
            openedCheck = (CompoundButton) itemView.findViewById(R.id.check_opened);
            labelText = (TextView) itemView.findViewById(R.id.text_label);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            WrappedItem item = adapter.getItemAt(position);
            switch (view.getId()) {
                case R.id.check_opened: {
                    adapter.doOpen(item, !item.isOpened());
                    adapter.onItemOpenChanged(item.item, item.isOpened());
                    break;
                }
                default: {
                    // no-op
                    break;
                }
            }
        }
    }

    public interface IMyTreeItemAdapterListener {
        void onItemOpenChanged(@NonNull MyTreeItem item, boolean open);
    }
}
