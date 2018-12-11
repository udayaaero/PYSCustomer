package com.coeuz.pyscustomer;

import android.Manifest;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.coeuz.pyscustomer.AdapterClass.AfterSelectVendorAmenitiesAdapter;
import com.coeuz.pyscustomer.AdapterClass.ConsecutiveDateBookingAdapter;
import com.coeuz.pyscustomer.AdapterClass.DateBookingAdapter;
import com.coeuz.pyscustomer.AdapterClass.GalleryAdapter;
import com.coeuz.pyscustomer.AdapterClass.NearVendorSLotsAdapter;
import com.coeuz.pyscustomer.AdapterClass.SelectCourseAdapter;
import com.coeuz.pyscustomer.AdapterClass.SelectMemberAdapter;
import com.coeuz.pyscustomer.AdapterClass.OfferAdapter;
import com.coeuz.pyscustomer.AdapterClass.OtherServiceAdapter;
import com.coeuz.pyscustomer.ModelClass.SlotModel;
import com.coeuz.pyscustomer.ModelClass.SubActivityModel;
import com.coeuz.pyscustomer.Requiredclass.ConnectionDetector;

import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.MyBounceInterpolator;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;
import com.coeuz.pyscustomer.Requiredclass.VolleySingleton;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
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
import java.util.Locale;
import java.util.Map;


import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;


public class AfterSelectVendor extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener,OnItemClick  {


    private Integer dayName;
    OnItemClick listener;
    private  String changedSubActivityId;
    BarChart barChart;
    ArrayList<BarEntry> barValueList = new ArrayList<>();
    ArrayList<Integer> mCountList = new ArrayList<>();
    ArrayList<String> mlabelList = new ArrayList<>();
    ArrayList<JSONArray> firstLableList = new ArrayList<>();
    JSONArray jsonArray1;
    String label,lables;
    HashMap<String,JSONArray> totalGraphValue = new HashMap<>();
    private  boolean firstRun=true;
    private String vendorContact;

    private TinyDB mTinyDb;
    Bundle bundle;
    private String vendorId;
    private String msubActivityId;
    private String mBookingType;
    private String mcbookingType;
    private ArrayList<String> slotTypeList=new ArrayList<>();
    ArrayList<Bitmap> bitmapsLists = new ArrayList<>();

    private GoogleMap gMap;
    ConnectionDetector cd;
    private static LatLng GREENBELT1;
    Double lat;
    Double longi;
    private RelativeLayout amenitiesLayout;
    LinearLayout mapVisibleLayout;
    RecyclerView recyclerViewAmenities,recyclerGallery;
    private ArrayList<String> amenitiesList=new ArrayList<>();
    private ArrayList<String> amenitiesIdList=new ArrayList<>();
    private HashMap<String,Bitmap> amenitiesImageList=new HashMap<>();
  //  private ArrayList<String> amenitiesImagesList=new ArrayList<>();
    private TextView nArea,nCity,nMobile;
    private TextView nFrom,nTo;


    private String offerStart,offerEnd;
    private HorizontalScrollView linearLayout1;
    private LinearLayout noInternetLayout;
    private ScrollView allViewLayout;

     boolean visible;

    ArrayList<String> offerStartList=new ArrayList<>();
    ArrayList<String> offerEndList=new ArrayList<>();
    ArrayList<String> offerTypeList=new ArrayList<>();
    ArrayList<String> offerBenefits=new ArrayList<>();


    ArrayList<String> motherServiceList=new ArrayList<>();
    ArrayList<String> otherServiceid=new ArrayList<>();
    RecyclerView mRecyclerView;
private RelativeLayout galleryLayout;
    private String  mCalenderdate,todayDate;
    private ArrayList<SlotModel> slotModel;
    private ArrayList<String> consecutiveslotModel=new ArrayList<>();
    private ArrayList<String> consecutiveslotModelCost=new ArrayList<>();
    private ArrayList<String> consecutiveslotModelTiming=new ArrayList<>();
    private ArrayList<String> sendconsecutiveslotModelTiming=new ArrayList<>();
    private ArrayList<String> consecutivePersonModel=new ArrayList<>();
    private ConsecutiveDateBookingAdapter consecutiveSlotDateAdapter;
    private DateBookingAdapter SlotDateAdapter;
    private RecyclerView RecyclerDateSlot;
    private  Integer maxAllowed,slotId,bookingSlotCost;
    private String slotStartTime,slotEndTime,sendStartTime,slotReccurence;
    private Integer personCount;
    private String ConsecpersonCount;
    JSONArray ConsecutiveTimingArray=new JSONArray();

    public Animation myAnim;
    private  ImageView mofferDetails;
    Dialog offerDialog;
    ImageView cancel;
    LinearLayout membersLayout;
    RecyclerView offerRecycler,memberCourseRecycler,nearVendorRecycler;
    private LinearLayout courseBtn;
    private CardView membershipBtn;
    ArrayList<String> memberbookingType = new ArrayList<>();
    ArrayList<String> memberbookingCost = new ArrayList<>();
    ArrayList<String> memberbookingSlotId = new ArrayList<>();
    ArrayList<String> memberbookingPerson = new ArrayList<>();
private Button cAllday,cweekday,cweekend;

    ArrayList<String> mslotidsList=new ArrayList<>();
    ArrayList<String> mslotStartTimeList=new ArrayList<>();
    ArrayList<String> mslotEndTimeList=new ArrayList<>();
    ArrayList<String> mmaxAllowedList=new ArrayList<>();
    ArrayList<String> mslotReccurenceList=new ArrayList<>();
    ArrayList<String> mcourseStartDateList=new ArrayList<>();
    ArrayList<String> mcourseEndDateList=new ArrayList<>();
    ArrayList<String> mcourseRegistrationEndDateList=new ArrayList<>();
    ArrayList<String> mCourseCostList=new ArrayList<>();
    ArrayList<String> mCourseDurationList=new ArrayList<>();
    ArrayList<String> mCoursePersonList=new ArrayList<>();

    boolean layoutShow=true;
    private RelativeLayout recommendedLayout;
    private NearVendorSLotsAdapter nearVendorSLotsAdapter;
    private ArrayList<SubActivityModel> subActivityModels;
    private ArrayList<String> slotRecurrence=new ArrayList<>();
    private LinearLayout calendarViewLayout,noCourseData;
    private String courseTypes,backGroundImage;
    private ImageView vendorBackgroundImage;
    private LinearLayout noSlotAvailableLayouts;
    int mOffset=0;
    int mLimit=5;
    int temp=5;
    String vvendorName;
    ViewGroup progressView;
    boolean isProgressShowing = false;
    RelativeLayout mainLayout;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_select_vendor);
        showProgressingView();

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        Toolbar toolbar = findViewById(R.id.toolbar1); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar
        getSupportActionBar().setDisplayShowTitleEnabled(false); // hide the current title from the Toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

//        mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        mTinyDb=new TinyDB(getApplicationContext());
        String latString = mTinyDb.getString("latttt");
        String longSting = mTinyDb.getString("longggg");
        msubActivityId=mTinyDb.getString("activityId");
        vvendorName = mTinyDb.getString(Constant.VENDORNAME);
        String vvendorArea = mTinyDb.getString(Constant.VENDORAREA);
        backGroundImage=mTinyDb.getString(Constant.BACKGROUNDIMAGE);
        vendorBackgroundImage=findViewById(R.id.vendorBackImage);
        if(!backGroundImage.equals("")){
            if(!backGroundImage.equals("null")){
            byte[] images= Base64.decode(backGroundImage,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(images,0,images.length);
            vendorBackgroundImage.setImageBitmap(bitmap);}
        }

        if(latString !=null &&!latString.isEmpty()){

            lat= Double.valueOf(latString);
            longi= Double.valueOf(longSting);
        }

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        todayDate = format.format(today);



        cd=new ConnectionDetector(getApplicationContext());
        recyclerGallery= findViewById(R.id.galleryrecycle);
        recyclerViewAmenities= findViewById(R.id.amenitiesrecycle);
      /*  recommendedRecycler=(RecyclerView)findViewById(R.id.recommendedRecycle);
        recommendedLayout=(RelativeLayout) findViewById(R.id.RecommendedLayout);
        recommendedRecycler.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false));
        recommendedRecycler.setHasFixedSize(true);
       // recommendedRecycler.setAdapter(nearYouAdapter);*/
        amenitiesLayout= findViewById(R.id.amenities);
        calendarViewLayout= findViewById(R.id.calenderViewLayout);
        mapVisibleLayout= findViewById(R.id.mapVisible);
        RecyclerDateSlot = findViewById(R.id.RecyclerDateBooking);
        mapVisibleLayout.setVisibility(View.GONE);
        galleryLayout=findViewById(R.id.gallery);
        noSlotAvailableLayouts=findViewById(R.id.noSlotAvailableLayout);

        mofferDetails= findViewById(R.id.offerDetails);
        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        myAnim.setRepeatCount(Animation.INFINITE);
        mofferDetails.startAnimation(myAnim);


        LinearLayout mapView = findViewById(R.id.mapview);
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

        noInternetLayout = findViewById(R.id.NoInternetLayout);
        allViewLayout = findViewById(R.id.allViewlayout);


        linearLayout1= findViewById(R.id.l1);
         barChart = findViewById(R.id.barchart);



        TextView nvendorName = findViewById(R.id.NameOfVendor);
        TextView nvendorArea = findViewById(R.id.AdressOfVendor);
        nArea= findViewById(R.id.mArea);
        nCity= findViewById(R.id.mCity);
        nMobile= findViewById(R.id.mMobile);
       // nEmail= findViewById(R.id.mEmails);
        nFrom= findViewById(R.id.from);
        nTo= findViewById(R.id.to);
        mRecyclerView= findViewById(R.id.recycler);
        membershipBtn= findViewById(R.id.membershibBtn);
        courseBtn= findViewById(R.id.CourseBtn);
        cAllday= findViewById(R.id.allday);
        cweekday= findViewById(R.id.weekday);
        cweekend= findViewById(R.id.weekEnd);

        memberCourseRecycler= findViewById(R.id.recycle_memberCourse);
        membersLayout= findViewById(R.id.memberslayout);
        noCourseData= findViewById(R.id.noCourseData);

        nvendorName.setText(vvendorName);
        nvendorArea.setText(vvendorArea);

        recommendedLayout= findViewById(R.id.RecommendedLayout);
        nearVendorRecycler= findViewById(R.id.recommendedRecycle);
        subActivityModels = new ArrayList<>();
        nearVendorSLotsAdapter = new NearVendorSLotsAdapter(AfterSelectVendor.this,subActivityModels);
        nearVendorRecycler.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false));
        nearVendorRecycler.setHasFixedSize(true);
        nearVendorRecycler.setAdapter(nearVendorSLotsAdapter);






        bundle=getIntent().getExtras();
        if (bundle != null) {
           // String vendorName = bundle.getString("mVendorName");
            vendorId= String.valueOf(bundle.getInt("mVendorIds"));
           // String area = bundle.getString("mArea");
           // this.setTitle(vendorName);
        }
        mTinyDb.putString(Constant.VENDORID,vendorId);

        mBookingType=mTinyDb.getString(Constant.BOOKINGTYPE);
        mcbookingType=mTinyDb.getString(Constant.MCBOOKINGTYPE);

        membersLayout.setVisibility(View.GONE);
        if(mcbookingType.equals("MEMBERSHIP")){
            mcbookingType="MEMBERSHIP";
            membershipBtn.setVisibility(View.VISIBLE);
            courseBtn.setVisibility(View.GONE);

        }else if(mcbookingType.equals("COURSE")){
            noSlotAvailableLayouts.setVisibility(View.GONE);
            mcbookingType="COURSE";
            courseBtn.setVisibility(View.VISIBLE);
          //  courseDetails();
            membershipBtn.setVisibility(View.GONE);
            calendarViewLayout.setVisibility(View.GONE);
            courseTypes="ALL_DAYS";
        }

        getGalleryImage();


        String URL15 = Constant.API +"/general/vendorOtherServices?vendorId="+vendorId;

        StringRequest request15 = new StringRequest(Request.Method.GET, URL15, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String subActivityId=jsonObject.getString("subActivityId");
                        String subActivityType=jsonObject.getString("subActivityType");
                        motherServiceList.add(subActivityType);
                        otherServiceid.add(subActivityId);
                    }


                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(AfterSelectVendor.this,3);
                    mRecyclerView.setLayoutManager(layoutManager);
                    RecyclerView.Adapter adapter = new OtherServiceAdapter(getApplicationContext(),otherServiceid, motherServiceList, new OnItemClick() {
                        @Override
                        public void onClick(final String value) {

                            changedSubActivityId=value;
                            if(changedSubActivityId!=null){
                           // changingSubActivityTypes(changedSubActivityId);
                                membershipBtn.setVisibility(View.GONE);
                                recommendedLayout.setVisibility(View.GONE);
                                membersLayout.setVisibility(View.GONE);

                                 mOffset=0;
                                 mLimit=5;
                                 temp=5;

                            slotTypeList.clear();
                            String URL10 = Constant.API +"/user/getSlotTypesBySubActivityIdAndVendorId?subActivityId="+changedSubActivityId+"&vendorId="+vendorId;

                            StringRequest request10 = new StringRequest(Request.Method.GET, URL10, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    noSlotAvailableLayouts.setVisibility(View.GONE);
                                    mBookingType="";
                                    mcbookingType="";

                                    try {
                                        JSONArray jsonArray = new JSONArray(response);

                                        if (jsonArray.length() == 0) {

                                            calendarViewLayout.setVisibility(View.VISIBLE);
                                            membersLayout.setVisibility(View.GONE);
                                            courseBtn.setVisibility(View.GONE);
                                            noSlotAvailableLayouts.setVisibility(View.GONE);
                                            RecyclerDateSlot.setVisibility(View.GONE);
                       /* Toast toast = Toast.makeText(AfterSelectVendor.this, "No Values", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();*/
                                        } else {
                                            RecyclerDateSlot.setVisibility(View.VISIBLE);
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                String slotTypes = String.valueOf(jsonArray.get(i));


                                                slotTypeList.add(slotTypes);
                                            }
                                            if(slotTypeList.contains("PRE_DEFINED_SLOT")){
                                                mBookingType="PRE_DEFINED_SLOT";
                                                mTinyDb.putString(Constant.BOOKINGTYPE,mBookingType);
                                                membersLayout.setVisibility(View.GONE);
                                                courseBtn.setVisibility(View.GONE);
                                                noCourseData.setVisibility(View.GONE);
                                                calendarViewLayout.setVisibility(View.VISIBLE);
                                            }else if(slotTypeList.contains("CONSECUTIVE")){
                                                mBookingType="CONSECUTIVE";
                                                membersLayout.setVisibility(View.GONE);
                                                courseBtn.setVisibility(View.GONE);
                                                noCourseData.setVisibility(View.GONE);
                                                membershipBtn.setVisibility(View.GONE);
                                                calendarViewLayout.setVisibility(View.VISIBLE);
                                                mTinyDb.putString(Constant.BOOKINGTYPE,mBookingType);
                                            }
                                            if(slotTypeList.contains("MEMBERSHIP")){
                                                membersLayout.setVisibility(View.GONE);
                                                mcbookingType="MEMBERSHIP";
                                                membershipBtn.setVisibility(View.VISIBLE);
                                                membershipService(vendorId,changedSubActivityId,mCalenderdate);
                                                membersLayout.setVisibility(View.GONE);
                                                courseBtn.setVisibility(View.GONE);
                                                noCourseData.setVisibility(View.GONE);
                                                calendarViewLayout.setVisibility(View.VISIBLE);

                                            }else if(slotTypeList.contains("COURSE")){
                                                noSlotAvailableLayouts.setVisibility(View.GONE);
                                                mcbookingType="COURSE";
                                                membersLayout.setVisibility(View.GONE);
                                                courseBtn.setVisibility(View.VISIBLE);
                                                courseTypes="ALL_DAYS";
                                                courseDetails();
                                                membershipBtn.setVisibility(View.GONE);
                                                calendarViewLayout.setVisibility(View.GONE);
                                                courseTypes="ALL_DAYS";
                                            }
                                        }


                                        if(mBookingType.equals("")&&mcbookingType.equals("")){
                                            noSlotAvailableLayouts.setVisibility(View.VISIBLE);
                                        }
    if(mBookingType.equals("PRE_DEFINED_SLOT")){
        mTinyDb.putString(Constant.PREDEFINEDSUBACTIVITYID,value);
                                        slotModel = new ArrayList<>();
                                        SlotDateAdapter = new DateBookingAdapter(getApplicationContext(), slotModel);
                                        RecyclerDateSlot.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                                        RecyclerDateSlot.setHasFixedSize(true);
                                        RecyclerDateSlot.setAdapter(SlotDateAdapter);

                                        slotModel.clear();


                                        String URL = Constant.API + "/slot/getSlotsByDate?vendorId=" + vendorId + "&subActivityId=" + value + "&date=" + mCalenderdate + "&type=" + mBookingType;

                                        StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {

                                                try {
                                                    JSONArray jsonArray = new JSONArray(response);
                                                    if (jsonArray.length() == 0) {
                                                        noSlotAvailableLayouts.setVisibility(View.VISIBLE);
                                                         getNearVendorSlots();
                                                    } else {
                                                        firstRun=true;
                                                        for (int i = 0; i < jsonArray.length(); i++) {
                                                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                                                            slotId = jsonObject.getInt("slotId");
                                                            slotStartTime = jsonObject.getString("slotStartTime");
                                                            slotEndTime = jsonObject.getString("slotEndTime");
                                                            personCount = jsonObject.getInt("personCount");

                                                            try {
                                                                final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                                                                final Date dateObj = sdf.parse(slotStartTime );
                                                                String timein12Format=new SimpleDateFormat("hh:mmaa").format(dateObj);
                                                                slotStartTime=String.valueOf(timein12Format);
                                                                slotStartTime = slotStartTime.replace(".", "");
                                                            } catch (final ParseException e) {
                                                                e.printStackTrace();
                                                            }
                                                            try {
                                                                final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                                                                final Date dateObj = sdf.parse(slotEndTime );
                                                                String timein12Format=new SimpleDateFormat("hh:mmaa").format(dateObj);
                                                                slotEndTime=String.valueOf(timein12Format);
                                                                slotEndTime = slotEndTime.replace(".", "");
                                                            } catch (final ParseException e) {
                                                                e.printStackTrace();
                                                            }

                                                            maxAllowed = jsonObject.getInt("maxAllowed");
                                                            bookingSlotCost = jsonObject.getInt("bookingCost");
                                                            slotReccurence=jsonObject.getString("slotReccurence");

                                                            if(slotReccurence.equalsIgnoreCase("WEEKDAYS")){
                                                                if(dayName==1||dayName==2||dayName==3||dayName==4||dayName==5){

                                                                    firstRun=false;
                                                                    slotModel.add(new SlotModel(slotId, slotStartTime, slotEndTime, maxAllowed, bookingSlotCost, personCount));


                                                                }
                                                            }else if(slotReccurence.equalsIgnoreCase("WEEKEND")){
                                                                if(dayName==0||dayName==6){
                                                                    firstRun=false;

                                                                    slotModel.add(new SlotModel(slotId, slotStartTime, slotEndTime, maxAllowed, bookingSlotCost, personCount));}

                                                            }else{

                                                                firstRun=false;
                                                                slotModel.add(new SlotModel(slotId, slotStartTime, slotEndTime, maxAllowed, bookingSlotCost,personCount));

                                                            }
                                                        }
                                                        if(firstRun){
                                                            noSlotAvailableLayouts.setVisibility(View.VISIBLE);
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

                                                } else if (error instanceof ParseError) {
                                                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                                                } else if (error instanceof TimeoutError) {
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
        VolleySingleton.getInstance(AfterSelectVendor.this).addToRequestQueue(request1);}
    else if(mBookingType.equals("CONSECUTIVE")){

        mTinyDb.putString(Constant.PREDEFINEDSUBACTIVITYID,value);
        consecutiveslotModelCost.clear();
        consecutiveslotModel.clear();
        consecutivePersonModel.clear();
        consecutiveslotModelTiming.clear();
        sendconsecutiveslotModelTiming.clear();


        String URL = Constant.API + "/slot/getSlotsByDate?vendorId=" + vendorId + "&subActivityId=" + value + "&date=" + mCalenderdate + "&type=" + mBookingType;

        StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() == 0) {
                      noSlotAvailableLayouts.setVisibility(View.VISIBLE);
                      getNearVendorSlots();
                    } else {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            slotId = jsonObject.getInt("slotId");
                            maxAllowed = jsonObject.getInt("maxAllowed");
                            bookingSlotCost = jsonObject.getInt("bookingCost");
                            ConsecpersonCount = jsonObject.getString("personCount");

                            ConsecutiveTimingArray=jsonObject.getJSONArray("timings");

                        }
                        for (int i = 0; i < ConsecutiveTimingArray.length(); i++) {
                            JSONObject jsonObject = ConsecutiveTimingArray.getJSONObject(i);
                            slotStartTime = jsonObject.getString("startTime");
                            sendStartTime=jsonObject.getString("startTime");
                            slotEndTime = jsonObject.getString("endTime");
                        }


                        try {
                            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",Locale.getDefault());
                            final Date dateObj = sdf.parse(slotStartTime );
                            String timein12Format=new SimpleDateFormat("hh:mmaa",Locale.getDefault()).format(dateObj);

                            slotStartTime=String.valueOf(timein12Format);
                            slotStartTime = slotStartTime.replace(".", "");
                        } catch (final ParseException e) {
                            e.printStackTrace();
                        }


                        consecutiveslotModel.add(String.valueOf(slotId));
                        consecutiveslotModelCost.add(String.valueOf(bookingSlotCost));
                        consecutiveslotModelTiming.add( slotStartTime);
                        sendconsecutiveslotModelTiming.add(sendStartTime);
                        consecutivePersonModel.add(ConsecpersonCount);




                    }
                    consecutiveSlotDateAdapter = new ConsecutiveDateBookingAdapter(getApplicationContext(), consecutiveslotModel,consecutiveslotModelCost,consecutiveslotModelTiming,sendconsecutiveslotModelTiming,consecutivePersonModel);
                    RecyclerDateSlot.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
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
        VolleySingleton.getInstance(AfterSelectVendor.this).addToRequestQueue(request1);

    }





                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {


                                    if (error instanceof NetworkError) {

                                    } else if (error instanceof ServerError) {


                                    } else if (error instanceof ParseError) {
                                        Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                                    } else if (error instanceof TimeoutError) {


                                    }
                                }
                            });
                                VolleySingleton.getInstance(AfterSelectVendor.this).addToRequestQueue(request10);

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


                if (error instanceof NetworkError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button= findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});
                } else if (error instanceof ServerError) {


                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                }  else if (error instanceof TimeoutError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button= findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});

                }
            }
        });
        VolleySingleton.getInstance(AfterSelectVendor.this).addToRequestQueue(request15);


       /* slotTypeList.clear();
        String URL10 = Constant.API +"/user/getSlotTypesBySubActivityIdAndVendorId?subActivityId="+changedSubActivityId+"&vendorId="+vendorId;

        StringRequest request10 = new StringRequest(Request.Method.GET, URL10, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray jsonArray = new JSONArray(response);

                    if (jsonArray.length() == 0) {

                       *//* Toast toast = Toast.makeText(AfterSelectVendor.this, "No Values", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();*//*
                    } else {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String slotTypes = String.valueOf(jsonArray.get(i));


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


                if (error instanceof NetworkError) {

                } else if (error instanceof ServerError) {


                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {


                }
            }
        });
        RequestQueue requestQueue10 = Volley.newRequestQueue(getApplicationContext());
        requestQueue10.add(request10);

*/



        amenitiesList.clear();
        amenitiesIdList.clear();
        amenitiesImageList.clear();
        String URL = Constant.API +"/general/getVendorByVendorId?vendorId="+vendorId;

        StringRequest requests = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.length() == 0) {

                       /* Toast toast = Toast.makeText(AfterSelectVendor.this, "No Values", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();*/
                    } else {

                            String city = jsonObject.getString("city");
                            String area = jsonObject.getString("area");
                            String latitude = jsonObject.getString("latitude");
                            String longitude = jsonObject.getString("longitude");
                           // String availableAmenities = jsonObject.getString("availableAmenities");
                            String email = jsonObject.getString("email");
                              vendorContact = jsonObject.getString("contact1");
                            String openingTime = jsonObject.getString("openingTime");
                            String closingTime = jsonObject.getString("closingTime");

                        lat= Double.valueOf(latitude);
                        longi= Double.valueOf(longitude);
                   /*     try {
                            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",Locale.getDefault());
                            final Date dateObj = sdf.parse(openingTime );
                            String timein12Format=new SimpleDateFormat("hh:mmaa",Locale.getDefault()).format(dateObj);

                            openingTime=String.valueOf(timein12Format);
                            openingTime = openingTime.replace(".", "");
                        } catch (final ParseException e) {
                            e.printStackTrace();
                        }
                        try {
                            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",Locale.getDefault());
                            final Date dateObj = sdf.parse(closingTime );
                            String timein12Format=new SimpleDateFormat("hh:mmaa",Locale.getDefault()).format(dateObj);

                            closingTime=String.valueOf(timein12Format);
                            closingTime = closingTime.replace(".", "");
                        } catch (final ParseException e) {
                            e.printStackTrace();
                        }*/

                        nFrom.setText(openingTime);
                        nTo.setText(closingTime);

                        nArea.setText(area);
                        nCity.setText(city);
                        nMobile.setText(vendorContact);
                      /*  if(email.equalsIgnoreCase("null")){
                            nEmail.setVisibility(View.GONE);
                        }else
                        { nEmail.setText(email);}*/
                            JSONArray jsonArray=jsonObject.getJSONArray("amenities");

                        if (jsonArray.length() == 0) {

                       /* Toast toast = Toast.makeText(AfterSelectVendor.this, "No Values", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();*/
                        }else{
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            final String amenityId = jsonObject1.getString("amenityId");

                            String amenityType = jsonObject1.getString("amenityType");
                          //  String image1 = jsonObject1.getString("image");
                            //String status = jsonObject1.getString("status");

                            String status=jsonObject1.getString("status");
                            if(status.equalsIgnoreCase("true")) {
                                amenitiesIdList.add(amenityId);
                                amenitiesList.add(amenityType);

                                if (amenitiesIdList.size() == 0) {
                                    amenitiesLayout.setVisibility(View.GONE);
                                }
                                amenitiesLayout.setVisibility(View.VISIBLE);

                                String Url =Constant.API+"/rest/photos/getAmenityIconImagesByAmenityId?amenityId=" + amenityId;
                                StringRequest stringRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        String imagess = String.valueOf(response);

                                        byte[] images = Base64.decode(imagess, Base64.DEFAULT);
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(images, 0, images.length);

                                        amenitiesImageList.put(amenityId,bitmap);


                                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AfterSelectVendor.this, LinearLayoutManager.HORIZONTAL, false);
                                        recyclerViewAmenities.setLayoutManager(layoutManager);
                                        RecyclerView.Adapter adapter = new AfterSelectVendorAmenitiesAdapter(getApplicationContext(),amenitiesIdList, amenitiesList,amenitiesImageList);
                                        recyclerViewAmenities.setAdapter(adapter);}


                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {


                                    }
                                }) {
                                    @Override
                                    public Map<String, String> getHeaders() {
                                        HashMap<String, String> headers = new HashMap<>();
                                        String Tokens = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJkMDk4ZTNjMC1iMTRmLTRlNDItODljYS01Y2FmYTM2MDhjODYiLCJpYXQiOjE1NDAzOTQ0NTQsInN1YiI6ImtrQGdtYWlsLmNvbSIsImlzcyI6ImFuYW5kcGxheXMiLCJST0xFX1BST1ZJREVSIjoiQXZhaWxhYmxlIiwiUk9MRV9WRU5ET1IiOiJBdmFpbGFibGUiLCJleHAiOjE1NDE4NjU2ODN9.Z82ekQlctjR-sUHCh98fHhYO4hQZjyWX0O7HM7I8zNc";

                                        headers.put("X-Auth-Token", String.valueOf(Tokens).replaceAll("\"", ""));

                                        return headers;
                                    }
                                };


                                VolleySingleton.getInstance(AfterSelectVendor.this).addToRequestQueue(stringRequest);


                            }
                        }



                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



                if (error instanceof NetworkError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button= findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});
                } else if (error instanceof ServerError) {


                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                }  else if (error instanceof TimeoutError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button= findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});

                }
            }
        });

        VolleySingleton.getInstance(AfterSelectVendor.this).addToRequestQueue(requests);



        Calendar start = Calendar.getInstance();
        start.add(Calendar.MONTH, -1);
        Date date=java.util.Calendar.getInstance().getTime();

/* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DAY_OF_MONTH, 6);
        final HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)

                .startDate(date)
                .endDate(endDate.getTime())
                .datesNumberOnScreen(5)// Number of Dates cells shown on screen (Recommended 5)
                .dayNameFormat("EEE")      // WeekDay text format
                .textSizeDayNumber(15)
                .textSizeDayName(9)
                // Month format.monthFormat("KKK")
                .dayNumberFormat("dd")    // DateBooking format
                .selectedDateBackground(Drawable.createFromPath("#16711936"))
                .showDayName(true)      // Show or Hide dayName text
                .showMonthName(true)      // Show or Hide month text
                .textColor(Color.BLACK, Color.GREEN)    // Text color for none selected Dates, Text color for selected DateBooking.
                // Background Drawable of the selected date cell.
                .selectorColor(Color.GREEN)   // Color of the selection indicator bar (default to colorAccent).
                // DateBooking to be seleceted at start (default to Today)

                .build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {

                 dayName = date.getDay();




                          /*  Calendar endDate = Calendar.getInstance();
                            endDate.setTimeInMillis(endDateInMillSecs);

                            Calendar cl = Calendar.getInstance();
                            cl.setTimeInMillis(startDateInMillSecs);*/
                Long timeStamp200 = date.getTime();
                String S1 = String.valueOf(timeStamp200);

                Long timestamp1 = Long.parseLong(S1);
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
                    Date netDate = (new Date(timestamp1));
                    String CalenderDate1 = sdf.format(netDate);
                    mCalenderdate = CalenderDate1;

                } catch (Exception e) {
                    e.printStackTrace();
                }

                mTinyDb.putString(Constant.CALENDERDATE,mCalenderdate);
                membershipBtn.setVisibility(View.GONE);

                if(changedSubActivityId != null){
                    msubActivityId=changedSubActivityId;
                    }
                    mTinyDb.putString(Constant.PREDEFINEDSUBACTIVITYID,msubActivityId);
                recommendedLayout.setVisibility(View.GONE);
                slotTypeList.clear();
                String URL10 = Constant.API +"/user/getSlotTypesBySubActivityIdAndVendorId?subActivityId="+msubActivityId+"&vendorId="+vendorId;

                StringRequest request10 = new StringRequest(Request.Method.GET, URL10, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        noSlotAvailableLayouts.setVisibility(View.GONE);

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            if (jsonArray.length() == 0) {

                       /* Toast toast = Toast.makeText(AfterSelectVendor.this, "No Values", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();*/
                            } else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    String slotTypes = String.valueOf(jsonArray.get(i));


                                    slotTypeList.add(slotTypes);
                                }
                                if(slotTypeList.contains("PRE_DEFINED_SLOT")){
                                    mBookingType="PRE_DEFINED_SLOT";
                                    mTinyDb.putString(Constant.BOOKINGTYPE,mBookingType);
                                    membersLayout.setVisibility(View.GONE);
                                    courseBtn.setVisibility(View.GONE);
                                    noCourseData.setVisibility(View.GONE);
                                    calendarViewLayout.setVisibility(View.VISIBLE);
                                }else if(slotTypeList.contains("CONSECUTIVE")){
                                    mBookingType="CONSECUTIVE";
                                    membersLayout.setVisibility(View.GONE);
                                    courseBtn.setVisibility(View.GONE);
                                    noCourseData.setVisibility(View.GONE);
                                    membershipBtn.setVisibility(View.GONE);
                                    calendarViewLayout.setVisibility(View.VISIBLE);
                                    mTinyDb.putString(Constant.BOOKINGTYPE,mBookingType);
                                }
                                if(slotTypeList.contains("MEMBERSHIP")){
                                    membersLayout.setVisibility(View.GONE);
                                    mcbookingType="MEMBERSHIP";
                                    membershipBtn.setVisibility(View.VISIBLE);
                                    membershipService(vendorId,msubActivityId,mCalenderdate);
                                    membersLayout.setVisibility(View.GONE);
                                    courseBtn.setVisibility(View.GONE);
                                    noCourseData.setVisibility(View.GONE);
                                    calendarViewLayout.setVisibility(View.VISIBLE);

                                }else if(slotTypeList.contains("COURSE")){
                                    noSlotAvailableLayouts.setVisibility(View.GONE);
                                    mcbookingType="COURSE";
                                    membersLayout.setVisibility(View.GONE);
                                    courseBtn.setVisibility(View.VISIBLE);
                                    courseTypes="ALL_DAYS";
                                    courseDetails();
                                    membershipBtn.setVisibility(View.GONE);
                                    calendarViewLayout.setVisibility(View.GONE);
                                    courseTypes="ALL_DAYS";
                                }
                            }


                            if(mBookingType.equals("")&&mcbookingType.equals("")){

                                noSlotAvailableLayouts.setVisibility(View.VISIBLE);
                            }



                if(mBookingType.equals("PRE_DEFINED_SLOT")){
                slotModel = new ArrayList<>();
                SlotDateAdapter = new DateBookingAdapter(getApplicationContext(), slotModel);
                RecyclerDateSlot.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                RecyclerDateSlot.setHasFixedSize(true);
                RecyclerDateSlot.setAdapter(SlotDateAdapter);

                slotModel.clear();
                    noSlotAvailableLayouts.setVisibility(View.GONE);

                String URL = Constant.API + "/slot/getSlotsByDate?vendorId=" + vendorId + "&subActivityId=" + msubActivityId + "&date=" + mCalenderdate + "&type=" + mBookingType;

                StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0) {
                                noSlotAvailableLayouts.setVisibility(View.VISIBLE);
                                getNearVendorSlots();
                                /*Toast toast = Toast.makeText(SlotPages.this, "No Values", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();*/
                            } else {
                                firstRun=true;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    slotId = jsonObject.getInt("slotId");
                                    slotStartTime = jsonObject.getString("slotStartTime");
                                    slotEndTime = jsonObject.getString("slotEndTime");
                                    personCount = jsonObject.getInt("personCount");


                                   try {
                                        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                                        final Date dateObj = sdf.parse(slotStartTime );
                                        String timein12Format=new SimpleDateFormat("hh:mmaa").format(dateObj);

                                        slotStartTime=String.valueOf(timein12Format);
                                        slotStartTime = slotStartTime.replace(".", "");
                                    } catch (final ParseException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                                        final Date dateObj = sdf.parse(slotEndTime );
                                        String timein12Format=new SimpleDateFormat("hh:mmaa").format(dateObj);

                                        slotEndTime=String.valueOf(timein12Format);
                                        slotEndTime = slotEndTime.replace(".", "");
                                    } catch (final ParseException e) {
                                        e.printStackTrace();
                                    }
                                    maxAllowed = jsonObject.getInt("maxAllowed");
                                    bookingSlotCost = jsonObject.getInt("bookingCost");
                                    slotReccurence=jsonObject.getString("slotReccurence");


                                    if(slotReccurence.equalsIgnoreCase("WEEKDAYS")){
                                    if(dayName==1||dayName==2||dayName==3||dayName==4||dayName==5){

                                            firstRun=false;
                                            slotModel.add(new SlotModel(slotId, slotStartTime, slotEndTime, maxAllowed, bookingSlotCost, personCount));


                                    }
                                    }else if(slotReccurence.equalsIgnoreCase("WEEKEND")){
                                        if(dayName==0||dayName==6){
                                            firstRun=false;

                                            slotModel.add(new SlotModel(slotId, slotStartTime, slotEndTime, maxAllowed, bookingSlotCost, personCount));}

                                    }else{

                                            firstRun=false;
                                    slotModel.add(new SlotModel(slotId, slotStartTime, slotEndTime, maxAllowed, bookingSlotCost, personCount));

                                    }
                                }
                                if(firstRun){
                                    noSlotAvailableLayouts.setVisibility(View.VISIBLE);
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

                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        } else if (error instanceof TimeoutError) {
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
                    VolleySingleton.getInstance(AfterSelectVendor.this).addToRequestQueue(request1);

                }else if(mBookingType.equals("CONSECUTIVE")){



                    consecutiveslotModel.clear();
                    consecutiveslotModelTiming.clear();
                    consecutiveslotModelCost.clear();
                    sendconsecutiveslotModelTiming.clear();
                    consecutivePersonModel.clear();



                    String URL = Constant.API + "/slot/getSlotsByDate?vendorId=" + vendorId + "&subActivityId=" + msubActivityId + "&date=" + mCalenderdate + "&type=" + mBookingType;

                    StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                if (jsonArray.length() == 0) {

                                    noSlotAvailableLayouts.setVisibility(View.VISIBLE);
                                    getNearVendorSlots();
                                } else {

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        slotId = jsonObject.getInt("slotId");
                                        maxAllowed = jsonObject.getInt("maxAllowed");
                                        bookingSlotCost = jsonObject.getInt("bookingCost");
                                        ConsecutiveTimingArray=jsonObject.getJSONArray("timings");
                                        ConsecpersonCount=jsonObject.getString("personCount");

                                    }
                                    for (int i = 0; i < ConsecutiveTimingArray.length(); i++) {
                                        JSONObject jsonObject = ConsecutiveTimingArray.getJSONObject(i);
                                        slotStartTime = jsonObject.getString("startTime");
                                        sendStartTime = jsonObject.getString("startTime");
                                        slotEndTime = jsonObject.getString("endTime");
                                    }


                                  try {
                                        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",Locale.getDefault());
                                        final Date dateObj = sdf.parse(slotStartTime );
                                        String timein12Format=new SimpleDateFormat("hh:mmaa",Locale.getDefault()).format(dateObj);

                                        slotStartTime=String.valueOf(timein12Format);
                                        slotStartTime = slotStartTime.replace(".", "");
                                    } catch (final ParseException e) {
                                        e.printStackTrace();
                                    }


                                    consecutiveslotModel.add(String.valueOf(slotId));
                                    consecutiveslotModelCost.add(String.valueOf(bookingSlotCost));
                                    consecutiveslotModelTiming.add( slotStartTime);
                                    sendconsecutiveslotModelTiming.add( sendStartTime);
                                    consecutivePersonModel.add(ConsecpersonCount);




                                }
                                consecutiveSlotDateAdapter = new ConsecutiveDateBookingAdapter(getApplicationContext(), consecutiveslotModel,consecutiveslotModelCost,consecutiveslotModelTiming,sendconsecutiveslotModelTiming, consecutivePersonModel);
                                RecyclerDateSlot.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
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
                    VolleySingleton.getInstance(AfterSelectVendor.this).addToRequestQueue(request1);

                }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


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
                VolleySingleton.getInstance(AfterSelectVendor.this).addToRequestQueue(request10);

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


                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() == 0) {

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
                                RadioButton radioBtn = findViewById(checkedRadioButtonId);
                               // Toast.makeText(AfterSelectVendor.this, radioBtn.getText(), Toast.LENGTH_SHORT).show();
                                String selectedLable=radioBtn.getText().toString();

                                if(totalGraphValue.containsKey(selectedLable)){
                                    mCountList.clear();
                                 JSONArray jsonArray2=totalGraphValue.get(selectedLable);

                                    for (int j = 0; j < jsonArray2.length(); j++) {
                                        Object jsonObject1 = null;
                                        try {
                                            jsonObject1 = jsonArray2.get(j);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

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

                                    ArrayList<String> labels = new ArrayList<>();
                                    labels.add("Sun");
                                    labels.add("Mon");
                                    labels.add("Tue");
                                    labels.add("Wed");
                                    labels.add("Thu");
                                    labels.add("Fri");
                                    labels.add("Sat");

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

                            for (int j = 0; j < jsonArray2.length(); j++) {
                                Object jsonObject1 = null;
                                try {
                                    jsonObject1 = jsonArray2.get(j);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

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

                            ArrayList<String> labels = new ArrayList<>();
                            labels.add("Sun");
                            labels.add("Mon");
                            labels.add("Tue");
                            labels.add("Wed");
                            labels.add("Thu");
                            labels.add("Fri");
                            labels.add("Sat");

                            BarData data1 = new BarData(labels,  barDataSet1);
                            YAxisValueFormatter customYaxisFormatter = new YAxisValueFormatter() {
                                @Override
                                public String getFormattedValue(float value, YAxis yAxis) {
                                    //  return String.valueOf((int)value);
                                    return String.valueOf((int) Math.floor(value));
                                }
                            };
                             barChart.getAxisLeft().setAxisMinValue(0f);
                            barChart.getAxisRight().setAxisMinValue(0f);
                            barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                            barChart.getXAxis().setAxisLineWidth(1f);
                            barChart.getAxisLeft().setValueFormatter(customYaxisFormatter);
                            barChart.getAxisRight().setValueFormatter(customYaxisFormatter);


                            XAxis xAxis = barChart.getXAxis();


                            barChart.setData(data1);






                   /*     mCountList.clear();

                        for (int j = 0; j < firstLableList.size(); j++) {
                            jsonArray4 = firstLableList.get(0);

                        }
                        for (int j = 0; j < jsonArray4.length(); j++) {
                            Object jsonObject1 = jsonArray4.get(0);

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

                hideProgressingView();
                if (error instanceof NetworkError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button= findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});
                } else if (error instanceof ServerError) {


                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                }  else if (error instanceof TimeoutError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button= findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});

                }
            }
        });
        VolleySingleton.getInstance(AfterSelectVendor.this).addToRequestQueue(request);


        String URL2 = Constant.API +"/general/offerChecksByVendor?vendorId="+vendorId;

        StringRequest request2 = new StringRequest(Request.Method.GET, URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


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


                if (error instanceof NetworkError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button= findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});
                } else if (error instanceof ServerError) {

                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                } else if (error instanceof TimeoutError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button= findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});

                }
            }
        });
        VolleySingleton.getInstance(AfterSelectVendor.this).addToRequestQueue(request2);

        mofferDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offerDialog = new Dialog(AfterSelectVendor.this,R.style.AppTheme);

                offerDialog.setContentView(R.layout.offer_layout);

                offerDialog.setCancelable(true);

                cancel = offerDialog.findViewById(R.id.cancels);
                offerRecycler= offerDialog.findViewById(R.id.RecyclerOffer);


                cancel.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        offerDialog.dismiss();
                    }
                });

                offerDialog.show();
                offerStartList.clear();
                offerEndList.clear();
                offerTypeList.clear();
                offerBenefits.clear();
                // http://localhost:8080/UPass/api/service/general/viewOffers
                String URL3 = Constant.API +"/general/viewOffers?vendorId="+vendorId;

                StringRequest request3 = new StringRequest(Request.Method.GET, URL3, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0) {

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
                                   // String type = jsonObject.getString("type");


                                    Long timestamp10 = Long.parseLong(startDate);
                                    Long timestamp20 = Long.parseLong(expiryDate);
                                    try {
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
                                        Date netDate = (new Date(timestamp10));
                                        offerStart = sdf.format(netDate);


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
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


                        if (error instanceof NetworkError) {

                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button= findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }});
                        } else if (error instanceof ServerError) {


                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        } else if (error instanceof TimeoutError) {

                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button= findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }});

                        }
                    }
                });
                VolleySingleton.getInstance(AfterSelectVendor.this).addToRequestQueue(request3);


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





        membershipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layoutShow){

                  membersLayout.setVisibility(View.VISIBLE);

                    if(changedSubActivityId!=null){
                        msubActivityId=changedSubActivityId;
                    }
                    mTinyDb.putString(Constant.MEMBERSUBACTIVITYID,msubActivityId);
                    memberbookingType.clear();
                    memberbookingCost.clear();


                  membershipService(vendorId,msubActivityId,mCalenderdate);

                }else{
                    membersLayout.setVisibility(View.GONE);
                    layoutShow=true;
                }

            }
        });


        cAllday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseTypes="ALL_DAYS";

                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    cAllday.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg1) );
                    cweekday.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg2) );
                    cweekend.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg2) );

                } else {
                    cAllday.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg1));
                    cweekday.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg2));
                    cweekend.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg2));

                }

                courseDetails();
            }
        });
        cweekday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseTypes="WEEKDAYS";
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    cAllday.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg2) );
                    cweekday.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg1) );
                    cweekend.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg2) );

                } else {
                    cAllday.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg2));
                    cweekday.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg1));
                    cweekend.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg2));

                }
                courseDetails();
            }
        });
        cweekend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseTypes="WEEKEND";
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    cAllday.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg2) );
                    cweekday.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg2) );
                    cweekend.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg1) );

                } else {
                    cAllday.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg2));
                    cweekday.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg2));
                    cweekend.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg1));

                }
                courseDetails();
            }
        });




       SupportMapFragment mapFragment = ((SupportMapFragment)this.getSupportFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);
         GREENBELT1 = new LatLng(lat, longi);

        Button getDirection = findViewById(R.id.getDirection);
        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cd.isConnected()) {

                    if (!gMap.isMyLocationEnabled())
                        if (ActivityCompat.checkSelfPermission(AfterSelectVendor.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(AfterSelectVendor.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

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

        if(mBookingType.equals("")&&mcbookingType.equals("")){

            noSlotAvailableLayouts.setVisibility(View.VISIBLE);
        }

    }

    private void membershipService(String vendorId, String msubActivityId, String mCalenderdate) {

        memberbookingType.clear();
        memberbookingCost.clear();
        memberbookingSlotId.clear();
        memberbookingPerson.clear();

        String URL25 = Constant.API +"/slot/getSlotsByDate?vendorId="+vendorId+"&subActivityId="+msubActivityId+"&date="+mCalenderdate+"&type=MEMBERSHIP";

        StringRequest request25 = new StringRequest(Request.Method.GET, URL25, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                     /*   TransitionManager.beginDelayedTransition(memberLayout);
                        visible = !visible;
                       memberLayout.setVisibility(View.VISIBLE);
                        memberLayout.setVisibility(visible ? View.VISIBLE : View.GONE);*/


                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() == 0) {
                        membershipBtn.setVisibility(View.GONE);

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AfterSelectVendor.this);
                        memberCourseRecycler.setLayoutManager(layoutManager);
                        RecyclerView.Adapter adapter = new SelectMemberAdapter(getApplicationContext(), memberbookingType,memberbookingCost,memberbookingSlotId, memberbookingPerson);
                        memberCourseRecycler.setAdapter(adapter);
                    /*    Toast toast = Toast.makeText(AfterSelectVendor.this, "No Values", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();*/
                    } else {
                        membershipBtn.setVisibility(View.VISIBLE);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String  membershipType = jsonObject.getString("membershipType");
                            String bookingCost = jsonObject.getString("bookingCost");
                            String slotId=jsonObject.getString("slotId");
                            String personCount=jsonObject.getString("personCount");

                            memberbookingType.add(membershipType);
                            memberbookingCost.add(bookingCost);
                            memberbookingSlotId.add(slotId);
                            memberbookingPerson.add(personCount);

                        }
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AfterSelectVendor.this);
                        memberCourseRecycler.setLayoutManager(layoutManager);
                        RecyclerView.Adapter adapter = new SelectMemberAdapter(getApplicationContext(), memberbookingType,memberbookingCost,memberbookingSlotId,memberbookingPerson);
                        memberCourseRecycler.setAdapter(adapter);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



                if (error instanceof NetworkError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button= findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});
                } else if (error instanceof ServerError) {

                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                }else if (error instanceof TimeoutError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button= findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});

                }
            }
        });
        VolleySingleton.getInstance(AfterSelectVendor.this).addToRequestQueue(request25);
        layoutShow=false;
    }

    private void getGalleryImage() {
        {
            String URL=Constant.API+"/rest/photos/getVendorImageKeys?vendorId="+vendorId;

            StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    bitmapsLists.clear();

                    try {
                        JSONArray jsonArray=new JSONArray(response);

                        if (jsonArray.length() ==0) {
                            galleryLayout.setVisibility(View.GONE);
                            hideProgressingView();
                        }

                        for (int i = 0; i < jsonArray.length(); i++) {
                          String  values = String.valueOf(jsonArray.get(i));
                      /*      keyList.add(values);
                            if(keyList!=null){
                                NewTinyDB.putListString("KEYLIST",keyList);}*/
                            int ifder = getApplicationContext().getResources().getIdentifier(values,"ic_launcher", getApplicationContext().getPackageName());

                            int resID = getResources().getIdentifier(values,
                                    "string", getPackageName());

                            String URL = Constant.API +"/rest/photos/getVendorImagesByKey?key=" + values;

                            StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    String imagess = String.valueOf(response);

                                    byte[] imageBytes = Base64.decode(imagess, Base64.DEFAULT);
                                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);



                                    bitmapsLists.add(decodedImage);


                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AfterSelectVendor.this, LinearLayoutManager.HORIZONTAL, false);
                                    recyclerGallery.setLayoutManager(layoutManager);
                                    RecyclerView.Adapter adapter = new GalleryAdapter(getApplicationContext(),bitmapsLists);
                                    recyclerGallery.setAdapter(adapter);

                                    hideProgressingView();


                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    hideProgressingView();


                                }
                            });

                            VolleySingleton.getInstance(AfterSelectVendor.this).addToRequestQueue(request1);
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideProgressingView();




                }
            });
            request1.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 10000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 10000;
                }

                @Override
                public void retry(VolleyError error) {

                }
            });

            VolleySingleton.getInstance(AfterSelectVendor.this).addToRequestQueue(request1);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(cd.isConnected()) {
            gMap = googleMap;
            if (!gMap.isMyLocationEnabled())
                if (ActivityCompat.checkSelfPermission(AfterSelectVendor.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AfterSelectVendor.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(AfterSelectVendor.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    ActivityCompat.requestPermissions(AfterSelectVendor.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    ActivityCompat.requestPermissions(AfterSelectVendor.this, new String[]{android.Manifest.permission.INTERNET}, 1);

                } else {


                  //  Marker davao = gMap.addMarker(new MarkerOptions().position(GREENBELT1).title("COEUZ").snippet("Technology"));

                    // zoom in the camera to Greenbelt 1

                    gMap.addMarker(new MarkerOptions().position(GREENBELT1).title("vvendorName"));
         /*           mGoogleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(latitude, longitude))
                            .title(getAddressOfLatLng(latitude, longitude))
                            .icon(<your_marker_icon>));*/
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
  /*  private  void changingSubActivityTypes(String changedSubActivityId){

    }*/
    private void courseDetails(){
     membersLayout.setVisibility(View.VISIBLE);

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        String todayDates = format.format(today);


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
            mCourseDurationList.clear();
        mCoursePersonList.clear();
        slotRecurrence.clear();

            String URL50 = Constant.API + "/slot/getSlotsByDate?vendorId=" + vendorId + "&subActivityId=" + msubActivityId + "&date=" + todayDates + "&type=COURSE";

            StringRequest request50 = new StringRequest(Request.Method.GET, URL50, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    noSlotAvailableLayouts.setVisibility(View.GONE);

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        if (jsonArray.length() == 0) {

                            noCourseData.setVisibility(View.VISIBLE);
                            membersLayout.setVisibility(View.GONE);
                        /*    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AfterSelectVendor.this);
                            memberCourseRecycler.setLayoutManager(layoutManager);
                            RecyclerView.Adapter adapter = new SelectCourseAdapter(getApplicationContext(), mslotidsList,mslotStartTimeList,
                                    mslotEndTimeList,mmaxAllowedList,mslotReccurenceList,mcourseStartDateList,mcourseEndDateList,
                                    mcourseRegistrationEndDateList,mCourseCostList,mCourseDurationList);
                            memberCourseRecycler.setAdapter(adapter);*/
                       /* Toast toast = Toast.makeText(AfterSelectVendor.this, "No Values", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();*/
                        } else {
                            noCourseData.setVisibility(View.GONE);
                            membersLayout.setVisibility(View.VISIBLE);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String slotId = jsonObject.getString("slotId");
                                String slotStartTime = jsonObject.getString("slotStartTime");
                                String slotEndTime = jsonObject.getString("slotEndTime");
                                String maxAllowed = jsonObject.getString("maxAllowed");
                                String slotReccurence = jsonObject.getString("slotReccurence");
                                slotRecurrence.add(slotReccurence);

                                String courseStartDate = jsonObject.getString("courseStartDate");
                                String courseEndDate = jsonObject.getString("courseEndDate");
                                String courseRegistrationEndDate = jsonObject.getString("courseRegistrationEndDate");
                                String courseCost = jsonObject.getString("bookingCost");
                                String personCount = jsonObject.getString("personCount");

                                Date sDate = null;
                                try {
                                    sDate = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).parse(courseStartDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Date eDate = null;
                                try {
                                    eDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(courseEndDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                long diff = 0;
                                if (sDate != null) {
                                    if (eDate != null) {
                                        diff = eDate.getTime() - sDate.getTime();
                                    }
                                }

                                int numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
                                String duration=String.valueOf(numOfDays);

                                try {

                                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
                                    Date date = formatter.parse(courseStartDate);
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy",Locale.getDefault());
                                    courseStartDate = sdf.format(date);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {

                                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
                                    Date date = formatter.parse(courseEndDate);
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy",Locale.getDefault());
                                    courseEndDate = sdf.format(date);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {

                                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
                                    Date date = formatter.parse(courseRegistrationEndDate);
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy",Locale.getDefault());
                                    courseRegistrationEndDate = sdf.format(date);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                if(slotRecurrence.size()==3){

                                    LinearLayout layout=findViewById(R.id.courese);
                                    layout.setWeightSum(3f);
                                    layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,3f));
                                }else if(slotRecurrence.size()==2){
                                    LinearLayout layout=findViewById(R.id.courese);
                                    layout.setWeightSum(2f);

                                }else{
                                    LinearLayout layout=findViewById(R.id.courese);
                                    layout.setWeightSum(1f);
                                }
                                if(slotRecurrence.contains("ALL_DAYS")){
                                    cAllday.setVisibility(View.VISIBLE);
                                }else{
                                    cAllday.setVisibility(View.GONE);
                                }
                                if(slotRecurrence.contains("WEEKDAYS")){
                                    cweekday.setVisibility(View.VISIBLE);
                                }else{
                                    cweekday.setVisibility(View.GONE);
                                }
                                if(slotRecurrence.contains("WEEKEND")){
                                    cweekend.setVisibility(View.VISIBLE);
                                }else{
                                    cweekend.setVisibility(View.GONE);
                                }


                                if(courseTypes.equalsIgnoreCase("ALL_DAYS")){
                                courseTypes="ALL_DAYS";

                                final int sdk = android.os.Build.VERSION.SDK_INT;
                                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                    cAllday.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg1) );
                                    cweekday.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg2) );
                                    cweekend.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg2) );

                                } else {
                                    cAllday.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg1));
                                    cweekday.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg2));
                                    cweekend.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg2));

                                }}else   if(courseTypes.equalsIgnoreCase("WEEKDAYS")){

                                        courseTypes="WEEKDAYS";
                                        final int sdk = android.os.Build.VERSION.SDK_INT;
                                        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                            cAllday.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg2) );
                                            cweekday.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg1) );
                                            cweekend.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg2) );

                                        } else {
                                            cAllday.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg2));
                                            cweekday.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg1));
                                            cweekend.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg2));

                                        }}else   if(courseTypes.equalsIgnoreCase("WEEKEND")){


                                        courseTypes="WEEKEND";
                                        final int sdk = android.os.Build.VERSION.SDK_INT;
                                        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                            cAllday.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg2) );
                                            cweekday.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg2) );
                                            cweekend.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg1) );

                                        } else {
                                            cAllday.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg2));
                                            cweekday.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg2));
                                            cweekend.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.coursebtnbg1));

                                        }}


if(slotReccurence.equals(courseTypes)) {
    mslotidsList.add(slotId);
    mslotStartTimeList.add(slotStartTime);
    mslotEndTimeList.add(slotEndTime);
    mmaxAllowedList.add(maxAllowed);
    mslotReccurenceList.add(slotReccurence);
    mcourseStartDateList.add(courseStartDate);
    mcourseEndDateList.add(courseEndDate);
    mcourseRegistrationEndDateList.add(courseRegistrationEndDate);
    mCourseCostList.add(courseCost);
    mCourseDurationList.add(duration);
    mCoursePersonList.add(personCount);


}



                            }
                            if(mslotidsList.size()!=0) {

                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AfterSelectVendor.this);
                                memberCourseRecycler.setLayoutManager(layoutManager);
                                RecyclerView.Adapter adapter = new SelectCourseAdapter(getApplicationContext(), mslotidsList, mslotStartTimeList,
                                        mslotEndTimeList, mmaxAllowedList, mslotReccurenceList, mcourseStartDateList, mcourseEndDateList,
                                        mcourseRegistrationEndDateList, mCourseCostList, mCourseDurationList,mCoursePersonList);
                                memberCourseRecycler.setAdapter(adapter);

                        }else {      noCourseData.setVisibility(View.VISIBLE);
                                membersLayout.setVisibility(View.GONE);}

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    noSlotAvailableLayouts.setVisibility(View.GONE);

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
        VolleySingleton.getInstance(AfterSelectVendor.this).addToRequestQueue(request50);

    }
    private  void getNearVendorSlots(){



        subActivityModels.clear();
        String lat;
        String lon;
        String  initialLat=mTinyDb.getString(Constant.INITIALLAT);
        String  intialLong=mTinyDb.getString(Constant.INITIALLONG);
        String  searchLat=mTinyDb.getString(Constant.LATITUDE);
        String  searchLong=mTinyDb.getString(Constant.LONGITUDE);
        if (searchLat != null && !searchLat.isEmpty()) {
            lat=searchLat;
            lon=searchLong;
        }else{
            lat=initialLat;
            lon=intialLong;
        }
        if(changedSubActivityId!=null){
            msubActivityId=changedSubActivityId;
        }




        String URL = Constant.API + "/general/getVendorsByAvailableSlots?subActivityId="+msubActivityId+"&offset="+String.valueOf(mOffset)+"&limit="+String.valueOf(mLimit)+"&lat="+lat+"&long="+lon+"&distance=5"+"&date="+mCalenderdate+"&type="+mBookingType;

        StringRequest stringrequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() == 0) {


                            getNearVendorSlots2();


                    } else {
                        recommendedLayout.setVisibility(View.VISIBLE);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String vendorName = jsonObject.getString("vendorName");
                            Integer vendorId = jsonObject.getInt("vendorId");
                            String area = jsonObject.getString("area");

                            String vendorShopImage = jsonObject.getString("vendorShopImage");


                            subActivityModels.add(new SubActivityModel(vendorName, area,vendorId,vendorShopImage));
                            nearVendorSLotsAdapter.notifyDataSetChanged();


                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



                if (error instanceof NetworkError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button= findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});
                } else if (error instanceof ServerError) {


                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                } else if (error instanceof TimeoutError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button= findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});

                }
            }
        });
        VolleySingleton.getInstance(AfterSelectVendor.this).addToRequestQueue(stringrequest);

    }
    private  void getNearVendorSlots2(){
        mOffset=temp;
        mLimit=temp+5;
        temp=mLimit;

        subActivityModels.clear();
        String lat;
        String lon;
        String  initialLat=mTinyDb.getString(Constant.INITIALLAT);
        String  intialLong=mTinyDb.getString(Constant.INITIALLONG);
        String  searchLat=mTinyDb.getString(Constant.LATITUDE);
        String  searchLong=mTinyDb.getString(Constant.LONGITUDE);
        if (searchLat != null && !searchLat.isEmpty()) {
            lat=searchLat;
            lon=searchLong;
        }else{
            lat=initialLat;
            lon=intialLong;
        }
        if(changedSubActivityId!=null){
            msubActivityId=changedSubActivityId;
        }



        String URL = Constant.API + "/general/getVendorsByAvailableSlots?subActivityId="+msubActivityId+"&offset="+String.valueOf(mOffset)+"&limit="+String.valueOf(mLimit)+"&lat="+lat+"&long="+lon+"&distance=5"+"&date="+mCalenderdate+"&type="+mBookingType;

        StringRequest requests = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() == 0) {

                        if(temp<25){
                            getNearVendorSlots2();
                         }
                        else{
                            recommendedLayout.setVisibility(View.GONE);
                        }

                    } else {
                        recommendedLayout.setVisibility(View.VISIBLE);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String vendorName = jsonObject.getString("vendorName");
                            Integer vendorId = jsonObject.getInt("vendorId");
                            String area = jsonObject.getString("area");

                            String vendorShopImage = jsonObject.getString("vendorShopImage");


                            subActivityModels.add(new SubActivityModel(vendorName, area,vendorId,vendorShopImage));
                            nearVendorSLotsAdapter.notifyDataSetChanged();


                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



                if (error instanceof NetworkError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button= findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});
                } else if (error instanceof ServerError) {


                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                } else if (error instanceof TimeoutError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button= findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});

                }
            }
        });

        VolleySingleton.getInstance(AfterSelectVendor.this).addToRequestQueue(requests);

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
    public void showProgressingView() {

        if (!isProgressShowing) {
            isProgressShowing = true;

            view = getLayoutInflater().inflate(R.layout.progressbar_layout, null);
            mainLayout =  findViewById(R.id. detailsMainLayout);
            view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mainLayout.addView(view);

        }
    }

    public void hideProgressingView() {
            mainLayout.removeView(view);
            isProgressShowing = false;
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.call_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        if (id == R.id.call) {
           callVendor();
        }

        return super.onOptionsItemSelected(item);
    }

    private void callVendor() {
        MyPhoneListener phoneListener = new MyPhoneListener();

        TelephonyManager telephonyManager =

                (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);

        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        Intent callIntent = new Intent(Intent.ACTION_CALL);

        callIntent.setData(Uri.parse("tel:+91"+vendorContact));


        if (ContextCompat.checkSelfPermission(AfterSelectVendor.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AfterSelectVendor.this, new String[]{Manifest.permission.CALL_PHONE}, 1);

            return;
        } else {

            startActivity(callIntent);
        }


    }


    @Override
    public void onClick(String value) {


    }
    class MyPhoneListener extends PhoneStateListener {

        private boolean onCall = false;
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    // phone ringing...

                    Toast.makeText(AfterSelectVendor.this, incomingNumber + " calls you",

                            Toast.LENGTH_LONG).show();

                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:

                    // one call exists that is dialing, active, or on hold

                    Toast.makeText(AfterSelectVendor.this, "on call...",

                            Toast.LENGTH_LONG).show();

                    //because user answers the incoming call

                    onCall = true;

                    break;



                case TelephonyManager.CALL_STATE_IDLE:

                    break;

                default:
                    break;

            }
    }}
}