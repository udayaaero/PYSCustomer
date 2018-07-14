package com.coeuz.pyscustomer;

import android.*;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.coeuz.pyscustomer.AdapterClass.GooglePlacesAutompleteAdapter;
import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LocationChangeActivity extends AppCompatActivity implements PlaceSelectionListener {

    private static final String LOG_TAG = "PlaceSelectionListener";
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private static final int REQUEST_SELECT_PLACE = 1000;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION=99;


    CardView getCurrentLocation;
    private final static int MY_PERMISSION_REQUEST_lOCATION = 1;
    private String provider, locatonValues;


    GooglePlacesAutompleteAdapter dataAdapter;

    String mToken;
    TinyDB mtinyDb;
    private ProgressBar progressBar;
    private Handler mHandler = new Handler();
    GpsTracker gps;
    RelativeLayout notYourcity,firstViews;

    FusedLocationProviderClient fusedLocationProviderClient;
    Location location100;
    boolean notWorking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_change);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mtinyDb = new TinyDB(getApplicationContext());


        progressBar = (ProgressBar) findViewById(R.id.progressbar200);
        notYourcity=(RelativeLayout) findViewById(R.id.NotYourCity);
        firstViews=(RelativeLayout) findViewById(R.id.firstviews);

        getCurrentLocation = (CardView) findViewById(R.id.cardFirst);


        getCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doStuff();
            }
        });

    }

    private void doStuff() {

        mtinyDb.putString(Constant.LATITUDE, "");
        mtinyDb.putString(Constant.LONGITUDE, "");
        gps = new GpsTracker(LocationChangeActivity.this);

        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            String lati = String.valueOf(latitude);
            if (!lati.equals("0.0")) {
                mtinyDb.putString(Constant.INITIALLAT, String.valueOf(latitude));
                mtinyDb.putString(Constant.INITIALLONG, String.valueOf(longitude));
                hereLocattion(latitude, longitude);
            } else {
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

        } else {

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
                    }
                });
                builder.setNegativeButton(android.R.string.no, null);
                builder.show();
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {

            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("TAGww", "coarse location permission granted");
                } else {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            }

        }

    }

    public void hereLocattion(double lat, double lon) {

        Log.d("fhrwuihejriytruuwei1", "fhrwuihejriytruuwei");
        String curCity = "";
        Geocoder geocoder = new Geocoder(LocationChangeActivity.this, Locale.getDefault());
        List<Address> addressList;
        try {
            Log.d("fhrwuihejriytruuwei2", "fhrwuihejriytruuwei");
            addressList = geocoder.getFromLocation(lat, lon, 1);
            if (addressList.size() > 0) {
                curCity = addressList.get(0).getLocality();
                locatonValues = curCity.toLowerCase();
                Log.d("yfhuewir", locatonValues);
                Log.d("fhrwuihejriytruuwei3", "fhrwuihejriytruuwei");

            }
            List<Address> addressList1 = geocoder.getFromLocation(
                    lat, lon, 1);
            Log.d("fhrwuihejriytruuwei4", "fhrwuihejriytruuwei");
            if (addressList1 != null && addressList1.size() > 0) {
                Address address = addressList1.get(0);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append("\n");
                }
                Log.d("fhrwuihejriytruuwei5", "fhrwuihejriytruuwei");
               /* sb.append(address.getSubAdminArea()).append("\n");*/
                sb.append(address.getSubLocality());
                String sb1=address.getLocality();
               /* sb.append(address.getLocale()).append("\n");
                sb.append(address.getAdminArea()).append("\n");
                sb.append(address.getLocality()).append("\n");*/
                /*sb.append(address.getPostalCode()).append("\n");
                sb.append(address.getCountryName());*/
                notWorking=false;
                Log.d("fhrwuihejriytruuwei6", "fhrwuihejriytruuwei");
                locatonValues=sb.toString()+ System.getProperty("line.separator")+sb1;
                mtinyDb.putString(Constant.INITIALLOCATION, locatonValues);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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


    }

    public void findPlace(View view) {
        firstViews.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        mHandler.postDelayed(new Runnable() {
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        }, 200);

     /*   try {
            Intent intent =
                    new PlaceAutocomplete
                            .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, 1);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }*/
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder
                    (PlaceAutocomplete.MODE_FULLSCREEN)
                    .setBoundsBias(BOUNDS_MOUNTAIN_VIEW)
                    .build(LocationChangeActivity.this);
            startActivityForResult(intent, REQUEST_SELECT_PLACE);
        } catch (GooglePlayServicesRepairableException |
                GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }


    // A place has been received; use requestCode to track the request.

    @Override
    public void onPlaceSelected(Place place) {
        Log.d("fhuewrifierf1", "Place Selected: " + place.getAddress());
        String address= String.valueOf(place.getAddress());
        if(address.contains("Tamil Nadu")){
            Log.d("fjrifreui","Tamil Nadu");

        Log.d("fhuewrifierf2", "Place Selected: " + place.getPlaceTypes());
        Log.d("fhuewrifierf3", "Place Selected: " + place.getLocale());
        Log.d("fhuewrifierf", "Place Selected: " + place.getName());
        Log.d("imowefiiewo", "Place Selected: " + place.getLatLng());
        locatonValues = String.valueOf(place.getName());

        String lat = place.getLatLng().toString();
        String latlon = lat.replace("lat/lng:", "");
        String lass = latlon.replace("(", "");
        String longss = lass.replace(")", "");
        String[] la = longss.split(",");
        String sLat = la[0];
        String sLng = la[1];
        mtinyDb.putString(Constant.LATITUDE, sLat);
        mtinyDb.putString(Constant.LONGITUDE, sLng);
        mtinyDb.putString(Constant.INITIALLOCATION, locatonValues);
        Log.d("jfioowfj", sLat);
        Log.d("jfioowfj1", sLng);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("EXTRA_SESSION_ID", locatonValues);
        intent.putExtra("EXTRA_LAT", sLat);
        intent.putExtra("EXTRA_LON", sLng);
        startActivity(intent);
        finish();
        }else{
            notYourcity.setVisibility(View.VISIBLE);
            firstViews.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError(Status status) {
        Log.e(LOG_TAG, "onError: Status = " + status.toString());
        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_PLACE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                this.onPlaceSelected(place);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                this.onError(status);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            this.finish();
        }

        return super.onOptionsItemSelected(item);
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
                        Intent intent = new Intent(LocationChangeActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("EXTRA_SESSION_ID", locatonValues);
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
                    final AlertDialog.Builder builder = new AlertDialog.Builder(LocationChangeActivity.this);

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