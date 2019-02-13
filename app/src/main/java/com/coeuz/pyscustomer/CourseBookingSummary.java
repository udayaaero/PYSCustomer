package com.coeuz.pyscustomer;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.coeuz.pyscustomer.requiredclass.Constant;
import com.coeuz.pyscustomer.requiredclass.TinyDB;
import com.coeuz.pyscustomer.requiredclass.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class CourseBookingSummary extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout noInternetLayout;
    private RelativeLayout allViewLayout;
    private AlertDialog alert;
    RecyclerView offerRecycler;
    private String offerStart,offerEnd;

   // TextView mTotalDiscount;
    Integer sum=0;
    private TinyDB tinyDB;
    private String  msubActivityId,mVendorId,selectedSlotIds,personCounts;
    String vvendorName,vvendorArea,vsessionDate,vsessionTime,vsessionCost,newDates;
    String mBookingType;
    TextView mSessionBookedFor,mSessionDate,mSessionTime,mAddress,mbookCosts;
    private Button btnOne,btnTwo,btnThree,btnFour,btnFive;
    private  Integer totalCost;
    private LinearLayout goOffer;
    TextView mslotStartTime,mslotEndTime
            ,mslotReccurence,mcourseStartDate,mcourseEndDate,mcourseRegistrationEndDate;
    String nCourseCost,nslotStartTime,nslotEndTime,ncourseDuration
            ,nmaxAllowed,nslotReccurence,ncourseStartDate,ncourseEndDate,ncourseRegistrationEndDate;
    private String mpersonCount;
    private TextView applyText,offerRemove;

    @SuppressLint("SetTextI18n")
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_booking_summary);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tinyDB=new TinyDB(getApplicationContext());
        tinyDB.putString(Constant.HISTORYPAGE,"COU");
        mpersonCount=tinyDB.getString(Constant.PERSONCOUNT);
       // mToken=tinyDB.getString(Constant.TOKEN);
        msubActivityId=tinyDB.getString(Constant.COURSESUBACTIVITYID);
        selectedSlotIds=tinyDB.getString(Constant.COURSESLOTID);
        mVendorId=tinyDB.getString(Constant.VENDORID);
        mBookingType=tinyDB.getString(Constant.BOOKINGTYPE);
        vvendorName=tinyDB.getString(Constant.VENDORNAME);
        vvendorArea=tinyDB.getString(Constant.VENDORAREA);
        vsessionDate=tinyDB.getString(Constant.CALENDERDATE);
        vsessionTime=tinyDB.getString("SlotbookingTime");
        vsessionCost=tinyDB.getString("SlotbookingCost");
        tinyDB.putString(Constant.PAYMENTPAGESUBID,msubActivityId);
        tinyDB.putString(Constant.PAYMENTPAGESLOTID,selectedSlotIds);


                nslotStartTime=tinyDB.getString("courseStartTime");
                nslotEndTime=tinyDB.getString("courseendTime");
                nmaxAllowed=tinyDB.getString("courseMaxAllowed");
                nslotReccurence=tinyDB.getString("courseReccurence");
                ncourseStartDate=tinyDB.getString("courseStartDate");
                ncourseEndDate=tinyDB.getString("courseEndDate");
                ncourseRegistrationEndDate=tinyDB.getString("courseRegistrationEndDate");
                nCourseCost=tinyDB.getString("SlotbookingCost");
        ncourseDuration=tinyDB.getString("courseDuration");


        noInternetLayout = findViewById(R.id.NoInternetLayout);
        allViewLayout = findViewById(R.id.allViewlayout);
        Button proceed = findViewById(R.id.nBooking3);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseBookingSlot();
            }
        });

        offerRecycler=findViewById(R.id.RecyclerOffer);
        //mTotalDiscount= findViewById(R.id.TotalDiscount);
        goOffer=findViewById(R.id.goOffer);
        goOffer.setOnClickListener(this);
        applyText=findViewById(R.id.ApplyText);

        mSessionBookedFor=findViewById(R.id.SessionBookedFor);
        mSessionDate=findViewById(R.id.SessionDate);
        mSessionTime=findViewById(R.id.SessionTime);
        mAddress=findViewById(R.id.Address);
        mbookCosts=findViewById(R.id.bookCosts);

        mslotStartTime = findViewById(R.id.slotStartTime);
        mslotEndTime = findViewById(R.id.slotEndTime);

        mslotReccurence = findViewById(R.id.slotReccurence);
        mcourseStartDate = findViewById(R.id.courseStartDate);
        mcourseEndDate = findViewById(R.id.courseEndDate);
        mcourseRegistrationEndDate = findViewById(R.id.courseRegistrationEndDate);


        mslotStartTime.setText(nslotStartTime+" - "+nslotEndTime);
        mslotEndTime.setText(ncourseDuration+" days");
       String nslotReccurence1=nslotReccurence.toLowerCase();;

        mslotReccurence.setText(nslotReccurence1);
        mcourseStartDate.setText(ncourseStartDate);
        mcourseEndDate.setText(ncourseEndDate);
        mcourseRegistrationEndDate.setText( ncourseRegistrationEndDate);


        btnOne =  findViewById(R.id.oneButton);
        btnOne.setOnClickListener(this); // calling onClick() method
        btnTwo = findViewById(R.id.twoButton);
        btnTwo.setOnClickListener(this);
        btnThree =  findViewById(R.id.threeButton);
        btnThree.setOnClickListener(this);
        btnFour =  findViewById(R.id.fourButton);
        btnFour.setOnClickListener(this);
        btnFive =  findViewById(R.id.fiveButton);
        btnFive.setOnClickListener(this);
         offerRemove = findViewById(R.id.offerRemove);
        offerRemove.setOnClickListener(this);

        try {

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = formatter.parse(vsessionDate);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy",Locale.getDefault());
            newDates = sdf.format(date);




        } catch (Exception e) {
            e.printStackTrace();
        }


        mSessionBookedFor.setText(vvendorName);
        mSessionDate.setText(newDates);
        mSessionTime.setText(vsessionTime);
        mAddress.setText(vvendorArea);
        mbookCosts.setText(vsessionCost);


    }
    public void courseBookingSlot() {

            if(personCounts==null){
                personCounts="1";
            }

            totalCost=Integer.valueOf(mbookCosts.getText().toString());


        tinyDB.putString(Constant.PAYMENTPERSONCOUNT,personCounts);

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
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        String status=jsonObject.getString("status");
                        if(status.equals("true")){

                    String sDate=mSessionDate.getText().toString();

                    if(!sDate.isEmpty()){
                        tinyDB.putString(Constant.PAYSDATE, sDate);
                    }

                    tinyDB.putString(Constant.PAYEDATE,"");

                    String tCost= mbookCosts.getText().toString();

                    if(!tCost.isEmpty()){
                        tinyDB.putString(Constant.PAYCOST, String.valueOf(totalCost));
                    }
                  /*  String offer= mTotalDiscount.getText().toString();
                    if(!offer.isEmpty()){
                        tinyDB.putString(Constant.PAYOFFER, offer);
                    }*/
                    String sTime= mSessionTime.getText().toString();
                    if(!sTime.isEmpty()){
                        tinyDB.putString(Constant.PAYSTIME, sTime);
                    }
                        /*    String eTime= mSessionEndTime.getText().toString();
                            if(!eTime.isEmpty()){
                                tinyDB.putString(Constant.PAYETIME, eTime);
                            }*/

                    tinyDB.putString(Constant.PAYETIME, "");
                    Intent refresh = new Intent(CourseBookingSummary.this, PaymentActivity.class);
                    startActivity(refresh);
                }else{
                    Toast.makeText(CourseBookingSummary.this, "Please Select another slot", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
            e.printStackTrace();
        }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    mProgressDialog.dismiss();

                    Toast.makeText(CourseBookingSummary.this, "Please try again", Toast.LENGTH_SHORT).show();

                }
            }) {
                @Override
                public byte[] getBody() {

                    HashMap<String, Object> hashMap = new HashMap<>();

                    hashMap.put("vendorId", mVendorId);
                    hashMap.put("subActivityId", msubActivityId);
                    hashMap.put("personCount", personCounts);
                    hashMap.put("slotId", selectedSlotIds);
                    hashMap.put("bookingType", "COURSE");
                    hashMap.put("bookedForDate", vsessionDate);

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
                    return 200000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 200000;
                }

                @Override
                public void retry(VolleyError error) {

                }
            });
        VolleySingleton.getInstance(CourseBookingSummary.this).addToRequestQueue(request);

    }
    public void checkPersonCount() {

        if(personCounts==null){
            personCounts="1";
        }

        String URL = Constant.API + "/slot/validatePersonCount";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String status=jsonObject.getString("status");
                    String errorMessage=jsonObject.getString("errorMessage");
                    if(status.equals("true")){

                    }else {
                        Toast toast = Toast.makeText(CourseBookingSummary.this, errorMessage, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {
            @Override
            public byte[] getBody() {

                HashMap<String, Object> hashMap = new HashMap<>();

                hashMap.put("vendorId", mVendorId);
                hashMap.put("subActivityId", msubActivityId);
                hashMap.put("personCount", personCounts);
                hashMap.put("slotId", selectedSlotIds);
                hashMap.put("bookingType", "COURSE");
                hashMap.put("bookedForDate", vsessionDate);

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
                return 200000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 200000;
            }

            @Override
            public void retry(VolleyError error) {

            }
        });
        VolleySingleton.getInstance(CourseBookingSummary.this).addToRequestQueue(request);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.oneButton:
                personCounts = btnOne.getText().toString();
                checkPersonCount();
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
                checkPersonCount();
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
                checkPersonCount();
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
                checkPersonCount();
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
                checkPersonCount();
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
            case R.id.goOffer:
                Intent intent=new Intent(CourseBookingSummary.this,ApplyOffer.class);
                startActivity(intent);
                break;

            case R.id.offerRemove:
                AlertDialog.Builder builder = new AlertDialog.Builder(CourseBookingSummary.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = inflater.inflate(R.layout.remove_offer_alert, null);
                builder.setView(customView);

                builder.setCancelable(true);
                builder.setInverseBackgroundForced(true);

                Button mDialogNo = customView.findViewById(R.id.frmNo);
                Button mDialogOk = customView.findViewById(R.id.frmOk);

                mDialogOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tinyDB.putString("APPLIED","");
                        tinyDB.putString(Constant.OFFERCODE, "");
                        tinyDB.putString(Constant.OFFERAMOUNT, "");
                        tinyDB.putString(Constant.OFFERPERCENTAGE, "");
                        alert.dismiss();
                        recreate();
                    }
                });

                mDialogNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alert.dismiss();
                    }
                });
                alert = builder.create();

                alert.show();
                break;




        }

    }
    @Override
    protected void onResume() {
        String applied=tinyDB.getString("APPLIED");
        String malert=tinyDB.getString("ALERT");
        String offerPercentage=tinyDB.getString(Constant.OFFERPERCENTAGE);
        if(applied.equalsIgnoreCase("true")){
            applyText.setText(offerPercentage+" Off is Applied");
            offerRemove.setVisibility(View.VISIBLE);

        }else{
            offerRemove.setVisibility(View.GONE);
            applyText.setText("Apply Offer");
        }
        if(!malert.equalsIgnoreCase("")){

            tinyDB.putString("ALERT","");
            AlertDialog.Builder builder = new AlertDialog.Builder(CourseBookingSummary.this);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View customView = inflater.inflate(R.layout.offer_alert, null);
            builder.setView(customView);

            builder.setCancelable(true);
            builder.setInverseBackgroundForced(true);


            Button cancel = customView.findViewById(R.id.frmOk);
            TextView offerDetails = customView.findViewById(R.id.text1);


            offerDetails.setText("your cashback coupon Rs."+malert+" is Successfully applied!");

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    alert.dismiss();
                }
            });
            alert = builder.create();

            alert.show();

        }
        super.onResume();
    }
}
