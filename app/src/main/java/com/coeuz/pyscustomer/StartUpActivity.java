package com.coeuz.pyscustomer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;

import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.LocationAddress;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class StartUpActivity extends AppCompatActivity implements ConnectionCallbacks,
        OnConnectionFailedListener, LocationListener {

    private Handler mHandler = new Handler();
    private String locatonValues;

    TinyDB mtinyDb;
    private String mToken, muserId, mcustomerName, mmobileNumber, memailId;

    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 999;

    GpsTracker gps;

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;

    FusedLocationProviderClient fusedLocationProviderClient;
    Location location100;
    boolean notWorking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);


        mtinyDb = new TinyDB(getApplicationContext());
        mToken = mtinyDb.getString(Constant.TOKEN);


        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(StartUpActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_REQUEST_COARSE_LOCATION);
        }


        mGoogleApiClient = new GoogleApiClient.Builder(this)

                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);


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
            Log.d("fjrijfri2", "hfiewuhfi");
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            String lati = String.valueOf(latitude);
            Log.d("feuihiuer", String.valueOf(latitude));
            if (!lati.equals("0.0")) {
                mtinyDb.putString(Constant.INITIALLAT, String.valueOf(latitude));
                mtinyDb.putString(Constant.INITIALLONG, String.valueOf(longitude));
                hereLocattion(latitude, longitude);
            } else {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        location100 = task.getResult();
                        double lat = location100.getLatitude();
                        double lon = location100.getLongitude();
                        Log.d("fhewuihfw", String.valueOf(lat+lon));
                        mtinyDb.putString(Constant.INITIALLAT, String.valueOf(lat));
                        mtinyDb.putString(Constant.INITIALLONG, String.valueOf(lon));
                        hereLocattion(lat, lon);


                    }
                });
            }


        } else{

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            boolean isGpsProviderEnabled, isNetworkProviderEnabled;
            isGpsProviderEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkProviderEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

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
                builder.show();
            }
        }


        if (mToken != null) {
            String URL1 = Constant.APIONE + "/user/getCurrentUser";

            StringRequest request = new StringRequest(Request.Method.GET, URL1, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("mvefn", String.valueOf(response));
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        muserId = jsonObject.getString("userId");
                        mcustomerName = jsonObject.getString("customerName");
                        mmobileNumber = jsonObject.getString("mobileNumber");
                        memailId = jsonObject.getString("emailId");
                        mtinyDb.putString(Constant.CUSTOMERNAME, mcustomerName);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("miremio", String.valueOf(error));

                    if (error instanceof NetworkError) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(StartUpActivity.this);

                        builder.setTitle("Internet Permission");
                        builder.setMessage("The app needs Internet permissions. Please grant this permission to continue using the features of the app.");
                        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), StartUpActivity.class);
                                startActivity(intent);
                            }
                        });
                        builder.setNegativeButton(android.R.string.no, null);
                        builder.show();
                    } else if (error instanceof ServerError) {

                        Log.d("heuiwirhu1", String.valueOf(error));
                    } else if (error instanceof ParseError) {
                        Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                    } else if (error instanceof NoConnectionError) {
                        Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof TimeoutError) {
                        Toast.makeText(getApplicationContext(), "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();

                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers1 = new HashMap<String, String>();

                    headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
                    return headers1;

                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);

        }

    }


    public String hereLocattion(double lat, double lon) {

        Log.d("ttwrtfrefr", "tret3t5");
        String curCity = "";
        Geocoder geocoder = new Geocoder(StartUpActivity.this, Locale.getDefault());
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocation(lat, lon, 2);
            if (addressList.size() > 0) {
                curCity = addressList.get(0).getLocality();
                locatonValues = curCity.toLowerCase();
                Log.d("yfhuewir", locatonValues);



            }
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
                /*sb.append(address.getPostalCode()).append("\n");
                sb.append(address.getCountryName());*/
                locatonValues = sb.toString() + System.getProperty("line.separator") + sb1;

                if (!locatonValues.equals("")) {
                    mtinyDb.putString(Constant.INITIALLOCATION, locatonValues);
                }
                notWorking=false;

                Intent intent = new Intent(StartUpActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("EXTRA_SESSION_ID", locatonValues);
                startActivity(intent);
                finish();
                Log.d("fhrwuihejriytruuwei", sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(notWorking){
            getFromLocations(lat,lon);
            Log.d("fhuwifhi","fhuwhe");
        }

        return curCity;
    }



    @Override
    public void onRequestPermissionsResult(int permsRequestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(permsRequestCode, permissions, grantResults);
        switch (permsRequestCode) {

            case PERMISSION_REQUEST_COARSE_LOCATION:
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        doStuff();
                    }
                }, 500);
                Log.d("tyerye", "trytery");


                break;
        }

    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(StartUpActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }


    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {

            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();



        }
    }


    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        if (connectionResult.hasResolution()) {
            try {

                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);

            } catch (IntentSender.SendIntentException e) {

                e.printStackTrace();
            }
        } else {

            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

    }



        public  void getFromLocations(double lat, double lng) {

            String address = String.format(Locale.US,
                    "https://maps.googleapis.com/maps/api/geocode/json?latlng=%1$f,%2$f&sensor=false&language="
                            + Locale.getDefault().getCountry(), lat,lng);


            StringRequest request45 = new StringRequest(Request.Method.GET, address, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("fiuwhwiufrew", String.valueOf(response));
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray valuess=jsonObject.getJSONArray("results");
                        Log.d("fiuwhwiufrew1", String.valueOf(valuess));
                        JSONArray jsonArray = null;
                        for(int i=0;i<valuess.length();i++){
                            JSONObject valuess1= valuess.getJSONObject(0);
                            jsonArray=valuess1.getJSONArray("address_components");
                        }
                        Log.d("fiuwhwiufrew2", String.valueOf(jsonArray));
                        String areaName = null;
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject2= jsonArray.getJSONObject(1);
                            areaName=jsonObject2.getString("long_name");

                        }
                        if(areaName!=null){
                            mtinyDb.putString(Constant.INITIALLOCATION, areaName);
                            Intent intent = new Intent(StartUpActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("EXTRA_SESSION_ID", locatonValues);
                            intent.putExtra("UserName", mcustomerName);
                            startActivity(intent);
                            finish();

                        }
                        Log.d("fiuwhwiufrew3", areaName);




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("dfeterte", String.valueOf(error));

                    if (error instanceof NetworkError) {
                  final AlertDialog.Builder builder = new AlertDialog.Builder(StartUpActivity.this);

                        builder.setTitle("Internet Permission");
                        builder.setMessage("The app needs Internet permissions. Please grant this permission to continue using the features of the app.");
                        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), StartUpActivity.class);
                                startActivity(intent);
                            }
                        });
                        builder.setNegativeButton(android.R.string.no, null);
                        builder.show();
                    } else if (error instanceof ServerError) {

                        Log.d("heuiwirhu1", String.valueOf(error));
                    } else if (error instanceof ParseError) {
                        Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                    } else if (error instanceof NoConnectionError) {
                        Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof TimeoutError) {
                        Toast.makeText(getApplicationContext(), "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();

                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers1 = new HashMap<String, String>();

                    headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
                    return headers1;

                }
            };
            RequestQueue requestQueue45 = Volley.newRequestQueue(getApplicationContext());
            requestQueue45.add(request45);



        }


}





