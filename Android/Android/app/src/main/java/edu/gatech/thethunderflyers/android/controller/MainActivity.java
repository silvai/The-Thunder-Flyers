package edu.gatech.thethunderflyers.android.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.model.RatData;
import edu.gatech.thethunderflyers.android.util.APIClient;
import edu.gatech.thethunderflyers.android.util.AlertDialogProvider;
import edu.gatech.thethunderflyers.android.util.AsyncHandler;
import edu.gatech.thethunderflyers.android.util.Navigator;

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
                Navigator.goToDetailRatDataActivity(context, rat);
            }
        });

        dataManager = new LinearLayoutManager(this);
        dataView.setAdapter(dataAdapter);
        dataView.setLayoutManager(dataManager);
        dataView.addItemDecoration(new DividerItemDecoration(dataView.getContext(),
                dataManager.getOrientation()));
        dataView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                itemCount = dataManager.getItemCount();
                lastVisibleItem = dataManager.findLastVisibleItemPosition();
                if (!loading && (itemCount <= (lastVisibleItem + 5))) {
                    loading = true;
                    APIClient.getInstance().getRatDataList(dataAdapter.getLastId(),
                            dataAdapter.getDate().getTime(), new WeakReference<>(MainActivity.this));
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        APIClient.getInstance().getRatDataList(dataAdapter.getLastId(),
                dataAdapter.getDate().getTime(), new WeakReference<>(MainActivity.this));
    }

    /**
     * Button click action to go to maps activity
     * @param view the call back parameter
     */
    public void mapStart(View view) {
        Navigator.goToMapsActivity(this);
    }

    /**
     * Handles the new rat report button click
     * @param view the callback parameter
     */
    public void report(View view) {
        Navigator.goToReportRatActivity(this);
    }
    /**
     * Handles logout button click.
     * @param view the callback parameter
     */
    public void logout(View view) {
        Navigator.goToWelcomeActivity(this);
    }

    /**
     * Button pressed to go view the graph of rat reports
     * @param view the callback parameter
     */
    public void graphStart(View view) {
        Navigator.goToGraphActivity(this);
    }

    @Override
    public void handleResponse(List<RatData> response, Exception ex) {
        loading = false;
        if (ex != null) {
            AlertDialogProvider.getExceptionDialog(this).show();
        } else {
            dataAdapter.result(response);
        }
    }
}
