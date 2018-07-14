package com.coeuz.pyscustomer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.coeuz.pyscustomer.AdapterClass.OfferAdapterBookSummary;
import com.coeuz.pyscustomer.AdapterClass.SelectCourseAdapter;
import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CourseBookingSummary extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout noInternetLayout;
    private RelativeLayout allViewLayout;

    RecyclerView offerRecycler;
    private String offerStart,offerEnd,totalDiscount;
    ArrayList<String> offerStartList=new ArrayList<>();
    ArrayList<String> offerEndList=new ArrayList<>();
    ArrayList<String> offerTypeList=new ArrayList<>();
    ArrayList<String> offerBenefits=new ArrayList<>();
    ArrayList<Integer> offerDiscount=new ArrayList<>();
    TextView mTotalDiscount;
    Integer sum=0;
    private TinyDB tinyDB;
    private String  mToken,msubActivityId,mVendorId,selectedSlotIds,selectedDays,personCounts,days;
    String vvendorName,vvendorArea,vsessionDate,vsessionTime,vsessionCost,newDates;
    String mBookingType;
    TextView mSessionBookedFor,mSessionDate,mSessionTime,mAddress,mbookCosts;
    private Button btnOne,btnTwo,btnThree,btnFour,btnFive;
    private  Integer totalCost;

    TextView mslotStartTime,mslotEndTime
            ,mslotReccurence,mcourseStartDate,mcourseEndDate,mcourseRegistrationEndDate;
    String nCourseCost,nslotStartTime,nslotEndTime
            ,nmaxAllowed,nslotReccurence,ncourseStartDate,ncourseEndDate,ncourseRegistrationEndDate;
    private Button proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_booking_summary);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tinyDB=new TinyDB(getApplicationContext());

        mToken=tinyDB.getString(Constant.TOKEN);
        msubActivityId=tinyDB.getString(Constant.COURSESUBACTIVITYID);
        selectedSlotIds=tinyDB.getString(Constant.COURSESLOTID);
        mVendorId=tinyDB.getString(Constant.VENDORID);
        mBookingType=tinyDB.getString(Constant.BOOKINGTYPE);
        vvendorName=tinyDB.getString(Constant.VENDORNAME);
        vvendorArea=tinyDB.getString(Constant.VENDORAREA);
        vsessionDate=tinyDB.getString(Constant.CALENDERDATE);
        vsessionTime=tinyDB.getString("SlotbookingTime");
        vsessionCost=tinyDB.getString("SlotbookingCost");


                nslotStartTime=tinyDB.getString("courseStartTime");
                nslotEndTime=tinyDB.getString("courseendTime");
                nmaxAllowed=tinyDB.getString("courseMaxAllowed");
                nslotReccurence=tinyDB.getString("courseReccurence");
                ncourseStartDate=tinyDB.getString("courseStartDate");
                ncourseEndDate=tinyDB.getString("courseEndDate");
                ncourseRegistrationEndDate=tinyDB.getString("courseRegistrationEndDate");
                nCourseCost=tinyDB.getString("SlotbookingCost");


        noInternetLayout = (LinearLayout) findViewById(R.id.NoInternetLayout);
        allViewLayout = (RelativeLayout) findViewById(R.id.allViewlayout);
        proceed = (Button) findViewById(R.id.nBooking3);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseBookingSlot();
            }
        });

        offerRecycler=(RecyclerView)findViewById(R.id.RecyclerOffer);
        mTotalDiscount=(TextView) findViewById(R.id.TotalDiscount);


        mSessionBookedFor=(TextView) findViewById(R.id.SessionBookedFor);
        mSessionDate=(TextView) findViewById(R.id.SessionDate);
        mSessionTime=(TextView) findViewById(R.id.SessionTime);
        mAddress=(TextView) findViewById(R.id.Address);
        mbookCosts=(TextView) findViewById(R.id.bookCosts);

        mslotStartTime = (TextView)findViewById(R.id.slotStartTime);
        mslotEndTime = (TextView)findViewById(R.id.slotEndTime);

        mslotReccurence = (TextView)findViewById(R.id.slotReccurence);
        mcourseStartDate = (TextView)findViewById(R.id.courseStartDate);
        mcourseEndDate = (TextView)findViewById(R.id.courseEndDate);
        mcourseRegistrationEndDate = (TextView)findViewById(R.id.courseRegistrationEndDate);


        mslotStartTime.setText(nslotStartTime);
        mslotEndTime.setText(nslotEndTime);

        mslotReccurence.setText(nslotReccurence);
        mcourseStartDate.setText(ncourseStartDate);
        mcourseEndDate.setText(ncourseEndDate);
        mcourseRegistrationEndDate.setText( ncourseRegistrationEndDate);


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
        mSessionTime.setText(vsessionTime);
        mAddress.setText(vvendorArea);
        mbookCosts.setText(vsessionCost);






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


                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CourseBookingSummary.this);
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
    public void courseBookingSlot() {

            if(personCounts==null){
                personCounts="1";
            }

            totalCost=Integer.valueOf(mbookCosts.getText().toString());
            Log.d("jfwiejfiwre", String.valueOf(totalCost));


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
                    Log.d("fhhuiefh", response);
                    mProgressDialog.dismiss();


                    String sDate=mSessionDate.getText().toString();

                    if(!sDate.isEmpty()){
                        tinyDB.putString(Constant.PAYSDATE, sDate);
                    }

                    tinyDB.putString(Constant.PAYEDATE,"");

                    String tCost= mbookCosts.getText().toString();

                    if(!tCost.isEmpty()){
                        tinyDB.putString(Constant.PAYCOST, String.valueOf(totalCost));
                    }
                    String offer= mTotalDiscount.getText().toString();
                    if(!offer.isEmpty()){
                        tinyDB.putString(Constant.PAYOFFER, offer);
                    }
                    String sTime= mSessionTime.getText().toString();
                    if(!sTime.isEmpty()){
                        tinyDB.putString(Constant.PAYSTIME, sTime);
                    }

                    tinyDB.putString(Constant.PAYETIME, "");
                    Intent refresh = new Intent(CourseBookingSummary.this, PaymentActivity.class);
                    startActivity(refresh);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("ryeuiryweq", error.toString());
                    mProgressDialog.dismiss();
                    //  Log.d("ewqdadsfewr", String.valueOf(error.networkResponse.statusCode));
                    Toast.makeText(CourseBookingSummary.this, "Please try again", Toast.LENGTH_SHORT).show();

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
