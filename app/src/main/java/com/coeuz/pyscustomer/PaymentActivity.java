    package com.coeuz.pyscustomer;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


import android.util.Log;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.coeuz.pyscustomer.requiredclass.Constant;
import com.coeuz.pyscustomer.requiredclass.TinyDB;
import com.coeuz.pyscustomer.requiredclass.VolleySingleton;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

    public class PaymentActivity extends AppCompatActivity {


    Button confirm;

    TinyDB mTinyDb;


        private String  mToken,msubActivityId,mVendorId,selectedSlotIds,mBookingType,personCounts,days,userId;

        TextView mSessionBookedFor,
                sessionCost,sessionOffer,mAddress,applyAmount,applyAmount1,saveAmount;
        private TextView mYear,dayofMonth,sTime;
        String vvendorName,vvendorArea,vsessionDate,vsessionStartTime,vsessionEndTime,newDates,startTwel,endTwel;

        String sYear,sMonth;
        String sessionStartDate,sessionEndDate,subTotalCost,sessionStartTime,sessionEndTime,
                applyCost,offercosts,saveCost,offerAmount,offerPercentage,finalOffer;
        private LinearLayout greenLayout,offerReductionLayout,mSubtotalLayout;


        String bookingIds,timemillisecond;
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Context mContext = getApplicationContext();

        mTinyDb=new TinyDB(getApplicationContext());
        mToken=mTinyDb.getString(Constant.TOKEN);
       // LinearLayout noInternetLayout = findViewById(R.id.NoInternetLayout);
       // LinearLayout allViewLayout = findViewById(R.id.allViewlayout);

        msubActivityId=mTinyDb.getString(Constant.PAYMENTPAGESUBID);
        selectedSlotIds=mTinyDb.getString(Constant.PAYMENTPAGESLOTID);
        mVendorId=mTinyDb.getString(Constant.VENDORID);
        mBookingType=mTinyDb.getString(Constant.SELECTEDTYPE);
        vvendorName=mTinyDb.getString(Constant.VENDORNAME);
        vvendorArea=mTinyDb.getString(Constant.VENDORAREA);
        vsessionDate=mTinyDb.getString(Constant.CALENDERDATE);
        vsessionStartTime=mTinyDb.getString(Constant.PAYMENTSTARTTIME);
        vsessionEndTime=mTinyDb.getString(Constant.PAYMENTENDTIME);
        Log.d("grweger",vsessionStartTime+"-----"+vsessionEndTime);
        personCounts=mTinyDb.getString(Constant.PAYMENTPERSONCOUNT);
        days=mTinyDb.getString(Constant.PAYMENTPREDEFINEDDAYS);
        userId=mTinyDb.getString(Constant.USERID);

        greenLayout=findViewById(R.id.greenLayout);
        offerReductionLayout=findViewById(R.id.offerReductionLayout);
        mSubtotalLayout=findViewById(R.id.SubtotalLayout);



        sessionStartDate=mTinyDb.getString(Constant.PAYSDATE);
        sessionEndDate=mTinyDb.getString(Constant.PAYEDATE);
        Log.d("ncvneirevr",sessionStartDate+"-----"+sessionEndDate);
        subTotalCost=mTinyDb.getString(Constant.PAYCOST);
        sessionStartTime=mTinyDb.getString(Constant.PAYSTIME);
        sessionEndTime=mTinyDb.getString(Constant.PAYETIME);


        offerAmount=mTinyDb.getString(Constant.OFFERAMOUNT);
        offerPercentage=mTinyDb.getString(Constant.OFFERPERCENTAGE);

        Log.d("trehyt4hy",offerAmount+"---"+offerPercentage);
        finalOffer="";
        if(offerPercentage !=null &&!offerPercentage.isEmpty()){
            offerPercentage=offerPercentage.replace("%","");
            Log.d("trehyt4hycw",offerAmount+"---"+offerPercentage);
            Integer offPercentage= Integer.valueOf(offerPercentage);
            Integer offAmount= Integer.valueOf(offerAmount);
            Integer subTotalCosts=Integer.valueOf(subTotalCost);
            Log.d("trehyt4hycw",String.valueOf(offAmount)+"---"+String.valueOf(offPercentage));
            Integer total=(subTotalCosts*offPercentage)/100;
            if (total>offAmount) {
                finalOffer= String.valueOf(offAmount);

            }else{
                finalOffer= String.valueOf(total);
            }
            Log.d("mvninie",String.valueOf(total)+"---"+finalOffer);

        }



        Integer subTotalCosts=Integer.valueOf(subTotalCost);

        if(finalOffer!=null &&!finalOffer.isEmpty()){
            offercosts= String.valueOf(finalOffer);
            Integer fiOffer=Integer.valueOf(finalOffer);
            Integer totalcost=subTotalCosts-fiOffer;

            applyCost= String.valueOf(totalcost);
            saveCost= String.valueOf(fiOffer);
        }else{
        applyCost= String.valueOf(subTotalCosts);
        saveCost= "";}





        //pay=(Button)findViewById(R.id.razorpay);
        confirm=findViewById(R.id.buttonConfirm);


          mSessionBookedFor= findViewById(R.id.SessionBookedFor);
    /*  mSessionStartDate= findViewById(R.id.startDates1);
        mSessionEndDate=findViewById(R.id.endDates1);
        mSessionStartTime= findViewById(R.id.DurationStart);
        mSessionEndTime=findViewById(R.id.DurationEnd);*/
        sessionCost=findViewById(R.id.subtotal);
        sessionOffer=findViewById(R.id.offerReduction);
        mAddress=findViewById(R.id.Address);
        applyAmount=findViewById(R.id.netAmount);
        applyAmount1=findViewById(R.id.netAmount1);
        saveAmount=findViewById(R.id.saveAmount);
      //  TextView startDateText = findViewById(R.id.startDateText);
       // LinearLayout endDateLayout = findViewById(R.id.endDateLayout);

        mYear= findViewById(R.id.Year);
        dayofMonth= findViewById(R.id.dayofmonth);
        sTime=findViewById(R.id.STime);

      //  ScrollView scrollviewss = findViewById(R.id.scrollviewss);


        try {

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = formatter.parse(vsessionDate);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy",Locale.getDefault());
            newDates = sdf.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }

        mSessionBookedFor.setText(vvendorName);
        mAddress.setText(vvendorArea);
        Log.d("gtehtrh",sessionStartDate);

        String[] split1 = sessionStartDate.split("-");
        String firstSubString1 = split1[0];
        String secondSubString1 = split1[1];
        String thirdSubString1 = split1[2];
        sYear="20"+thirdSubString1;
        sMonth=firstSubString1+"-"+secondSubString1;


        if(!sessionEndDate.equals("")){
            String[] split2 = sessionEndDate.split("-");
            String firstSubString2 = split2[0];
            String secondSubString2 = split2[1];
            String thirdSubString2 = split2[2];
            if(thirdSubString1.equalsIgnoreCase(thirdSubString2)){
                sYear="20"+thirdSubString1;
            }else{
                sYear="20"+thirdSubString1+"-"+"20"+thirdSubString2;
            }
            sMonth=firstSubString1+"-"+secondSubString1+" to "+firstSubString2+"-"+secondSubString2;
        }
        mYear.setText(sYear);
        dayofMonth.setText(sMonth);
        if(sessionEndTime.equals("")){
            sTime.setText(sessionStartTime);
        }else{
            sTime.setText(sessionStartTime + "-" + sessionEndTime);
        }
        if(saveCost!=null && !saveCost.isEmpty()){
            greenLayout.setVisibility(View.VISIBLE);
            offerReductionLayout.setVisibility(View.VISIBLE);
            mSubtotalLayout.setVisibility(View.VISIBLE);
            saveAmount.setText(" ₹"+saveCost);
            sessionOffer.setText("₹ "+saveCost);
        }else{
            offerReductionLayout.setVisibility(View.GONE);
            mSubtotalLayout.setVisibility(View.GONE);
            greenLayout.setVisibility(View.GONE);
        }
        sessionCost.setText("₹ "+subTotalCost);
        applyAmount.setText("₹ "+applyCost);
        applyAmount1.setText("₹ "+applyCost);




        long time= System.currentTimeMillis();
        timemillisecond= String.valueOf(time);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mToken.isEmpty()) {

                    vsessionStartTime=vsessionStartTime.toUpperCase();


                    try {
                        SimpleDateFormat displayFormat1 = new SimpleDateFormat("HH:mm");
                        SimpleDateFormat parseFormat1 = new SimpleDateFormat("hh:mm a",Locale.US);
                        Date date10 = parseFormat1.parse(vsessionStartTime);
                        startTwel=String.valueOf(displayFormat1.format(date10));
                      if(vsessionEndTime!=null &&!vsessionEndTime.isEmpty()){
                          vsessionEndTime=vsessionEndTime.toUpperCase();
                          Date date11 = parseFormat1.parse(vsessionEndTime);
                          endTwel=String.valueOf(displayFormat1.format(date11));
                      }



                    } catch (ParseException e) {

                        e.printStackTrace();
                    }
                    Log.d("ghtrhtyh",vsessionStartTime+"---"+vsessionEndTime);
                    Log.d("ghtrhtyhfdnew",startTwel+"---"+endTwel);

                    final String offerCode=mTinyDb.getString(Constant.OFFERCODE);
                                final ProgressDialog mProgressDialog;
                                mProgressDialog = new ProgressDialog(PaymentActivity.this);
                                mProgressDialog.setMessage("Loading........");
                                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                mProgressDialog.setCancelable(false);
                                mProgressDialog.show();
                                mProgressDialog.setCancelable(false);
                                mProgressDialog.setCanceledOnTouchOutside(false);

                                String URL =Constant.APIONE+"/slot/initiateBooking";
                                StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("fwfw",response);
                                                bookingIds=response;
                                        bookingIds=bookingIds.replace("[","");
                                        bookingIds=bookingIds.replace("]","");

                                startPayment();
                                        mProgressDialog.dismiss();

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {



                                        if(String.valueOf(error).equalsIgnoreCase("com.android.volley.AuthFailureError"))
                                        {
                                            Intent intent=new Intent(PaymentActivity.this,LoginActivity.class);
                                            startActivity(intent);
                                        }



                                        mProgressDialog.dismiss();

                                        if (error instanceof NetworkError) {

                                          /*  noInternetLayout.setVisibility(View.VISIBLE);
                                            allViewLayout.setVisibility(View.GONE);
                                            Button button = findViewById(R.id.TryAgain);
                                            button.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    recreate();
                                                }
                                            });*/
                                        } else if (error instanceof ServerError) {


                                        } else if (error instanceof ParseError) {
                                            Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                                        } else if (error instanceof TimeoutError) {

                                         /*   noInternetLayout.setVisibility(View.VISIBLE);
                                            allViewLayout.setVisibility(View.GONE);
                                            Button button = findViewById(R.id.TryAgain);
                                            button.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    recreate();
                                                }
                                            });*/

                                        }

                                    }
                                }) {
                                    @Override
                                    public byte[] getBody() {

                                        HashMap<String, Object> hashMap = new HashMap<>();

                                        switch (mBookingType) {
                                            case "PRE-DEFINED":



                                                hashMap.put("vendorId", mVendorId);
                                                hashMap.put("subActivityId", msubActivityId);
                                                hashMap.put("personCount", personCounts);
                                                hashMap.put("slotId", selectedSlotIds);
                                                hashMap.put("bookedForDate", vsessionDate);
                                           /*     hashMap.put("slotStartTime", vsessionStartTime);
                                                hashMap.put("slotEndTime", vsessionEndTime);*/
                                                hashMap.put("slotStartTime", startTwel);
                                                hashMap.put("slotEndTime", endTwel);
                                                hashMap.put("userId", userId);
                                              //  hashMap.put("paymentId", timemillisecond);
                                                Log.d("fewfwe",days);
                                                if (days.equals("1")) {
                                                    hashMap.put("type", "single");


                                                } else {

                                                    hashMap.put("type", "continous");
                                                    hashMap.put("days", days);
                                                }
                                                break;
                                            case "CONSECUTIVE":

                                                hashMap.put("vendorId", mVendorId);
                                                hashMap.put("subActivityId", msubActivityId);
                                                hashMap.put("personCount", personCounts);
                                                hashMap.put("slotId", selectedSlotIds);
                                                hashMap.put("type", "consecutive");
                                                hashMap.put("bookedForDate", vsessionDate);
                                                hashMap.put("startTime", startTwel);
                                                hashMap.put("userId", userId);
                                              //  hashMap.put("paymentId", timemillisecond);

                                                break;
                                            case "COURSE":


                                                hashMap.put("vendorId", mVendorId);
                                                hashMap.put("subActivityId", msubActivityId);
                                                hashMap.put("personCount", personCounts);
                                                hashMap.put("slotId", selectedSlotIds);
                                                hashMap.put("type", "course");
                                                hashMap.put("bookedForDate", vsessionDate);
                                                hashMap.put("slotStartTime", startTwel);
                                                hashMap.put("slotEndTime", endTwel);
                                                hashMap.put("userId", userId);
                                              //  hashMap.put("paymentId", timemillisecond);

                                                break;
                                            case "MEMBERSHIP":

                                                hashMap.put("vendorId", mVendorId);
                                                hashMap.put("subActivityId", msubActivityId);
                                                hashMap.put("personCount", personCounts);
                                                hashMap.put("slotId", selectedSlotIds);
                                                hashMap.put("type", "MEMBERSHIP");
                                                hashMap.put("bookedForDate", vsessionDate);
                                                hashMap.put("userId", userId);
                                               // hashMap.put("paymentId", timemillisecond);


                                                break;
                                        }
Log.d("hyrhry", String.valueOf(hashMap));

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
                                        headers1.put("offerCode",offerCode);
                                        return headers1;

                                    }


                                };
                          /*      request.setRetryPolicy(new RetryPolicy() {
                                    @Override
                                    public int getCurrentTimeout() {
                                        return 150000;
                                    }

                                    @Override
                                    public int getCurrentRetryCount() {
                                        return 150000;
                                    }

                                    @Override
                                    public void retry(VolleyError error) {

                                    }
                                });*/


                    VolleySingleton.getInstance(PaymentActivity.this).addToRequestQueue(request);

                          /*  }
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
                        public Map<String, String> getHeaders() {
                            HashMap<String, String> headers1 = new HashMap<>();


                            headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
                            return headers1;

                        }
                    };
                    RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                    requestQueue1.add(request1);

*/

                }else{
                    Intent intent=new Intent(PaymentActivity.this,LoginActivity.class);
                    startActivity(intent);
                }

            }
        });

    }


       private void startPayment() {



           String date;
           if(!sessionEndDate.equals("")){
               date=sessionStartDate+"-"+sessionEndDate;
           }else{
               date=sessionStartDate;
           }
           String time = "";
           if(sessionEndTime.equals("")){
               time=sessionStartTime;
           }else{
               time=sessionStartTime + "-" + sessionEndTime;
           }

          mTinyDb.putString("datesss", date);
         mTinyDb.putString("timesss", time);


           Intent intent=new Intent(PaymentActivity.this,ProgressActivity.class);
           intent.putExtra("bookingId",bookingIds);
           intent.putExtra("subTotalCost",applyCost);
           startActivity(intent);

        /*    if(!mToken.equals("")) {

          int  payamount=Integer.parseInt(subTotalCost);

            Checkout checkout=new Checkout();
            checkout.setImage(R.drawable.icon);
            final Activity activity=this;

                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("bookingIds",bookingIds);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                JSONObject options=new JSONObject();
                options.put("description","Booking Sessions");
                options.put("notes",jsonObject);
                options.put("currency","INR");
                options.put("amount",payamount*100);
                checkout.open(activity,options);
            }catch (JSONException e){
                e.printStackTrace();
            }
            }else{
                Intent intent=new Intent(PaymentActivity.this,LoginActivity.class);
                startActivity(intent);

            }*/
        }



        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                this.finish();
            }

            return super.onOptionsItemSelected(item);
        }
    }
       /* Raghu (to udaya) 5:35 PM>
            {
            "type":"course",
            "vendorId":"2",
            "slotId":"221",
            "subActivityId":"2",
            "personCount":"1",
            "slotStartTime":"10:40",
            "slotEndTime":"17:50",
            "bookedForDate":"2018-06-20",
            "userId":"3",
            "paymentId":"pay_92zUQM8FifZb1r"
            }
            Raghu (to udaya) 5:36 PM> {
            "type":"consecutive",
            "vendorId":"2",
            "slotId":"221",
            "subActivityId":"2",
            "personCount":"1",
            "slotStartTime":"17:40",
            "bookedForDate":"2018-06-20",
            "userId":"3",
            "paymentId":"pay_92zUQM8FifZb1r"
            }
            Raghu (to udaya) 5:38 PM> {
            "bookingCost" : "2222",
            "bookingType" : "MEMBERSHIP",
            "currencyCode":"INR",
            "maxAllowed":" 2",
            "membershipType":"MONTHLY",
            "subActivityId":"4",
            "vendorId":"1",
            "userId":"3"
            }
            Raghu (to udaya) 5:40 PM> {
            "type":"single",
            "vendorId":"2",
            "slotId":"221",
            "subActivityId":"2",
            "personCount":"1",
            "slotStartTime":"10:40",
            "slotEndTime":"17:50",
            "bookedForDate":"2018-06-20",
            "userId":"3",
            "paymentId":"pay_92zUQM8FifZb1r"
            }
            Raghu (to udaya) 6:34 PM> http://localhost:8080/UPass/api/service/slot/slotBooking
            Raghu (to udaya) 6:35 PM>  {
            "bookingCost" : "2222",
            "bookingType" : "MEMBERSHIP",
            "currencyCode":"INR",
            "maxAllowed":" 2",
            "membershipType":"MONTHLY",
            "subActivityId":"4",
            "vendorId":"1",
            "userId":"3"
            }
            Raghu (to udaya) 6:35 PM> {
            "type":"consecutive",
            "vendorId":"2",
            "slotId":"221",
            "subActivityId":"2",
            "personCount":"1",
            "slotStartTime":"17:40",
            "bookedForDate":"2018-06-20",
            "userId":"3",
            "paymentId":"pay_92zUQM8FifZb1r"
            }
            Raghu (to udaya) 6:36 PM> {
            "type":"course",
            "vendorId":"2",
            "slotId":"221",
            "subActivityId":"2",
            "personCount":"1",
            "slotStartTime":"10:40",
            "slotEndTime":"17:50",
            "bookedForDate":"2018-06-20",
            "userId":"3",
            "paymentId":"pay_92zUQM8FifZb1r"
            }
*/