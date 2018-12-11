package com.coeuz.pyscustomer;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
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
import com.coeuz.pyscustomer.Requiredclass.TinyDB;
import com.coeuz.pyscustomer.Requiredclass.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    private EditText mName,mMobileNumber,mEmail;
    private String iUserName,iPhoneNumber,iEmailId;
    private String mToken;
    private String mcustomerName,mmobileNumber,memailId;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView mChangePass =  findViewById(R.id.ChangePass);

        mChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ChangePasswordActivity.class);
                startActivity(intent);
                finish();

            }
        });


        TinyDB mTinyDb = new TinyDB(getApplicationContext());
        mToken= mTinyDb.getString(Constant.TOKEN);


        mName = findViewById(R.id.username);
        mMobileNumber = findViewById(R.id.MobileNumber);
        mEmail = findViewById(R.id.emails);
        //mCity = findViewById(R.id.city);
       // mArea = findViewById(R.id.mArea);
        Button mConfirm = findViewById(R.id.confirm);


        String URL1 = Constant.APIONE + "/user/getCurrentUser";

        StringRequest request = new StringRequest(Request.Method.GET, URL1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                   // muserId = jsonObject.getString("userId");
                    mcustomerName = jsonObject.getString("customerName");
                    mmobileNumber = jsonObject.getString("mobileNumber");
                    memailId = jsonObject.getString("emailId");

                    mName.setText(mcustomerName);
                    mMobileNumber.setText(mmobileNumber);
                    mEmail.setText(memailId);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }  else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getApplicationContext(), "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers1 = new HashMap<>();

                headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
                return headers1;

            }
        };
        VolleySingleton.getInstance(SettingsActivity.this).addToRequestQueue(request);

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iUserName = mName.getText().toString();
                iPhoneNumber = mMobileNumber.getText().toString();
                iEmailId = mEmail.getText().toString();
                //iCity = mCity.getText().toString();
                //iArea = mArea.getText().toString();

                String URL = Constant.APIONE + "/user/editProfile ";
                StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getApplicationContext(), "Your Profile is Updated", Toast.LENGTH_LONG).show();

                          /*  Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.putExtra("finish", true);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                            startActivity(intent);
                            finish();*/


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        if (error instanceof NetworkError) {
                            Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(getApplicationContext(), "Please Enter Correct Password", Toast.LENGTH_SHORT).show();

                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getApplicationContext(), "Please Check Current Password", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(getApplicationContext(), "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                {
                    @Override
                    public byte[] getBody() {
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("customerName", iUserName);
                        hashMap.put("emailId", iEmailId);
                        hashMap.put("mobileNumber", iPhoneNumber);
                     /*    hashMap.put("password", iPassword);*/
                        return new JSONObject(hashMap).toString().getBytes();
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json";
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        HashMap<String, String> headers = new HashMap<>();

                        headers.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));

                        return headers;
                    }
                };
                VolleySingleton.getInstance(SettingsActivity.this).addToRequestQueue(request);
            }




        });



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