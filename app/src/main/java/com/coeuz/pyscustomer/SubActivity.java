package com.coeuz.pyscustomer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.coeuz.pyscustomer.AdapterClass.SubActivityAdapter;
import com.coeuz.pyscustomer.ModelClass.SubActivityModel;
import com.coeuz.pyscustomer.Requiredclass.Constant;

import com.coeuz.pyscustomer.Requiredclass.TinyDB;
import com.google.gson.Gson;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SubActivity extends AppCompatActivity {


    private TinyDB mtinyDb;

    private RecyclerView recyclerView;
    private ProgressBar mprogressBar;

    private android.widget.SearchView searchView;
    private boolean itShouldLoadMore = true;
   private ArrayList<SubActivityModel> subActivityModels;
    private SubActivityAdapter subactivityAdapter;

    int mOffset=0;
    int mLimit=5;
    int temp=5;
    String mvendorId;
    String mPositons,mSubActivityId;

    ArrayList<String> nSubActivityIdList=new ArrayList<>();
    ArrayList<String> nVendorIdList=new ArrayList<>();
    Bundle bundle;
    String msubActivityName,msubActivityId;
   LocationManager locationManager;
   static final int REQUEST=1;
    double latitude;
    double longitude;
    String searchLat,searchLong;
    String URL1,URL2;
    private String filterVales,mGender,mRelavance,mfromAmount,mtoAmount,mprogressRate,mamenitiesList;
    Intent intent;
    RelativeLayout noValuesLayout;
    private LinearLayout noInternetLayout;
    private RelativeLayout allViewLayout;

    private String searchText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mtinyDb=new TinyDB(getApplicationContext());

        nSubActivityIdList=mtinyDb.getListString(Constant.SubActivityIdList);
        nVendorIdList=mtinyDb.getListString(Constant.VendorIdList);

        noValuesLayout=(RelativeLayout) findViewById(R.id.noValuesLayout);
        noValuesLayout.setVisibility(View.GONE);

        noInternetLayout = (LinearLayout) findViewById(R.id.NoInternetLayout);
        allViewLayout = (RelativeLayout) findViewById(R.id.allViewlayout);

        searchLat=mtinyDb.getString(Constant.LATITUDE);
        searchLong=mtinyDb.getString(Constant.LONGITUDE);

         intent = getIntent();
        filterVales=intent.getStringExtra("FilterValues");




        msubActivityName=mtinyDb.getString("activityName");
        msubActivityId=mtinyDb.getString("activityId");
        this.setTitle(msubActivityName);
 /*   bundle=getIntent().getExtras();
        if (bundle != null) {
           msubActivityName=bundle.getString("subActivityName");
            msubActivityId=bundle.getString("subActivityId");
           this.setTitle(msubActivityName);
        }*/

       locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        getLocation();
        Log.d("ertrefvg", String.valueOf(latitude));
        Log.d("frterter", String.valueOf(longitude));

        mprogressBar=(ProgressBar)findViewById(R.id.progressbar100);
        mprogressBar.setVisibility(View.VISIBLE);

        subActivityModels = new ArrayList<>();
        subactivityAdapter = new SubActivityAdapter(SubActivity.this,subActivityModels);

        recyclerView = (RecyclerView) this.findViewById(R.id.RecyclerHistoryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(subactivityAdapter);
        firstLoadData();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            // for this tutorial, this is the ONLY method that we need, ignore the rest
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy >0) {
                    // Recycle view scrolling downwards...
                    // this if statement detects when user reaches the end of recyclerView, this is only time we should load more
                    if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                        // remember "!" is the same as "== false"
                        // here we are now allowed to load more, but we need to be careful
                        // we must check if itShouldLoadMore variable is true [unlocked]
                        if (itShouldLoadMore) {


                            if(searchText!=null){
                                Log.d("endjweni",searchText);
                                searchLoadMore();
                            }else{
                                loadMore();
                            }
                        }
                    }

                }
            }
        });

        //setSupportActionBar(toolbar);

    }
    private void getLocation(){
        if (ActivityCompat.checkSelfPermission(SubActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SubActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST);
        }else{
            Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location !=null){
                latitude=location.getLatitude();
                longitude=location.getLongitude();
                Log.d("uiwefhweui", String.valueOf(latitude));
                Log.d("uiwefhweui1", String.valueOf(longitude));
            }
        }
    }
    private void firstLoadData() {
        subActivityModels.clear();
        if ( filterVales != null  && !filterVales.isEmpty()&&!filterVales.equals("[]")) {


            Log.d("wwwwwww1","wwwwwwwww1");


            mGender=intent.getStringExtra("nGender");
            mRelavance=intent.getStringExtra("nRelavance");
            mfromAmount=intent.getStringExtra("fromAmount");
            mtoAmount=intent.getStringExtra("toAmount");
            mprogressRate=intent.getStringExtra("progressRate");
            mamenitiesList=intent.getStringExtra("amenitiesList");
            if( mGender != null  && !mGender.isEmpty()){
                mGender=mGender;
            }else{mGender="";}
            if( mRelavance != null  && !mRelavance.isEmpty()){
                mRelavance=mRelavance;
            }else{mRelavance="";}

            if( mfromAmount != null  && !mfromAmount.isEmpty()){
                mfromAmount=mfromAmount;
            }else{mfromAmount="0";}
            if( mtoAmount != null  && !mtoAmount.isEmpty()){
                mtoAmount=mtoAmount;
            }else{mtoAmount="0";}
            if( mprogressRate != null  && !mprogressRate.isEmpty()){
                mprogressRate=mprogressRate;
            }else{mprogressRate="0";}
            if( mamenitiesList != null  && !mamenitiesList.isEmpty()){
                mamenitiesList=mamenitiesList;
            }else{mamenitiesList="0";}



            if (searchLat != null && !searchLat.isEmpty()) {
                Log.d("jfiwejf", searchLat);
                Log.d("jfiwejf1", searchLong);
                double mSearchLat = Double.parseDouble(searchLat);
                double mSearchLong = Double.parseDouble(searchLong);
                String URL = Constant.API +"/user/getVendorsByFilters?amenityId="+mamenitiesList+"&cost="+mprogressRate+"&fromCost="+mfromAmount+"&tocost="+mtoAmount+"&order="+mRelavance+"&lat="+mSearchLat+"&long="+mSearchLong+"&offset=0&limit=5&gender="+mGender+"&subActivityId="+msubActivityId;
                //String URL = Constant.API + "/user/getVendorsByFilters?amenityId="+mamenitiesList+"&cost="+mprogressRate+"&fromCost="+mfromAmount+"&tocost="+mtoAmount+"&order="+mRelavance+"&lat="+searchLat+"&long="+searchLong+"&offset=0&limit=5"+"&gender="+mGender;
                itShouldLoadMore = false;
                StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("hgerithjiow", String.valueOf(response));
                        mprogressBar.setVisibility(View.GONE);
                        itShouldLoadMore = true;
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0) {
                                noValuesLayout.setVisibility(View.VISIBLE);
                               /* Toast toast = Toast.makeText(SubActivity.this, "No Values1", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();*/
                            } else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    String vendorName = jsonObject.getString("vendorName");
                                    Integer vendorId = jsonObject.getInt("vendorId");
                                    String area = jsonObject.getString("area");
                                    String amenities = jsonObject.getString("amenities");
                                    Log.d("ncwiwnfiri", amenities);
                                    Log.d("ncwiwnfiri1", vendorName);

                                    subActivityModels.add(new SubActivityModel(vendorName, area,vendorId));
                                    subactivityAdapter.notifyDataSetChanged();

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("frwgtw", String.valueOf(error));

                        itShouldLoadMore = true;
                        mprogressBar.setVisibility(View.GONE);

                        if (error instanceof NetworkError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button=(Button)findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }});
                        } else if (error instanceof ServerError) {

                            Log.d("heuiwirhu1", String.valueOf(error));
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button=(Button)findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }});

                        }
                    }
                }) ;
                RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                requestQueue1.add(request1);
            } else {

                String URL = Constant.API +"/user/getVendorsByFilters?amenityId="+mamenitiesList+"&cost="+mprogressRate+"&fromCost="+mfromAmount+"&tocost="+mtoAmount+"&order="+mRelavance+"&lat="+latitude+"&long="+longitude+"&offset=0&limit=5&gender="+mGender+"&subActivityId="+msubActivityId;

              Log.d("fjwiof","fjwiu");
                Log.d("rrrrr1",mGender);
                Log.d("rrrrr2",mRelavance);
                Log.d("rrrrr3",mfromAmount);
                Log.d("rrrrr4",mtoAmount);
                Log.d("rrrrr5",mprogressRate);
                Log.d("rrrrr6",mamenitiesList);
                itShouldLoadMore = false;
                Log.d("jfiwjfio", URL);
                StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ureuerjure", String.valueOf(response));
                        mprogressBar.setVisibility(View.GONE);
                        itShouldLoadMore = true;
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0) {
                                noValuesLayout.setVisibility(View.VISIBLE);
                              /*  Toast toast = Toast.makeText(SubActivity.this, "No Values2", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();*/
                            } else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    String vendorName = jsonObject.getString("vendorName");
                                    Integer vendorId = jsonObject.getInt("vendorId");
                                    String area = jsonObject.getString("area");
                                    String amenities = jsonObject.getString("amenities");
                                    Log.d("ncwiwnfiri", amenities);
                                    Log.d("ncwiwnfiri1", vendorName);

                                    subActivityModels.add(new SubActivityModel(vendorName, area,vendorId));
                                    subactivityAdapter.notifyDataSetChanged();

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("hfdiu", String.valueOf(error));


                        itShouldLoadMore = true;
                        mprogressBar.setVisibility(View.GONE);

                        if (error instanceof NetworkError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button=(Button)findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }});
                        } else if (error instanceof ServerError) {

                            Log.d("heuiwirhu1", String.valueOf(error));
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button=(Button)findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }});

                        }
                    }
                });
                RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                requestQueue1.add(request1);

            }





        } else {
            Log.d("wwwwwww2","wwwwwwwww2");
            if (searchLat != null && !searchLat.isEmpty()) {
                Log.d("jfiwejf", searchLat);
                Log.d("jfiwejf1", searchLong);
                double mSearchLat = Double.parseDouble(searchLat);
                double mSearchLong = Double.parseDouble(searchLong);
                String URL = Constant.API + "/general/getVendorsByActivityAndSubActivity?subActivityId=" + msubActivityId + "&offset=0&limit=5&lat=" + mSearchLat + "&long=" + mSearchLong + "&distance=5";
                itShouldLoadMore = false;
                StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("hgerithjiow", String.valueOf(response));
                        mprogressBar.setVisibility(View.GONE);
                        itShouldLoadMore = true;
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0) {
                                noValuesLayout.setVisibility(View.VISIBLE);
                              /*  Toast toast = Toast.makeText(SubActivity.this, "No Values3", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();*/
                            } else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    String vendorName = jsonObject.getString("vendorName");
                                    Integer vendorId = jsonObject.getInt("vendorId");
                                    String area = jsonObject.getString("area");
                                    String amenities = jsonObject.getString("amenities");
                                    Log.d("ncwiwnfiri", amenities);
                                    Log.d("ncwiwnfiri1", vendorName);

                                    subActivityModels.add(new SubActivityModel(vendorName, area,vendorId));
                                    subactivityAdapter.notifyDataSetChanged();

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("frwgtw", String.valueOf(error));

                        itShouldLoadMore = true;
                        mprogressBar.setVisibility(View.GONE);

                        if (error instanceof NetworkError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button=(Button)findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }});
                        } else if (error instanceof ServerError) {

                            Log.d("heuiwirhu1", String.valueOf(error));
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button=(Button)findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }});

                        }
                    }
                });
                RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                requestQueue1.add(request1);
            } else {


                String URL = Constant.API + "/general/getVendorsByActivityAndSubActivity?subActivityId=" + msubActivityId + "&offset=0&limit=5&lat=" + latitude + "&long=" + longitude + "&distance=5";
                itShouldLoadMore = false;
                Log.d("jfiwjfio", URL);
                StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("hgerithjiow", String.valueOf(response));
                        mprogressBar.setVisibility(View.GONE);
                        itShouldLoadMore = true;
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0) {
                                noValuesLayout.setVisibility(View.VISIBLE);
                               /* Toast toast = Toast.makeText(SubActivity.this, "No Values4", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();*/
                            } else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    String vendorName = jsonObject.getString("vendorName");
                                    Integer vendorId = jsonObject.getInt("vendorId");
                                    String area = jsonObject.getString("area");
                                    String amenities = jsonObject.getString("amenities");
                                    Log.d("ncwiwnfiri", amenities);
                                    Log.d("ncwiwnfiri1", vendorName);
                                    subActivityModels.add(new SubActivityModel(vendorName, area,vendorId));
                                    subactivityAdapter.notifyDataSetChanged();

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("jfiwjfio1", String.valueOf(error));

                        itShouldLoadMore = true;
                        mprogressBar.setVisibility(View.GONE);

                        if (error instanceof NetworkError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button=(Button)findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }});
                        } else if (error instanceof ServerError) {

                            Log.d("heuiwirhu1", String.valueOf(error));
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button=(Button)findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }});
                        }
                    }
                });
                RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                requestQueue1.add(request1);

            }
        }

    }


    private void loadMore() {

        mOffset = temp;
        mLimit = temp + 5;
        temp = mLimit;

        if (filterVales != null && !filterVales.isEmpty()&&!filterVales.equals("[]")) {



            if (searchLat != null && !searchLat.isEmpty()) {
                Log.d("jfiwejf", searchLat);
                Log.d("jfiwejf1", searchLong);
                double mSearchLat = Double.parseDouble(searchLat);
                double mSearchLong = Double.parseDouble(searchLong);
                String URL = Constant.API + "/user/getVendorsByFilters?amenityId=" + mamenitiesList + "&cost=" + mprogressRate + "&fromCost=" + mfromAmount + "&tocost=" + mtoAmount + "&order=" + mRelavance + "&lat=" + mSearchLat + "&long=" + mSearchLong + "&offset=" + mOffset + "&limit=" + mLimit + "&gender=" + mGender+"&subActivityId="+msubActivityId;

                itShouldLoadMore = false;
                StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("hgerithjiow", String.valueOf(response));
                        mprogressBar.setVisibility(View.GONE);
                        itShouldLoadMore = true;
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0) {
                                Toast toast = Toast.makeText(SubActivity.this, "No Values", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            } else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    String vendorName = jsonObject.getString("vendorName");
                                    Integer vendorId = jsonObject.getInt("vendorId");
                                    String area = jsonObject.getString("area");
                                    String amenities = jsonObject.getString("amenities");
                                    Log.d("ncwiwnfiri", amenities);
                                    Log.d("ncwiwnfiri1", vendorName);

                                    subActivityModels.add(new SubActivityModel(vendorName, area,vendorId));
                                    subactivityAdapter.notifyDataSetChanged();

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("frwgtw", String.valueOf(error));

                        itShouldLoadMore = true;
                        mprogressBar.setVisibility(View.GONE);

                        if (error instanceof NetworkError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button=(Button)findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }});
                        } else if (error instanceof ServerError) {

                            Log.d("heuiwirhu1", String.valueOf(error));
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button=(Button)findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }});
                        }
                    }
                });
                RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                requestQueue1.add(request1);
            } else {

                String URL = Constant.API + "/user/getVendorsByFilters?amenityId=" + mamenitiesList + "&cost=" + mprogressRate + "&fromCost=" + mfromAmount + "&tocost=" + mtoAmount + "&order=" + mRelavance + "&lat=" + latitude + "&long=" + longitude + "&offset=" + mOffset + "&limit=" + mLimit + "&gender=" + mGender+"&subActivityId="+msubActivityId;
                Log.d("fjwiof", "fjwiu");
                Log.d("rrrrr1", mGender);
                Log.d("rrrrr2", mRelavance);
                Log.d("rrrrr3", mfromAmount);
                Log.d("rrrrr4", mtoAmount);
                Log.d("rrrrr5", mprogressRate);
                Log.d("rrrrr6", mamenitiesList);
                itShouldLoadMore = false;
                Log.d("jfiwjfio", URL);
                StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ureuerjure", String.valueOf(response));
                        mprogressBar.setVisibility(View.GONE);
                        itShouldLoadMore = true;
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0) {
                                Toast toast = Toast.makeText(SubActivity.this, "Your search is over", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            } else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    String vendorName = jsonObject.getString("vendorName");
                                    Integer vendorId = jsonObject.getInt("vendorId");
                                    String area = jsonObject.getString("area");
                                    String amenities = jsonObject.getString("amenities");
                                    Log.d("ncwiwnfiri", amenities);
                                    Log.d("ncwiwnfiri1", vendorName);

                                    subActivityModels.add(new SubActivityModel(vendorName, area,vendorId));
                                    subactivityAdapter.notifyDataSetChanged();

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("hfdiu", String.valueOf(error));
                        Log.d("hfdiu1", String.valueOf(error.networkResponse.statusCode));

                        itShouldLoadMore = true;
                        mprogressBar.setVisibility(View.GONE);

                        if (error instanceof NetworkError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button=(Button)findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }});
                        } else if (error instanceof ServerError) {

                            Log.d("heuiwirhu1", String.valueOf(error));
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button=(Button)findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }});

                        }
                    }
                });
                RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                requestQueue1.add(request1);

            }


        } else {

            if (searchLat != null && !searchLat.isEmpty()) {
                Log.d("jfiwejf", searchLat);
                Log.d("jfiwejf1", searchLat);
                double mSearchLat = Double.parseDouble(searchLat);
                double mSearchLong = Double.parseDouble(searchLong);
                String URL = Constant.API + "/general/getVendorsByActivityAndSubActivity?subActivityId=" + msubActivityId + "&offset=" + mOffset + "&limit=" + mLimit + "&lat=" + mSearchLat + "&long=" + mSearchLong + "&distance=5";
                itShouldLoadMore = false;
                final ProgressWheel progressWheel = (ProgressWheel) this.findViewById(R.id.progress_wheel);
                progressWheel.setVisibility(View.VISIBLE);

                StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("hgerithjiow", String.valueOf(response));

                        progressWheel.setVisibility(View.GONE);

                        itShouldLoadMore = true;

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0) {
                                Toast.makeText(SubActivity.this, "Your search is over", Toast.LENGTH_SHORT).show();
                            } else {
                                Gson gson = new Gson();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String vendorName = jsonObject.getString("vendorName");
                                    Integer vendorId = jsonObject.getInt("vendorId");
                                    String area = jsonObject.getString("area");

                                    subActivityModels.add(new SubActivityModel(vendorName, area,vendorId));
                                    subactivityAdapter.notifyDataSetChanged();

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("frwgtw", String.valueOf(error));
                        progressWheel.setVisibility(View.GONE);

                        itShouldLoadMore = true;


                        if (error instanceof NetworkError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button=(Button)findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }});
                        } else if (error instanceof ServerError) {

                            Log.d("heuiwirhu1", String.valueOf(error));
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button=(Button)findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }});

                        }
                    }
                });
                RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                requestQueue1.add(request1);

            } else {

                String URL = Constant.API + "/general/getVendorsByActivityAndSubActivity?subActivityId=" + msubActivityId + "&offset=" + mOffset + "&limit=" + mLimit + "&lat=" + latitude + "&long=" + longitude + "&distance=5";


                itShouldLoadMore = false; // lock this until volley completes processing

                // progressWheel is just a loading spinner, please see the content_main.xml
                final ProgressWheel progressWheel = (ProgressWheel) this.findViewById(R.id.progress_wheel);
                progressWheel.setVisibility(View.VISIBLE);

                StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("hgerithjiow", String.valueOf(response));

                        progressWheel.setVisibility(View.GONE);


                        itShouldLoadMore = true;


                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0) {
                                Toast.makeText(SubActivity.this, "Your search is over", Toast.LENGTH_SHORT).show();
                            } else {
                                Gson gson = new Gson();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String vendorName = jsonObject.getString("vendorName");
                                    Integer vendorId = jsonObject.getInt("vendorId");
                                    String area = jsonObject.getString("area");

                                    subActivityModels.add(new SubActivityModel(vendorName, area,vendorId));
                                    subactivityAdapter.notifyDataSetChanged();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("frwgtw", String.valueOf(error));
                        progressWheel.setVisibility(View.GONE);

                        itShouldLoadMore = true;


                        if (error instanceof NetworkError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button=(Button)findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }});
                        } else if (error instanceof ServerError) {

                            Log.d("heuiwirhu1", String.valueOf(error));
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button=(Button)findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }});

                        }
                    }
                }) /*{
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers1 = new HashMap<String, String>();

                        headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
                        return headers1;

                    }
                }*/;
                RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                requestQueue1.add(request1);

            }
        }
    }
    private void searchLoadMore(){
        Log.d("fjifiedwedew",searchText);

        mOffset = temp;
        mLimit = temp + 5;
        temp = mLimit;

        String URL = Constant.API + "/general/searchIndividualOrChain?searchText="+searchText+"&limit="+mLimit+"&offset="+mOffset;
        itShouldLoadMore = false;
        StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("mierefmier", String.valueOf(response));
                mprogressBar.setVisibility(View.GONE);
                itShouldLoadMore = true;
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.d("mierefmier1", String.valueOf(jsonArray));
                    if (jsonArray.length() == 0) {
                       // noValuesLayout.setVisibility(View.VISIBLE);
                              /*  Toast toast = Toast.makeText(SubActivity.this, "No Values3", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();*/
                    } else {
                        Log.d("mierefmier3", String.valueOf(jsonArray.length()));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Log.d("mierefmier2", String.valueOf(jsonObject.length()));

                            String vendorName = jsonObject.getString("vendorName");
                            Integer vendorId = jsonObject.getInt("vendorId");
                            String area = jsonObject.getString("area");
                            String activityId = jsonObject.getString("activityId");
                            mtinyDb.putString("activityId",activityId);

                            Log.d("iiiiiii1", vendorName);

                            subActivityModels.add(new SubActivityModel(vendorName, area,vendorId));
                            subactivityAdapter.notifyDataSetChanged();

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("nciefeir", String.valueOf(error));

                itShouldLoadMore = true;
                mprogressBar.setVisibility(View.GONE);

                if (error instanceof NetworkError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button=(Button)findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});
                } else if (error instanceof ServerError) {

                    Log.d("heuiwirhu1", String.valueOf(error));
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button=(Button)findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});

                }
            }
        });
        RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
        requestQueue1.add(request1);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST:
                getLocation();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.submenu, menu);
        MenuItem item=menu.findItem(R.id.search);

        searchView = (android.widget.SearchView) item.getActionView();
        searchView.setFocusable(false);
        searchView.setActivated(true);
        searchView.setQueryHint("Type your search word here");
        searchView.clearFocus();
        searchView.setOnCloseListener(new android.widget.SearchView.OnCloseListener() {

            @Override
            public boolean onClose() {
               Log.d("fwuiifjuw","fjrirw");

                    startActivity(getIntent());
                    finish();

                return false;
            }
        });


        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                subActivityModels.clear();

                searchText = s.toUpperCase();
                Log.d("jrwuii",searchText);
                searchMethods(searchText);
                return false;
            }
        });


        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.filter) {
            Intent intent=new Intent(this,FilterActivity.class);
            startActivity(intent);
            finish();
        }
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
    protected void searchMethods(String text){
        Log.d("fhewuhui",text);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(subactivityAdapter);
        String URL = Constant.API + "/general/searchIndividualOrChain?searchText="+text+"&limit=5&offset=0";
        itShouldLoadMore = false;
        StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ngietgierg", String.valueOf(response));
                mprogressBar.setVisibility(View.GONE);
               itShouldLoadMore = true;
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.d("ngietgierg1", String.valueOf(jsonArray));
                    if (jsonArray.length() == 0) {
                        noValuesLayout.setVisibility(View.VISIBLE);
                              /*  Toast toast = Toast.makeText(SubActivity.this, "No Values3", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();*/
                    } else {
                        Log.d("ngietgierg2", String.valueOf(jsonArray.length()));
                        noValuesLayout.setVisibility(View.GONE);
                        for (int k = 0; k < jsonArray.length(); k++) {
                            Log.d("ngietgierg10", "run1");
                            JSONObject jsonObject = jsonArray.getJSONObject(k);

                            String vendorName = jsonObject.getString("vendorName");
                            Integer vendorId = jsonObject.getInt("vendorId");
                            String area = jsonObject.getString("area");
                            String activityId = jsonObject.getString("activityId");
                            mtinyDb.putString("activityId",activityId);


                            Log.d("ngietgierg3", String.valueOf(jsonObject.length()));
                            Log.d("ngietgierg4", String.valueOf(jsonObject));

                            Log.d("ngietgierg11", "run2");

                            Log.d("vuinreru43", vendorName);
                            Log.d("dfneinfe3", String.valueOf(vendorId));


                            subActivityModels.add(new SubActivityModel(vendorName, area,vendorId));
                            subactivityAdapter.notifyDataSetChanged();

                        }
                        Log.d("ngietgierg12", "run3");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("ngietgierg14", String.valueOf(e));
                    Log.d("ngietgierg13", "run4");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ncncrrfuir", String.valueOf(error));

                itShouldLoadMore = true;
               mprogressBar.setVisibility(View.GONE);

                if (error instanceof NetworkError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button=(Button)findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});
                } else if (error instanceof ServerError) {

                    Log.d("heuiwirhu1", String.valueOf(error));
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button=(Button)findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});

                }
            }
        });
        RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
        requestQueue1.add(request1);


        }
}