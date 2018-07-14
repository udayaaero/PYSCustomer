package com.coeuz.pyscustomer;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coeuz.pyscustomer.Requiredclass.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {




    private EditText muserName,mphoneNumber,mEmailId,mPassword;

    private String iUserName,iPhoneNumber,iEmailId,iPassword;
    private Button msignUp;

    private LinearLayout noInternetLayout;
    private ScrollView allViewLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        muserName = (EditText) findViewById(R.id.Username);
        mphoneNumber = (EditText) findViewById(R.id.PhoneNumber);
        mEmailId = (EditText) findViewById(R.id.EmailId);

        noInternetLayout = (LinearLayout) findViewById(R.id.NoInternetLayout);
        allViewLayout = (ScrollView) findViewById(R.id.allViewlayout);

        mPassword = (EditText) findViewById(R.id.Passwords);



        msignUp = (Button) findViewById(R.id.signup1);


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
                    return;
                } else if (mphoneNumber.getText().toString().equals("") || !mphoneNumber.getText().toString().matches(MobilePattern)) {
                    mphoneNumber.setError("Please enter valid PhoneNumber");
                    mphoneNumber.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(mEmailId.getText().toString())) {
                    mEmailId.setError("Please enter Email");
                    mEmailId.requestFocus();
                    return;

                } else if (!mEmailId.getText().toString().trim().matches(emailPattern)) {
                    mEmailId.setError("Invalid Email Address");
                    mEmailId.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(mPassword.getText().toString())) {
                    mPassword.setError("Please enter Password");
                    mPassword.requestFocus();
                    return;

                } else if (mPassword.getText().toString().length() < 6) {
                    mPassword.setError("Enter minimum six characters");
                    mPassword.requestFocus();
                    return;

                }
                else {
                    try  {
                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {

                    }

                    String URL = Constant.API+ "/base/reg/user ";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("qqqqqlllllqqqq1", response);

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String Successfull = jsonObject.getString("status");

                                if (Successfull.equals("SUCCESS")) {
                                    SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.clear();
                                    editor.commit();
                                    finish();
                                    Toast.makeText(getApplicationContext(), "Successful ", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    finish();
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

                            Log.d("ooo2", error.toString());

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
                    }) {
                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
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

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);

                }
            }
        });
    }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
            }
            this.finish();
            return super.onOptionsItemSelected(item);
        }
    }

