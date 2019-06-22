package com.luisbar.waterdelivery.presentation.view.service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.luisbar.waterdelivery.data.repository.TrackingRepository;
import com.strongloop.android.loopback.RestAdapter;

public class TrackingService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GpsStatus.Listener {

    private RestAdapter mRestAdapter;
    private TrackingRepository mTrackingRepository;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationManager mLocationManager;
    private boolean mGpsEnabled = false;

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationManager = mLocationManager == null ? (LocationManager) getSystemService(Context.LOCATION_SERVICE) : mLocationManager;
        mGpsEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        createRestAdpater();
        mTrackingRepository = mTrackingRepository == null ? new TrackingRepository() : mTrackingRepository;

        configLocationRequest();
        buildGoogleApiClient();
        Log.e("onCreate: ", "SI");
    }

    /**
     * It enables the location updates if gps is enable and receive an intent when
     * the gps is enable or disabled
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mGpsEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (intent != null &&
            intent.getAction() != null &&
            intent.getAction().matches("GPS")) {

            if (mGpsEnabled)
                startLocationUpdates();
            else
                stopLocationUpdates();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
        configLocationRequest();
    }

    /**
     * Listener for GpsStatus
     * @param event Integer
     */
    @Override
    public void onGpsStatusChanged(int event) {
        switch (event) {
            case GpsStatus.GPS_EVENT_STARTED:
                startLocationUpdates();
                Log.e("GPS_EVENT_STARTED: ", "SI");
                break;

            case GpsStatus.GPS_EVENT_STOPPED:
                Log.e("GPS_EVENT_STOPPED: ", "SI");
                stopLocationUpdates();
                break;

            case GpsStatus.GPS_EVENT_FIRST_FIX:
                Log.e("GPS_EVENT_FIRST_FIX: ", "SI");
                break;

            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                Log.e("GPS_EVENT_SATELLITE_STATUS: ", "SI");
                break;
        }
    }

    /**
     * Listener for GoogleApiClient.ConnectionCallbacks
     * when the request of connection was successful
     * @param bundle Bundle object
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (mGpsEnabled) startLocationUpdates();
    }

    /**
     * Listener for GoogleApiClient.ConnectionCallbacks
     * when the request of connection was suspended
     * @param i Integer
     */
    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    /**
     * Listener for GoogleApiClient.ConnectionCallbacks
     * when the request of connection was failed
     * @param connectionResult {@link ConnectionResult object}
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("onConnectionFailed", connectionResult.getErrorMessage().toString());
    }

    /**
     * Listener for LocationListener
     * when the location changed
     * @param location Location object
     */
    @Override
    public void onLocationChanged(Location location) {
        if (location.getAccuracy() <= 10) // Se cambio la precision de 4 metros a 10 metros
            mTrackingRepository.saveLocation(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), mRestAdapter);
        Log.e("onLocationChanged: ", String.valueOf(location.getLatitude()) + "|" + String.valueOf(location.getLongitude()));
        Log.e("onLocationChanged: ", location.getAccuracy() + "|" + location.getProvider());
    }

    /**
     * Initialize client for requesting data to the loopback API,
     * with it you can create your ModelRepository
     * @return RestAdapter
     */
    private RestAdapter createRestAdpater() {
        if (mRestAdapter == null)
            mRestAdapter = new RestAdapter(getApplicationContext(), getHost());

        return mRestAdapter;
    }

    /**
     * It sets the GoogleApiClient and save listeners for it
     */
    private void buildGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        if (!mGoogleApiClient.isConnected()) mGoogleApiClient.connect();
    }

    /**
     * It sets the settings for LocationRequest object
     */
    private void configLocationRequest() {
        mLocationRequest = mLocationRequest == null ? new LocationRequest() : mLocationRequest;
        mLocationRequest.setInterval(2000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * It adds listener for getting the location updated
     */
    private void startLocationUpdates() {
        Log.e("startLocationUpdates: ", "SI");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("startLocationUpdates: ", "not ok");
            return;
        }
        Log.e("startLocationUpdates: ", "ok");
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected())
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
    }

    /**
     * It stops the location updates
     */
    private void stopLocationUpdates() {
        Log.e("stopLocationUpdates: ", "SI");
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected())
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);
    }

    private String getHost() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

        return settings.getString("IP", null);
    }
}
