package com.coeuz.pyscustomer;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import android.widget.TextView;

import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;

public class ConfirmationActivity extends AppCompatActivity {


    TinyDB mTinyDb;
    String vvendorName,vvendorArea,userName,sessionTime,sessionDate;

    TextView mcustomername,mvendname,mContinues,mSessionDate,mSessionTime,mAddress;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_confirmation);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mTinyDb=new TinyDB(getApplicationContext());
       // Context mContext = getApplicationContext();
       // String mToken = mTinyDb.getString(Constant.TOKEN);
       // PopupWindow mPopupWindow = new PopupWindow(ConfirmationActivity.this);

        vvendorName=mTinyDb.getString(Constant.VENDORNAME);
        vvendorArea=mTinyDb.getString(Constant.VENDORAREA);
        userName=mTinyDb.getString(Constant.CUSTOMERNAME);
       // String userId = mTinyDb.getString(Constant.USERID);
       // String msubActivityId = mTinyDb.getString(Constant.PAYMENTPAGESUBID);
       // String mVendorId = mTinyDb.getString(Constant.VENDORID);
        sessionDate=mTinyDb.getString("datesss");
        sessionTime=mTinyDb.getString("timesss");


      //  RelativeLayout allViewLayout = findViewById(R.id.allviewsLayout);
        mcustomername=findViewById(R.id.customerName);
        mvendname= findViewById(R.id.vendName);
        mContinues= findViewById(R.id.continues);
        mSessionDate= findViewById(R.id.SessionDates);
        mSessionTime= findViewById(R.id.Sessiontimes);
        mAddress=findViewById(R.id.Addressd);
        ImageView home = findViewById(R.id.homes);

        mcustomername.setText(userName);

        mvendname.setText(vvendorName);
        mContinues.setText("has been confirmed.we have also sent you a cofirmation Email and SMS.");
        mAddress.setText(vvendorArea);
        mSessionDate.setText(sessionDate);
        mSessionTime.setText(sessionTime);



                       /* LayoutInflater inflater = (LayoutInflater)ConfirmationActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                        View customView = inflater.inflate(R.layout.cofirmation_layout,null);


                        mPopupWindow = new PopupWindow(customView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,true);

                        if(Build.VERSION.SDK_INT>=21){
                            mPopupWindow.setElevation(5.0f);
                        }
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);

        final RatingBar simpleRatingBar = (RatingBar)customView.findViewById(R.id.simpleRatingBar);
        Button submitButton = (Button)customView.findViewById(R.id.sub);
        textFeedback = (EditText)customView.findViewById(R.id.textFeedback);
        mTinyDb.putString(Constant.STATUS,"NotSuccess");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedback=textFeedback.getText().toString().trim();
                String totalStars = "Total Stars:: " + simpleRatingBar.getNumStars();
                final String rating= String.valueOf(simpleRatingBar.getRating());
                final String ratings = rating.substring(0, rating.indexOf("."));

                Log.d("fnrinfi",ratings);
                String rating1 = "Rating :: " + simpleRatingBar.getRating();
               // Toast.makeText(getApplicationContext(),  rating1, Toast.LENGTH_SHORT).show();

                String URL = "http://13.126.119.71:8081/api/service/general/customerFeedback";
                StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("fsgdsgfd", response);
                        Toast.makeText(getApplicationContext(),  "Thanks for your feedback", Toast.LENGTH_SHORT).show();
                        mPopupWindow.dismiss();
                        mTinyDb.putString(Constant.STATUS,"Success");

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ewrerewr", error.toString());
                        mPopupWindow.dismiss();


                    }
                }) {
                    @Override
                    public byte[] getBody() throws AuthFailureError {

                        HashMap<String, Object> hashMap = new HashMap<>();

                            hashMap.put("rating", ratings);
                            hashMap.put("feedback", feedback);
                            hashMap.put("subActivityId", msubActivityId);
                            hashMap.put("userId", userId);
                            hashMap.put("vendorId", mVendorId);


                        return new JSONObject(hashMap).toString().getBytes();

                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json";
                    }
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers1 = new HashMap<String, String>();

                        headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
                        return headers1;

                    }


                }; request.setRetryPolicy(new RetryPolicy() {
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
        });

                        TextView closeButton = (TextView) customView.findViewById(R.id.okBtn);

                        closeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                mPopupWindow.dismiss();
                            }
                        });

        new Handler().postDelayed(new Runnable() {
            public void run() {
                mPopupWindow.showAtLocation(allViewLayout, Gravity.BOTTOM,0,0);
            }
        }, 100);*/

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ConfirmationActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                ConfirmationActivity.this.finish();
            }
        });

    }


    @Override
    public void onBackPressed() {
        String historyPage=mTinyDb.getString(Constant.HISTORYPAGE);
        switch (historyPage) {
            case "PRE": {
                Intent intent = new Intent(ConfirmationActivity.this, BookingHistoryActivity.class);
                startActivity(intent);
                ConfirmationActivity.this.finish();
                break;
            }
            case "COU": {
                Intent intent = new Intent(ConfirmationActivity.this, CourseActivity.class);
                startActivity(intent);
                ConfirmationActivity.this.finish();
                break;
            }
            case "MEM": {
                Intent intent = new Intent(ConfirmationActivity.this, MembershipActivity.class);
                startActivity(intent);
                ConfirmationActivity.this.finish();
                break;
            }
        }
        }
    }

