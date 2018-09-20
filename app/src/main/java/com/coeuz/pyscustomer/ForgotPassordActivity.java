package com.coeuz.pyscustomer;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class ForgotPassordActivity extends AppCompatActivity {


    private EditText mEmails;
    private EditText mOtp,mMobile;
    private String iMobile,iOTP;

    private String mEmail;
    private LinearLayout mForgotLayout,mSubmitLayout;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_passord);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mEmails=findViewById(R.id.Emails1);
        mOtp=findViewById(R.id.otp);
        mMobile=findViewById(R.id.mobile);
        Button mSubmit =findViewById(R.id.Submit);
      /*  mback=(Button)findViewById(R.id.back);*/
        mForgotLayout=findViewById(R.id.forgotLayout);
        mSubmitLayout=findViewById(R.id.submitLayout);

        Button mRecover =  findViewById(R.id.recover);
        mRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String MobilePattern = "[0-9]{10}";

                mEmail = mEmails.getText().toString().trim();
                Log.d("fwifjiow",mEmail);

                if (!mEmails.getText().toString().trim().matches(emailPattern)&&!mEmails.getText().toString().matches(MobilePattern)) {
                    mEmails.setError("Please enter valid input ");
                    mEmails.requestFocus();
                }
                else{


                    String URL = Constant.API+"/base/user/sendOTP?userName="+mEmail;
                    StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("cnjenfir", String.valueOf(response));

                            try {
                                JSONObject jsonObject= new JSONObject(response);
                                String status=jsonObject.getString("status");
                                if(status.equalsIgnoreCase("SUCCESS"))  {
                                    mForgotLayout.setVisibility(View.GONE);
                                    mSubmitLayout.setVisibility(View.VISIBLE);
                                }else{
                                    Toast.makeText(getApplicationContext(), "User Not Available", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("ppwsewwpp123", String.valueOf(error));

                            if (error instanceof NetworkError) {
                                Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ServerError) {
                                Toast.makeText(getApplicationContext(), "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();

                            } else if (error instanceof AuthFailureError) {
                                Toast.makeText(getApplicationContext(), "AuthFailureError", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ParseError) {
                                Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                            } else if (error instanceof TimeoutError) {
                                Toast.makeText(getApplicationContext(), "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

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
                        public void retry(VolleyError error) {

                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(request);
                }


            }




        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String MobilePattern = "[0-9]{10}";

                iMobile = mMobile.getText().toString().trim();
                iOTP = mOtp.getText().toString().trim();

                if(TextUtils.isEmpty(iOTP)){
                    mOtp.setError("Please enter OTP");
                    mOtp.requestFocus();
                     return;
                }
                if (!mMobile.getText().toString().matches(MobilePattern)) {
                    mMobile.setError("Please enter valid input ");
                    mMobile.requestFocus();
                } else {
                    String URL = Constant.API+"/base/user/verifyOTP?mobileNumber="+iMobile+"&otp="+iOTP;
                    StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("ctfctct", String.valueOf(response));
                          if(response.equals("true")){
                              Intent intent=new Intent(getApplicationContext(),UpdatePasswordActivity.class);
                              startActivity(intent);
                              finish();
                            }else{
                                Toast.makeText(ForgotPassordActivity.this, "Please try after some time", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("ppwsewwpp123", String.valueOf(error));

                            if (error instanceof NetworkError) {
                                Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ServerError) {
                                Toast.makeText(getApplicationContext(), "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();

                            } else if (error instanceof AuthFailureError) {
                                Toast.makeText(getApplicationContext(), "AuthFailureError", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ParseError) {
                                Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                            } else if (error instanceof TimeoutError) {
                                Toast.makeText(getApplicationContext(), "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

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
                        public void retry(VolleyError error) {

                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(request);

                }
            }
        });
/*mback.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        mForgotLayout.setVisibility(View.VISIBLE);
        mSubmitLayout.setVisibility(View.GONE);
    }
});*/
        }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) Objects.requireNonNull(this.getSystemService(Context.INPUT_METHOD_SERVICE))).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}