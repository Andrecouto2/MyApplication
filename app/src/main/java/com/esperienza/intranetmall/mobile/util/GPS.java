package com.esperienza.intranetmall.mobile.util;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

public class GPS implements LocationListener{
	private int MIN_TIME_BW_UPDATES = 5000;
	private float MIN_DISTANCE_CHANGE_FOR_UPDATES = 5000;
	// The minimum distance to change Updates in meters
	//private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

	// The minimum time between updates in milliseconds
	//private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
	private Context context;
	private LocationManager locationManager;

	public GPS(Context context){
		this.context = context;
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

	}
	public Location getLocation() {
		Location location = null;
	    try {
	        Boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	        Boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			if( !isGPSEnabled && !isNetworkEnabled) {


			}else {
				if (isNetworkEnabled) {
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					Log.d("Network", "Network Enabled");
					if (locationManager != null) {
						location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					}
				}
				if (isGPSEnabled) {
					if (location == null) {
						locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d("GPS", "GPS Enabled");
						if (locationManager != null) {
							location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						}
					}
				}
			}


	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return location;
	}
	@Override
	public void onLocationChanged(Location location) {
		
	}
	@Override
	public void onProviderDisabled(String provider) {
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}
	
}
