package net.cattaka.android.snippets.example;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import net.cattaka.android.snippets.view.ViewSnapper;

import java.util.Locale;

/**
 * Created by takao on 2016/10/26.
 */
public class ViewSnapperExampleActivity extends AppCompatActivity {
    ScrollView mVerticalScroll;
    HorizontalScrollView mHorizontalScroll;
    LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_snapper);

        mVerticalScroll = (ScrollView) findViewById(R.id.scroll_vertical);
        mHorizontalScroll = (HorizontalScrollView) findViewById(R.id.scroll_horizontal);
        mLayout = (LinearLayout) findViewById(R.id.layout);
        makeLayout();

        ViewSnapper viewSnapper = new ViewSnapper();
        viewSnapper.apply(mVerticalScroll, true, true, ViewSnapper.withClass(Button.class), ViewSnapper.anchorVertical(true, true));
        viewSnapper.apply(mHorizontalScroll, true, true, ViewSnapper.withClass(Button.class), ViewSnapper.anchorHorizontal(true, true));
    }

    private void makeLayout() {
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);

        for (int c = 0; c < 32; c++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for (int r = 0; r < 32; r++) {
                Button b = new Button(this);
                b.setText(String.format(Locale.ROOT, "%d, %d", c, r));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size.x * 2 / 5, size.y * 2 / 5);
                b.setLayoutParams(params);
                b.setBackgroundColor(0xFF000000 + (int) (Math.random() * 0xFFFFFF));
                linearLayout.addView(b);
            }
            mLayout.addView(linearLayout);
        }
    }
}
