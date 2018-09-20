package com.coeuz.pyscustomer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;

import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coeuz.pyscustomer.AdapterClass.BookingRememberAdapter;
import com.coeuz.pyscustomer.AdapterClass.MainAdapter;
import com.coeuz.pyscustomer.AdapterClass.NearYouAdapter;
import com.coeuz.pyscustomer.AdapterClass.ViewPagerAdapter;
import com.coeuz.pyscustomer.ModelClass.SubActivityModel;
import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ArrayList<String> subActivityList = new ArrayList<>();
    ArrayList<String> subActivityIdList = new ArrayList<>();

    ArrayList<String> rememberVendorName = new ArrayList<>();
    ArrayList<String> rememberBookedDate = new ArrayList<>();
    ArrayList<String> rememberType = new ArrayList<>();
    ArrayList<String> rememberbookingType = new ArrayList<>();
    ArrayList<String> rememberOtp = new ArrayList<>();
    ArrayList<String> rememberstarttime = new ArrayList<>();
    ArrayList<String> rememberendtime = new ArrayList<>();
    ArrayList<String> rememberArea = new ArrayList<>();
    ArrayList<String> remembercontactno = new ArrayList<>();
    ArrayList<String> rememberpersonCount = new ArrayList<>();
    ArrayList<String> rememberNotes = new ArrayList<>();


    String locatonValues, initialLat, intialLong;
    private ProgressBar mprogressBar;
    RecyclerView mRecyclerView, nearRecycler, bookingRememberRecycler;
    private TinyDB tinyDB;
    String mToken;
    TextView nav_user;

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;


     String muserId, mcustomerName, mmobileNumber, memailId;

    private LinearLayout noInternetLayout;
    private RelativeLayout allViewLayout, rememberlayout;
    private NestedScrollView layoutPopup;


    private TextView mviewMoretext;
    private ArrayList<SubActivityModel> subActivityModels;
    private NearYouAdapter nearYouAdapter;
    private PopupWindow mPopupWindow;
    private String feedback;
    private EditText textFeedback;
    private LinearLayout nearYouLayout;
    JSONObject objects;
    String bookingId;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        tinyDB = new TinyDB(getApplicationContext());
        mcustomerName = tinyDB.getString(Constant.CUSTOMERNAME);

        mToken = tinyDB.getString(Constant.TOKEN);
        initialLat = tinyDB.getString(Constant.INITIALLAT);
        intialLong = tinyDB.getString(Constant.INITIALLONG);
      /*  String popuserId = tinyDB.getString(Constant.USERID);
        String popsubActivityId = tinyDB.getString(Constant.PAYMENTPAGESUBID);
        String popVendorId = tinyDB.getString(Constant.VENDORID);*/

        locatonValues = tinyDB.getString(Constant.INITIALLOCATION);
        //locatonValues = getIntent().getStringExtra("EXTRA_SESSION_ID");

        noInternetLayout = findViewById(R.id.NoInternetLayout);
        allViewLayout = findViewById(R.id.allViewlayout);
        layoutPopup = findViewById(R.id.nestedScrollview);

        mprogressBar = findViewById(R.id.progressbar100);
        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setNestedScrollingEnabled(false);
        nearYouLayout = findViewById(R.id.nearyouLayouts);
        nearRecycler = findViewById(R.id.galleryrecycle);
        bookingRememberRecycler = findViewById(R.id.BookingRememberRecycle);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(bookingRememberRecycler);
        rememberlayout = findViewById(R.id.rememberlayout);

        ViewGroup.LayoutParams params1 = mRecyclerView.getLayoutParams();
        params1.height = 400;
        mRecyclerView.setLayoutParams(params1);

       /* final ViewFlipper flipper = (ViewFlipper)findViewById(R.id.flipper1);
        flipper.startFlipping();

        Timer t1 = new Timer(false);
        t1.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        flipper.stopFlipping();
                    }
                });
            }
        }, 8000);*/


        if (!mToken.equals("")) {
            Log.d("fwuiffewew", mToken);
        }
        LinearLayout mViewMore = findViewById(R.id.viewMore);
        mviewMoretext = findViewById(R.id.viewMoreText);
        final boolean[] showingFirst = {true};
        mViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (showingFirst[0]) {
                    runAnimation(mRecyclerView);
                    ViewGroup.LayoutParams params = mRecyclerView.getLayoutParams();
                    params.height = RecyclerView.LayoutParams.WRAP_CONTENT;
                    mRecyclerView.setLayoutParams(params);
                    showingFirst[0] = false;
                    mviewMoretext.setText("View Less Categories");
                } else {
                    ViewGroup.LayoutParams params = mRecyclerView.getLayoutParams();
                    params.height = 400;
                    mRecyclerView.setLayoutParams(params);
                    showingFirst[0] = true;
                    mviewMoretext.setText("View More Categories");
                }


            }
        });

        subActivityModels = new ArrayList<>();
        nearYouAdapter = new NearYouAdapter(MainActivity.this, subActivityModels);


        nearRecycler.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false));
        nearRecycler.setHasFixedSize(true);
        nearRecycler.setAdapter(nearYouAdapter);
       /* cd = new ConnectionDetector(getApplicationContext());
        if (!cd.isConnected()) {
            noInternetLayout.setVisibility(View.VISIBLE);
            allViewLayout.setVisibility(View.GONE);
            Button button=(Button)findViewById(R.id.TryAgain);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recreate();
                }
            });
        }*/

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);


        //Todo
        View hView = navigationView.getHeaderView(0);
        //ImageView imgvw = (ImageView)hView.findViewById(R.id.imageView);
        nav_user = hView.findViewById(R.id.textView);
        //imgvw .setImageResource(R.drawable.ic_today_black_24dp);
        if (!mcustomerName.equals("")) {
            nav_user.setText(mcustomerName.toUpperCase());
        } else {
            nav_user.setText("PYS");
        }
        navigationView.setNavigationItemSelectedListener(this);
        if (mToken.equals("")) {
            Log.d("ndjn", "huihi");
            navigationView.getMenu().getItem(1).setVisible(false);
            navigationView.getMenu().getItem(2).setVisible(false);
            navigationView.getMenu().getItem(3).setVisible(false);
            navigationView.getMenu().getItem(4).setVisible(false);
            navigationView.getMenu().getItem(5).setVisible(false);
            navigationView.getMenu().getItem(6).setVisible(false);
            navigationView.getMenu().getItem(7).setVisible(false);//if you want to hide first item
            // navigationView.getMenu().getItem(1).setVisible(true); // if you want to show second menu item should be visible
        }


        viewPager = findViewById(R.id.viewPager);
        sliderDotspanel = findViewById(R.id.SliderDots);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getApplicationContext());
        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];
        for (int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView(getApplicationContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 3000);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        mprogressBar.setVisibility(View.VISIBLE);
        subActivityList.clear();
        subActivityIdList.clear();
        String URL = Constant.API + "/general/getAllSubActivities";
        StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jjtirjtit9i3oti3", String.valueOf(response));


                mprogressBar.setVisibility(View.GONE);


                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);


                        String subActivityId = jsonObject.getString("subActivityId");
                        String subActivityType = jsonObject.getString("subActivityType");

                        subActivityList.add(subActivityType);
                        subActivityIdList.add(subActivityId);

                        tinyDB.putListString(Constant.SubActivityIdList, subActivityIdList);
                        //tinyDB.putListString(Constant.VendorIdList,vendorIdList);
                        Log.d("yeruwiuifwi1", String.valueOf(subActivityList.size()));


                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 3);
                        mRecyclerView.setLayoutManager(layoutManager);
                        RecyclerView.Adapter adapter = new MainAdapter(getApplicationContext(), subActivityList, subActivityIdList);
                        mRecyclerView.setAdapter(adapter);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("bsduf", String.valueOf(error));
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

                    Log.d("heuiwirhu1", String.valueOf(error));
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                }  else if (error instanceof TimeoutError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button = findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }
                    });

                }
            }
        }) {

        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
        requestQueue1.add(request1);


        if (!mToken.isEmpty()) {
            String URL1 = Constant.APIONE + "/user/getCurrentUser";

            StringRequest request = new StringRequest(Request.Method.GET, URL1, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("mvefn1", String.valueOf(response));
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        muserId = jsonObject.getString("userId");
                        mcustomerName = jsonObject.getString("customerName");
                        mmobileNumber = jsonObject.getString("mobileNumber");
                        memailId = jsonObject.getString("emailId");
                        tinyDB.putString(Constant.CUSTOMERNAME, mcustomerName);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("miremio", String.valueOf(error));

                 /*   if (error instanceof NetworkError) {
                        Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ServerError) {

                        Log.d("heuiwirhu1", String.valueOf(error));
                    } else if (error instanceof ParseError) {
                        Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                    } else if (error instanceof NoConnectionError) {
                        Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof TimeoutError) {
                        Toast.makeText(getApplicationContext(), "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();

                    }*/
                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers1 = new HashMap<>();

                    headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
                    return headers1;

                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);

        }
        if (intialLong != null) {
            Log.d("fjiewjfiw", intialLong);
            Log.d("fjiewjfiw1", initialLat);
        }
        String URL5 = Constant.API + "/user/getVendorsByNearingYou?&lat=" + initialLat + "&long=" + intialLong + "&offset=0&limit=5";


        StringRequest request5 = new StringRequest(Request.Method.GET, URL5, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("hgerithjiow", String.valueOf(response));


                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() == 0) {
                        Log.d("hgerithjiow", String.valueOf(response));
                        nearYouLayout.setVisibility(View.GONE);
                    } else {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String vendorName = jsonObject.getString("vendorName");
                            Integer vendorId = jsonObject.getInt("vendorId");
                            String area = jsonObject.getString("area");
                            String subActivityId = jsonObject.getString("subActivityId");

                            subActivityModels.add(new SubActivityModel(vendorName, area, vendorId,subActivityId));
                            nearYouAdapter.notifyDataSetChanged();

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

                    Log.d("heuiwirhu1", String.valueOf(error));
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                }  else if (error instanceof TimeoutError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button = findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }
                    });

                }
            }
        });
        RequestQueue requestQueue5 = Volley.newRequestQueue(getApplicationContext());
        requestQueue5.add(request5);


        if (!mToken.isEmpty()) {
            rememberVendorName.clear();
            rememberBookedDate.clear();
            rememberType.clear();
            rememberbookingType.clear();
            rememberOtp.clear();
            rememberstarttime.clear();
           rememberendtime.clear();
           rememberArea.clear();
           remembercontactno.clear();
         rememberpersonCount.clear();
            rememberNotes.clear();
            String URL1 = Constant.APIONE + "/slot/getBookingDetails";

            StringRequest request = new StringRequest(Request.Method.GET, URL1, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("frgergertdf", String.valueOf(response));
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        if (jsonArray.length() == 0) {
                            rememberlayout.setVisibility(View.GONE);
                        } else {
                            rememberlayout.setVisibility(View.VISIBLE);


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);



                                String vendorName = jsonObject.getString("vendorName");
                                String bookedforDate = jsonObject.getString("bookedforDate");
                                String bookingType = jsonObject.getString("bookingType");
                                String subActivityType = jsonObject.getString("subActivityType");
                                String startTime = jsonObject.getString("startTime");
                                String endTime = jsonObject.getString("endTime");
                                String personcount = jsonObject.getString("personcount");
                                String area = jsonObject.getString("area");
                                String contactNo = jsonObject.getString("contactNo");
                                String otp = jsonObject.getString("otp");
                                String notes = jsonObject.getString("notes");
                                Log.d("grgrwgwrw",startTime);
                                Log.d("grgrwgwrw1",endTime);
                                Log.d("grgrwgwrw2",otp);


                                rememberVendorName.add(vendorName);
                                rememberBookedDate.add(bookedforDate);
                                rememberType.add(subActivityType);
                                rememberbookingType.add(bookingType);
                                rememberOtp.add(otp);
                                rememberstarttime.add(startTime);
                                rememberendtime.add(endTime);
                                rememberArea.add(area);
                                remembercontactno.add(contactNo);
                                rememberpersonCount.add(personcount);
                                rememberNotes.add(notes);

                                bookingRememberRecycler.setLayoutManager(new GridLayoutManager(MainActivity.this, 1, GridLayoutManager.HORIZONTAL, false));
                                bookingRememberRecycler.setHasFixedSize(true);
                                RecyclerView.Adapter adapter = new BookingRememberAdapter(MainActivity.this, rememberVendorName, rememberBookedDate, rememberType,
                                        rememberbookingType,rememberOtp,rememberstarttime,rememberendtime,rememberArea,remembercontactno,rememberpersonCount,rememberNotes);
                                bookingRememberRecycler.setAdapter(adapter);

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("fddgrgerg", String.valueOf(error));


                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers1 = new HashMap<>();

                    headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
                    return headers1;

                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);

        }
        if (!mToken.isEmpty()) {

            String URL1 = Constant.APIONE + "/general/checkFeedback ";

            StringRequest request = new StringRequest(Request.Method.GET, URL1, new Response.Listener<String>() {
                @SuppressLint("InflateParams")
                @Override
                public void onResponse(String response) {
                    Log.d("vdferer", String.valueOf(response));
                    try {
                        JSONArray jsonArray = new JSONArray(response);


                        Log.d("fjwuifjwi1", String.valueOf(jsonArray));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            objects = jsonArray.getJSONObject(i);
                             bookingId=objects.getString("bookingId");
                            Log.d("fdsfw", String.valueOf(bookingId));
                        }

                        if (bookingId!=null) {
                            if (!bookingId.equals("0")) {

                                LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                View customView = null;
                                if (inflater != null) {
                                    customView = inflater.inflate(R.layout.cofirmation_layout, null);
                                }


                                mPopupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                                if (Build.VERSION.SDK_INT >= 21) {
                                    mPopupWindow.setElevation(5.0f);
                                }
                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        mPopupWindow.showAtLocation(layoutPopup, Gravity.BOTTOM, 0, 0);
                                    }
                                }, 100);

                                assert customView != null;
                                final RatingBar simpleRatingBar = customView.findViewById(R.id.simpleRatingBar);
                                Button submitButton = customView.findViewById(R.id.sub);
                                textFeedback = customView.findViewById(R.id.textFeedback);


                                submitButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        feedback = textFeedback.getText().toString().trim();
                                      //  String totalStars = "Total Stars:: " + simpleRatingBar.getNumStars();
                                        final String rating = String.valueOf(simpleRatingBar.getRating());
                                        final String ratings = rating.substring(0, rating.indexOf("."));

                                        Log.d("fnrinfi", ratings);
                                      //  String rating1 = "Rating :: " + simpleRatingBar.getRating();
                                        // Toast.makeText(getApplicationContext(),  rating1, Toast.LENGTH_SHORT).show();

                                        String URL = "http://13.126.119.71:8081/api/service/general/customerFeedback";
                                        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Log.d("fsgdsgfd", response);
                                                Toast.makeText(getApplicationContext(), "Thanks for your feedback", Toast.LENGTH_SHORT).show();
                                                mPopupWindow.dismiss();
                                                tinyDB.putString(Constant.STATUS, "Success");

                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.d("vdwfewq32", error.toString());
                                                mPopupWindow.dismiss();


                                            }
                                        }) {
                                            @Override
                                            public byte[] getBody() {

                                                HashMap<String, Object> hashMap = new HashMap<>();

                                                hashMap.put("rating", ratings);
                                                hashMap.put("feedback", feedback);
                                                //  hashMap.put("subActivityId", popsubActivityId);
                                                //  hashMap.put("userId", popuserId);
                                                //  hashMap.put("vendorId", popVendorId);
                                                hashMap.put("bookingId", String.valueOf(bookingId));
                                                Log.d("fjwuifjwi", String.valueOf(bookingId));


                                                return new JSONObject(hashMap).toString().getBytes();

                                            }

                                            @Override
                                            public String getBodyContentType() {
                                                return "application/json";
                                            }

                                            @Override
                                            public Map<String, String> getHeaders() {
                                                HashMap<String, String> headers1 = new HashMap<>();

                                                headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
                                                return headers1;

                                            }


                                        };
                                        request.setRetryPolicy(new RetryPolicy() {
                                            @Override
                                            public int getCurrentTimeout() {
                                                return 60000;
                                            }

                                            @Override
                                            public int getCurrentRetryCount() {
                                                return 60000;
                                            }

                                            @Override
                                            public void retry(VolleyError error) {

                                            }
                                        });

                                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                        requestQueue.add(request);


                                    }
                                });

                                TextView closeButton = customView.findViewById(R.id.okBtn);

                                closeButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        tinyDB.putString(Constant.STATUS, "Success");
                                        mPopupWindow.dismiss();
                                    }
                                });
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("cwfewfe", String.valueOf(error));


                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers1 = new HashMap<>();

                    headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
                    return headers1;

                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);


        }
    }

    private void runAnimation(RecyclerView mRecyclerView) {
        Context context = mRecyclerView.getContext();
        LayoutAnimationController controller;
        controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_fall_down);

        mRecyclerView.setLayoutAnimation(controller);
        mRecyclerView.getAdapter().notifyDataSetChanged();
        mRecyclerView.scheduleLayoutAnimation();
    }


    @Override
    public void onBackPressed() {
        tinyDB.putString(Constant.STATUS, "Success");
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(true);
            builder.setIcon(R.drawable.doorexiticon);
            builder.setTitle("Do you want to Exit?");
            builder.setInverseBackgroundForced(true);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    LoginManager.getInstance().logOut();
                    Intent i = new Intent();
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setAction(Intent.ACTION_MAIN);
                    i.addCategory(Intent.CATEGORY_HOME);
                    MainActivity.this.startActivity(i);


                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
            // super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

       // MenuItem refreshItem = menu.findItem(R.id.mlocation);

  /*      TransitionManager.beginDelayedTransition(refreshItem, new Rotate());
        R.id.LocationChange.setRotation(isRotated ? 135 : 0);*/


        if (locatonValues != null) {
            menu.findItem(R.id.mlocation).setTitle(locatonValues);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
      /*  if (id == R.id.mlocation) {

        }*/


        //noinspection SimplifiableIfStatement

        if (id == R.id.LocationChange) {

            Intent intent = new Intent(this, LocationChangeActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.Home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.BookingHistory) {
            if (!mToken.equals("")) {
                Intent intent = new Intent(this, BookingHistoryActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }

        } else if (id == R.id.Settings) {
            if (!mToken.equals("")) {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }

        } else if (id == R.id.Notifications) {
            Intent intent = new Intent(this, NotificationActivitys.class);
            startActivity(intent);

        } else if (id == R.id.Memberships) {

            if (!mToken.equals("")) {
                Intent intent = new Intent(this, MembershipActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }

        } else if (id == R.id.Course) {

            if (!mToken.equals("")) {
                Intent intent = new Intent(this, CourseActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }

        } else if (id == R.id.LogOut) {
            tinyDB.putString(Constant.TOKEN, "");
            tinyDB.putString(Constant.CUSTOMERNAME, "");
            nav_user.setText("PYS");
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_share) {
            String shareBody = "https://play.google.com/store/apps/details?id=************************";
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "APP NAME (Open it in Google Play Store to Download the Application)");

            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));


        } else if (id == R.id.AboutUs) {

            Intent intent = new Intent(this, AboutPys.class);
            startActivity(intent);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    // This fires when a notification is opened by tapping on it or one is received while the app is running.

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(2);
                    } else if (viewPager.getCurrentItem() == 2) {
                        viewPager.setCurrentItem(3);
                    } else if (viewPager.getCurrentItem() == 3) {
                        viewPager.setCurrentItem(4);
                    } else if (viewPager.getCurrentItem() == 4) {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}
