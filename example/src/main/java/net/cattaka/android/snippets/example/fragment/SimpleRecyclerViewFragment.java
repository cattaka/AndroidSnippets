package net.cattaka.android.snippets.example.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.snippets.example.R;
import net.cattaka.android.snippets.example.adapter.factory.SimpleStringViewHolderFactory;

import java.util.ArrayList;

public class SimpleRecyclerViewFragment extends Fragment {
    RecyclerView mRecyclerView;
    ScrambleAdapter<String> mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_simple_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.view_recycler);

        {   // Setup mRecyclerView
            mAdapter = new ScrambleAdapter<>(requireContext(), new ArrayList<>(), null, new SimpleStringViewHolderFactory());
            mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(mAdapter);
            for (int i = 0; i < 100; i++) {
                mAdapter.getItems().add("Item : " + mAdapter.getItems().size());
            }
        }
    }
}
