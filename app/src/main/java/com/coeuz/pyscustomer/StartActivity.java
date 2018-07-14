package com.coeuz.pyscustomer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.karan.churi.PermissionManager.PermissionManager;

import java.util.ArrayList;

import static android.provider.UserDictionary.Words.APP_ID;


public class StartActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_start);

        SharedPreferences settings=getSharedPreferences("prefs",0);
        boolean firstRun=settings.getBoolean("firstRun",false);
        if(firstRun==false)//if running for first time
        //Splash will load for first time
        {
            SharedPreferences.Editor editor=settings.edit();
            editor.putBoolean("firstRun",true);
            editor.commit();
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



}
