package com.gargi.example.location;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

public class LocationUtil implements ConnectionCallbacks,
									 OnConnectionFailedListener {
	
	Context mContext;
	private GoogleApiClient mGoogleApiClient;
	private LocationRequest mLocationRequest;
	private LocationListener mLocationListener;
	private Location mCurrentLocation;
	
	private long UPDATE_INTERVAL_IN_MILLISECONDS = 60000;
	private final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

	
	public LocationUtil(Context context, LocationListener locationListener) {
		mContext = context;
		mLocationListener = locationListener;
		buildGoogleApiClient();
	}
		
	public void setUpdateInterval(long updateInterval) {
		UPDATE_INTERVAL_IN_MILLISECONDS = updateInterval;
	}
	
	private synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(mContext)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API)
				.build();
		mGoogleApiClient.connect();
	}
	
	protected void createLocationRequest() {
		mLocationRequest = new LocationRequest();
		mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
		mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	}
	
	public void startLocationUpdates() {
		if(mGoogleApiClient == null || !mGoogleApiClient.isConnected())
			buildGoogleApiClient();
		else {
			createLocationRequest();
			LocationServices.FusedLocationApi.requestLocationUpdates(
					mGoogleApiClient, mLocationRequest, mLocationListener);
		}
	}

	public void stopLocationUpdates() {
		if(mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
			LocationServices.FusedLocationApi.removeLocationUpdates(
					mGoogleApiClient, mLocationListener);
			mGoogleApiClient.disconnect();
		}
	}

	@Override
	public void onConnected(Bundle bundle) {
		if (mCurrentLocation == null) {
			mCurrentLocation = LocationServices.FusedLocationApi
					.getLastLocation(mGoogleApiClient);
			startLocationUpdates();
		}		
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		mGoogleApiClient.connect();
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Log.d("Location Util", "onConnectionFailed"+result.toString());
	}
}
