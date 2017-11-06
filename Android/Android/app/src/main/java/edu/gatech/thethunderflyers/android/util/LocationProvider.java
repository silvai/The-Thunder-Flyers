package edu.gatech.thethunderflyers.android.util;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationProvider implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public interface LocationCallback {
        void handleLocation(Location loc);
    }

    private final LocationCallback lc;

    private GoogleApiClient gac;
    private LocationRequest lrq;

    public LocationProvider(Context context, LocationCallback lc) {
        if (gac == null) {
            gac = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        if (lrq == null) {
            lrq = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(10 * 1000)
                    .setFastestInterval(1000);
        }

        Context context1 = context;
        this.lc = lc;
    }

    public void connect() {
        gac.connect();
    }

    public void disconnect() {
        if (gac.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(gac, this);
            gac.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try {
            Location loc = LocationServices.FusedLocationApi.getLastLocation(gac);
            if (loc == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(gac, lrq, this);
            } else {
                lc.handleLocation(loc);
            }
        } catch (SecurityException se) {
            Log.e("ReportRatActivity", se.getMessage());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("ReportRatActivity", connectionResult.getErrorMessage());
    }

    @Override
    public void onLocationChanged(Location location) {
        lc.handleLocation(location);
    }
}
