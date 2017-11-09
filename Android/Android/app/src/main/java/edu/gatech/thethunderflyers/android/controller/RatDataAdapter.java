package edu.gatech.thethunderflyers.android.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.model.RatData;

class RatDataAdapter extends RecyclerView.Adapter{
    private final List<RatData> data = new ArrayList<>();
    private Date date = new Date(0);
    private int lastId = 0;

    /**
     * Method to get the result
     * @param response is the list of ratData
     */
    void result(List<RatData> response) {
        RatData rd = response.get(response.size() - 1);
        this.date = rd.getDate();
        this.lastId = rd.getId();
        this.data.addAll(response);
        this.notifyDataSetChanged();
    }

    /**
     * ClickListener for the RatData item
     */
    public interface OnItemClickListener {
        void onItemClick(RatData item);
    }

    private final OnItemClickListener listener;

    /**
     * Constructor for RatDataAdapter
     * @param data the list of ratData
     * @param listener the click listener
     */
    RatDataAdapter(Collection<RatData> data, OnItemClickListener listener) {
        this.data.addAll(data);
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
        Context c = dvh.message.getContext();
        dvh.message.setText(c.getString(R.string.dvh_message));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(rd);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

     /**
     * gets the lastId
     * @return the lastId
     */
    int getLastId() {
        return lastId;
    }

    /**
     * gets the date
     * @return returns the date of the report
     */
    public Date getDate() {
        return date;
    }

    private static class DataViewHolder extends RecyclerView.ViewHolder {
        private final TextView dataDate;
        private final TextView message;

        DataViewHolder(View itemView) {
            super(itemView);
            dataDate = itemView.findViewById(R.id.reportDate);
            message = itemView.findViewById(R.id.message);
        }
    }
}
