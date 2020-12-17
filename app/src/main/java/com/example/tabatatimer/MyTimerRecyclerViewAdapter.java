package com.example.tabatatimer;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Models.SequenceInterval;
import ViewModels.AppViewModel;


public class MyTimerRecyclerViewAdapter extends RecyclerView.Adapter<MyTimerRecyclerViewAdapter.ViewHolder> {

    private final List<SequenceInterval> mIntervals;
    private final AppViewModel mAppViewModel;
    private final int mColor;

    public MyTimerRecyclerViewAdapter(AppViewModel appViewModel, List<SequenceInterval> intervals, int color) {
        mAppViewModel = appViewModel;
        mIntervals = intervals;
        mColor = color;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_timer, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mContentView.setText(Integer.toString(position + 1) + ". " + mIntervals.get(position).name
                + ": " + Integer.toString(mIntervals.get(position).value));
        holder.mContentView.setBackgroundColor(mColor);
    }

    @Override
    public int getItemCount() {
        return mIntervals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.contentTimerItem);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}