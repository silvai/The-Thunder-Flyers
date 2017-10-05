package edu.gatech.thethunderflyers.android.controller;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import edu.gatech.thethunderflyers.android.model.RatData;

public class RatDataAdapter extends RecyclerView.Adapter{

    private RatData[] data;
    private final int DATA = 0;
    private final int LOADING = 1;

    public RatDataAdapter(RatData[] data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
