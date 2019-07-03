package net.cattaka.android.snippets.view;

import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import net.cattaka.android.snippets.animator.LayoutAnimatorHelper;

public class ExpandableTextViewHelper {
    public static void apply(@NonNull final TextView textView, @NonNull final CompoundButton expandButton, final int shrinkLines) {
        int lineHeight = (int) (textView.getPaint().getFontMetrics().bottom - textView.getPaint().getFontMetrics().top);
        LayoutAnimatorHelper.obtain(expandButton).setHeight(lineHeight);

        final CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layoutTextViewHeight(textView, expandButton, isChecked ? Integer.MAX_VALUE : shrinkLines, isChecked, true);
            }
        };

        expandButton.setOnCheckedChangeListener(onCheckedChangeListener);

        textView.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // no-op
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // no-op
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        layoutTextViewHeight(textView, expandButton, shrinkLines, false, false);
                        boolean v = isEllipsized(textView, shrinkLines);
                        expandButton.setOnCheckedChangeListener(null);
                        expandButton.setChecked(!v);
                        expandButton.setOnCheckedChangeListener(onCheckedChangeListener);
                        expandButton.setVisibility(v ? View.VISIBLE : View.INVISIBLE);
                    }
                }
        );

        layoutTextViewHeight(textView, expandButton, shrinkLines, false, false);
    }

    private static void layoutTextViewHeight(@NonNull TextView textView, CompoundButton expandButton, int shrinkLines, boolean expanded, boolean animated) {
        textView.setMaxLines(expanded ? Integer.MAX_VALUE : shrinkLines);
        textView.measure(
                View.MeasureSpec.makeMeasureSpec(textView.getWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.getMode(View.MeasureSpec.UNSPECIFIED)
        );
        int goalHeight = textView.getMeasuredHeight();
        int goalBottomMargin = (expanded) ? expandButton.getHeight() : 0;
        LayoutAnimatorHelper helper = LayoutAnimatorHelper.obtain(textView);
        if (goalHeight != helper.getHeight()) {
            if (animated) {
                ObjectAnimator.ofInt(helper, LayoutAnimatorHelper.HEIGHT, textView.getHeight(), goalHeight).start();
                ObjectAnimator.ofInt(helper, LayoutAnimatorHelper.BOTTOM_MARGIN, helper.getBottomMargin(), goalBottomMargin).start();
            } else {
                helper.setHeight(goalHeight);
                helper.setBottomMargin(goalBottomMargin);
            }
        }
    }

    private static boolean isEllipsized(@NonNull TextView textView, int shrinkLines) {
        Layout l = textView.getLayout();
        return (l != null && l.getLineCount() > shrinkLines);
    }
}
