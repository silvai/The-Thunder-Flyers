package edu.gatech.thethunderflyers.android.util;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * LocationProvider: a helper class for ReportRatActivity to control Location Services
 */
public class LocationProvider implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public interface LocationCallback {
        /**
         * A method to allow a class (Activity) to asynchronously respond to new location data
         * @param loc the new Location returned by Location Services
         */
        void handleLocation(Location loc);
    }

    private final LocationCallback lc;

    private GoogleApiClient gac;
    private LocationRequest lrq;

    /**
     * Constructor for LocationProvider
     * @param context the Context of the Activity implementing LocationCallback
     * @param lc a LocationCallback object (really the Activity that has implemented LocationCallback)
     */
    public LocationProvider(Context context, LocationCallback lc) {
        if (gac == null) {
            GoogleApiClient.Builder gacBuilder = new GoogleApiClient.Builder(context);
            gacBuilder.addConnectionCallbacks(this);
            gacBuilder.addOnConnectionFailedListener(this);
            gacBuilder.addApi(LocationServices.API);
            gac = gacBuilder.build();
        }

        if (lrq == null) {
            lrq = LocationRequest.create();
            lrq.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            lrq.setInterval(10 * 1000);
            lrq.setFastestInterval(1000);
        }

        this.lc = lc;
    }

    /**
     * Enables Location Services.
     */
    public void connect() {
        gac.connect();
    }

    /**
     * Disables Location Services and stops location updates.
     */
    public void disconnect() {
        if (gac.isConnected()) {
            FusedLocationProviderApi flpa = LocationServices.FusedLocationApi;
            flpa.removeLocationUpdates(gac, this);
            gac.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try {
            FusedLocationProviderApi flpa = LocationServices.FusedLocationApi;
            Location loc = flpa.getLastLocation(gac);
            if (loc == null) {
                flpa.requestLocationUpdates(gac, lrq, this);
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
