package edu.gatech.thethunderflyers.android.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.model.RatData;
import edu.gatech.thethunderflyers.android.util.AsyncHandler;
import edu.gatech.thethunderflyers.android.util.DataGetTask;

public class MainActivity extends AppCompatActivity implements AsyncHandler<List<RatData>> {

    private RecyclerView dataView;
    private RatDataAdapter dataAdapter;
    private LinearLayoutManager dataManager;

    private int itemCount;
    private int lastVisibleItem;
    private boolean loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataView = (RecyclerView) findViewById(R.id.dataView);
        dataAdapter = new RatDataAdapter(new ArrayList<RatData>(), new RatDataAdapter.OnItemClickListener() {
            @Override public void onItemClick(RatData rat) {
                Context context = dataView.getContext();
                Intent intent = new Intent(context, DetailRatDataActivity.class);
                intent.putExtra("rat", rat);
                startActivity(intent);
            }
        });

        dataManager = new LinearLayoutManager(this);
        dataView.setAdapter(dataAdapter);
        dataView.setLayoutManager(dataManager);
        dataView.addItemDecoration(new DividerItemDecoration(dataView.getContext(),
                dataManager.getOrientation()));
        new DataGetTask(getString(R.string.get_data_url) + dataAdapter.getLastId() + "/"
                + dataAdapter.getPage() + "/", this).execute();
        dataView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                itemCount = dataManager.getItemCount();
                lastVisibleItem = dataManager.findLastVisibleItemPosition();
                if (!loading && itemCount <= (lastVisibleItem + 5)) {
                    loading = true;
                    String url = getString(R.string.get_data_url) + dataAdapter.getLastId() + "/"
                            + dataAdapter.getPage() + "/";
                    new DataGetTask(url, MainActivity.this).execute();
                }
            }
        });
    }

    /**
     * Handles the new rat report button click
     * @param view the callback parameter
     */
    public void report(View view) {
        Intent intent = new Intent(this, ReportRatActivity.class);
        startActivity(intent);
    }
    /**
     * Handles logout button click.
     * @param view the callback parameter
     */
    public void logout(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void handleResponse(List<RatData> response, Exception ex) {
        loading = false;
        if (ex != null) {
            AlertDialog ad = new AlertDialog.Builder(this)
                    .setMessage(ex.toString())
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).create();
            ad.show();
        } else {
            dataAdapter.setPage(dataAdapter.getPage() + 1);
            dataAdapter.setLastId(response.get(19).getId());
            dataAdapter.getData().addAll(response);
            dataAdapter.notifyDataSetChanged();
        }
    }
}
