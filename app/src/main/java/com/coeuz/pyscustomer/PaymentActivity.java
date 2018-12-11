    package com.coeuz.pyscustomer;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;

import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;
import com.coeuz.pyscustomer.Requiredclass.VolleySingleton;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

    public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {


    Button confirm;

    TinyDB mTinyDb;


        private String  mToken,msubActivityId,mVendorId,selectedSlotIds,mBookingType,personCounts,days,userId;

        TextView mSessionBookedFor,mSessionStartDate,mSessionEndDate,mSessionStartTime,mSessionEndTime,
                sessionCost,sessionOffer,mAddress,applyAmount,applyAmount1,saveAmount;
        String vvendorName,vvendorArea,vsessionDate,vsessionStartTime,vsessionEndTime,newDates;


        String sessionStartDate,sessionEndDate,subTotalCost,offerCost,sessionStartTime,sessionEndTime,
                applyCost,offercosts,saveCost;


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
        personCounts=mTinyDb.getString(Constant.PAYMENTPERSONCOUNT);
        days=mTinyDb.getString(Constant.PAYMENTPREDEFINEDDAYS);
        userId=mTinyDb.getString(Constant.USERID);



        sessionStartDate=mTinyDb.getString(Constant.PAYSDATE);
        sessionEndDate=mTinyDb.getString(Constant.PAYEDATE);
        subTotalCost=mTinyDb.getString(Constant.PAYCOST);
        offerCost=mTinyDb.getString(Constant.PAYOFFER);
        sessionStartTime=mTinyDb.getString(Constant.PAYSTIME);
        sessionEndTime=mTinyDb.getString(Constant.PAYETIME);

        Integer offerCosts=Integer.valueOf(offerCost);

        Integer subTotalCosts=Integer.valueOf(subTotalCost);

        Integer total=(subTotalCosts*offerCosts)/100;

        offercosts= String.valueOf(total);
        Integer totalcost=subTotalCosts-total;

        applyCost= String.valueOf(totalcost);
        saveCost= String.valueOf(total);





        //pay=(Button)findViewById(R.id.razorpay);
        confirm=findViewById(R.id.buttonConfirm);


        mSessionBookedFor= findViewById(R.id.SessionBookedFor);
        mSessionStartDate= findViewById(R.id.startDates1);
        mSessionEndDate=findViewById(R.id.endDates1);
        mSessionStartTime= findViewById(R.id.DurationStart);
        mSessionEndTime=findViewById(R.id.DurationEnd);
        sessionCost=findViewById(R.id.subtotal);
        sessionOffer=findViewById(R.id.offerReduction);
        mAddress=findViewById(R.id.Address);
        applyAmount=findViewById(R.id.netAmount);
        applyAmount1=findViewById(R.id.netAmount1);
        saveAmount=findViewById(R.id.saveAmount);
        TextView startDateText = findViewById(R.id.startDateText);
        LinearLayout endDateLayout = findViewById(R.id.endDateLayout);
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
        mSessionStartDate.setText(sessionStartDate);

        if(sessionEndDate.equals("")){
            endDateLayout.setVisibility(View.GONE);
            startDateText.setText("Date");
        }else{
            sessionEndDate= sessionEndDate.substring(2);

        }
        mSessionEndDate.setText(sessionEndDate);
        mSessionStartTime.setText(sessionStartTime);
        mSessionEndTime.setText(sessionEndTime);
        sessionCost.setText("₹ "+subTotalCost);
        sessionOffer.setText("₹ "+offercosts);
        applyAmount.setText("₹ "+applyCost);
        applyAmount1.setText("₹ "+applyCost);
        saveAmount.setText(" ₹"+saveCost);




       /* pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });*/
        long time= System.currentTimeMillis();
        timemillisecond= String.valueOf(time);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mToken.isEmpty()) {



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
                                                hashMap.put("slotStartTime", vsessionStartTime);
                                                hashMap.put("slotEndTime", vsessionEndTime);
                                                hashMap.put("userId", userId);
                                              //  hashMap.put("paymentId", timemillisecond);
                                                if (!days.equals("0")) {
                                                    hashMap.put("type", "continous");
                                                    hashMap.put("days", days);

                                                } else {
                                                    hashMap.put("type", "single");

                                                }
                                                break;
                                            case "CONSECUTIVE":

                                                hashMap.put("vendorId", mVendorId);
                                                hashMap.put("subActivityId", msubActivityId);
                                                hashMap.put("personCount", personCounts);
                                                hashMap.put("slotId", selectedSlotIds);
                                                hashMap.put("type", "consecutive");
                                                hashMap.put("bookedForDate", vsessionDate);
                                                hashMap.put("startTime", vsessionStartTime);
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
                                                hashMap.put("slotStartTime", vsessionStartTime);
                                                hashMap.put("slotEndTime", vsessionEndTime);
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
            if(!mToken.equals("")) {

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

            }
        }

        @Override
        public void onPaymentSuccess(String s) {


            String URL1 = Constant.APIONE+"/slot/confirmBooking?paymentId="+s+"&bookingIdList="+bookingIds;

            StringRequest request = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                   try {
                        JSONObject jsonObject = new JSONObject(response);
                       String status=jsonObject.getString("status");

                       if(status.equals("SUCCESS")){

                        String startDate = mSessionStartDate.getText().toString();
                        String endDate = mSessionEndDate.getText().toString();
                        String startTime = mSessionStartTime.getText().toString();
                        String endTime = mSessionEndTime.getText().toString();
                        String date;
                        if (startDate.equals(endDate)) {
                            date = startDate;
                        } else {
                            date = startDate + " " + endDate;
                        }

                        String time = startTime + " " + endTime;
                        mTinyDb.putString("datesss", date);
                        mTinyDb.putString("timesss", time);

                        Intent intent = new Intent(PaymentActivity.this, ConfirmationActivity.class);
                                        startActivity(intent);}
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
            VolleySingleton.getInstance(PaymentActivity.this).addToRequestQueue(request);

        }

        @Override
        public void onPaymentError(int i, String s) {

            Toast.makeText(this, "Your Payment failed", Toast.LENGTH_SHORT).show();

            String URL1 = Constant.APIONE+"/slot/reverseBooking?bookingIdList="+bookingIds;

            StringRequest request = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                   // Toast.makeText(getApplicationContext(), "Your Payment is failure,please try again", Toast.LENGTH_SHORT).show();

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
            VolleySingleton.getInstance(PaymentActivity.this).addToRequestQueue(request);
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