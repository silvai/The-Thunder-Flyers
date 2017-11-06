package edu.gatech.thethunderflyers.android.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.model.RatData;

public class RatDataAdapter extends RecyclerView.Adapter{
    private List<RatData> data;
    private Date date = new Date(0);
    private int lastId = 0;

    public void result(List<RatData> response) {
        this.date = response.get(response.size() - 1).getDate();
        this.lastId = response.get(response.size() - 1).getId();
        this.data.addAll(response);
        this.notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(RatData item);
    }

    private final OnItemClickListener listener;
    public RatDataAdapter(List<RatData> data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View v = li.inflate(R.layout.report_card, parent, false);
        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DataViewHolder dvh = (DataViewHolder) holder;
        final RatData rd = data.get(position);
        dvh.dataDate.setText(String.format("%tc", rd.getDate()));
        dvh.message.setText("CLICK FOR MORE INFO...");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(rd);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public List<RatData> getData() {
        return data;
    }

    public int getLastId() {
        return lastId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private static class DataViewHolder extends RecyclerView.ViewHolder {
        private TextView dataDate;
        private TextView message;

        public DataViewHolder(View itemView) {
            super(itemView);
            dataDate = itemView.findViewById(R.id.reportDate);
            message = itemView.findViewById(R.id.message);
        }
    }
}
