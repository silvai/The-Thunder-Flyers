package edu.gatech.thethunderflyers.android.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.model.RatData;

public class RatDataAdapter extends RecyclerView.Adapter{
    private List<RatData> data;
    private final int DATA = 0;
    private int page = 0;
    private int lastId = 0;

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
        View v = li.inflate(R.layout.data_item, parent, false);
        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DataViewHolder dvh = (DataViewHolder) holder;
        final RatData rd = data.get(position);
        dvh.dataDate.setText(String.format("%tc", rd.getDate()));
        dvh.dataAddress.setText(rd.getAddress());
        dvh.dataZip.setText(String.valueOf(rd.getZip()));
        dvh.dataLocationType.setText(rd.getLocatType().toString());
        dvh.dataLatLong.setText("(" + String.format(Locale.US, "%.5f", rd.getLatitude())
                + ", " + String.format(Locale.US, "%.5f", rd.getLongitude()) + ")");
        dvh.dataBorough.setText(rd.getBorough().toString());
        dvh.dataCity.setText(rd.getCity());

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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<RatData> getData() {
        return data;
    }

    public int getLastId() {
        return lastId;
    }

    public void setLastId(int lastId) {
        this.lastId = lastId;
    }

    private static class DataViewHolder extends RecyclerView.ViewHolder {
        private TextView dataDate;
        private TextView dataAddress;
        private TextView dataZip;
        private TextView dataLocationType;
        private TextView dataLatLong;
        private TextView dataBorough;
        private TextView dataCity;

        public DataViewHolder(View itemView) {
            super(itemView);
            dataDate = itemView.findViewById(R.id.dataDate);
            dataAddress = itemView.findViewById(R.id.dataAddress);
            dataZip = itemView.findViewById(R.id.dataZip);
            dataLocationType = itemView.findViewById(R.id.dataLocationType);
            dataLatLong = itemView.findViewById(R.id.dataLatLong);
            dataBorough = itemView.findViewById(R.id.dataBorough);
            dataCity = itemView.findViewById(R.id.dataCity);
        }
    }
}
