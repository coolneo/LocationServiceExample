package com.gargi.example.location;

import com.google.android.gms.location.LocationListener;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.widget.Toast;

public class LocationService extends Service {
	
	LocationUtil mLocationUtil;
	
	@Override
	public void onCreate() {
		initLocationUtil();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {	
		initLocationUtil();		
		mLocationUtil.startLocationUpdates();		
		return START_NOT_STICKY;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		initLocationUtil();
		mLocationUtil.stopLocationUpdates();
	}
	
	private void initLocationUtil() {
		if(mLocationUtil == null){
			mLocationUtil = new LocationUtil(LocationService.this, locationListener);
		}
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
		Toast.makeText(LocationService.this, msg, Toast.LENGTH_LONG).show();
	}
}
