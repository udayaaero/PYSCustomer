    package com.coeuz.pyscustomer;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
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


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;
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


        String timemillisecond;
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

       Log.d("fhuifhsrr",mToken);
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
        Log.d("dhewuidfhewi", String.valueOf(offerCosts));
        Integer subTotalCosts=Integer.valueOf(subTotalCost);
        Log.d("dhewuidfhewi1", String.valueOf(subTotalCosts));
        Integer total=(subTotalCosts*offerCosts)/100;
        Log.d("dhewuidfhewi2", String.valueOf(total));
        offercosts= String.valueOf(total);
        Integer totalcost=subTotalCosts-total;
        Log.d("dhewuidfhewi3", String.valueOf(totalcost));
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
            Log.d("fhruifhruei1",vsessionDate);
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = formatter.parse(vsessionDate);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy",Locale.getDefault());
            newDates = sdf.format(date);

            Log.d("fhruifhruei",newDates);


        } catch (Exception e) {
            e.printStackTrace();
        }

        mSessionBookedFor.setText(vvendorName);
        mAddress.setText(vvendorArea);
        mSessionStartDate.setText(sessionStartDate);
        Log.d("nenioer1",sessionEndDate);
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
        Log.d("fjeriojgioerj",timemillisecond);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mToken.isEmpty()) {

                    String URL1 = Constant.APIONE +"/slot/myslotBooking?paymentId="+timemillisecond;
                    StringRequest request1 = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("fwefewfdscdw", response);
                            JSONObject jsonObject;
                            try {
                                jsonObject = new JSONObject(response);

                            String success=jsonObject.getString("message");
                            if(success.equals("SUCCESS")){

                                final ProgressDialog mProgressDialog;
                                mProgressDialog = new ProgressDialog(PaymentActivity.this);
                                mProgressDialog.setMessage("Loading........");
                                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                mProgressDialog.setCancelable(false);
                                mProgressDialog.show();
                                mProgressDialog.setCancelable(false);
                                mProgressDialog.setCanceledOnTouchOutside(false);

                                String URL = Constant.APIONE + "/slot/slotBooking";
                                StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("oipuoy", response);
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

                                        mProgressDialog.dismiss();
                                        Intent intent = new Intent(PaymentActivity.this, ConfirmationActivity.class);
                                        startActivity(intent);

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("oipuoy1", error.toString());
                                        Log.d("oipuoy1", String.valueOf(error.networkResponse.statusCode));

                                        mProgressDialog.dismiss();

                                    }
                                }) {
                                    @Override
                                    public byte[] getBody() {

                                        HashMap<String, Object> hashMap = new HashMap<>();

                                        switch (mBookingType) {
                                            case "PRE-DEFINED":

                                                Log.d("lmkikom", mVendorId);
                                                Log.d("lmkikom1", msubActivityId);
                                                Log.d("lmkikom2", personCounts);
                                                Log.d("lmkikom3", selectedSlotIds);
                                                Log.d("lmkikom4", vsessionDate);
                                                Log.d("lmkikom5", vsessionStartTime);
                                                Log.d("lmkikom6", vsessionEndTime);
                                                Log.d("lmkikom7", days);
                                                Log.d("lmkikom8", timemillisecond);

                                                hashMap.put("vendorId", mVendorId);
                                                hashMap.put("subActivityId", msubActivityId);
                                                hashMap.put("personCount", personCounts);
                                                hashMap.put("slotId", selectedSlotIds);
                                                hashMap.put("bookedForDate", vsessionDate);
                                                hashMap.put("slotStartTime", vsessionStartTime);
                                                hashMap.put("slotEndTime", vsessionEndTime);
                                                hashMap.put("userId", userId);
                                                hashMap.put("paymentId", timemillisecond);
                                                if (!days.equals("0")) {
                                                    hashMap.put("type", "continous");
                                                    hashMap.put("days", days);
                                                    Log.d("jfiojfero", "hewruithui");
                                                } else {
                                                    hashMap.put("type", "single");
                                                    Log.d("jfiojfero1", "hewruithui");
                                                }
                                                break;
                                            case "CONSECUTIVE":
                                                Log.d("reiyteior", mVendorId);
                                                Log.d("reiyteior1", msubActivityId);
                                                Log.d("reiyteior2", personCounts);
                                                Log.d("reiyteior3", selectedSlotIds);
                                                Log.d("reiyteior4", vsessionDate);
                                                Log.d("reiyteior6", vsessionStartTime);


                                                hashMap.put("vendorId", mVendorId);
                                                hashMap.put("subActivityId", msubActivityId);
                                                hashMap.put("personCount", personCounts);
                                                hashMap.put("slotId", selectedSlotIds);
                                                hashMap.put("type", "consecutive");
                                                hashMap.put("bookedForDate", vsessionDate);
                                                hashMap.put("startTime", vsessionStartTime);
                                                hashMap.put("userId", userId);
                                                hashMap.put("paymentId", timemillisecond);

                                                break;
                                            case "COURSE":

                                                Log.d("cmvnmxnz", mVendorId);
                                                Log.d("cmvnmxnz1", msubActivityId);
                                                Log.d("cmvnmxnz3", personCounts);
                                                Log.d("cmvnmxnz4", selectedSlotIds);
                                                Log.d("cmvnmxnz5", vsessionDate);
                                                Log.d("cmvnmxnz6", vsessionStartTime);
                                                Log.d("cmvnmxnz7", vsessionEndTime);

                                                hashMap.put("vendorId", mVendorId);
                                                hashMap.put("subActivityId", msubActivityId);
                                                hashMap.put("personCount", personCounts);
                                                hashMap.put("slotId", selectedSlotIds);
                                                hashMap.put("type", "course");
                                                hashMap.put("bookedForDate", vsessionDate);
                                                hashMap.put("slotStartTime", vsessionStartTime);
                                                hashMap.put("slotEndTime", vsessionEndTime);
                                                hashMap.put("userId", userId);
                                                hashMap.put("paymentId", timemillisecond);

                                                break;
                                            case "MEMBERSHIP":
                                                Log.d("cxsecesa", mVendorId);
                                                Log.d("cxsecesa1", msubActivityId);
                                                Log.d("cxsecesa2", personCounts);
                                                Log.d("cxsecesa3", selectedSlotIds);
                                                Log.d("cxsecesa4", vsessionDate);

                                                hashMap.put("vendorId", mVendorId);
                                                hashMap.put("subActivityId", msubActivityId);
                                                hashMap.put("personCount", personCounts);
                                                hashMap.put("slotId", selectedSlotIds);
                                                hashMap.put("type", "MEMBERSHIP");
                                                hashMap.put("bookedForDate", vsessionDate);
                                                hashMap.put("userId", userId);
                                                hashMap.put("paymentId", timemillisecond);


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
                                request.setRetryPolicy(new RetryPolicy() {
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
                                });


                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                requestQueue.add(request);

                            }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("crtrt", String.valueOf(error));


                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() {
                            HashMap<String, String> headers1 = new HashMap<>();
                            Log.d("gnerjingie",mToken);

                            headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
                            return headers1;

                        }
                    };
                    RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                    requestQueue1.add(request1);



                }else{
                    Intent intent=new Intent(PaymentActivity.this,LoginActivity.class);
                    startActivity(intent);
                }

            }
        });
    }


      /*  private void startPayment() {
            if(!mToken.equals("")) {

            payamount=Integer.parseInt(applyCost);

            Checkout checkout=new Checkout();
            checkout.setImage(R.drawable.icon);
            final Activity activity=this;

            try {
                JSONObject options=new JSONObject();
                options.put("description","Booking Sessions");
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
        }*/

        @Override
        public void onPaymentSuccess(String s) {

            Toast.makeText(this, "Your Payment is Successful", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaymentError(int i, String s) {

            Toast.makeText(this, "Your Payment failed", Toast.LENGTH_SHORT).show();
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