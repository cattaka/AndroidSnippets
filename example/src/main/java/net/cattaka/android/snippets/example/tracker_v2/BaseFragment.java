package net.cattaka.android.snippets.example.tracker_v2;

import android.support.v4.app.Fragment;

/**
 * Created by cattaka on 18/02/07.
 */

public class BaseFragment extends Fragment {
    @Override
    public void onStart() {
        super.onStart();
        MyTracker.getInstance().logScreen(this);
    }
}
