package com.coeuz.pyscustomer;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.LoginFrontPage;
import com.coeuz.pyscustomer.Requiredclass.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {


    private EditText muserName,mphoneNumber,mEmailId,mPassword;

    private String iUserName,iPhoneNumber,iEmailId,iPassword;

    private LinearLayout noInternetLayout;
    private ScrollView allViewLayout;
    private  String fromMainPage;
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fromMainPage= Objects.requireNonNull(getIntent().getExtras()).getString("mainpage");


        muserName = findViewById(R.id.Username);
        mphoneNumber = findViewById(R.id.PhoneNumber);
        mEmailId = findViewById(R.id.EmailId);

        noInternetLayout =  findViewById(R.id.NoInternetLayout);
        allViewLayout = findViewById(R.id.allViewlayout);

        mPassword =findViewById(R.id.Passwords);


        Button msignUp = findViewById(R.id.signup1);


        msignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iUserName = muserName.getText().toString();
                iPhoneNumber = mphoneNumber.getText().toString();
                iEmailId = mEmailId.getText().toString();
                iPassword = mPassword.getText().toString();

                String MobilePattern = "[0-9]{10}";
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (muserName.getText().toString().equals("")) {
                    muserName.setError("Please enter valid name");
                    muserName.requestFocus();
                } else if (mphoneNumber.getText().toString().equals("") || !mphoneNumber.getText().toString().matches(MobilePattern)) {
                    mphoneNumber.setError("Please enter valid PhoneNumber");
                    mphoneNumber.requestFocus();

                } else if (TextUtils.isEmpty(mEmailId.getText().toString())) {
                    mEmailId.setError("Please enter Email");
                    mEmailId.requestFocus();


                } else if (!mEmailId.getText().toString().trim().matches(emailPattern)) {
                    mEmailId.setError("Invalid Email Address");
                    mEmailId.requestFocus();

                } else if (TextUtils.isEmpty(mPassword.getText().toString())) {
                    mPassword.setError("Please enter Password");
                    mPassword.requestFocus();


                } else if (mPassword.getText().toString().length() < 6) {
                    mPassword.setError("Enter minimum six characters");
                    mPassword.requestFocus();


                }
                else {
                    try  {
                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
                        }
                    } catch (Exception ignored) {

                    }

                    String URL = Constant.API+ "/base/reg/user";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String Successfull = jsonObject.getString("status");

                                if (Successfull.equals("SUCCESS")) {

                                    Toast.makeText(getApplicationContext(), "Successful ", Toast.LENGTH_SHORT).show();

                                        if ( fromMainPage.equalsIgnoreCase("MainPage")) {

                                            Intent intent = new Intent(getApplicationContext(), LoginFrontPage.class);
                                            startActivity(intent);
                                            finish();
                                        }else{

                                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Already Exists!\n Please Change Your MobileNumber or Email", Toast.LENGTH_SHORT).show();
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
                                Button button=findViewById(R.id.TryAgain);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        recreate();
                                    }});
                            }else if (error instanceof ParseError) {
                                Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                            } else if (error instanceof TimeoutError) {
                                noInternetLayout.setVisibility(View.VISIBLE);
                                allViewLayout.setVisibility(View.GONE);
                                Button button=findViewById(R.id.TryAgain);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        recreate();
                                    }});

                            }
                        }
                    }) {
                        @Override
                        public byte[] getBody() {
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("customerName", iUserName);
                            hashMap.put("emailId", iEmailId);
                            hashMap.put("mobileNumber", iPhoneNumber);
                            hashMap.put("password", iPassword);
                            return new JSONObject(hashMap).toString().getBytes();
                        }

                        @Override
                        public String getBodyContentType() {
                            return "application/json";
                        }
                    };


                    VolleySingleton.getInstance(SignUpActivity.this).addToRequestQueue(stringRequest);

                }
            }
        });
    }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                this.finish();
            }

            return super.onOptionsItemSelected(item);
        }
    }

