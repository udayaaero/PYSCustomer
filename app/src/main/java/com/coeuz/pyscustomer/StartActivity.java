package com.coeuz.pyscustomer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.ArrayList;

import android.Manifest;
import android.app.Service;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends Activity  {



    LocationManager locationManager;
    ArrayList<String> permissions = new ArrayList<>();
    boolean isGPS = false;
    boolean isNetwork = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences settings=getSharedPreferences("prefs",0);
        boolean firstRun=settings.getBoolean("firstRun",false);
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        if (locationManager != null) {
            isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }


        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);




        if(!firstRun)
        {
            SharedPreferences.Editor editor=settings.edit();
            editor.putBoolean("firstRun",true);
            editor.apply();
            Intent i=new Intent(StartActivity.this,WelcomesActivity.class);
            startActivity(i);
            finish();
        }
        else
        {
            Intent a=new Intent(StartActivity.this,StartUpActivity.class);
                startActivity(a);
                finish();

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}




