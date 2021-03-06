package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import net.cattaka.android.adaptertoolbox.adapter.CodeLabelAdapter;
import net.cattaka.android.snippets.example.data.OrdinalLabel;
import net.cattaka.android.snippets.example.tracker.IScreen;
import net.cattaka.android.snippets.example.tracker.TrackEvent;
import net.cattaka.android.snippets.example.tracker.TrackParam;
import net.cattaka.android.snippets.example.tracker.Tracker;

import static net.cattaka.android.snippets.example.tracker.TrackParamBundle.toParamsMap;

/**
 * Created by cattaka on 16/05/02.
 */
public class CodeLabelExampleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, IScreen {
    CodeLabelExampleActivity me = this;
    Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_label_example);

        // find views
        mSpinner = (Spinner) findViewById(R.id.spinner);

        // bind event handlers
        mSpinner.setOnItemSelectedListener(this);

        // set adapter
        mSpinner.setAdapter(CodeLabelAdapter.newInstance(this, OrdinalLabel.values(), true));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinner) {
            OrdinalLabel item = (OrdinalLabel) parent.getItemAtPosition(position);
            Tracker.getInstance().recordAction(me, TrackEvent.SELECTED, toParamsMap(TrackParam.ITEM_NAME, (item != null) ? item.getCode() : null));
            String text = (item != null) ? item.getLabel(getResources()) : "null";
            Toast.makeText(this, text + " is selected.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // no-op
    }
}
