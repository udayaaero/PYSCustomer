package com.coeuz.pyscustomer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View.OnClickListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;

import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
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
import com.coeuz.pyscustomer.AdapterClass.CourseAdapter;
import com.coeuz.pyscustomer.AdapterClass.DateBookingAdapter;
import com.coeuz.pyscustomer.AdapterClass.FilterAmenityAdapter;
import com.coeuz.pyscustomer.AdapterClass.OfferAdapter;
import com.coeuz.pyscustomer.AdapterClass.OfferAdapterBookSummary;
import com.coeuz.pyscustomer.ModelClass.SlotModel;
import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;
import com.transitionseverywhere.TransitionManager;

import org.apache.commons.lang3.time.DateUtils;
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
import java.util.List;
import java.util.Map;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;

public class SlotPages extends AppCompatActivity implements View.OnClickListener {



    private String  mToken,msubActivityId,mVendorId,selectedSlotIds,personCounts,days;


    private RadioButton mweekDays,mWeekEnd,mNextSevendays,mNextThirtyDays;

    private TinyDB tinyDB;


    private LinearLayout noInternetLayout;
    private RelativeLayout allViewLayout;


    private  Integer totalCost;


    String mBookingType;




    TextView mSessionBookedFor,mSessionDate,mSessionDateEnd,mSessionStartTime,mSessionEndTime,mAddress,mbookCosts;
    String vvendorName,vvendorArea,vsessionDate,vsessionStartTime,vsessionEndTime,vsessionCost,newDates;
    String newvsessionStartTime,newvsessionEndTime;


    RecyclerView offerRecycler;
    private String offerStart,offerEnd,totalDiscount;
    ArrayList<String> offerStartList=new ArrayList<>();
    ArrayList<String> offerEndList=new ArrayList<>();
    ArrayList<String> offerTypeList=new ArrayList<>();
    ArrayList<String> offerBenefits=new ArrayList<>();
    ArrayList<Integer> offerDiscount=new ArrayList<>();
    TextView mTotalDiscount;
    Integer sum=0;
    private Button btnOne,btnTwo,btnThree,btnFour,btnFive;
    private Button proceed;
    Date date11;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_pages);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tinyDB=new TinyDB(getApplicationContext());

        mToken=tinyDB.getString(Constant.TOKEN);
        msubActivityId=tinyDB.getString(Constant.PREDEFINEDSUBACTIVITYID);
        selectedSlotIds=tinyDB.getString(Constant.PRESLOTID);
        mVendorId=tinyDB.getString(Constant.VENDORID);
        mBookingType=tinyDB.getString(Constant.BOOKINGTYPE);
        vvendorName=tinyDB.getString(Constant.VENDORNAME);
        vvendorArea=tinyDB.getString(Constant.VENDORAREA);
        vsessionDate=tinyDB.getString(Constant.CALENDERDATE);
        vsessionStartTime=tinyDB.getString("SlotbookingStartTime");
        vsessionEndTime=tinyDB.getString("SlotbookingEndTime");

        vsessionCost=tinyDB.getString("SlotbookingCost");

        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            final Date dateObj = sdf.parse(vsessionStartTime);
            String timein12Format=new SimpleDateFormat("hh:mmaa").format(dateObj);
            Log.d("fnuifreui45", String.valueOf(timein12Format));
            newvsessionStartTime=String.valueOf(timein12Format);
            newvsessionStartTime = newvsessionStartTime.replace(".", "");
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            final Date dateObj = sdf.parse(vsessionEndTime );
            String timein12Format=new SimpleDateFormat("hh:mmaa").format(dateObj);
            Log.d("fnuifreui45", String.valueOf(timein12Format));
            newvsessionEndTime=String.valueOf(timein12Format);
            newvsessionEndTime = newvsessionEndTime.replace(".", "");
        } catch (final ParseException e) {
            e.printStackTrace();
        }




        offerRecycler=(RecyclerView)findViewById(R.id.RecyclerOffer);
        mTotalDiscount=(TextView) findViewById(R.id.TotalDiscount);
        proceed = (Button) findViewById(R.id.nBooking1);
        proceed.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                bookingSlot();
            }
        });



        noInternetLayout = (LinearLayout) findViewById(R.id.NoInternetLayout);
        allViewLayout = (RelativeLayout) findViewById(R.id.allViewlayout);


        mSessionBookedFor=(TextView) findViewById(R.id.SessionBookedFor);
        mSessionDate=(TextView) findViewById(R.id.SessionDate);
        mSessionDateEnd=(TextView) findViewById(R.id.SessionDateEnd);
        mSessionStartTime=(TextView) findViewById(R.id.SessionStartTime);
        mSessionEndTime=(TextView) findViewById(R.id.SessionEndTime);
        mAddress=(TextView) findViewById(R.id.Address);
        mbookCosts=(TextView) findViewById(R.id.bookCosts);

        try {
            Log.d("fhruifhruei1",vsessionDate);
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = (Date)formatter.parse(vsessionDate);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
            newDates = sdf.format(date);

            Log.d("fhruifhruei",newDates);


        } catch (Exception e) {
            e.printStackTrace();
        }

        mSessionBookedFor.setText(vvendorName);
        mSessionDate.setText(newDates);
        mSessionStartTime.setText(newvsessionStartTime);
        mSessionEndTime.setText("-"+newvsessionEndTime);
        mAddress.setText(vvendorArea);
        mbookCosts.setText(vsessionCost);
     /*   TextView text=(TextView)findViewById(R.id.tex);
        Typeface tf = Typeface.createFromAsset(this.getAssets(),"Bamini.ttf");
        text.setTypeface(tf);
        text.setText("வணக்கம்");*/


        mweekDays=(RadioButton)findViewById(R.id.weekDays);
        mWeekEnd=(RadioButton)findViewById(R.id.weekEnds);
        mNextSevendays=(RadioButton)findViewById(R.id.nextseven);
        mNextThirtyDays=(RadioButton)findViewById(R.id.nextthirty);

        mweekDays.setOnClickListener(this);
        mWeekEnd.setOnClickListener(this);
        mNextSevendays.setOnClickListener(this);
        mNextThirtyDays.setOnClickListener(this);



         btnOne = (Button) findViewById(R.id.oneButton);
        btnOne.setOnClickListener(this); // calling onClick() method
         btnTwo = (Button) findViewById(R.id.twoButton);
        btnTwo.setOnClickListener(this);
         btnThree = (Button) findViewById(R.id.threeButton);
        btnThree.setOnClickListener(this);
        btnFour = (Button) findViewById(R.id.fourButton);
        btnFour.setOnClickListener(this);
        btnFive = (Button) findViewById(R.id.fiveButton);
        btnFive.setOnClickListener(this);



        offerStartList.clear();
        offerEndList.clear();
        offerTypeList.clear();
        offerBenefits.clear();

        String URL3 = Constant.API +"/general/viewOffers?vendorId="+mVendorId;

        StringRequest request3 = new StringRequest(Request.Method.GET, URL3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("trwtyfewfe", String.valueOf(response));

                try {

                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() == 0) {
                        Log.d("trwty", String.valueOf(response));

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
                            Integer discount1 = jsonObject.getInt("discount");

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
                            offerDiscount.add(discount1);
                            offerStartList.add(offerStart);
                            offerEndList.add(offerEnd);
                            offerTypeList.add(category);
                            offerBenefits.add(discount);
                         int s=0;
                         s+=discount1;
                         Log.d("fjeifj", String.valueOf(s));
                            Log.d("fjeriujre123", String.valueOf(offerBenefits));
                            Log.d("fjeriujre1234", String.valueOf(offerDiscount));
                            Log.d("fjeriujre", String.valueOf(offerBenefits));


                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SlotPages.this);
                            offerRecycler.setLayoutManager(layoutManager);
                            RecyclerView.Adapter adapter = new OfferAdapterBookSummary(getApplicationContext(),offerTypeList,offerBenefits);
                            offerRecycler.setAdapter(adapter);
                            Log.d("fjeriujrefewrfw3", String.valueOf(offerDiscount));



                        }
                        Log.d("dewfewfwfew", String.valueOf(offerBenefits));
                        Log.d("fjeriujrefewrfw34", String.valueOf(offerDiscount));


                                for(int j = 0; j < offerDiscount.size(); j++){
                                if(offerDiscount.get(j)!=null){
                                sum += offerDiscount.get(j);}}

                            Log.d("fjwiofio", String.valueOf(sum));
                            mTotalDiscount.setText(String.valueOf(sum));

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


    }


    public void bookingSlot() {

            if(personCounts==null){
                personCounts="1";
            }

                 totalCost=Integer.valueOf(mbookCosts.getText().toString());
                Log.d("jfwiejfiwre", String.valueOf(totalCost));

                       if(mweekDays.isSelected()){
                           days="5";
                           totalCost=totalCost*5;
                           Log.d("jfierj", days);
                       }
                       if(mWeekEnd.isSelected()){
                        days="2";
                           totalCost=totalCost*2;
                           Log.d("jfierj", days);
                         }

                        if(mNextSevendays.isSelected()){
                        days="7";
                            totalCost=totalCost*7;
                            Log.d("jfierj", days);
                         }
                          if(mNextThirtyDays.isSelected()){
                            days="30";
                              totalCost=totalCost*30;
                              Log.d("jfierj", days);
                           }



            final ProgressDialog mProgressDialog;
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading........");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

                String URL = Constant.API + "/slot/validateSlotBooking";
                StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mProgressDialog.dismiss();
                        Log.d("fhhuiefh", response);
                        String sDate=mSessionDate.getText().toString();

                        if(!sDate.isEmpty()){
                            tinyDB.putString(Constant.PAYSDATE, sDate);
                        }
                        String eDate=mSessionDateEnd.getText().toString();
                        if(!eDate.isEmpty()){
                            Log.d("nenioer",eDate);
                            tinyDB.putString(Constant.PAYEDATE,eDate);
                        }else{
                            tinyDB.putString(Constant.PAYEDATE,sDate);
                        }
                        String tCost= mbookCosts.getText().toString();

                        if(!tCost.isEmpty()){
                            tinyDB.putString(Constant.PAYCOST, String.valueOf(totalCost));
                        }
                        String offer= mTotalDiscount.getText().toString();
                        if(!offer.isEmpty()){
                            tinyDB.putString(Constant.PAYOFFER, offer);
                        }
                        String sTime= mSessionStartTime.getText().toString();
                        if(!sTime.isEmpty()){
                            tinyDB.putString(Constant.PAYSTIME, sTime);
                        }
                        String eTime= mSessionEndTime.getText().toString();
                        if(!eTime.isEmpty()){
                            tinyDB.putString(Constant.PAYETIME, eTime);
                        }

                        Intent refresh = new Intent(SlotPages.this, PaymentActivity.class);
                        startActivity(refresh);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ryeuiryweq", error.toString());
                        mProgressDialog.dismiss();
                      //  Log.d("ewqdadsfewr", String.valueOf(error.networkResponse.statusCode));
                        Toast.makeText(SlotPages.this, "Please try again", Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    public byte[] getBody() throws AuthFailureError {

                        HashMap<String, Object> hashMap = new HashMap<>();

                        Log.d("jfiojfero2",mVendorId);
                        Log.d("jfiojfero3",msubActivityId);
                        Log.d("jfiojfero4",personCounts);
                        Log.d("jfiojfero5",selectedSlotIds);
                        Log.d("jfiojfero6",vsessionDate);
                        hashMap.put("vendorId", mVendorId);
                        hashMap.put("subActivityId", msubActivityId);
                        hashMap.put("personCount", personCounts);
                        hashMap.put("slotId", selectedSlotIds);
                        hashMap.put("bookingType", "PRE_DEFINED_SLOT");
                        hashMap.put("bookedForDate", vsessionDate);
                        if(days != null && !days.isEmpty()) {
                            hashMap.put("type", "continous");
                            hashMap.put("days", days);
                            Log.d("jfiojfero","hewruithui");
                        }
                        else{
                            hashMap.put("type", "single");
                            Log.d("jfiojfero1","hewruithui");
                        }

                        return new JSONObject(hashMap).toString().getBytes();

                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json";
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
                    public void retry(VolleyError error) throws VolleyError {

                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);




    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
        }
        this.finish();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.weekDays:
                if(mweekDays.isSelected()){

                    mSessionDateEnd.setText("");
                    mweekDays.setSelected(false);
                    mweekDays.setChecked(false);
                }else{
                    String startDate=mSessionDate.getText().toString();
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        date11 = format1.parse(vsessionDate);
                        Log.d("nfuinfr", String.valueOf(date11));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date increment = DateUtils.addDays(date11, 5);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
                    Date date = new Date();
                    String dateTime = dateFormat.format(increment);

                    mSessionDateEnd.setText(" - "+dateTime);


                    mweekDays.setSelected(true);
                    mweekDays.setChecked(true);
                    mWeekEnd.setSelected(false);
                    mWeekEnd.setChecked(false);
                    mNextSevendays.setSelected(false);
                    mNextSevendays.setChecked(false);
                    mNextThirtyDays.setSelected(false);
                    mNextThirtyDays.setChecked(false);
                }
                break;
            case R.id.weekEnds:
            if(mWeekEnd.isSelected()){
                mSessionDateEnd.setText("");
                mWeekEnd.setSelected(false);
                mWeekEnd.setChecked(false);

            }else{
                String startDate=mSessionDate.getText().toString();
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date11 = format1.parse(vsessionDate);
                    Log.d("nfuinfr", String.valueOf(date11));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date increment = DateUtils.addDays(date11, 2);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
                Date date = new Date();
                String dateTime = dateFormat.format(increment);

                mSessionDateEnd.setText(" - "+dateTime);

                mWeekEnd.setSelected(true);
                mWeekEnd.setChecked(true);
                mweekDays.setSelected(false);
                mweekDays.setChecked(false);
                mNextSevendays.setSelected(false);
                mNextSevendays.setChecked(false);
                mNextThirtyDays.setSelected(false);
                mNextThirtyDays.setChecked(false);
            }
                break;
            case R.id.nextseven:
                if(mNextSevendays.isSelected()){
                    mSessionDateEnd.setText("");
                    mNextSevendays.setSelected(false);
                    mNextSevendays.setChecked(false);
                }else{
                    String startDate=mSessionDate.getText().toString();
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        date11 = format1.parse(vsessionDate);
                        Log.d("nfuinfr", String.valueOf(date11));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date increment = DateUtils.addDays(date11, 7);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
                    Date date = new Date();
                    String dateTime = dateFormat.format(increment);

                    mSessionDateEnd.setText(" - "+dateTime);

                    mNextSevendays.setSelected(true);
                    mNextSevendays.setChecked(true);
                    mweekDays.setSelected(false);
                    mweekDays.setChecked(false);
                    mWeekEnd.setSelected(false);
                    mWeekEnd.setChecked(false);
                    mNextThirtyDays.setSelected(false);
                    mNextThirtyDays.setChecked(false);
                }
                break;
            case R.id.nextthirty:
                if(mNextThirtyDays.isSelected()){
                    mSessionDateEnd.setText("");
                    mNextThirtyDays.setSelected(false);
                    mNextThirtyDays.setChecked(false);
                }else{
                    String startDate=mSessionDate.getText().toString();
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        date11 = format1.parse(vsessionDate);
                        Log.d("nfuinfr", String.valueOf(date11));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date increment = DateUtils.addDays(date11, 30);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
                    Date date = new Date();
                    String dateTime = dateFormat.format(increment);

                    mSessionDateEnd.setText(" - "+dateTime);

                    mNextThirtyDays.setSelected(true);
                    mNextThirtyDays.setChecked(true);
                    mNextSevendays.setSelected(false);
                    mNextSevendays.setChecked(false);
                    mweekDays.setSelected(false);
                    mweekDays.setChecked(false);
                    mWeekEnd.setSelected(false);
                    mWeekEnd.setChecked(false);

                }
                break;
            case R.id.oneButton:
                personCounts = btnOne.getText().toString();
                final int a1 = Integer.parseInt(vsessionCost);
                    int count1=Integer.parseInt(personCounts);
                    Integer AddingBookCost1=a1*count1;
                    mbookCosts.setText(String.valueOf(AddingBookCost1));
                final int sdk1 = android.os.Build.VERSION.SDK_INT;
                if(sdk1 < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    btnOne.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count1));
                    btnTwo.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnThree.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnFour.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnFive.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));


                } else {
                    btnOne.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count1));
                    btnTwo.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnThree.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnFour.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnFive.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                }



                break;

            case R.id.twoButton:
                personCounts = btnTwo.getText().toString();
                final int a2 = Integer.parseInt(vsessionCost);
                int count2=Integer.parseInt(personCounts);
                Integer AddingBookCost2=a2*count2;
                mbookCosts.setText(String.valueOf(AddingBookCost2));
                final int sdk2 = android.os.Build.VERSION.SDK_INT;
                if(sdk2 < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    btnOne.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnTwo.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count1));
                    btnThree.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnFour.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnFive.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));


                } else {
                    btnOne.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnTwo.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count1));
                    btnThree.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnFour.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnFive.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                }

                break;

            case R.id.threeButton:
                personCounts = btnThree.getText().toString();
                final int a3 = Integer.parseInt(vsessionCost);
                int count3=Integer.parseInt(personCounts);
                Integer AddingBookCost3=a3*count3;
                mbookCosts.setText(String.valueOf(AddingBookCost3));

                final int sdk3 = android.os.Build.VERSION.SDK_INT;
                if(sdk3 < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    btnOne.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnTwo.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnThree.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count1));
                    btnFour.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnFive.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));


                } else {
                    btnOne.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnTwo.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnThree.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count1));
                    btnFour.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnFive.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                }

                break;


            case R.id.fourButton:
                personCounts = btnFour.getText().toString();
                final int a4 = Integer.parseInt(vsessionCost);
                int count4=Integer.parseInt(personCounts);
                Integer AddingBookCost4=a4*count4;
                mbookCosts.setText(String.valueOf(AddingBookCost4));
                final int sdk4 = android.os.Build.VERSION.SDK_INT;
                if(sdk4 < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    btnOne.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnTwo.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnThree.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnFour.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count1));
                    btnFive.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));


                } else {
                    btnOne.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnTwo.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnThree.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnFour.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count1));
                    btnFive.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                }

                break;

            case R.id.fiveButton:
                personCounts = btnFive.getText().toString();
                final int a5 = Integer.parseInt(vsessionCost);
                int count5=Integer.parseInt(personCounts);
                Integer AddingBookCost5=a5*count5;
                mbookCosts.setText(String.valueOf(AddingBookCost5));

                final int sdk5 = android.os.Build.VERSION.SDK_INT;
                if(sdk5 < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    btnOne.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnTwo.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnThree.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnFour.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnFive.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count1));


                } else {
                    btnOne.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnTwo.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnThree.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnFour.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count));
                    btnFive.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_person_count1));
                }

                break;


        }

    }
}
