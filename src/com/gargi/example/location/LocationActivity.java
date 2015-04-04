package com.gargi.example.location;

import com.google.android.gms.location.LocationListener;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LocationActivity extends ActionBarActivity {
	
	Button mLocationButton, mStopLocationButton;
	GPSManager mGpsManager;
	LocationUtil mLocationUtil;
	
	TextView mLatitudeTextView, mLongitudeTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		//mLocationUtil = new LocationUtil(this, locationListener);
		mLocationButton = (Button)findViewById(R.id.location_btn);
		mStopLocationButton = (Button)findViewById(R.id.location_stop_btn);
		mLatitudeTextView = (TextView)findViewById(R.id.lat_textview);
		mLongitudeTextView = (TextView)findViewById(R.id.long_textview);
		
		mLocationButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startLocationService(true);
			}
		});
		
		mStopLocationButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startLocationService(false);
			}
		});
	}
	
	
	
	private void startLocationUpdate() {
		//mLocationUtil.startLocationUpdates();
		/*if(mGpsManager == null) {
			mGpsManager = new GPSManager();
			mGpsManager.setGPSCallback(gpsCallback);
		}
		mGpsManager.startListening(this);*/
		
	}
	
	private void startLocationService(boolean state) {
		Intent locationServiceIntent = new Intent(this, LocationService.class);
		//locationServiceIntent.putExtra(LocationIntentService.START_STOP_KEY, state);
		if(state) {
			startService(locationServiceIntent);
		}else {
			stopService(locationServiceIntent);			
		}
	}
	
	LocationListener locationListener = new LocationListener() {
		
		@Override
		public void onLocationChanged(Location location) {
			mLatitudeTextView.setText(String.valueOf(location.getLatitude()));
			mLongitudeTextView.setText(String.valueOf(location.getLongitude()));
		}
	};
	
	GPSCallback gpsCallback = new GPSCallback() {
		
		@Override
		public void onGPSUpdate(Location location) {
			
			mLatitudeTextView.setText(String.valueOf(location.getLatitude()));
			mLongitudeTextView.setText(String.valueOf(location.getLongitude()));
			
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.location, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
