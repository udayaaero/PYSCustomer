package com.coeuz.pyscustomer;

import android.Manifest;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.coeuz.pyscustomer.AdapterClass.AfterSelectVendorAmenitiesAdapter;
import com.coeuz.pyscustomer.AdapterClass.ConsecutiveDateBookingAdapter;
import com.coeuz.pyscustomer.AdapterClass.DateBookingAdapter;
import com.coeuz.pyscustomer.AdapterClass.GalleryAdapter;
import com.coeuz.pyscustomer.AdapterClass.SelectCourseAdapter;
import com.coeuz.pyscustomer.AdapterClass.SelectMemberAdapter;
import com.coeuz.pyscustomer.AdapterClass.OfferAdapter;
import com.coeuz.pyscustomer.AdapterClass.OtherServiceAdapter;
import com.coeuz.pyscustomer.ModelClass.ConsecutiveSlotModel;
import com.coeuz.pyscustomer.ModelClass.OfferModel;
import com.coeuz.pyscustomer.ModelClass.SlotModel;
import com.coeuz.pyscustomer.Requiredclass.ConnectionDetector;

import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.MyBounceInterpolator;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;


public class AfterSelectVendor extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener,OnItemClick  {


    OnItemClick listener;
    private  String changedSubActivityId;
    BarChart barChart;
    ArrayList<BarEntry> barValueList = new ArrayList<>();
    ArrayList<Integer> mCountList = new ArrayList<>();
    ArrayList<String> mlabelList = new ArrayList<>();
    ArrayList<JSONArray> firstLableList = new ArrayList<>();
    JSONArray jsonArray1;
    JSONArray jsonArray4;
    String label,lables;
    HashMap<String,JSONArray> totalGraphValue = new HashMap<String, JSONArray>();

    private TinyDB mTinyDb;
    Bundle bundle;
    private String vendorName,area,vendorId,msubActivityId,mBookingType,mcbookingType;;
    private ArrayList<String> slotTypeList=new ArrayList<>();

    private GoogleMap gMap;
    private Button getDirection;
    ConnectionDetector cd;
    private static LatLng GREENBELT1;
    private String latString,longSting;
    Double lat;
    Double longi;
    LinearLayout mapVisibleLayout;
    RecyclerView recyclerViewAmenities,recyclerGallery;
    ArrayList<String> subActivityList=new ArrayList<>();
    private ArrayList<String> amenitiesList=new ArrayList<>();
    private ArrayList<String> amenitiesImagesList=new ArrayList<>();
    private TextView nArea,nCity,nMobile,nEmail;
    private TextView nFrom,nTo;
    private TextView nvendorArea,nvendorName;
    private String vvendorArea,vvendorName;


    private String offerStart,offerEnd;
    private HorizontalScrollView linearLayout1;
    private LinearLayout noInternetLayout;
    private ScrollView allViewLayout;

     boolean visible;


    private ArrayList<OfferModel> mOfferModel;
    private OfferAdapter offerAdapter;
    ArrayList<String> offerStartList=new ArrayList<>();
    ArrayList<String> offerEndList=new ArrayList<>();
    ArrayList<String> offerTypeList=new ArrayList<>();
    ArrayList<String> offerBenefits=new ArrayList<>();

    boolean flag = true;



    ArrayList<String> motherServiceList=new ArrayList<>();
    ArrayList<String> otherServiceid=new ArrayList<>();
    RecyclerView mRecyclerView;

    private String  mCalenderdate,todayDate;
    private ArrayList<SlotModel> slotModel;
    private ArrayList<String> consecutiveslotModel=new ArrayList<String>();
    private ArrayList<String> consecutiveslotModelTiming=new ArrayList<String>();
    private ConsecutiveDateBookingAdapter consecutiveSlotDateAdapter;
    private DateBookingAdapter SlotDateAdapter;
    private RecyclerView RecyclerDateSlot;
    private  Integer maxAllowed,slotId,bookingSlotCost;
    private String slotStartTime,slotEndTime;
    JSONArray ConsecutiveTimingArray=new JSONArray();

    private Animation myAnim;
    private  ImageView mofferDetails;
    Dialog offerDialog;
    ImageView cancel;
    RecyclerView offerRecycler,memberCourseRecycler;

    private CardView membershipBtn,courseBtn;
    ArrayList<String> memberbookingType = new ArrayList<>();
    ArrayList<String> memberbookingCost = new ArrayList<>();
    ArrayList<String> memberbookingSlotId = new ArrayList<>();


    ArrayList<String> mslotidsList=new ArrayList<>();
    ArrayList<String> mslotStartTimeList=new ArrayList<>();
    ArrayList<String> mslotEndTimeList=new ArrayList<>();
    ArrayList<String> mmaxAllowedList=new ArrayList<>();
    ArrayList<String> mslotReccurenceList=new ArrayList<>();
    ArrayList<String> mcourseStartDateList=new ArrayList<>();
    ArrayList<String> mcourseEndDateList=new ArrayList<>();
    ArrayList<String> mcourseRegistrationEndDateList=new ArrayList<>();
    ArrayList<String> mCourseCostList=new ArrayList<>();

    private LinearLayout mapView;
    boolean layoutShow=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_select_vendor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar
        getSupportActionBar().setDisplayShowTitleEnabled(false); // hide the current title from the Toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        mTinyDb=new TinyDB(getApplicationContext());
        latString=mTinyDb.getString("latttt");
        longSting=mTinyDb.getString("longggg");
        msubActivityId=mTinyDb.getString("activityId");
        vvendorName=mTinyDb.getString(Constant.VENDORNAME);
        vvendorArea=mTinyDb.getString(Constant.VENDORAREA);

        if(latString !=null &&!latString.isEmpty()){
            Log.d("jgreio",latString);
            lat= Double.valueOf(latString);
            longi= Double.valueOf(longSting);
        }

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        todayDate = format.format(today);
        Log.d("dateToStr",todayDate);


        cd=new ConnectionDetector(getApplicationContext());
        recyclerGallery=(RecyclerView)findViewById(R.id.galleryrecycle);
        recyclerViewAmenities=(RecyclerView)findViewById(R.id.amenitiesrecycle);

        mapVisibleLayout=(LinearLayout)findViewById(R.id.mapVisible);
        RecyclerDateSlot = (RecyclerView)findViewById(R.id.RecyclerDateBooking);
        mapVisibleLayout.setVisibility(View.GONE);

        mofferDetails=(ImageView)findViewById(R.id.offerDetails);
        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        myAnim.setRepeatCount(Animation.INFINITE);
        mofferDetails.startAnimation(myAnim);

        mapView=(LinearLayout)findViewById(R.id.mapview);
        mapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapVisibleLayout.setVisibility(View.GONE);
                mapVisibleLayout.setFocusableInTouchMode(false);
                mapVisibleLayout.setVisibility(View.VISIBLE);
                mapVisibleLayout.setFocusable(true);
                mapVisibleLayout.setFocusableInTouchMode(true);
                mapVisibleLayout.requestFocus();

            }
        });




       /* mOfferModel = new ArrayList<>();
        offerAdapter = new OfferAdapter(this,mOfferModel);


        mOfferRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mOfferRecyclerView.setHasFixedSize(true);
        mOfferRecyclerView.setAdapter(offerAdapter);*/

        noInternetLayout = (LinearLayout) findViewById(R.id.NoInternetLayout);
        allViewLayout = (ScrollView) findViewById(R.id.allViewlayout);


        linearLayout1=(HorizontalScrollView) findViewById(R.id.l1);
         barChart = (BarChart) findViewById(R.id.barchart);

        nvendorName=(TextView)findViewById(R.id.NameOfVendor);
        nvendorArea=(TextView)findViewById(R.id.AdressOfVendor);
        nArea=(TextView)findViewById(R.id.mArea);
        nCity=(TextView)findViewById(R.id.mCity);
        nMobile=(TextView)findViewById(R.id.mMobile);
        nEmail=(TextView)findViewById(R.id.mEmails);
        nFrom=(TextView)findViewById(R.id.from);
        nTo=(TextView)findViewById(R.id.to);
        mRecyclerView=(RecyclerView)findViewById(R.id.recycler);
        membershipBtn=(CardView)findViewById(R.id.membershibBtn);
        courseBtn=(CardView)findViewById(R.id.CourseBtn);
        memberCourseRecycler=(RecyclerView)findViewById(R.id.recycle_memberCourse);

        nvendorName.setText(vvendorName);
        nvendorArea.setText(vvendorArea);



        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AfterSelectVendor.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerGallery.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new GalleryAdapter(AfterSelectVendor.this);
        recyclerGallery.setAdapter(adapter);



        offerDialog = new Dialog(AfterSelectVendor.this,R.style.AppTheme);

        offerDialog.setContentView(R.layout.offer_layout);

        offerDialog.setCancelable(true);

        cancel = (ImageView) offerDialog.findViewById(R.id.cancels);
        offerRecycler=(RecyclerView)offerDialog.findViewById(R.id.RecyclerOffer);


        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                offerDialog.dismiss();
            }
        });



        bundle=getIntent().getExtras();
        if (bundle != null) {
            vendorName=bundle.getString("mVendorName");
            vendorId= String.valueOf(bundle.getInt("mVendorIds"));
            area=bundle.getString("mArea");
           // this.setTitle(vendorName);
        }
        mTinyDb.putString(Constant.VENDORID,vendorId);

        mBookingType=mTinyDb.getString(Constant.BOOKINGTYPE);
        mcbookingType=mTinyDb.getString(Constant.MCBOOKINGTYPE);


        if(mcbookingType.equals("MEMBERSHIP")){
            mcbookingType="MEMBERSHIP";
            membershipBtn.setVisibility(View.VISIBLE);
            courseBtn.setVisibility(View.GONE);

        }else if(mcbookingType.equals("COURSE")){
            mcbookingType="COURSE";
            courseBtn.setVisibility(View.VISIBLE);
            membershipBtn.setVisibility(View.GONE);
        }




        String URL15 = Constant.API +"/general/vendorOtherServices?vendorId="+vendorId;

        StringRequest request15 = new StringRequest(Request.Method.GET, URL15, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("qwrrew", String.valueOf(response));

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String subActivityId=jsonObject.getString("subActivityId");
                        String subActivityType=jsonObject.getString("subActivityType");
                        motherServiceList.add(subActivityType);
                        otherServiceid.add(subActivityId);
                    }
                    Log.d("fuew", String.valueOf(otherServiceid));

                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(AfterSelectVendor.this,3);
                    mRecyclerView.setLayoutManager(layoutManager);
                    RecyclerView.Adapter adapter = new OtherServiceAdapter(getApplicationContext(),otherServiceid, motherServiceList, new OnItemClick() {
                        @Override
                        public void onClick(final String value) {
                            Log.d("jfrij2",value);
                            changedSubActivityId=value;
                            if(changedSubActivityId!=null){
                            changingSubActivityTypes(changedSubActivityId);


                            slotTypeList.clear();
                            String URL10 = Constant.API +"/user/getSlotTypesBySubActivityIdAndVendorId?subActivityId="+changedSubActivityId+"&vendorId="+vendorId;

                            StringRequest request10 = new StringRequest(Request.Method.GET, URL10, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("rtrews", String.valueOf(response));

                                    try {
                                        JSONArray jsonArray = new JSONArray(response);

                                        if (jsonArray.length() == 0) {
                                            Log.d("rtrews", String.valueOf(response));
                       /* Toast toast = Toast.makeText(AfterSelectVendor.this, "No Values", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();*/
                                        } else {
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                String slotTypes = String.valueOf(jsonArray.get(i));
                                                Log.d("fsdfw", String.valueOf(slotTypes));

                                                slotTypeList.add(slotTypes);
                                            }
                                            if(slotTypeList.contains("PRE_DEFINED_SLOT")){
                                                mBookingType="PRE_DEFINED_SLOT";
                                                mTinyDb.putString(Constant.BOOKINGTYPE,mBookingType);
                                                memberCourseRecycler.setVisibility(View.GONE);
                                            }else if(slotTypeList.contains("CONSECUTIVE")){
                                                mBookingType="CONSECUTIVE";
                                                memberCourseRecycler.setVisibility(View.GONE);
                                                courseBtn.setVisibility(View.GONE);
                                                membershipBtn.setVisibility(View.GONE);
                                                mTinyDb.putString(Constant.BOOKINGTYPE,mBookingType);
                                            }
                                            if(slotTypeList.contains("MEMBERSHIP")){
                                                memberCourseRecycler.setVisibility(View.GONE);
                                                mcbookingType="MEMBERSHIP";
                                                membershipBtn.setVisibility(View.VISIBLE);
                                                courseBtn.setVisibility(View.GONE);

                                            }else if(slotTypeList.contains("COURSE")){
                                                mcbookingType="COURSE";
                                                memberCourseRecycler.setVisibility(View.GONE);
                                                courseBtn.setVisibility(View.VISIBLE);
                                                membershipBtn.setVisibility(View.GONE);
                                            }
                                        }
    if(mBookingType.equals("PRE_DEFINED_SLOT")){
        mTinyDb.putString(Constant.PREDEFINEDSUBACTIVITYID,value);
                                        slotModel = new ArrayList<>();
                                        SlotDateAdapter = new DateBookingAdapter(getApplicationContext(), slotModel);
                                        RecyclerDateSlot.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                                        RecyclerDateSlot.setHasFixedSize(true);
                                        RecyclerDateSlot.setAdapter(SlotDateAdapter);

                                        slotModel.clear();


                                        String URL = Constant.API + "/slot/getSlotsByDate?vendorId=" + vendorId + "&subActivityId=" + value + "&date=" + mCalenderdate + "&type=" + mBookingType;

                                        StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Log.d("yrwuiyiw", String.valueOf(response));

                                                try {
                                                    JSONArray jsonArray = new JSONArray(response);
                                                    if (jsonArray.length() == 0) {
                                                        Log.d("yrwuiyiw1", String.valueOf(response));
                                /*Toast toast = Toast.makeText(SlotPages.this, "No Values", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();*/
                                                    } else {

                                                        for (int i = 0; i < jsonArray.length(); i++) {
                                                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                                                            slotId = jsonObject.getInt("slotId");
                                                            slotStartTime = jsonObject.getString("slotStartTime");
                                                            slotEndTime = jsonObject.getString("slotEndTime");
                                                /*            try {
                                                                final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                                                                final Date dateObj = sdf.parse(slotStartTime );
                                                                String timein12Format=new SimpleDateFormat("hh:mmaa").format(dateObj);
                                                                Log.d("fnuifreui45", String.valueOf(timein12Format));
                                                                slotStartTime=String.valueOf(timein12Format);
                                                                slotStartTime = slotStartTime.replace(".", "");
                                                            } catch (final ParseException e) {
                                                                e.printStackTrace();
                                                            }
                                                            try {
                                                                final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                                                                final Date dateObj = sdf.parse(slotEndTime );
                                                                String timein12Format=new SimpleDateFormat("hh:mmaa").format(dateObj);
                                                                Log.d("fnuifreui45", String.valueOf(timein12Format));
                                                                slotEndTime=String.valueOf(timein12Format);
                                                                slotEndTime = slotEndTime.replace(".", "");
                                                            } catch (final ParseException e) {
                                                                e.printStackTrace();
                                                            }*/
                                                            maxAllowed = jsonObject.getInt("maxAllowed");
                                                            bookingSlotCost = jsonObject.getInt("bookingCost");
                                                            slotModel.add(new SlotModel(slotId, slotStartTime, slotEndTime, maxAllowed, bookingSlotCost));

                                                        }
                                                        SlotDateAdapter.notifyDataSetChanged();

                                                    }


                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.d("dscer", String.valueOf(error));

                                                if (error instanceof NetworkError) {
                                                    noInternetLayout.setVisibility(View.VISIBLE);
                                                    allViewLayout.setVisibility(View.GONE);
                                                    Button button = (Button) findViewById(R.id.TryAgain);
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

                                                } else if (error instanceof NoConnectionError) {
                                                    Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                                                } else if (error instanceof TimeoutError) {
                                                    noInternetLayout.setVisibility(View.VISIBLE);
                                                    allViewLayout.setVisibility(View.GONE);
                                                    Button button = (Button) findViewById(R.id.TryAgain);
                                                    button.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            recreate();
                                                        }
                                                    });

                                                }
                                            }
                                        });
                                        RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                                        requestQueue1.add(request1);}
    else if(mBookingType.equals("CONSECUTIVE")){

        mTinyDb.putString(Constant.PREDEFINEDSUBACTIVITYID,value);

        consecutiveslotModel.clear();
        consecutiveslotModelTiming.clear();


        String URL = Constant.API + "/slot/getSlotsByDate?vendorId=" + vendorId + "&subActivityId=" + value + "&date=" + mCalenderdate + "&type=" + mBookingType;

        StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ewfwefewfew", String.valueOf(response));

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() == 0) {
                        Log.d("yrwuiyiw1", String.valueOf(response));
                                /*Toast toast = Toast.makeText(SlotPages.this, "No Values", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();*/
                    } else {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            slotId = jsonObject.getInt("slotId");
                            maxAllowed = jsonObject.getInt("maxAllowed");
                            bookingSlotCost = jsonObject.getInt("bookingCost");
                            ConsecutiveTimingArray=jsonObject.getJSONArray("timings");

                        }
                        for (int i = 0; i < ConsecutiveTimingArray.length(); i++) {
                            JSONObject jsonObject = ConsecutiveTimingArray.getJSONObject(i);
                            slotStartTime = jsonObject.getString("startTime");
                            slotEndTime = jsonObject.getString("endTime");
                        }
                        Log.d("ewfwefewfew2", String.valueOf(slotStartTime));
                        Log.d("ewfwefewfew5", String.valueOf(slotId));

                        try {
                            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                            final Date dateObj = sdf.parse(slotStartTime );
                            String timein12Format=new SimpleDateFormat("hh:mmaa").format(dateObj);
                            Log.d("mcmcemciqc", String.valueOf(timein12Format));
                            slotStartTime=String.valueOf(timein12Format);
                            slotStartTime = slotStartTime.replace(".", "");
                        } catch (final ParseException e) {
                            e.printStackTrace();
                        }


                        consecutiveslotModel.add(String.valueOf(slotId));
                        consecutiveslotModelTiming.add( slotStartTime);
                        Log.d("fnrifi1", String.valueOf(consecutiveslotModel.size()));
                        Log.d("fnrifi2", String.valueOf(consecutiveslotModelTiming.size()));



                    }
                    consecutiveSlotDateAdapter = new ConsecutiveDateBookingAdapter(getApplicationContext(), consecutiveslotModel,consecutiveslotModelTiming);
                    RecyclerDateSlot.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                    RecyclerDateSlot.setHasFixedSize(true);
                    RecyclerDateSlot.setAdapter(consecutiveSlotDateAdapter);
                 //   SlotDateAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("dscer", String.valueOf(error));

                if (error instanceof NetworkError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button = (Button) findViewById(R.id.TryAgain);
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

                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button = (Button) findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }
                    });

                }
            }
        });
        RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
        requestQueue1.add(request1);

    }






                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("ewqreqw", String.valueOf(error));

                                    if (error instanceof NetworkError) {
                                        Log.d("ewqreqw", String.valueOf(error));
                                    } else if (error instanceof ServerError) {

                                        Log.d("heuiwirhu1", String.valueOf(error));
                                    } else if (error instanceof ParseError) {
                                        Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                                    } else if (error instanceof NoConnectionError) {
                                        Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                                    } else if (error instanceof TimeoutError) {
                                        Log.d("ewqreqw", String.valueOf(error));

                                    }
                                }
                            });
                            RequestQueue requestQueue10 = Volley.newRequestQueue(getApplicationContext());
                            requestQueue10.add(request10);







                        }}
                    });
                    mRecyclerView.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("yreuie", String.valueOf(error));

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
        RequestQueue requestQueue15 = Volley.newRequestQueue(getApplicationContext());
        requestQueue15.add(request15);


       /* slotTypeList.clear();
        String URL10 = Constant.API +"/user/getSlotTypesBySubActivityIdAndVendorId?subActivityId="+changedSubActivityId+"&vendorId="+vendorId;

        StringRequest request10 = new StringRequest(Request.Method.GET, URL10, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("rtrews", String.valueOf(response));

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    if (jsonArray.length() == 0) {
                        Log.d("rtrews", String.valueOf(response));
                       *//* Toast toast = Toast.makeText(AfterSelectVendor.this, "No Values", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();*//*
                    } else {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String slotTypes = String.valueOf(jsonArray.get(i));
                            Log.d("fsdfw", String.valueOf(slotTypes));

                            slotTypeList.add(slotTypes);
                        }
                    *//*    if(slotTypeList.contains("PRE_DEFINED_SLOT")){
                            mBookingType="PRE_DEFINED_SLOT";
                            mTinyDb.putString(Constant.BOOKINGTYPE,mBookingType);
                        }else if(slotTypeList.contains("CONSECUTIVE")){
                            mBookingType="CONSECUTIVE";
                            mTinyDb.putString(Constant.BOOKINGTYPE,mBookingType);
                        }*//*
                        if(slotTypeList.contains("MEMBERSHIP")){
                            bookingType="MEMBERSHIP";
                            membershipBtn.setVisibility(View.VISIBLE);
                            courseBtn.setVisibility(View.GONE);

                        }else if(slotTypeList.contains("COURSE")){
                            bookingType="COURSE";

                            courseBtn.setVisibility(View.VISIBLE);
                            membershipBtn.setVisibility(View.GONE);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ewqreqw", String.valueOf(error));

                if (error instanceof NetworkError) {
                    Log.d("ewqreqw", String.valueOf(error));
                } else if (error instanceof ServerError) {

                    Log.d("heuiwirhu1", String.valueOf(error));
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    Log.d("ewqreqw", String.valueOf(error));

                }
            }
        });
        RequestQueue requestQueue10 = Volley.newRequestQueue(getApplicationContext());
        requestQueue10.add(request10);

*/



        amenitiesList.clear();
        amenitiesImagesList.clear();
        String URL = Constant.API +"/general/getVendorByVendorId?vendorId="+vendorId;

        StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("uieioe", String.valueOf(response));

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.length() == 0) {
                        Log.d("uieioe1", String.valueOf(response));
                       /* Toast toast = Toast.makeText(AfterSelectVendor.this, "No Values", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();*/
                    } else {

                            String city = jsonObject.getString("city");
                            String area = jsonObject.getString("area");
                            String latitude = jsonObject.getString("latitude");
                            String longitude = jsonObject.getString("longitude");
                            String availableAmenities = jsonObject.getString("availableAmenities");
                            String email = jsonObject.getString("email");
                            String mobile = jsonObject.getString("mobile");
                            String openingTime = jsonObject.getString("openingTime");
                            String closingTime = jsonObject.getString("closingTime");

                        lat= Double.valueOf(latitude);
                        longi= Double.valueOf(longitude);
                        try {
                            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                            final Date dateObj = sdf.parse(openingTime );
                            String timein12Format=new SimpleDateFormat("hh:mmaa").format(dateObj);
                            Log.d("mcmcemciqc", String.valueOf(timein12Format));
                            openingTime=String.valueOf(timein12Format);
                            openingTime = openingTime.replace(".", "");
                        } catch (final ParseException e) {
                            e.printStackTrace();
                        }
                        try {
                            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                            final Date dateObj = sdf.parse(closingTime );
                            String timein12Format=new SimpleDateFormat("hh:mmaa").format(dateObj);
                            Log.d("mcmcemciqc", String.valueOf(timein12Format));
                            closingTime=String.valueOf(timein12Format);
                            closingTime = closingTime.replace(".", "");
                        } catch (final ParseException e) {
                            e.printStackTrace();
                        }

                        nFrom.setText(openingTime);
                        nTo.setText(closingTime);

                        nArea.setText(area);
                        nCity.setText(city);
                        nMobile.setText(mobile);
                        nEmail.setText(email);
                            JSONArray jsonArray=jsonObject.getJSONArray("amenities");
                        Log.d("nfirni4", String.valueOf(jsonArray));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String amenityId = jsonObject1.getString("amenityId");
                            String amenityType = jsonObject1.getString("amenityType");
                            String image1 = jsonObject1.getString("image");
                            String status = jsonObject1.getString("status");
                            Log.d("nfirni5",amenityType);
                            amenitiesList.add(amenityType);
                         /*   byte[] imageBytes = Base64.decode(image1, Base64.DEFAULT);
                            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                            amenityImage.setImageBitmap(decodedImage);*/
                            amenitiesImagesList.add(image1);



                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AfterSelectVendor.this, LinearLayoutManager.HORIZONTAL, false);
                            recyclerViewAmenities.setLayoutManager(layoutManager);
                            RecyclerView.Adapter adapter = new AfterSelectVendorAmenitiesAdapter(getApplicationContext(), amenitiesList,amenitiesImagesList);
                            recyclerViewAmenities.setAdapter(adapter);
                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ert", String.valueOf(error));


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



        Calendar start = Calendar.getInstance();
        start.add(Calendar.MONTH, -1);
        Date date=java.util.Calendar.getInstance().getTime();
        Log.d("hfuifhu", String.valueOf(date));
/* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DAY_OF_MONTH, 6);
        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)

                .startDate(date)
                .endDate(endDate.getTime())
                .datesNumberOnScreen(5)// Number of Dates cells shown on screen (Recommended 5)
                .dayNameFormat("EEE")	  // WeekDay text format
                .textSizeDayNumber(15)
                .textSizeDayName(9)
                // Month format.monthFormat("KKK")
                .dayNumberFormat("dd")    // DateBooking format
                .selectedDateBackground(Drawable.createFromPath("#16711936"))
                .showDayName(true)	  // Show or Hide dayName text
                .showMonthName(true)	  // Show or Hide month text
                .textColor(Color.BLACK, Color.GREEN)    // Text color for none selected Dates, Text color for selected DateBooking.
                // Background Drawable of the selected date cell.
                .selectorColor(Color.GREEN)   // Color of the selection indicator bar (default to colorAccent).
                // DateBooking to be seleceted at start (default to Today)

                .build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {

                          /*  Calendar endDate = Calendar.getInstance();
                            endDate.setTimeInMillis(endDateInMillSecs);

                            Calendar cl = Calendar.getInstance();
                            cl.setTimeInMillis(startDateInMillSecs);*/
                Long timeStamp200 = date.getTime();
                String S1 = String.valueOf(timeStamp200);
                Log.d("popopop", S1);
                Log.d("popopop1", String.valueOf(timeStamp200));
                Long timestamp1 = Long.parseLong(S1);
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date netDate = (new Date(timestamp1));
                    String CalenderDate1 = sdf.format(netDate);
                    mCalenderdate = CalenderDate1;
                    Log.d("kkkkkkkkkuuuu3", CalenderDate1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("nfwifniw2", mCalenderdate);
                Log.d("wfwerfwre1", msubActivityId);
                mTinyDb.putString(Constant.CALENDERDATE,mCalenderdate);

                if(changedSubActivityId != null){
                    msubActivityId=changedSubActivityId;
                    }
                    mTinyDb.putString(Constant.PREDEFINEDSUBACTIVITYID,msubActivityId);

                if(mBookingType.equals("PRE_DEFINED_SLOT")){
                slotModel = new ArrayList<>();
                SlotDateAdapter = new DateBookingAdapter(getApplicationContext(), slotModel);
                RecyclerDateSlot.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                RecyclerDateSlot.setHasFixedSize(true);
                RecyclerDateSlot.setAdapter(SlotDateAdapter);

                slotModel.clear();

                String URL = Constant.API + "/slot/getSlotsByDate?vendorId=" + vendorId + "&subActivityId=" + msubActivityId + "&date=" + mCalenderdate + "&type=" + mBookingType;

                StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("yrwuiyiw123", String.valueOf(response));

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0) {

                                /*Toast toast = Toast.makeText(SlotPages.this, "No Values", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();*/
                            } else {

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    slotId = jsonObject.getInt("slotId");
                                    slotStartTime = jsonObject.getString("slotStartTime");
                                    slotEndTime = jsonObject.getString("slotEndTime");
                                    Log.d("fnuifreui23", slotEndTime);
                                /*    try {
                                        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                                        final Date dateObj = sdf.parse(slotStartTime );
                                        String timein12Format=new SimpleDateFormat("hh:mmaa").format(dateObj);
                                        Log.d("fnuifreui45", String.valueOf(timein12Format));
                                        slotStartTime=String.valueOf(timein12Format);
                                        slotStartTime = slotStartTime.replace(".", "");
                                    } catch (final ParseException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                                        final Date dateObj = sdf.parse(slotEndTime );
                                        String timein12Format=new SimpleDateFormat("hh:mmaa").format(dateObj);
                                        Log.d("fnuifreui45", String.valueOf(timein12Format));
                                        slotEndTime=String.valueOf(timein12Format);
                                        slotEndTime = slotEndTime.replace(".", "");
                                    } catch (final ParseException e) {
                                        e.printStackTrace();
                                    }*/
                                    maxAllowed = jsonObject.getInt("maxAllowed");
                                    bookingSlotCost = jsonObject.getInt("bookingCost");
                                    slotModel.add(new SlotModel(slotId, slotStartTime, slotEndTime, maxAllowed, bookingSlotCost));

                                }
                                SlotDateAdapter.notifyDataSetChanged();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("dscer", String.valueOf(error));

                        if (error instanceof NetworkError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button = (Button) findViewById(R.id.TryAgain);
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

                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button = (Button) findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });

                        }
                    }
                });
                RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                requestQueue1.add(request1);

                }else if(mBookingType.equals("CONSECUTIVE")){



                    consecutiveslotModel.clear();
                    consecutiveslotModelTiming.clear();


                    String URL = Constant.API + "/slot/getSlotsByDate?vendorId=" + vendorId + "&subActivityId=" + msubActivityId + "&date=" + mCalenderdate + "&type=" + mBookingType;

                    StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("ewfwefewfew", String.valueOf(response));

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                if (jsonArray.length() == 0) {
                                    Log.d("yrwuiyiw1", String.valueOf(response));
                                /*Toast toast = Toast.makeText(SlotPages.this, "No Values", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();*/
                                } else {

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        slotId = jsonObject.getInt("slotId");
                                        maxAllowed = jsonObject.getInt("maxAllowed");
                                        bookingSlotCost = jsonObject.getInt("bookingCost");
                                        ConsecutiveTimingArray=jsonObject.getJSONArray("timings");

                                    }
                                    for (int i = 0; i < ConsecutiveTimingArray.length(); i++) {
                                        JSONObject jsonObject = ConsecutiveTimingArray.getJSONObject(i);
                                        slotStartTime = jsonObject.getString("startTime");
                                        slotEndTime = jsonObject.getString("endTime");
                                    }
                                    Log.d("ewfwefewfew2", String.valueOf(slotStartTime));
                                    Log.d("ewfwefewfew5", String.valueOf(slotId));

                                    try {
                                        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                                        final Date dateObj = sdf.parse(slotStartTime );
                                        String timein12Format=new SimpleDateFormat("hh:mmaa").format(dateObj);
                                        Log.d("mcmcemciqc", String.valueOf(timein12Format));
                                        slotStartTime=String.valueOf(timein12Format);
                                        slotStartTime = slotStartTime.replace(".", "");
                                    } catch (final ParseException e) {
                                        e.printStackTrace();
                                    }


                                    consecutiveslotModel.add(String.valueOf(slotId));
                                    consecutiveslotModelTiming.add( slotStartTime);
                                    Log.d("fnrifi1", String.valueOf(consecutiveslotModel.size()));
                                    Log.d("fnrifi2", String.valueOf(consecutiveslotModelTiming.size()));



                                }
                                consecutiveSlotDateAdapter = new ConsecutiveDateBookingAdapter(getApplicationContext(), consecutiveslotModel,consecutiveslotModelTiming);
                                RecyclerDateSlot.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                                RecyclerDateSlot.setHasFixedSize(true);
                                RecyclerDateSlot.setAdapter(consecutiveSlotDateAdapter);
                             //   SlotDateAdapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("dscer", String.valueOf(error));

                            if (error instanceof NetworkError) {
                                noInternetLayout.setVisibility(View.VISIBLE);
                                allViewLayout.setVisibility(View.GONE);
                                Button button = (Button) findViewById(R.id.TryAgain);
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

                            } else if (error instanceof NoConnectionError) {
                                Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof TimeoutError) {
                                noInternetLayout.setVisibility(View.VISIBLE);
                                allViewLayout.setVisibility(View.GONE);
                                Button button = (Button) findViewById(R.id.TryAgain);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        recreate();
                                    }
                                });

                            }
                        }
                    });
                    RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                    requestQueue1.add(request1);

                }

            }

            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView,
                                         int dx, int dy) {
            }
            @Override
            public boolean onDateLongClicked(Date date, int position) {
                return true;
            }
        });



        totalGraphValue.clear();
        String URLs = Constant.API +"/services/getVendorVisitorsCount?vendorId="+vendorId;

        StringRequest request = new StringRequest(Request.Method.GET, URLs, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("uieruwor", String.valueOf(response));

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() == 0) {
                        Log.d("uieruwo1r", String.valueOf(response));
                       /* Toast toast = Toast.makeText(AfterSelectVendor.this, "No Values", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();*/
                    } else {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String data = jsonObject.getString("data");
                         label = jsonObject.getString("label");
                            mlabelList.add(label);
                             jsonArray1=jsonObject.getJSONArray("data");
                            firstLableList.add(jsonArray1);
                        Log.d("dhuifhuwi1",data);
                            Log.d("dhuifhuwi2",label);
                            Log.d("dhuifhuwi3", String.valueOf(jsonArray1));

                            totalGraphValue.put(label, (jsonArray1));


                        }

                        final RadioButton[] rb = new RadioButton[mlabelList.size()];
                        final RadioGroup rg = new RadioGroup(getApplicationContext());

                        rg.setOrientation(RadioGroup.HORIZONTAL);//or RadioGroup.VERTICAL
                        for(int i=0; i<mlabelList.size(); i++){
                            rb[i]  = new RadioButton(getApplicationContext());
                            rb[i].setText(mlabelList.get(i));
                            rb[i].setTextColor(Color.BLACK);
                            rb[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
                            rb[i].setId(i);
                            rg.addView(rb[i]);
                        }
                        rg.check(0);
                        linearLayout1.addView(rg);
                        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                int checkedRadioButtonId = rg.getCheckedRadioButtonId();
                                RadioButton radioBtn = (RadioButton) findViewById(checkedRadioButtonId);
                               // Toast.makeText(AfterSelectVendor.this, radioBtn.getText(), Toast.LENGTH_SHORT).show();
                                String selectedLable=radioBtn.getText().toString();

                                if(totalGraphValue.containsKey(selectedLable)){
                                    mCountList.clear();
                                 JSONArray jsonArray2=totalGraphValue.get(selectedLable);
                                    Log.d("nfirjewio", String.valueOf(totalGraphValue.get(selectedLable)));
                                    for (int j = 0; j < jsonArray2.length(); j++) {
                                        Object jsonObject1 = null;
                                        try {
                                            jsonObject1 = jsonArray2.get(j);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        Log.d("dhuifhuwi4", String.valueOf(jsonObject1));
                                        mCountList.add(Integer.valueOf(String.valueOf(jsonObject1)));
                                    }
                                    barChart.animateY(1000);

                                    barChart.setDescription("");
                                    barValueList.clear();
                                    for(int j=0;j<mCountList.size();j++){
                                        barValueList.add(new BarEntry(mCountList.get(j), j));
                                    }

                                    BarDataSet barDataSet1 = new BarDataSet(barValueList, "Person Count");

                                    barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

                                    ArrayList<String> labels = new ArrayList<String>();
                                    labels.add("Sun");
                                    labels.add("Mon");
                                    labels.add("Tue");
                                    labels.add("Wed");
                                    labels.add("Thu");
                                    labels.add("Fri");
                                    labels.add("Sat");
                                    Log.d("nfurifiurw2", String.valueOf(labels));
                                    Log.d("nfurifiurw3", String.valueOf(barDataSet1));
                                    BarData data1 = new BarData(labels,  barDataSet1);

                                    barChart.setData(data1);

                                }
                            }
                        });
                       if( mlabelList!=null){
                            lables=mlabelList.get(0);
                       }
                        if(totalGraphValue.containsKey(lables)){
                            mCountList.clear();
                            JSONArray jsonArray2=totalGraphValue.get(lables);
                            Log.d("nfirjewio", String.valueOf(totalGraphValue.get(lables)));
                            for (int j = 0; j < jsonArray2.length(); j++) {
                                Object jsonObject1 = null;
                                try {
                                    jsonObject1 = jsonArray2.get(j);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.d("dhuifhuwi4", String.valueOf(jsonObject1));
                                mCountList.add(Integer.valueOf(String.valueOf(jsonObject1)));
                            }
                            barChart.animateY(1000);

                            barChart.setDescription("");
                            barValueList.clear();
                            for(int j=0;j<mCountList.size();j++){
                                barValueList.add(new BarEntry(mCountList.get(j), j));
                            }

                            BarDataSet barDataSet1 = new BarDataSet(barValueList, "Person Count");

                            barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

                            ArrayList<String> labels = new ArrayList<String>();
                            labels.add("Sun");
                            labels.add("Mon");
                            labels.add("Tue");
                            labels.add("Wed");
                            labels.add("Thu");
                            labels.add("Fri");
                            labels.add("Sat");
                            Log.d("nfurifiurw2", String.valueOf(labels));
                            Log.d("nfurifiurw3", String.valueOf(barDataSet1));
                            BarData data1 = new BarData(labels,  barDataSet1);

                            barChart.setData(data1);
                   /*     mCountList.clear();
                        Log.d("dhuifhuwi9", String.valueOf(totalGraphValue));
                        Log.d("dhuifhuwi8", String.valueOf(firstLableList.size()));
                        for (int j = 0; j < firstLableList.size(); j++) {
                            jsonArray4 = firstLableList.get(0);

                        }
                        for (int j = 0; j < jsonArray4.length(); j++) {
                            Object jsonObject1 = jsonArray4.get(0);
                            Log.d("dhuifhuwi4", String.valueOf(jsonObject1));
                            mCountList.add(Integer.valueOf(String.valueOf(jsonObject1)));
                        }
                        barChart.animateY(2000);

                        barChart.setDescription("");
                        barValueList.clear();
                        for(int j=0;j<mCountList.size();j++){
                            barValueList.add(new BarEntry(mCountList.get(j), j));
                        }

                        BarDataSet barDataSet1 = new BarDataSet(barValueList, "Person Count");

                        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

                        ArrayList<String> labels = new ArrayList<String>();
                        labels.add("Sun");
                        labels.add("Mon");
                        labels.add("Tue");
                        labels.add("Wed");
                        labels.add("Thu");
                        labels.add("Fri");
                        labels.add("Sat");
                        Log.d("nfurifiurw", String.valueOf(labels));
                        Log.d("nfurifiurw1", String.valueOf(barDataSet1));
                        BarData data1 = new BarData(labels,  barDataSet1);
                        barChart.setData(data1);*/

                    }}

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("yreuie", String.valueOf(error));

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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);


        String URL2 = Constant.API +"/general/offerChecksByVendor?vendorId="+vendorId;

        StringRequest request2 = new StringRequest(Request.Method.GET, URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("uieruwor1", String.valueOf(response));

                try {
                       JSONObject jsonObject = new JSONObject(response);

                     String message = jsonObject.getString("message");
                   if(message.equals("Available")){
                       mofferDetails.setVisibility(View.VISIBLE);
                   }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("yreuie", String.valueOf(error));

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
        RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());
        requestQueue2.add(request2);

        mofferDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                offerDialog.show();

                /* visible = !visible;
                TransitionSet set = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    set = new TransitionSet()
                            .addTransition(new Scale(0.7f))
                            .addTransition(new Fade())
                            .setInterpolator(visible ? new LinearOutSlowInInterpolator() :
                                    new FastOutLinearInInterpolator());
                }

                TransitionManager.beginDelayedTransition(OfferViewData, set);
                OfferViewData.setVisibility(visible ? View.VISIBLE : View.GONE);*/
            }
        });


        offerStartList.clear();
        offerEndList.clear();
        offerTypeList.clear();
        offerBenefits.clear();
       // http://localhost:8080/UPass/api/service/general/viewOffers
                String URL3 = Constant.API +"/general/viewOffers?vendorId="+vendorId;

                StringRequest request3 = new StringRequest(Request.Method.GET, URL3, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("trwty", String.valueOf(response));

                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0) {
                                Log.d("trwtyte", String.valueOf(response));
                             /*   Toast toast = Toast.makeText(AfterSelectVendor.this, "No Values", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();*/
                            } else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String startDate = jsonObject.getString("startDate");
                                    String expiryDate = jsonObject.getString("expiryDate");
                                    String discount = jsonObject.getString("discount");
                                    String category = jsonObject.getString("category");
                                    String type = jsonObject.getString("type");
                                    Log.d("nfjfnjfr", String.valueOf(startDate));
                                    Log.d("nfjfnjfr1", String.valueOf(expiryDate));

                              Long timestamp10 = Long.parseLong(startDate);
                                Long timestamp20 = Long.parseLong(expiryDate);
                                try {
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                    Date netDate = (new Date(timestamp10));
                                    offerStart = sdf.format(netDate);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                    Date netDate = (new Date(timestamp20));
                                    offerEnd = sdf.format(netDate);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                    offerStartList.add(offerStart);
                                    offerEndList.add(offerEnd);
                                    offerTypeList.add(category);
                                    offerBenefits.add(discount);


                                }
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AfterSelectVendor.this);
                                offerRecycler.setLayoutManager(layoutManager);
                                RecyclerView.Adapter adapter = new OfferAdapter(getApplicationContext(), offerStartList,offerEndList,offerTypeList,offerBenefits);
                                offerRecycler.setAdapter(adapter);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("yreuie", String.valueOf(error));

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
                RequestQueue requestQueue3 = Volley.newRequestQueue(getApplicationContext());
                requestQueue3.add(request3);



        membershipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layoutShow){
                  memberCourseRecycler.setVisibility(View.VISIBLE);

                    Log.d("fhdufhew",vendorId+msubActivityId+mCalenderdate);
                    if(changedSubActivityId!=null){
                        msubActivityId=changedSubActivityId;
                    }
                    mTinyDb.putString(Constant.MEMBERSUBACTIVITYID,msubActivityId);
                    memberbookingType.clear();
                    memberbookingCost.clear();
                    Log.d("fhdufhew123",vendorId+msubActivityId+mCalenderdate);
                    String URL25 = Constant.API +"/slot/getSlotsByDate?vendorId="+vendorId+"&subActivityId="+msubActivityId+"&date="+todayDate+"&type=MEMBERSHIP";

                    StringRequest request25 = new StringRequest(Request.Method.GET, URL25, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("frewerfew", String.valueOf(response));

                     /*   TransitionManager.beginDelayedTransition(memberLayout);
                        visible = !visible;
                       memberLayout.setVisibility(View.VISIBLE);
                        memberLayout.setVisibility(visible ? View.VISIBLE : View.GONE);*/


                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                if (jsonArray.length() == 0) {
                                    Log.d("frewerfew", String.valueOf(response));
                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AfterSelectVendor.this);
                                    memberCourseRecycler.setLayoutManager(layoutManager);
                                    RecyclerView.Adapter adapter = new SelectMemberAdapter(getApplicationContext(), memberbookingType,memberbookingCost,memberbookingSlotId);
                                    memberCourseRecycler.setAdapter(adapter);
                    /*    Toast toast = Toast.makeText(AfterSelectVendor.this, "No Values", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();*/
                                } else {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        String  membershipType = jsonObject.getString("membershipType");
                                        String bookingCost = jsonObject.getString("bookingCost");
                                        String slotId=jsonObject.getString("slotId");

                                        memberbookingType.add(membershipType);
                                        memberbookingCost.add(bookingCost);
                                        memberbookingSlotId.add(slotId);

                                    }
                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AfterSelectVendor.this);
                                    memberCourseRecycler.setLayoutManager(layoutManager);
                                    RecyclerView.Adapter adapter = new SelectMemberAdapter(getApplicationContext(), memberbookingType,memberbookingCost,memberbookingSlotId);
                                    memberCourseRecycler.setAdapter(adapter);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("dscer", String.valueOf(error));


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
                                Button button=( Button)findViewById(R.id.TryAgain);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        recreate();
                                    }});

                            }
                        }
                    });
                    RequestQueue requestQueue25 = Volley.newRequestQueue(getApplicationContext());
                    requestQueue25.add(request25);
                    layoutShow=false;
                }else{
                    memberCourseRecycler.setVisibility(View.GONE);
                    layoutShow=true;
                }

            }
        });


        courseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layoutShow){
                    memberCourseRecycler.setVisibility(View.VISIBLE);

                    Log.d("fhdufhew",vendorId+msubActivityId+mCalenderdate);
                    if(changedSubActivityId!=null){
                        msubActivityId=changedSubActivityId;
                    }
                    mTinyDb.putString(Constant.COURSESUBACTIVITYID,msubActivityId);

                    mslotidsList.clear();
                    mslotStartTimeList.clear();
                    mslotEndTimeList.clear();
                    mmaxAllowedList.clear();
                    mslotReccurenceList.clear();
                    mcourseStartDateList.clear();
                    mcourseEndDateList.clear();
                    mcourseRegistrationEndDateList.clear();
                    mCourseCostList.clear();
                    Log.d("fhdufhew123",vendorId+msubActivityId+mCalenderdate);
                    String URL50 = Constant.API + "/slot/getSlotsByDate?vendorId=" + vendorId + "&subActivityId=" + msubActivityId + "&date=" + mCalenderdate + "&type=COURSE";

                    StringRequest request50 = new StringRequest(Request.Method.GET, URL50, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("tyrutyrwi", String.valueOf(response));

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                if (jsonArray.length() == 0) {
                                    Log.d("uytjuki", String.valueOf(response));
                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AfterSelectVendor.this);
                                    memberCourseRecycler.setLayoutManager(layoutManager);
                                    RecyclerView.Adapter adapter = new SelectCourseAdapter(getApplicationContext(), mslotidsList,mslotStartTimeList,
                                            mslotEndTimeList,mmaxAllowedList,mslotReccurenceList,mcourseStartDateList,mcourseEndDateList,
                                            mcourseRegistrationEndDateList,mCourseCostList);
                                    memberCourseRecycler.setAdapter(adapter);
                       /* Toast toast = Toast.makeText(AfterSelectVendor.this, "No Values", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();*/
                                } else {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        String slotId = jsonObject.getString("slotId");
                                        String slotStartTime = jsonObject.getString("slotStartTime");
                                        String slotEndTime = jsonObject.getString("slotEndTime");
                                        String maxAllowed = jsonObject.getString("maxAllowed");
                                        String slotReccurence = jsonObject.getString("slotReccurence");
                                        String courseStartDate = jsonObject.getString("courseStartDate");
                                        String courseEndDate = jsonObject.getString("courseEndDate");
                                        String courseRegistrationEndDate = jsonObject.getString("courseRegistrationEndDate");
                                        String courseCost = jsonObject.getString("bookingCost");

                           mslotidsList.add(slotId);
                            mslotStartTimeList.add(slotStartTime);
                            mslotEndTimeList.add(slotEndTime);
                            mmaxAllowedList.add(maxAllowed);
                            mslotReccurenceList.add(slotReccurence);
                            mcourseStartDateList.add(courseStartDate);
                            mcourseEndDateList.add(courseEndDate);
                            mcourseRegistrationEndDateList.add(courseRegistrationEndDate);
                            mCourseCostList.add(courseCost);




                                    }

                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AfterSelectVendor.this);
                                    memberCourseRecycler.setLayoutManager(layoutManager);
                                    RecyclerView.Adapter adapter = new SelectCourseAdapter(getApplicationContext(), mslotidsList,mslotStartTimeList,
                                            mslotEndTimeList,mmaxAllowedList,mslotReccurenceList,mcourseStartDateList,mcourseEndDateList,
                                            mcourseRegistrationEndDateList,mCourseCostList);
                                    memberCourseRecycler.setAdapter(adapter);

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("dscer", String.valueOf(error));

                            if (error instanceof NetworkError) {
                                noInternetLayout.setVisibility(View.VISIBLE);
                                allViewLayout.setVisibility(View.GONE);
                                Button button = (Button) findViewById(R.id.TryAgain);
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

                            } else if (error instanceof NoConnectionError) {
                                Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof TimeoutError) {
                                noInternetLayout.setVisibility(View.VISIBLE);
                                allViewLayout.setVisibility(View.GONE);
                                Button button = (Button) findViewById(R.id.TryAgain);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        recreate();
                                    }
                                });

                            }
                        }
                    });
                    RequestQueue requestQueue50 = Volley.newRequestQueue(getApplicationContext());
                    requestQueue50.add(request50);


                    layoutShow=false;
                }else{
                    memberCourseRecycler.setVisibility(View.GONE);
                    layoutShow=true;
                }

            }
        });





        FragmentManager myFragmentManager = getFragmentManager();
       SupportMapFragment mapFragment = ((SupportMapFragment)this.getSupportFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);
         GREENBELT1 = new LatLng(lat, longi);
        getDirection=(Button)findViewById(R.id.getDirection);
        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cd.isConnected()) {

                    if (!gMap.isMyLocationEnabled())
                        if (ActivityCompat.checkSelfPermission(AfterSelectVendor.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(AfterSelectVendor.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            ActivityCompat.requestPermissions(AfterSelectVendor.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                            ActivityCompat.requestPermissions(AfterSelectVendor.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                            ActivityCompat.requestPermissions(AfterSelectVendor.this, new String[]{Manifest.permission.INTERNET}, 1);

                        } else {
                            String format = "geo:0,0?q=" + lat + "," + longi + "( COEUZ)";

                            Uri uri = Uri.parse(format);


                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);


                        }
                }else{
                    Toast toast = Toast.makeText(AfterSelectVendor.this,"Check Your Internet Connection", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

            }


        });

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(cd.isConnected()) {
            gMap = googleMap;
            if (!gMap.isMyLocationEnabled())
                if (ActivityCompat.checkSelfPermission(AfterSelectVendor.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AfterSelectVendor.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    ActivityCompat.requestPermissions(AfterSelectVendor.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    ActivityCompat.requestPermissions(AfterSelectVendor.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    ActivityCompat.requestPermissions(AfterSelectVendor.this, new String[]{android.Manifest.permission.INTERNET}, 1);

                } else {


                    Marker davao = gMap.addMarker(new MarkerOptions().position(GREENBELT1).title("COEUZ").snippet("Technology"));

                    // zoom in the camera to Greenbelt 1
                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(GREENBELT1, 15));

                    // animate the zoom process
                    gMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                }
        }else{
            Toast toast = Toast.makeText(AfterSelectVendor.this,"Check Your Internet Connection", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

    }
    private  void changingSubActivityTypes(String changedSubActivityId){



    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

 @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(String value) {
        Log.d("dhewuirw",value);

    }
}