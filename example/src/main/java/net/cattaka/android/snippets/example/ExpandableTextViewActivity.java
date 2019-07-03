package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.cattaka.android.snippets.view.ExpandableTextViewHelper;

public class ExpandableTextViewActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_text_view);

        textView = findViewById(R.id.text);

        findViewById(R.id.button_set_short_text).setOnClickListener(this);
        findViewById(R.id.button_set_long_text).setOnClickListener(this);

        ExpandableTextViewHelper.apply(
                (FrameLayout) findViewById(R.id.parent),
                (TextView) findViewById(R.id.text),
                (CompoundButton) findViewById(R.id.button),
                3
        );

        updateText("Long Text. Long Text. Long Text. Long Text. Long Text. Long Text. Long Text.");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_set_short_text:
                updateText("Short Text");
                break;
            case R.id.button_set_long_text:
                updateText("Long Text. Long Text. Long Text. Long Text. Long Text. Long Text. Long Text.");
                break;
        }
    }

    private void updateText(@NonNull String text) {
        textView.setText(text);
    }
}
