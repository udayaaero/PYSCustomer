package com.coeuz.pyscustomer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.coeuz.pyscustomer.requiredclass.Constant;
import com.coeuz.pyscustomer.requiredclass.TinyDB;


import com.coeuz.pyscustomer.requiredclass.VolleySingleton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class StartUpActivity extends AppCompatActivity{

    private Handler mHandler = new Handler();

    TinyDB mtinyDb;
    private String mToken, muserId, mcustomerName;
    String mmobileNumber,memailId;

    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 999;

    GpsTracker gps;

    boolean notWorking=true;
    double la,lo;
    String notRun="true";
    LocationManager locationManager;
    boolean isGpsProviderEnabled = false, isNetworkProviderEnabled = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

    /*    if (LeakCanary.isInAnalyzerProcess(this)) {

            return;
        }
        LeakCanary.install((Application) getApplicationContext());*/


        mtinyDb = new TinyDB(getApplicationContext());
        mToken = mtinyDb.getString(Constant.TOKEN);
        mtinyDb.putInt(Constant.FIRSTTIME,1);


        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(StartUpActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_REQUEST_COARSE_LOCATION);
        }



       mHandler.postDelayed(new Runnable() {
            public void run() {
                doStuff();
            }
        }, 800);
    }

    private void doStuff() {

        mtinyDb.putString(Constant.LATITUDE, "");
        mtinyDb.putString(Constant.LONGITUDE, "");
        gps = new GpsTracker(StartUpActivity.this);

        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            String lati = String.valueOf(latitude);

           hereLocattion(latitude, longitude);
            if (!lati.equals("0.0")) {
                mtinyDb.putString(Constant.INITIALLAT, String.valueOf(latitude));
                mtinyDb.putString(Constant.INITIALLONG, String.valueOf(longitude));
                mtinyDb.putDouble("INITIALLAT", latitude);
                mtinyDb.putDouble("NITIALLONG", longitude);
            }


        } else{

             locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (locationManager != null) {
                isGpsProviderEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            }
            if (locationManager != null) {
                isNetworkProviderEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            }

            if (!isGpsProviderEnabled && !isNetworkProviderEnabled) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Location Permission");
                builder.setMessage("The app needs location permissions. Please grant this permission to continue using the features of the app.");
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                        dialogInterface.cancel();
                        finish();
                    }
                });
                builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        }


        if (!mToken.isEmpty()) {
            String URL1 = Constant.APIONE + "/user/getCurrentUser";

            StringRequest request = new StringRequest(Request.Method.GET, URL1, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        muserId = jsonObject.getString("userId");
                        mcustomerName = jsonObject.getString("customerName");
                        mmobileNumber = jsonObject.getString("mobileNumber");
                        memailId = jsonObject.getString("emailId");
                        mtinyDb.putString(Constant.CUSTOMERNAME, mcustomerName);
                        mtinyDb.putString(Constant.USERID, muserId);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                if (error instanceof ParseError) {
                        Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                    } else if (error instanceof TimeoutError) {
                        Toast.makeText(getApplicationContext(), "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();

                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers1 = new HashMap<>();

                    headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
                    return headers1;

                }
            };
            VolleySingleton.getInstance(StartUpActivity.this).addToRequestQueue(request);

        }

    }


    public void hereLocattion(double lat, double lon) {
        la=lat;
        lo=lon;

        Geocoder geocoder = new Geocoder(StartUpActivity.this, Locale.getDefault());

        try {

            String locatonValues;

            List<Address> addressList1 = geocoder.getFromLocation(
                    lat, lon, 1);
            if (addressList1 != null && addressList1.size() > 0) {
                Address address = addressList1.get(0);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append("\n");
                }
               /* sb.append(address.getSubAdminArea()).append("\n");*/
                sb.append(address.getSubLocality());
                String sb1 = address.getLocality();

               /* sb.append(address.getAdminArea()).append("\n");
                sb.append(address.getLocality()).append("\n");*/

                if(sb.toString().equalsIgnoreCase("null")){
                    locatonValues =sb1;
                }else{
                locatonValues = sb.toString() + System.getProperty("line.separator") + sb1;}

                if (!locatonValues.equals("")) {

                    mtinyDb.putString(Constant.INITIALLOCATION, locatonValues);
                }
                notWorking=false;
                mtinyDb.putString(Constant.INITIALLAT, String.valueOf(la));
                mtinyDb.putString(Constant.INITIALLONG, String.valueOf(lo));
                Intent intent = new Intent(StartUpActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("grege", String.valueOf(e));

        }
        Log.d("rwger", String.valueOf(notWorking));
        if(notWorking){
        Log.d("rwger1", String.valueOf(notWorking));
            getFromLocations(la,lo);

        }

    }



    @Override
    public void onRequestPermissionsResult(int permsRequestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(permsRequestCode, permissions, grantResults);
        switch (permsRequestCode) {

            case PERMISSION_REQUEST_COARSE_LOCATION:
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        Log.d("grwgerhe","t4w3545");
                        doStuff();
                    }
                }, 50);

                break;

        }

    }



    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

        public  void getFromLocations(final double lat, final double lng) {

          /* String address = String.format(Locale.getDefault(),
                    "https://maps.googleapis.com/maps/api/geocode/json?latlng=%1$f,%2$f&sensor=false&language=&key=AIzaSyDA2L67fZwztMdooFr5eakRmpNdTFEhVjQ"
                            + Locale.getDefault().getCountry(), lat,lng);*/
          /*  String address ="https://maps.googleapis.com/maps/api/" +
                    "/json?latlng="+String.valueOf(lat)+","+String.valueOf(lng)+"&sensor=true&key=AIzaSyDziFGBS1ef_vIY89Dd-9N9nzfgM7HsjYs";*/

            String address="https://maps.googleapis.com/maps/api/geocode/json?latlng="+String.valueOf(lat)+","+String.valueOf(lng)+
                    "&sensor=true&key=AIzaSyD1UyGih4OiotMUDnHvjbcp0LlsRTGG9Pc";

            StringRequest request45 = new StringRequest(Request.Method.GET, address, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("eryte", String.valueOf(response));

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray valuess=jsonObject.getJSONArray("results");

                        JSONArray jsonArray = null;
                        for(int i=0;i<valuess.length();i++){
                            JSONObject valuess1= valuess.getJSONObject(1);
                            jsonArray=valuess1.getJSONArray("address_components");
                        }

                        String areaName = null;
                        String areaName1 = null;


                        if (jsonArray != null) {
                            for(int i = 0; i<jsonArray.length(); i++){
                                JSONObject jsonObject2= jsonArray.getJSONObject(3);
                                JSONObject jsonObject3= jsonArray.getJSONObject(2);
                                areaName=jsonObject2.getString("long_name");
                                areaName1=jsonObject3.getString("long_name");

                            }
                        }
                        if(areaName!=null){
                            String allAreaname=areaName1+ System.getProperty("line.separator") + areaName;
                            mtinyDb.putString(Constant.INITIALLOCATION, allAreaname);
                            mtinyDb.putString(Constant.INITIALLAT, String.valueOf(lat));
                            mtinyDb.putString(Constant.INITIALLONG, String.valueOf(lng));
                            Intent intent = new Intent(StartUpActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();

                        }else{

                            String allAreaname="Chennai";
                            mtinyDb.putString(Constant.INITIALLOCATION, allAreaname);
                            mtinyDb.putString(Constant.INITIALLAT, String.valueOf(lat));
                            mtinyDb.putString(Constant.INITIALLONG, String.valueOf(lng));
                            Intent intent = new Intent(StartUpActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("eryte", String.valueOf(error));


                    if (error instanceof NetworkError) {
                  final AlertDialog.Builder builder = new AlertDialog.Builder(StartUpActivity.this);

                        builder.setTitle("Internet Permission");
                        builder.setMessage("The app needs Internet permissions. Please grant this permission to continue using the features of the app.");
                        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                                startActivity(intent);

                            }
                        });
                        builder.setNegativeButton(android.R.string.no, null);
                        builder.show();
                    }  else if (error instanceof ParseError) {
                        Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                    }  else if (error instanceof TimeoutError) {
                        Toast.makeText(getApplicationContext(), "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();

                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers1 = new HashMap<>();

                    headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
                    return headers1;

                }
            };

            VolleySingleton.getInstance(StartUpActivity.this).addToRequestQueue(request45);

        }

    @Override
    public void onStop() {
        if(gps!=null){
            gps.onDestroy();
        }
        if (gps != null) {
            Log.d("fwrrwt","fwef");
            gps.stopUsingGPS();
        }
        if (locationManager != null) {
            Log.d("fwrrwt1","fwef");
            locationManager.removeUpdates((LocationListener) this);
        }
        super.onStop();

    }
    @Override
    public void onDestroy() {
        if(gps!=null){
            gps.onDestroy();
        }
        if (gps != null) {
            Log.d("fwrrwt2","fwef");
            gps.stopUsingGPS();
        }
        if (locationManager != null) {
            Log.d("fwrrwt3","fwef");
            locationManager.removeUpdates((LocationListener) this);
        }
        super.onDestroy();

    }



}





