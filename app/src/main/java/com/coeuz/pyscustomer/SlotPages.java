package com.coeuz.pyscustomer;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;

import android.content.Context;
import android.os.Build;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
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

import com.coeuz.pyscustomer.requiredclass.Constant;
import com.coeuz.pyscustomer.requiredclass.TinyDB;
import com.coeuz.pyscustomer.requiredclass.VolleySingleton;


import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;

import java.util.Locale;

import java.util.Map;
import java.util.Objects;


public class SlotPages extends AppCompatActivity implements View.OnClickListener {

    private AlertDialog alert;
    private String msubActivityId;
    private String selectedSlotIds;
    private String personCounts;
    private String days;


    private RadioButton mfivedays,mtwodays,mNextSevendays,mNextThirtyDays;
    private TinyDB tinyDB;
    private LinearLayout noInternetLayout;
    private RelativeLayout allViewLayout;
    private  Integer firstCost,totalCost;
    String mBookingType;
    private String  mToken,membershipAvailability;
    TextView mSessionBookedFor,mSessionDate,mSessionDateEnd,mSessionStartTime,mSessionEndTime,mAddress,mbookCosts,applyText,offerRemove;
    String vvendorName,vvendorArea,vsessionDate,vsessionStartTime,vsessionEndTime,vsessionCost,newDates;
   // String newvsessionStartTime,newvsessionEndTime;





    Integer sum=0;
    private Button btnOne,btnTwo,btnThree,btnFour,btnFive;
    Date date11;
    private LinearLayout goOffer;
    private Button proceed;
    private String mpersonCount, mVendorId;


    @SuppressLint("SetTextI18n")
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_pages);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        membershipAvailability="false";
        tinyDB=new TinyDB(getApplicationContext());
        tinyDB.putString(Constant.HISTORYPAGE,"PRE");
        mToken=tinyDB.getString(Constant.TOKEN);
       // String mToken = tinyDB.getString(Constant.TOKEN);
        mpersonCount=tinyDB.getString(Constant.PERSONCOUNT);
        msubActivityId=tinyDB.getString(Constant.PREDEFINEDSUBACTIVITYID);
        selectedSlotIds=tinyDB.getString(Constant.PRESLOTID);
         mVendorId = tinyDB.getString(Constant.VENDORID);
        mBookingType=tinyDB.getString(Constant.BOOKINGTYPE);
        vvendorName=tinyDB.getString(Constant.VENDORNAME);
        vvendorArea=tinyDB.getString(Constant.VENDORAREA);
        vsessionDate=tinyDB.getString(Constant.CALENDERDATE);
        vsessionStartTime=tinyDB.getString("SlotbookingStartTime");
        vsessionEndTime=tinyDB.getString("SlotbookingEndTime");
        vsessionCost=tinyDB.getString("SlotbookingCost");

        tinyDB.putString(Constant.PAYMENTPAGESUBID,msubActivityId);
        tinyDB.putString(Constant.PAYMENTPAGESLOTID,selectedSlotIds);
        personCounts="1";
        days="1";

        checkPersonMemberships();

    /*    try {
            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",Locale.getDefault());
            final Date dateObj = sdf.parse(vsessionStartTime);
            String timein12Format=new SimpleDateFormat("hh:mm aa",Locale.getDefault()).format(dateObj);
            newvsessionStartTime=String.valueOf(timein12Format);
            newvsessionStartTime = newvsessionStartTime.replace(".", "");
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",Locale.getDefault());
            final Date dateObj = sdf.parse(vsessionEndTime );
            String timein12Format=new SimpleDateFormat("hh:mm a",Locale.getDefault()).format(dateObj);
            newvsessionEndTime=String.valueOf(timein12Format);
            newvsessionEndTime = newvsessionEndTime.replace(".", "");
        } catch (final ParseException e) {
            e.printStackTrace();
        }
*/

         proceed = findViewById(R.id.nBookings1);
        proceed.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                bookingSlot();
            }
        });



        noInternetLayout = findViewById(R.id.NoInternetLayout);
        allViewLayout = findViewById(R.id.allViewlayout);


        mSessionBookedFor= findViewById(R.id.SessionBookedFor);
        mSessionDate=findViewById(R.id.SessionDate);
        mSessionDateEnd=findViewById(R.id.SessionDateEnd);
        mSessionStartTime=findViewById(R.id.SessionStartTime);
        mSessionEndTime=findViewById(R.id.SessionEndTime);
        mAddress=findViewById(R.id.Address);
        mbookCosts= findViewById(R.id.bookCosts);
        goOffer=findViewById(R.id.goOffer);

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
        mSessionStartTime.setText(vsessionStartTime);
        mSessionEndTime.setText("-"+vsessionEndTime);
        mAddress.setText(vvendorArea);
        mbookCosts.setText(vsessionCost);
     /*   TextView text=(TextView)findViewById(R.id.tex);
        Typeface tf = Typeface.createFromAsset(this.getAssets(),"Bamini.ttf");
        text.setTypeface(tf);
        text.setText("வணக்கம்");*/


        mfivedays= findViewById(R.id.fivedays);
        mtwodays= findViewById(R.id.twoDays);
        mNextSevendays= findViewById(R.id.nextseven);
        mNextThirtyDays= findViewById(R.id.nextthirty);
        applyText=findViewById(R.id.ApplyText);
        offerRemove=findViewById(R.id.offerRemove);
        offerRemove.setOnClickListener(this);

        mfivedays.setOnClickListener(this);
        mtwodays.setOnClickListener(this);
        mNextSevendays.setOnClickListener(this);
        mNextThirtyDays.setOnClickListener(this);



         btnOne = findViewById(R.id.oneButton);
        btnOne.setOnClickListener(this); // calling onClick() method
         btnTwo = findViewById(R.id.twoButton);
        btnTwo.setOnClickListener(this);
         btnThree =  findViewById(R.id.threeButton);
        btnThree.setOnClickListener(this);
        btnFour = findViewById(R.id.fourButton);
        btnFour.setOnClickListener(this);
        btnFive = findViewById(R.id.fiveButton);
        btnFive.setOnClickListener(this);
        goOffer.setOnClickListener(this);





    }



    public void bookingSlot() {

            if(personCounts==null){
                personCounts="1";
            }
        tinyDB.putString(Constant.PAYMENTPERSONCOUNT,personCounts);
                 firstCost=Integer.valueOf(mbookCosts.getText().toString());

            totalCost=firstCost;


if(!days.equals("0")){
            tinyDB.putString(Constant.PAYMENTPREDEFINEDDAYS,days);}

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
                        String eDate=mSessionDateEnd.getText().toString();
                        if(!eDate.isEmpty()){
                            eDate = eDate.substring(1);
                            eDate = eDate.substring(1);
                            tinyDB.putString(Constant.PAYEDATE,eDate);
                        }else{
                            tinyDB.putString(Constant.PAYEDATE,"");
                        }
                        String tCost= mbookCosts.getText().toString();

                        if(!tCost.isEmpty()){
                            tinyDB.putString(Constant.PAYCOST, String.valueOf(totalCost));

                        }
                      /*  String offer= mTotalDiscount.getText().toString();
                        if(!offer.isEmpty()){
                            tinyDB.putString(Constant.PAYOFFER, offer);
                        }*/
                        String sTime= mSessionStartTime.getText().toString();
                        if(!sTime.isEmpty()){
                            tinyDB.putString(Constant.PAYSTIME, sTime);
                        }
                        String eTime= mSessionEndTime.getText().toString();
                        if(!eTime.isEmpty()){
                            tinyDB.putString(Constant.PAYETIME, eTime);
                        }
                        if(membershipAvailability.equalsIgnoreCase("true")){

                            Intent refresh = new Intent(SlotPages.this, ConfirmationActivity.class);
                            startActivity(refresh);

                        }else{
                        Intent refresh = new Intent(SlotPages.this, PaymentActivity.class);
                        startActivity(refresh);
                        }
                            }else{
                                Toast.makeText(SlotPages.this, "Please Select another slot", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        mProgressDialog.dismiss();

                        Toast.makeText(SlotPages.this, "Please try again", Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    public byte[] getBody() {

                        HashMap<String, Object> hashMap = new HashMap<>();

                        hashMap.put("subActivityId", msubActivityId);
                        hashMap.put("personCount", personCounts);
                        hashMap.put("slotId", selectedSlotIds);
                        hashMap.put("bookingType", "PRE_DEFINED_SLOT");
                        hashMap.put("bookedForDate", vsessionDate);
                        if(days.equals("1")) {
                            hashMap.put("type", "single");

                        }
                        else{
                            hashMap.put("type", "continous");
                            hashMap.put("days", days);

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
        VolleySingleton.getInstance(SlotPages.this).addToRequestQueue(request);




    }
    public void checkPersonCount(){
       // http://13.126.119.71:8081/service/slot/validatePersonCount


        if(personCounts==null){
            personCounts="1";
        }
        tinyDB.putString(Constant.PAYMENTPERSONCOUNT,personCounts);
        firstCost=Integer.valueOf(mbookCosts.getText().toString());

        if(mfivedays.isSelected()){
            days="5";
            totalCost=firstCost*5*(Integer.valueOf(days)+1);

        }
        if(mtwodays.isSelected()){
            days="2";
            totalCost=firstCost*2*(Integer.valueOf(days)+1);

        }

        if(mNextSevendays.isSelected()){
            days="7";
            totalCost=firstCost*7*(Integer.valueOf(days)+1);

        }
        if(mNextThirtyDays.isSelected()){
            days="30";
            totalCost=firstCost*30*(Integer.valueOf(days)+1);

        }

        if(!days.equals("0")){
            tinyDB.putString(Constant.PAYMENTPREDEFINEDDAYS,days);}



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
                        Toast toast = Toast.makeText(SlotPages.this, errorMessage, Toast.LENGTH_SHORT);
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


                hashMap.put("subActivityId", msubActivityId);
                hashMap.put("personCount", personCounts);
                hashMap.put("slotId", selectedSlotIds);
                hashMap.put("bookingType", "PRE_DEFINED_SLOT");
                hashMap.put("bookedForDate", vsessionDate);
                if(days.equals("1")) {
                    hashMap.put("type", "single");
                }
                else{
                    hashMap.put("type", "continous");
                    hashMap.put("days", days);
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
        VolleySingleton.getInstance(SlotPages.this).addToRequestQueue(request);



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
    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.fivedays:
                if(mfivedays.isSelected()){
                    proceed.setEnabled(true);
                    proceed.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    mSessionDateEnd.setText("");
                    mfivedays.setSelected(false);
                    mfivedays.setChecked(false);
                    final int a4 = Integer.parseInt(vsessionCost);
                    int count4=Integer.parseInt(personCounts);
                    days="0";
                    Integer AddingBookCost4=(a4*count4)*(Integer.valueOf(days)+1);
                    mbookCosts.setText(String.valueOf(AddingBookCost4));
                }else{
                    //String startDate=mSessionDate.getText().toString();
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
                    try {
                        date11 = format1.parse(vsessionDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date increment = DateUtils.addDays(date11, 5);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy",Locale.getDefault());
                  //  Date date = new Date();
                    String dateTime = dateFormat.format(increment);

                    mSessionDateEnd.setText(" - "+dateTime);


                    mfivedays.setSelected(true);
                    mfivedays.setChecked(true);
                    mtwodays.setSelected(false);
                    mtwodays.setChecked(false);
                    mNextSevendays.setSelected(false);
                    mNextSevendays.setChecked(false);
                    mNextThirtyDays.setSelected(false);
                    mNextThirtyDays.setChecked(false);
                    nextDaysAvailable();
                }
                break;
            case R.id.twoDays:
            if(mtwodays.isSelected()){
                mSessionDateEnd.setText("");
                mtwodays.setSelected(false);
                mtwodays.setChecked(false);
                proceed.setEnabled(true);
                proceed.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                final int a4 = Integer.parseInt(vsessionCost);
                int count4=Integer.parseInt(personCounts);
                days="0";
                Integer AddingBookCost4=(a4*count4)*(Integer.valueOf(days)+1);
                mbookCosts.setText(String.valueOf(AddingBookCost4));
            }else{
              //  String startDate=mSessionDate.getText().toString();
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
                try {
                    date11 = format1.parse(vsessionDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date increment = DateUtils.addDays(date11, 2);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy",Locale.getDefault());

                String dateTime = dateFormat.format(increment);

                mSessionDateEnd.setText(" - "+dateTime);

                mtwodays.setSelected(true);
                mtwodays.setChecked(true);
                mfivedays.setSelected(false);
                mfivedays.setChecked(false);
                mNextSevendays.setSelected(false);
                mNextSevendays.setChecked(false);
                mNextThirtyDays.setSelected(false);
                mNextThirtyDays.setChecked(false);
                nextDaysAvailable();
            }
                break;
            case R.id.nextseven:
                if(mNextSevendays.isSelected()){
                    proceed.setEnabled(true);
                    proceed.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    mSessionDateEnd.setText("");
                    mNextSevendays.setSelected(false);
                    mNextSevendays.setChecked(false);
                    final int a4 = Integer.parseInt(vsessionCost);
                    int count4=Integer.parseInt(personCounts);
                    days="0";
                    Integer AddingBookCost4=(a4*count4)*(Integer.valueOf(days)+1);
                    mbookCosts.setText(String.valueOf(AddingBookCost4));
                }else{

                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
                    try {
                        date11 = format1.parse(vsessionDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date increment = DateUtils.addDays(date11, 7);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy",Locale.getDefault());

                    String dateTime = dateFormat.format(increment);

                    mSessionDateEnd.setText(" - "+dateTime);

                    mNextSevendays.setSelected(true);
                    mNextSevendays.setChecked(true);
                    mfivedays.setSelected(false);
                    mfivedays.setChecked(false);
                    mtwodays.setSelected(false);
                    mtwodays.setChecked(false);
                    mNextThirtyDays.setSelected(false);
                    mNextThirtyDays.setChecked(false);
                    nextDaysAvailable();
                }
                break;
            case R.id.nextthirty:
                if(mNextThirtyDays.isSelected()){
                    proceed.setEnabled(true);
                    proceed.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    mSessionDateEnd.setText("");
                    mNextThirtyDays.setSelected(false);
                    mNextThirtyDays.setChecked(false);
                    final int a4 = Integer.parseInt(vsessionCost);
                    int count4=Integer.parseInt(personCounts);
                    days="0";
                    Integer AddingBookCost4=(a4*count4)*(Integer.valueOf(days)+1);
                    mbookCosts.setText(String.valueOf(AddingBookCost4));
                }else{
                   // String startDate=mSessionDate.getText().toString();
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
                    try {
                        date11 = format1.parse(vsessionDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date increment = DateUtils.addDays(date11, 30);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy",Locale.getDefault());

                    String dateTime = dateFormat.format(increment);

                    mSessionDateEnd.setText(" - "+dateTime);

                    mNextThirtyDays.setSelected(true);
                    mNextThirtyDays.setChecked(true);
                    mNextSevendays.setSelected(false);
                    mNextSevendays.setChecked(false);
                    mfivedays.setSelected(false);
                    mfivedays.setChecked(false);
                    mtwodays.setSelected(false);
                    mtwodays.setChecked(false);
                    nextDaysAvailable();

                }
                break;
            case R.id.oneButton:
                personCounts = btnOne.getText().toString();
                checkPersonCount();
                final int a1 = Integer.parseInt(vsessionCost);
                    int count1=Integer.parseInt(personCounts);

                if(days.equalsIgnoreCase("1")){
                    Integer AddingBookCost1=a1*count1*(Integer.valueOf(days));
                    mbookCosts.setText(String.valueOf(AddingBookCost1));}
                    else{
                    Integer AddingBookCost1=a1*count1*(Integer.valueOf(days)+1);
                    mbookCosts.setText(String.valueOf(AddingBookCost1));
                }
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
                if(days.equalsIgnoreCase("1")){
                Integer AddingBookCost2=a2*count2*(Integer.valueOf(days));
                mbookCosts.setText(String.valueOf(AddingBookCost2));}
                else{
                    Integer AddingBookCost2=a2*count2*(Integer.valueOf(days)+1);
                    mbookCosts.setText(String.valueOf(AddingBookCost2));
                }
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

                if(days.equalsIgnoreCase("1")){
                    Integer AddingBookCost3=a3*count3*(Integer.valueOf(days));
                    mbookCosts.setText(String.valueOf(AddingBookCost3));
                }else{
                Integer AddingBookCost3=a3*count3*(Integer.valueOf(days)+1);
                mbookCosts.setText(String.valueOf(AddingBookCost3));}

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

                if(days.equalsIgnoreCase("1")){
                    Integer AddingBookCost4=a4*count4*(Integer.valueOf(days));
                    mbookCosts.setText(String.valueOf(AddingBookCost4));
                }else{
                    Integer AddingBookCost4=a4*count4*(Integer.valueOf(days)+1);
                    mbookCosts.setText(String.valueOf(AddingBookCost4));}


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

                if(days.equalsIgnoreCase("1")){
                    Integer AddingBookCost5=a5*count5*(Integer.valueOf(days));
                    mbookCosts.setText(String.valueOf(AddingBookCost5));
                }else{
                    Integer AddingBookCost5=a5*count5*(Integer.valueOf(days)+1);
                    mbookCosts.setText(String.valueOf(AddingBookCost5));}


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
             Intent intent=new Intent(SlotPages.this,ApplyOffer.class);
             startActivity(intent);
                break;

            case R.id.offerRemove:
                AlertDialog.Builder builder = new AlertDialog.Builder(SlotPages.this);
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
    public void nextDaysAvailable(){
        {


            if(personCounts==null){
                personCounts="1";
            }
            tinyDB.putString(Constant.PAYMENTPERSONCOUNT,personCounts);
            firstCost=Integer.valueOf(mbookCosts.getText().toString());


            if(mfivedays.isSelected()){
                days="5";
                totalCost=firstCost*5*(Integer.valueOf(days)+1);
            }
            if(mtwodays.isSelected()){
                days="2";
                totalCost=firstCost*2*(Integer.valueOf(days)+1);
            }

            if(mNextSevendays.isSelected()){
                days="7";
                totalCost=firstCost*7*(Integer.valueOf(days)+1);
            }
            if(mNextThirtyDays.isSelected()){
                days="30";
                totalCost=firstCost*30*(Integer.valueOf(days)+1);
            }

            if(!days.equals("0")){

                tinyDB.putString(Constant.PAYMENTPREDEFINEDDAYS,days);}
            final int a4 = Integer.parseInt(vsessionCost);
            int count4=Integer.parseInt(personCounts);

            Integer AddingBookCost4=a4*count4*(Integer.valueOf(days)+1);
            mbookCosts.setText(String.valueOf(AddingBookCost4));



            String URL = Constant.API + "/slot/validateSlotBooking";
            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        String status=jsonObject.getString("status");
                        if(status.equals("true")){
                            proceed.setEnabled(true);
                            proceed.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


                        }else{
                            proceed.setEnabled(false);
                            proceed.setBackgroundColor(getResources().getColor(R.color.Ash));
                            Toast.makeText(SlotPages.this, "Please Select another slot", Toast.LENGTH_LONG).show();

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    Toast.makeText(SlotPages.this, "Please try again", Toast.LENGTH_SHORT).show();

                }
            }) {
                @Override
                public byte[] getBody() {

                    HashMap<String, Object> hashMap = new HashMap<>();

                    hashMap.put("subActivityId", msubActivityId);
                    hashMap.put("personCount", personCounts);
                    hashMap.put("slotId", selectedSlotIds);
                    hashMap.put("bookingType", "PRE_DEFINED_SLOT");
                    hashMap.put("bookedForDate", vsessionDate);
                    if(days.equals("1")) {
                        hashMap.put("type", "single");
                       }
                    else{
                        hashMap.put("type", "continous");
                        hashMap.put("days", days);
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
            VolleySingleton.getInstance(SlotPages.this).addToRequestQueue(request);

        }

    }
    private void checkPersonMemberships() {


    String URL10 = Constant.APIONE +"/slot/checkMembershipAvailability?vendorId="+mVendorId+"&subActivityId="+msubActivityId+"&date="+vsessionDate;

        StringRequest request10 = new StringRequest(Request.Method.GET, URL10, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                membershipAvailability=response;



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                membershipAvailability="false";


                if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {

                }  else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                }  else if (error instanceof TimeoutError) {
                    Toast.makeText(getApplicationContext(), "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();

                }
            }
        }){   @Override
        public Map<String, String> getHeaders() {
            HashMap<String, String> headers1 = new HashMap<>();

            headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
            return headers1;

        }};
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request10);




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
            AlertDialog.Builder builder = new AlertDialog.Builder(SlotPages.this);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View customView = inflater.inflate(R.layout.offer_alert, null);
            builder.setView(customView);

            builder.setCancelable(true);
            builder.setInverseBackgroundForced(true);


            Button cancel = customView.findViewById(R.id.frmOk);
            TextView offerDetails = customView.findViewById(R.id.text1);


            offerDetails.setText("your cashback coupon  Rs."+malert+" is Successfully applied!");

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
