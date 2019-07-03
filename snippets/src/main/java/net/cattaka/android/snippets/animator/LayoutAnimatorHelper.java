package net.cattaka.android.snippets.animator;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;

import net.cattaka.android.snippets.R;

/**
 * Save all methods from proguard!!
 * <p/>
 * Created by cattaka on 2016/04/15.
 */
@Keep
public class LayoutAnimatorHelper {
    public static final Property<LayoutAnimatorHelper, Integer> TOP_MARGIN = Property.of(LayoutAnimatorHelper.class, int.class, "topMargin");
    public static final Property<LayoutAnimatorHelper, Integer> RIGHT_MARGIN = Property.of(LayoutAnimatorHelper.class, int.class, "rightMargin");
    public static final Property<LayoutAnimatorHelper, Integer> BOTTOM_MARGIN = Property.of(LayoutAnimatorHelper.class, int.class, "bottomMargin");
    public static final Property<LayoutAnimatorHelper, Integer> LEFT_MARGIN = Property.of(LayoutAnimatorHelper.class, int.class, "leftMargin");
    public static final Property<LayoutAnimatorHelper, Integer> WIDTH = Property.of(LayoutAnimatorHelper.class, int.class, "width");
    public static final Property<LayoutAnimatorHelper, Integer> HEIGHT = Property.of(LayoutAnimatorHelper.class, int.class, "height");

    @NonNull
    public static LayoutAnimatorHelper obtain(@NonNull View view) {
        Object obj = view.getTag(R.id.layout_animator_helper);
        if (obj instanceof LayoutAnimatorHelper) {
            return (LayoutAnimatorHelper) obj;
        } else {
            LayoutAnimatorHelper item = new LayoutAnimatorHelper(view);
            view.setTag(R.id.layout_animator_helper, item);
            return item;
        }
    }

    private View mView;

    public LayoutAnimatorHelper(View view) {
        mView = view;
    }

    public View getView() {
        return mView;
    }

    public int getTopMargin() {
        return ((ViewGroup.MarginLayoutParams) mView.getLayoutParams()).topMargin;
    }

    public void setTopMargin(int v) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mView.getLayoutParams();
        params.topMargin = v;
        mView.setLayoutParams(params);
    }

    public int getLeftMargin() {
        return ((ViewGroup.MarginLayoutParams) mView.getLayoutParams()).leftMargin;
    }

    public void setLeftMargin(int v) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mView.getLayoutParams();
        params.leftMargin = v;
        mView.setLayoutParams(params);
    }

    public int getRightMargin() {
        return ((ViewGroup.MarginLayoutParams) mView.getLayoutParams()).rightMargin;
    }

    public void setRightMargin(int v) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mView.getLayoutParams();
        params.rightMargin = v;
        mView.setLayoutParams(params);
    }

    public int getBottomMargin() {
        return ((ViewGroup.MarginLayoutParams) mView.getLayoutParams()).bottomMargin;
    }

    public void setBottomMargin(int v) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mView.getLayoutParams();
        params.bottomMargin = v;
        mView.setLayoutParams(params);
    }

    public int getWidth() {
        return (mView.getLayoutParams()).width;
    }

    public void setWidth(int v) {
        ViewGroup.LayoutParams params = mView.getLayoutParams();
        params.width = v;
        mView.setLayoutParams(params);
    }

    public int getHeight() {
        return (mView.getLayoutParams()).height;
    }

    public void setHeight(int v) {
        ViewGroup.LayoutParams params = mView.getLayoutParams();
        params.height = v;
        mView.setLayoutParams(params);
    }
}
