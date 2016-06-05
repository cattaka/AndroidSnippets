package net.cattaka.android.snippets.example.adapter.factory;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;

import net.cattaka.android.adaptertoolbox.adapter.CodeLabelAdapter;
import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ForwardingListener;
import net.cattaka.android.adaptertoolbox.utils.SpinnerUtils;
import net.cattaka.android.snippets.example.R;
import net.cattaka.android.snippets.example.data.MyInfo;
import net.cattaka.android.snippets.example.data.OrdinalLabel;

/**
 * Created by cattaka on 16/05/15.
 */
public class MyInfoViewHolderFactory extends ScrambleAdapter.AbsViewHolderFactory<MyInfoViewHolderFactory.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ScrambleAdapter<?> adapter, ViewGroup parent, ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder> forwardingListener) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_info, parent, false);
        ViewHolder vh = new ViewHolder(view);

        vh.intValueSeek.setOnSeekBarChangeListener(forwardingListener);
        vh.ordinalLabelSpinner.setOnItemSelectedListener(forwardingListener);
        vh.checkedSwitch.setOnCheckedChangeListener(forwardingListener);

        vh.ordinalLabelSpinner.setAdapter(CodeLabelAdapter.newInstance(view.getContext(), OrdinalLabel.values(), true));

        return vh;
    }

    @Override
    public void onBindViewHolder(ScrambleAdapter<?> adapter, ViewHolder holder, int position, Object object) {
        MyInfo item = (MyInfo) object;

        holder.intValueSeek.setProgress(item.getIntValue());
        SpinnerUtils.selectSpinnerValue(holder.ordinalLabelSpinner, item.getOrdinalLabel());
        holder.checkedSwitch.setChecked(item.isChecked());
    }

    @Override
    public boolean isAssignable(Object object) {
        return object instanceof MyInfo;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SeekBar intValueSeek;
        Spinner ordinalLabelSpinner;
        Switch checkedSwitch;

        public ViewHolder(View itemView) {
            super(itemView);
            intValueSeek = (SeekBar) itemView.findViewById(R.id.seek_int_value);
            ordinalLabelSpinner = (Spinner) itemView.findViewById(R.id.spinner_ordinal_label);
            checkedSwitch = (Switch) itemView.findViewById(R.id.switch_checked);
        }
    }
}
