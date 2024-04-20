package com.t1r2340.unwrappd.helpers;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import android.view.View;

/**
 * Created on 27/Sep.
 */

public class EmptyAdapter extends RecyclerView.Adapter<EmptyAdapter.EmptyHolder> {
    @Override
    public EmptyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(EmptyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class EmptyHolder extends RecyclerView.ViewHolder {
        public EmptyHolder(View itemView) {
            super(itemView);
        }
    }
}