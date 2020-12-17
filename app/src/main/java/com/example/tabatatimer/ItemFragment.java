package com.example.tabatatimer;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Data.SequenceDatabase;
import Helpers.SimpleItemTouchHelperCallback;
import Models.Sequence;
import ViewModels.AppViewModel;

public class ItemFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private List<Sequence> sequences;
    AppViewModel appViewModel;
    RecyclerView recyclerView;
    MyItemRecyclerViewAdapter adapter;


    private void initSequenceList() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                sequences = SequenceDatabase.getDatabase(requireActivity()).timerDao().getAll();
            }
        });
    }

    public ItemFragment() {
    }

    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        initSequenceList();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        Button btnAddSeq = view.findViewById(R.id.btnAddSeq);
        btnAddSeq.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_itemFragment_to_sequenceFragment);
        });

        Button btnSettings = view.findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_itemFragment_to_settingsFragment);
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        adapter = new MyItemRecyclerViewAdapter(sequences, context, appViewModel);

        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }
}