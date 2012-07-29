package com.location;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is a simple example of finding GPS location Lat long with WIFI and cell tower
 * This sample also shows how to use Android Geocode feature to reverse Geo coding
 * Generating Address from Latitude and Longitude
 * This same example can be use for all Location including GPS. One only need to change
 * the provider in case of GPS with adding extra fine_location permission in the manifest. 
 * Copyright (C) 2012
 * @author Pranay Airan
 * @email  pranay.airan@iiitb.net
 * gpswifilocator
 * com.location
 */
public class GpswifilocatorActivity extends Activity {
    /** Called when the activity is first created. */
	private Geocoder gc;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Context ctx = this;
        gc = new Geocoder(ctx);
        final LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
        Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        
        final TextView cord = (TextView)findViewById(R.id.gpslocationcordinate);
        final TextView addr = (TextView)findViewById(R.id.gpsLocationAdress);
        
        try {
        	cord.setText( "Latitude : "+location.getLongitude()+" Longitude : "+location.getLongitude());
        	Toast.makeText(getApplicationContext(), "Latitude : "+location.getLongitude()+" Longitude : "+location.getLongitude(), Toast.LENGTH_SHORT).show() ;
     		if(lm.isProviderEnabled(WIFI_SERVICE))
     		{
        	List<Address> locationList = gc.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
     		addr.setText( locationList.get(0).getFeatureName()+" "+locationList.get(0).getSubLocality()+" "+locationList.get(0).getSubAdminArea()+
                    " "+locationList.get(0).getLocality()+" "+locationList.get(0).getAdminArea()+" "+locationList.get(0).getCountryName());
     		}
           
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        final LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
              try {
            		cord.setText( "Latitude : "+location.getLongitude()+" Longitude : "+location.getLongitude());
                	Toast.makeText(getApplicationContext(), "Latitude : "+location.getLongitude()+" Longitude : "+location.getLongitude(), Toast.LENGTH_SHORT).show() ;
                	if(lm.isProviderEnabled(WIFI_SERVICE))
             		{
                	List<Address> locationList = gc.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
             		addr.setText( locationList.get(0).getFeatureName()+" "+locationList.get(0).getSubLocality()+" "+locationList.get(0).getSubAdminArea()+
                            " "+locationList.get(0).getLocality()+" "+locationList.get(0).getAdminArea()+" "+locationList.get(0).getCountryName());
             		}
	              
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             
            }

			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
        };
        
        //Change to LocationManager.GPS_PROVIDER to access GPS cordinates, Recommend way is to use Crietria
        //The update frequency is set to 500 which will drain battery change it to higher value for increasing
        //update time
        
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5, locationListener);
    }
}