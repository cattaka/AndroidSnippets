package net.cattaka.android.snippets.example;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

import net.cattaka.android.snippets.example.data.ColorItem;
import net.cattaka.android.snippets.example.tracker.IScreen;
import net.cattaka.android.snippets.example.tracker.Tracker;
import net.cattaka.android.snippets.util.StatusBarUtils;

import java.util.ArrayList;
import java.util.Random;

import static net.cattaka.android.snippets.example.tracker.TrackAction.ACTION_CLICK;
import static net.cattaka.android.snippets.example.tracker.TrackKey.INDEX;
import static net.cattaka.android.snippets.example.tracker.TrackKey.VALUE;
import static net.cattaka.android.snippets.example.tracker.TrackKey.VIEW_NAME;
import static net.cattaka.android.snippets.example.tracker.TrackParams.toParamsMap;

public class ControlStatusBarColorActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, IScreen {
    private ListView mListView;
    private CheckBox mCheckTranslucentStatus;
    private CheckBox mCheckDrawsSystemBarBackgrounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.onCreate(this);
        setContentView(R.layout.activity_control_status_bar_color);

        mListView = (ListView) findViewById(R.id.view_list);
        mCheckTranslucentStatus = (CheckBox) findViewById(R.id.check_translucent_status);
        mCheckDrawsSystemBarBackgrounds = (CheckBox) findViewById(R.id.check_draws_system_bar_backgrounds);

        mCheckTranslucentStatus.setOnClickListener(this);
        mCheckDrawsSystemBarBackgrounds.setOnClickListener(this);

        ArrayAdapter<ColorItem> adapter = new ArrayAdapter<ColorItem>(this, android.R.layout.simple_list_item_1, new ArrayList<ColorItem>()) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                view.setBackgroundColor(getItem(position).getColor());
                return view;
            }
        };
        Random random = new Random();
        adapter.add(new ColorItem(0x00000000));
        adapter.add(new ColorItem(0xFF000000));
        adapter.add(new ColorItem(0x00FFFFFF));
        adapter.add(new ColorItem(0xFFFFFFFF));
        for (int i = 0; i < 100; i++) {
            adapter.add(new ColorItem(random.nextInt()));
        }
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);

        refreshWindowFlags();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.check_translucent_status) {
            Tracker.getInstance().recordAction(this, ACTION_CLICK, toParamsMap(VIEW_NAME, "check_translucent_status", VALUE, String.valueOf(mCheckTranslucentStatus.isChecked())));
            refreshWindowFlags();
        } else if (view.getId() == R.id.check_draws_system_bar_backgrounds) {
            Tracker.getInstance().recordAction(this, ACTION_CLICK, toParamsMap(VIEW_NAME, "check_draws_system_bar_backgrounds", VALUE, String.valueOf(mCheckDrawsSystemBarBackgrounds.isChecked())));
            refreshWindowFlags();
        }
    }

    private void refreshWindowFlags() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (mCheckTranslucentStatus.isChecked()) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            } else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mCheckDrawsSystemBarBackgrounds.isChecked()) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            } else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (adapterView.getId() == R.id.view_list) {
            Tracker.getInstance().recordAction(this, ACTION_CLICK, toParamsMap(INDEX, position, VIEW_NAME, "view_list"));
            ColorItem item = (ColorItem) adapterView.getItemAtPosition(position);
            StatusBarUtils.setStatusBarColor(this, item.getColor());
        }
    }
}
