package com.coeuz.pyscustomer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

@SuppressLint("Registered")
public class GpsTracker extends Service implements LocationListener {


    static final int REQUEST=1;



    private static final int MY_PERMISSION_ACCESS_COURSE_LOCATION =888 ;
    private final Context mContext;


    boolean isGPSEnabled = false;

    boolean isNetworkEnabled = false;

    boolean canGetLocation = false;


    Location location;

    double latitude;

    double longitude;


    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;


    private static final long MIN_TIME_BW_UPDATES = 1000 * 60;




    protected LocationManager locationManager;


    public GpsTracker(Context context) {
        this.mContext = context;

        getLocation();
    }


    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);


            if (locationManager != null) {
                isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            }


            if (locationManager != null) {
                isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            }

            if (!isGPSEnabled && !isNetworkEnabled) {


            } else {

                this.canGetLocation = true;


                if (isNetworkEnabled) {
                    if ( ContextCompat.checkSelfPermission( mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

                        ActivityCompat.requestPermissions((Activity) mContext, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                                GpsTracker.MY_PERMISSION_ACCESS_COURSE_LOCATION );
                    }
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null)

                    {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null)
                        {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                        }
                    }
                }
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) mContext,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST);
                }else

                if (isGPSEnabled){

                    locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
                    if (locationManager != null) {
                        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                    }
                    if ( ContextCompat.checkSelfPermission( mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

                        ActivityCompat.requestPermissions((Activity) mContext, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                                GpsTracker.MY_PERMISSION_ACCESS_COURSE_LOCATION );
                    }
                {
                    if (location == null)
                    {
                        Criteria criteria = new Criteria();
                        criteria.setAccuracy(Criteria.ACCURACY_FINE);
                        criteria.setCostAllowed(false);

                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                        if (locationManager != null)
                            this.isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);


                        if (location != null) {

                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                        }


                    }
                }
            }
                if(isGPSEnabled) {
                    if(location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if(locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }}

        }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return location;
    }


    public double getLatitude()
    {
        if(location != null)
        {
            latitude = location.getLatitude();

        }

        return latitude;
    }

    public double getLongitude()
    {
        if(location != null)
        {
            longitude = location.getLongitude();

        }

        return longitude;
    }



    @Override
    public void onProviderDisabled(String provider)
    {
    }

    @Override
    public void onProviderEnabled(String provider)
    {
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
    }

    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    public boolean canGetLocation()
    {
        return this.canGetLocation;
    }


    @Override
    public void onDestroy() {

        stopUsingGPS();
        super.onDestroy();
    }
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(GpsTracker.this);
        }

}
}