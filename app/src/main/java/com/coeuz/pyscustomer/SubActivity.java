package com.coeuz.pyscustomer;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.coeuz.pyscustomer.AdapterClass.SubActivityAdapter;
import com.coeuz.pyscustomer.ModelClass.SubActivityModel;
import com.coeuz.pyscustomer.Requiredclass.Constant;

import com.coeuz.pyscustomer.Requiredclass.TinyDB;
import com.coeuz.pyscustomer.Requiredclass.VolleySingleton;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.Objects;

public class SubActivity extends AppCompatActivity {


    private TinyDB mtinyDb;

    private RecyclerView recyclerView;
    private ProgressBar mprogressBar;

    private boolean itShouldLoadMore = true;
   private ArrayList<SubActivityModel> subActivityModels;
    private SubActivityAdapter subactivityAdapter;

    int mOffset=0;
    int mLimit=5;
    int temp=5;

    ArrayList<String> nSubActivityIdList=new ArrayList<>();
    ArrayList<String> nVendorIdList=new ArrayList<>();
    Bundle bundle;
    String msubActivityName,msubActivityId;
   LocationManager locationManager;
   static final int REQUEST=1;
    double latitude;
    double longitude;
    String searchLat,searchLong;

    private String filterVales,mGender,mRelavance,mfromAmount,mtoAmount,mprogressRate,mamenitiesList;
    Intent intent;

    private LinearLayout noInternetLayout;
    private RelativeLayout allViewLayout;

    private String searchText;
    double textSearchlon,textSearchlat;
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);


        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mtinyDb=new TinyDB(getApplicationContext());

        nSubActivityIdList=mtinyDb.getListString(Constant.SubActivityIdList);
        nVendorIdList=mtinyDb.getListString(Constant.VendorIdList);



        noInternetLayout = findViewById(R.id.NoInternetLayout);
        allViewLayout = findViewById(R.id.allViewlayout);

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
       // getLocation();


        latitude= Double.parseDouble(mtinyDb.getString(Constant.INITIALLAT));
        longitude=Double.parseDouble( mtinyDb.getString(Constant.INITIALLONG));


        mprogressBar=findViewById(R.id.progressbar100);
        mprogressBar.setVisibility(View.VISIBLE);

        subActivityModels = new ArrayList<>();
        subactivityAdapter = new SubActivityAdapter(SubActivity.this,subActivityModels);

        recyclerView = this.findViewById(R.id.RecyclerHistoryList);
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

            }
        }
    }
    private void firstLoadData() {

        subActivityModels.clear();
        if ( filterVales != null  && !filterVales.isEmpty()&&!filterVales.equals("[]")) {



            mGender=intent.getStringExtra("nGender");
            mRelavance=intent.getStringExtra("nRelavance");
            mfromAmount=intent.getStringExtra("fromAmount");
            mtoAmount=intent.getStringExtra("toAmount");
            mprogressRate=intent.getStringExtra("progressRate");
             mamenitiesList=intent.getStringExtra("amenitiesList");
         /*   if( mGender != null  && !mGender.isEmpty()){

            }else{mGender="";}
            if( mRelavance != null  && !mRelavance.isEmpty()){

            }else{mRelavance="";}

            if( mfromAmount != null  && !mfromAmount.isEmpty()){

            }else{mfromAmount="0";}
            if( mtoAmount != null  && !mtoAmount.isEmpty()){

            }else{mtoAmount="0";}
            if( mprogressRate != null  && !mprogressRate.isEmpty()){

            }else{mprogressRate="0";}
            if( mamenitiesList != null  && !mamenitiesList.isEmpty()){

            }else{mamenitiesList="0";}*/



            if (searchLat != null && !searchLat.isEmpty()) {

                double mSearchLat = Double.parseDouble(searchLat);
                double mSearchLong = Double.parseDouble(searchLong);
                String URL = Constant.API +"/user/getVendorsByFilters?amenityId="+mamenitiesList+"&cost="+mprogressRate+"&fromCost="+mfromAmount+"&tocost="+mtoAmount+"&order="+mRelavance+"&lat="+mSearchLat+"&long="+mSearchLong+"&offset=0&limit=5&gender="+mGender+"&subActivityId="+msubActivityId;
                //String URL = Constant.API + "/user/getVendorsByFilters?amenityId="+mamenitiesList+"&cost="+mprogressRate+"&fromCost="+mfromAmount+"&tocost="+mtoAmount+"&order="+mRelavance+"&lat="+searchLat+"&long="+searchLong+"&offset=0&limit=5"+"&gender="+mGender;
                itShouldLoadMore = false;
                StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mprogressBar.setVisibility(View.GONE);
                        itShouldLoadMore = true;
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0) {

                                View view = getLayoutInflater().inflate(R.layout.no_values, allViewLayout,false);
                                view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                allViewLayout.addView(view);
                            } else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    String vendorName = jsonObject.getString("vendorName");
                                    Integer vendorId = jsonObject.getInt("vendorId");
                                    String area = jsonObject.getString("area");
                                    String vendorShopImage = jsonObject.getString("vendorShopImage");


                                    subActivityModels.add(new SubActivityModel(vendorName, area,vendorId,vendorShopImage));
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


                        itShouldLoadMore = true;
                        mprogressBar.setVisibility(View.GONE);

                        if (error instanceof NetworkError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button = findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                        } else if (error instanceof ServerError) {

                            View view = getLayoutInflater().inflate(R.layout.something_went_wrong, allViewLayout,false);
                            view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            allViewLayout.addView(view);
                            Button button =view.findViewById(R.id.SomethingTryAgain1);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        }  else if (error instanceof TimeoutError) {

                            View view = getLayoutInflater().inflate(R.layout.something_went_wrong, allViewLayout,false);
                            view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            allViewLayout.addView(view);
                            Button button =view.findViewById(R.id.SomethingTryAgain1);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                        }

                    }
                }) ;
                VolleySingleton.getInstance(SubActivity.this).addToRequestQueue(request1);
            } else {

                String URL = Constant.API +"/user/getVendorsByFilters?amenityId="+mamenitiesList+"&cost="+mprogressRate+"&fromCost="+mfromAmount+"&tocost="+mtoAmount+"&order="+mRelavance+"&lat="+latitude+"&long="+longitude+"&offset=0&limit=5&gender="+mGender+"&subActivityId="+msubActivityId;

                itShouldLoadMore = false;

                StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mprogressBar.setVisibility(View.GONE);
                        itShouldLoadMore = true;
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0) {
                                View view = getLayoutInflater().inflate(R.layout.no_values, allViewLayout,false);
                                view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                allViewLayout.addView(view);
                            } else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    String vendorName = jsonObject.getString("vendorName");
                                    Integer vendorId = jsonObject.getInt("vendorId");
                                    String area = jsonObject.getString("area");
                                    String vendorShopImage = jsonObject.getString("vendorShopImage");


                                    subActivityModels.add(new SubActivityModel(vendorName, area,vendorId,vendorShopImage));
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



                        itShouldLoadMore = true;
                        mprogressBar.setVisibility(View.GONE);

                        if (error instanceof NetworkError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button = findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                        } else if (error instanceof ServerError) {

                            View view = getLayoutInflater().inflate(R.layout.something_went_wrong, allViewLayout,false);
                            view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            allViewLayout.addView(view);
                            Button button =view.findViewById(R.id.SomethingTryAgain1);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        }  else if (error instanceof TimeoutError) {

                            View view = getLayoutInflater().inflate(R.layout.something_went_wrong, allViewLayout,false);
                            view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            allViewLayout.addView(view);
                            Button button =view.findViewById(R.id.SomethingTryAgain1);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                        }
                    }
                });
                VolleySingleton.getInstance(SubActivity.this).addToRequestQueue(request1);

            }





        } else {

            if (searchLat != null && !searchLat.isEmpty()) {

                double mSearchLat = Double.parseDouble(searchLat);
                double mSearchLong = Double.parseDouble(searchLong);
                String URL = Constant.API + "/general/getVendorsByActivityAndSubActivity?subActivityId=" + msubActivityId + "&offset=0&limit=5&lat=" + mSearchLat + "&long=" + mSearchLong + "&distance=5";
                itShouldLoadMore = false;
                StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mprogressBar.setVisibility(View.GONE);
                        itShouldLoadMore = true;
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0) {
                                View view = getLayoutInflater().inflate(R.layout.no_values, allViewLayout,false);
                                view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                allViewLayout.addView(view);
                            } else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    String vendorName = jsonObject.getString("vendorName");
                                    Integer vendorId = jsonObject.getInt("vendorId");
                                    String area = jsonObject.getString("area");
                                    String vendorShopImage = jsonObject.getString("vendorShopImage");


                                    subActivityModels.add(new SubActivityModel(vendorName, area,vendorId,vendorShopImage));
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


                        itShouldLoadMore = true;
                        mprogressBar.setVisibility(View.GONE);

                        if (error instanceof NetworkError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button = findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                        } else if (error instanceof ServerError) {

                            View view = getLayoutInflater().inflate(R.layout.something_went_wrong, allViewLayout,false);
                            view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            allViewLayout.addView(view);
                            Button button =view.findViewById(R.id.SomethingTryAgain1);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        }  else if (error instanceof TimeoutError) {

                            View view = getLayoutInflater().inflate(R.layout.something_went_wrong, allViewLayout,false);
                            view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            allViewLayout.addView(view);
                            Button button =view.findViewById(R.id.SomethingTryAgain1);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                        }
                    }
                });
                VolleySingleton.getInstance(SubActivity.this).addToRequestQueue(request1);
            } else {


                String URL = Constant.API + "/general/getVendorsByActivityAndSubActivity?subActivityId=" + msubActivityId + "&offset=0&limit=5&lat=" + latitude + "&long=" + longitude + "&distance=5";
                itShouldLoadMore = false;

                StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mprogressBar.setVisibility(View.GONE);
                        itShouldLoadMore = true;
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0) {
                                View view = getLayoutInflater().inflate(R.layout.no_values, allViewLayout,false);
                                view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                allViewLayout.addView(view);
                            } else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    String vendorName = jsonObject.getString("vendorName");
                                    Integer vendorId = jsonObject.getInt("vendorId");
                                    String area = jsonObject.getString("area");
                                    String vendorShopImage = jsonObject.getString("vendorShopImage");

                                    subActivityModels.add(new SubActivityModel(vendorName, area,vendorId,vendorShopImage));
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

                        itShouldLoadMore = true;
                        mprogressBar.setVisibility(View.GONE);
                        if (error instanceof NetworkError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button = findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                        } else if (error instanceof ServerError) {

                            View view = getLayoutInflater().inflate(R.layout.something_went_wrong, allViewLayout,false);
                            view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            allViewLayout.addView(view);
                            Button button =view.findViewById(R.id.SomethingTryAgain1);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        }  else if (error instanceof TimeoutError) {

                            View view = getLayoutInflater().inflate(R.layout.something_went_wrong, allViewLayout,false);
                            view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            allViewLayout.addView(view);
                            Button button =view.findViewById(R.id.SomethingTryAgain1);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                        }
                    }
                });
                VolleySingleton.getInstance(SubActivity.this).addToRequestQueue(request1);

            }
        }

    }


    private void loadMore() {

        mOffset = temp;
        mLimit = temp + 5;
        temp = mLimit;

        if (filterVales != null && !filterVales.isEmpty()&&!filterVales.equals("[]")) {



            if (searchLat != null && !searchLat.isEmpty()) {

                double mSearchLat = Double.parseDouble(searchLat);
                double mSearchLong = Double.parseDouble(searchLong);
                String URL = Constant.API + "/user/getVendorsByFilters?amenityId=" + mamenitiesList + "&cost=" + mprogressRate + "&fromCost=" + mfromAmount + "&tocost=" + mtoAmount + "&order=" + mRelavance + "&lat=" + mSearchLat + "&long=" + mSearchLong + "&offset=" + mOffset + "&limit=" + mLimit + "&gender=" + mGender+"&subActivityId="+msubActivityId;

                itShouldLoadMore = false;
                StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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
                                    String vendorShopImage = jsonObject.getString("vendorShopImage");


                                    subActivityModels.add(new SubActivityModel(vendorName, area,vendorId,vendorShopImage));
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

                        itShouldLoadMore = true;
                        mprogressBar.setVisibility(View.GONE);

                        if (error instanceof NetworkError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button = findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                        } else if (error instanceof ServerError) {

                            View view = getLayoutInflater().inflate(R.layout.something_went_wrong, allViewLayout,false);
                            view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            allViewLayout.addView(view);
                            Button button =view.findViewById(R.id.SomethingTryAgain1);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        }  else if (error instanceof TimeoutError) {

                            View view = getLayoutInflater().inflate(R.layout.something_went_wrong, allViewLayout,false);
                            view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            allViewLayout.addView(view);
                            Button button =view.findViewById(R.id.SomethingTryAgain1);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                        }
                    }
                });
                VolleySingleton.getInstance(SubActivity.this).addToRequestQueue(request1);
            } else {

                String URL = Constant.API + "/user/getVendorsByFilters?amenityId=" + mamenitiesList + "&cost=" + mprogressRate + "&fromCost=" + mfromAmount + "&tocost=" + mtoAmount + "&order=" + mRelavance + "&lat=" + latitude + "&long=" + longitude + "&offset=" + mOffset + "&limit=" + mLimit + "&gender=" + mGender+"&subActivityId="+msubActivityId;

                itShouldLoadMore = false;

                StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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
                                    String vendorShopImage = jsonObject.getString("vendorShopImage");


                                    subActivityModels.add(new SubActivityModel(vendorName, area,vendorId,vendorShopImage));
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



                        itShouldLoadMore = true;
                        mprogressBar.setVisibility(View.GONE);

                        if (error instanceof NetworkError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button = findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                        } else if (error instanceof ServerError) {

                            View view = getLayoutInflater().inflate(R.layout.something_went_wrong, allViewLayout,false);
                            view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            allViewLayout.addView(view);
                            Button button =view.findViewById(R.id.SomethingTryAgain1);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        }  else if (error instanceof TimeoutError) {

                            View view = getLayoutInflater().inflate(R.layout.something_went_wrong, allViewLayout,false);
                            view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            allViewLayout.addView(view);
                            Button button =view.findViewById(R.id.SomethingTryAgain1);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                        }
                    }
                });
                VolleySingleton.getInstance(SubActivity.this).addToRequestQueue(request1);

            }


        } else {

            if (searchLat != null && !searchLat.isEmpty()) {

                double mSearchLat = Double.parseDouble(searchLat);
                double mSearchLong = Double.parseDouble(searchLong);
                String URL = Constant.API + "/general/getVendorsByActivityAndSubActivity?subActivityId=" + msubActivityId + "&offset=" + mOffset + "&limit=" + mLimit + "&lat=" + mSearchLat + "&long=" + mSearchLong + "&distance=5";
                itShouldLoadMore = false;
                final ProgressWheel progressWheel = this.findViewById(R.id.progress_wheel);
                progressWheel.setVisibility(View.VISIBLE);

                StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        progressWheel.setVisibility(View.GONE);

                        itShouldLoadMore = true;

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0) {
                                Toast.makeText(SubActivity.this, "Your search is over", Toast.LENGTH_SHORT).show();
                            } else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String vendorName = jsonObject.getString("vendorName");
                                    Integer vendorId = jsonObject.getInt("vendorId");
                                    String area = jsonObject.getString("area");
                                    String vendorShopImage = jsonObject.getString("vendorShopImage");

                                    subActivityModels.add(new SubActivityModel(vendorName, area,vendorId,vendorShopImage));
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

                        progressWheel.setVisibility(View.GONE);

                        itShouldLoadMore = true;

                        if (error instanceof NetworkError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button = findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                        } else if (error instanceof ServerError) {

                            View view = getLayoutInflater().inflate(R.layout.something_went_wrong, allViewLayout,false);
                            view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            allViewLayout.addView(view);
                            Button button =view.findViewById(R.id.SomethingTryAgain1);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        }  else if (error instanceof TimeoutError) {

                            View view = getLayoutInflater().inflate(R.layout.something_went_wrong, allViewLayout,false);
                            view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            allViewLayout.addView(view);
                            Button button =view.findViewById(R.id.SomethingTryAgain1);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                        }
                    }
                });
                VolleySingleton.getInstance(SubActivity.this).addToRequestQueue(request1);

            } else {

                String URL = Constant.API + "/general/getVendorsByActivityAndSubActivity?subActivityId=" + msubActivityId + "&offset=" + mOffset + "&limit=" + mLimit + "&lat=" + latitude + "&long=" + longitude + "&distance=5";


                itShouldLoadMore = false; // lock this until volley completes processing

                // progressWheel is just a loading spinner, please see the content_main.xml
                final ProgressWheel progressWheel =this.findViewById(R.id.progress_wheel);
                progressWheel.setVisibility(View.VISIBLE);

                StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressWheel.setVisibility(View.GONE);


                        itShouldLoadMore = true;


                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0) {
                                Toast.makeText(SubActivity.this, "Your search is over", Toast.LENGTH_SHORT).show();
                            } else {

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String vendorName = jsonObject.getString("vendorName");
                                    Integer vendorId = jsonObject.getInt("vendorId");
                                    String area = jsonObject.getString("area");
                                    String vendorShopImage = jsonObject.getString("vendorShopImage");

                                    subActivityModels.add(new SubActivityModel(vendorName, area,vendorId,vendorShopImage));
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

                        progressWheel.setVisibility(View.GONE);

                        itShouldLoadMore = true;
                        if (error instanceof NetworkError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button = findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                        } else if (error instanceof ServerError) {

                            View view = getLayoutInflater().inflate(R.layout.something_went_wrong, allViewLayout,false);
                            view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            allViewLayout.addView(view);
                            Button button =view.findViewById(R.id.SomethingTryAgain1);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        }  else if (error instanceof TimeoutError) {

                            View view = getLayoutInflater().inflate(R.layout.something_went_wrong, allViewLayout,false);
                            view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            allViewLayout.addView(view);
                            Button button =view.findViewById(R.id.SomethingTryAgain1);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
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
                VolleySingleton.getInstance(SubActivity.this).addToRequestQueue(request1);
            }
        }
    }
    private void searchLoadMore(){


        mOffset = temp;
        mLimit = temp + 5;
        temp = mLimit;

        String URL = Constant.API + "/general/searchIndividualOrChain?searchText="+searchText+"&limit="+mLimit+"&offset="+mOffset;
        itShouldLoadMore = false;
        StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                mprogressBar.setVisibility(View.GONE);
                itShouldLoadMore = true;
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    if (jsonArray.length() == 0) {

                       // noValuesLayout.setVisibility(View.VISIBLE);
                              /*  Toast toast = Toast.makeText(SubActivity.this, "No Values3", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();*/
                    } else {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                         String vendorName = jsonObject.getString("vendorName");
                            Integer vendorId = jsonObject.getInt("vendorId");
                            String area = jsonObject.getString("area");
                          //  String activityId = jsonObject.getString("activityId");
                            String subActivityId = jsonObject.getString("subActivityId");

                            mtinyDb.putString("activityId",subActivityId);
                            String vendorShopImage = jsonObject.getString("vendorShopImage");


                            subActivityModels.add(new SubActivityModel(vendorName, area,vendorId,vendorShopImage));
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

                itShouldLoadMore = true;
                mprogressBar.setVisibility(View.GONE);

                if (error instanceof NetworkError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button = findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }
                    });
                } else if (error instanceof ServerError) {

                    View view = getLayoutInflater().inflate(R.layout.something_went_wrong, allViewLayout,false);
                    view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    allViewLayout.addView(view);
                    Button button =view.findViewById(R.id.SomethingTryAgain1);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }
                    });
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                }  else if (error instanceof TimeoutError) {

                    View view = getLayoutInflater().inflate(R.layout.something_went_wrong, allViewLayout,false);
                    view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    allViewLayout.addView(view);
                    Button button =view.findViewById(R.id.SomethingTryAgain1);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }
                    });
                }
            }
        });
        VolleySingleton.getInstance(SubActivity.this).addToRequestQueue(request1);


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

        android.widget.SearchView searchView = (android.widget.SearchView) item.getActionView();
        searchView.setFocusable(false);
        searchView.setActivated(true);
        searchView.setQueryHint("Type your search word here");
        searchView.clearFocus();
        searchView.setOnCloseListener(new android.widget.SearchView.OnCloseListener() {

            @Override
            public boolean onClose() {

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

        if(text.equals("")){

            firstLoadData();
        }else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(subactivityAdapter);
            if (searchLat != null && !searchLat.isEmpty()) {

                textSearchlat = Double.parseDouble(searchLat);
                textSearchlon = Double.parseDouble(searchLong);}
                else{
                textSearchlat=latitude;
                textSearchlon=longitude;
            }

            String URL = Constant.API + "/general/searchIndividualOrChain?searchText=" + text + "&limit=5&offset=0&subActivityId="+msubActivityId+"&lat="+textSearchlat+"&long="+textSearchlon+"&distance=5";
            itShouldLoadMore = false;
            StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    mprogressBar.setVisibility(View.GONE);
                    itShouldLoadMore = true;
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        if (jsonArray.length() == 0) {
                            View view = getLayoutInflater().inflate(R.layout.no_values, allViewLayout,false);
                            view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            allViewLayout.addView(view);
                        } else {

                            for (int k = 0; k < jsonArray.length(); k++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(k);

                                String vendorName = jsonObject.getString("vendorName");
                                Integer vendorId = jsonObject.getInt("vendorId");
                                String area = jsonObject.getString("area");
                                String subActivityId = jsonObject.getString("subActivityId");
                                String vendorShopImage = jsonObject.getString("vendorShopImage");

                                mtinyDb.putString("activityId", subActivityId);


                                subActivityModels.add(new SubActivityModel(vendorName, area,vendorId,vendorShopImage));
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


                    itShouldLoadMore = true;
                    mprogressBar.setVisibility(View.GONE);

                    if (error instanceof NetworkError) {
                        noInternetLayout.setVisibility(View.VISIBLE);
                        allViewLayout.setVisibility(View.GONE);
                        Button button = findViewById(R.id.TryAgain);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                recreate();
                            }
                        });
                    } else if (error instanceof ServerError) {

                        View view = getLayoutInflater().inflate(R.layout.something_went_wrong, allViewLayout,false);
                        view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        allViewLayout.addView(view);
                        Button button =view.findViewById(R.id.SomethingTryAgain1);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                recreate();
                            }
                        });
                    } else if (error instanceof ParseError) {
                        Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                    }  else if (error instanceof TimeoutError) {

                        View view = getLayoutInflater().inflate(R.layout.something_went_wrong, allViewLayout,false);
                        view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        allViewLayout.addView(view);
                        Button button =view.findViewById(R.id.SomethingTryAgain1);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                recreate();
                            }
                        });
                    }
                }
            });
            VolleySingleton.getInstance(SubActivity.this).addToRequestQueue(request1);

        }
        }
}