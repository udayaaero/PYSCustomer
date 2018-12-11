package com.coeuz.pyscustomer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import com.google.android.gms.location.LocationRequest;

@SuppressLint("Registered")
public class GpsTracker extends Service implements ConnectionCallbacks,
        OnConnectionFailedListener,
        LocationListener {


    //private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;

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
                }else/*{
                     location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if(location !=null){
                        latitude=location.getLatitude();
                        longitude=location.getLongitude();

                    }
                }*/
                // GPS'ten alinan konum bilgisi;



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

    // Konum bilgisi kapali ise kullaniciya ayarlar sayfasina baglanti iceren bir mesaj goruntulenir
    public void showSettingsAlert()
    {
        AlertDialog.Builder alertDialog    = new AlertDialog.Builder(mContext);

        // Mesaj basligi
        alertDialog.setTitle("GPS Kapalı");

        // Mesaj
        alertDialog.setMessage("Konum bilgisi alınamıyor. Ayarlara giderek gps'i aktif hale getiriniz.");

        // Mesaj ikonu
        //alertDialog.setIcon(R.drawable.delete);

        // Ayarlar butonuna tiklandiginda
        alertDialog.setPositiveButton("Ayarlar", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog,int which)
            {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);

            }
        });

        // Iptal butonuna tiklandiginda
        alertDialog.setNegativeButton("İptal", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        // Mesaj kutusunu goster
        alertDialog.show();
    }

    // LocationManager'in gps isteklerini durdurur
    public void stopUsingGPS()
    {
        if(locationManager != null)
        {
            locationManager.removeUpdates(GpsTracker.this);
        }
    }
    private void getAnotherLocation() {
        mGoogleApiClient = new GoogleApiClient.Builder(GpsTracker.this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(GpsTracker.this)
                .addOnConnectionFailedListener(GpsTracker.this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();





        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1000); // 1 second, in milliseconds
    }




    /**
     * If connected get lat and long
     *
     */
    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) GpsTracker.this);

        } else {

            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
          //  mcurrentLatitude= String.valueOf(currentLatitude);
           // mcurrentLongitude= String.valueOf(currentLongitude);

            if (latitude == 0.0)
            {
                latitude = currentLatitude;
                longitude =currentLongitude;

            }


        }
    }


    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }
    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

       // mcurrentLatitude= String.valueOf(currentLatitude);
      //  mcurrentLongitude= String.valueOf(currentLongitude);


        if (latitude == 0.0)
        {
            latitude = currentLatitude;
            longitude =currentLongitude;

        }

    }


}