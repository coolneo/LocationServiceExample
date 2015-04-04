package com.gargi.example.location;

import com.google.android.gms.location.LocationListener;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.widget.Toast;

public class LocationIntentService extends IntentService {
	
	private LocationUtil mLocationUtil;
	private boolean runningStatus = false;
	
	public final static String START_STOP_KEY = "STARTSTOPKEY";
	
	public LocationIntentService() {
		super(LocationIntentService.class.getName());
	}
			
	public boolean isServiceRunning() {
		return runningStatus;
	}
	
	private void initLocationUtil() {
		if(mLocationUtil == null) {
			mLocationUtil = new LocationUtil(LocationIntentService.this, locationListener);
		}
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		boolean startState = intent.getBooleanExtra(START_STOP_KEY, false);
		
		initLocationUtil();
		
		if(startState) {
			runningStatus = true;
			mLocationUtil.startLocationUpdates();
		}
		
		else {
			runningStatus = false;
			mLocationUtil.stopLocationUpdates();
			stopSelf();
		}

	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mLocationUtil.stopLocationUpdates();
	}
	
	private LocationListener locationListener = new LocationListener() {
		
		@Override
		public void onLocationChanged(Location location) {
			String locationStr = String.format("Location Changed: latitude = %s longitude = %s", 
												String.valueOf(location.getLatitude()), 
												String.valueOf(location.getLongitude()));
			showToast(locationStr);
		}
	};
	
	private void showToast(String msg) {		
		Toast.makeText(LocationIntentService.this, msg, Toast.LENGTH_LONG).show();
	}

	/*private void sendLocationRequest() {
	NetworkRequest locationRequest = new NetworkRequest(mContext);
	locationRequest.sendUserLocationRequest(
			NetworkConstants.API_SET_USER_LOCATION, createLocationData(),
			locationCallback);
}

private List<NameValuePair> createLocationData() {
	List<NameValuePair> locationData = new ArrayList<NameValuePair>();
	locationData.add(new BasicNameValuePair(NetworkConstants.LATITUDE,
			String.valueOf(mCurrentLocation.getLatitude())));
	locationData.add(new BasicNameValuePair(NetworkConstants.LONGITUDE,
			String.valueOf(mCurrentLocation.getLongitude())));

	return locationData;
}

NetworkRequest.NetworkRequestCallback locationCallback = new NetworkRequest.NetworkRequestCallback() {

	@Override
	public void OnNetworkResponseReceived(JSONObject response) {
		WantonLogger.d(response.toString());
	}

	@Override
	public void OnNetworkErrorReceived(String error) {
		WantonLogger.e(error);
	}
};*/
	
}
