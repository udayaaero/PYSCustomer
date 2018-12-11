package com.coeuz.pyscustomer;


import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText mpas,mnewpas,mnewConformpass;
    private String Token;
    private String mCurrentPassword,mNewPassword,mConformPassword;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TinyDB newTinyDB = new TinyDB(getApplicationContext());
        Token = newTinyDB.getString(Constant.TOKEN);

        mpas = findViewById(R.id.oldpassword);
        mnewpas =  findViewById(R.id.passwordnew);
        mnewConformpass =  findViewById(R.id.passwordnewconform);
        Button mupdatepass = findViewById(R.id.updatepasswor);

        mupdatepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                mCurrentPassword = mpas.getText().toString().trim();
                mNewPassword = mnewpas.getText().toString().trim();
                mConformPassword = mnewConformpass.getText().toString().trim();



                if (TextUtils.isEmpty(mpas.getText().toString())) {

                    mpas.setError("Please enter password");
                    mpas.requestFocus();
                    return;

                }else
                if (TextUtils.isEmpty(mnewpas.getText().toString())) {

                    mnewpas.setError("Please enter new password");
                    mnewpas.requestFocus();
                    return;

                }else
                if (mnewpas.getText().toString().equals(mpas.getText().toString())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Enter Different Password than Old Password", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;

                }else
                if (mnewpas.getText().toString().length() < 6) {

                    mnewpas.setError("Enter minimum six characters");
                    mnewpas.requestFocus();
                    return;
                }else
                if (TextUtils.isEmpty(mnewConformpass.getText().toString())) {

                    mnewConformpass.setError("Please enter confirm password");
                    mnewConformpass.requestFocus();
                } else  if (!mNewPassword.matches(mConformPassword)){

                    mnewConformpass.setError("Password does not match");
                    mnewConformpass.requestFocus();
                }
                else {
                    // Toast.makeText(getActivity(), "Your password Updated", Toast.LENGTH_SHORT).show();
                    String URL = Constant.APIONE + "/user/changeCurrentPassword?currPass=" + mCurrentPassword + "&newPass=" + mConformPassword;
                    StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(getApplicationContext(), "Your Password is Updated", Toast.LENGTH_LONG).show();

                           Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                            startActivity(intent);
                            finish();


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
                    }) {
                        @Override
                        public Map<String, String> getHeaders() {
                            HashMap<String, String> headers = new HashMap<>();

                            headers.put("X-Auth-Token", String.valueOf(Token).replaceAll("\"", ""));

                            return headers;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(request);

                }


            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}