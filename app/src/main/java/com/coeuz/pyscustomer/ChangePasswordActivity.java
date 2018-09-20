package com.coeuz.pyscustomer;


import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
    private String CurrentPassword,ConformPassword;

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

                CurrentPassword = mpas.getText().toString().trim();
                //NewPassWord = mnewpas.getText().toString().trim();
                ConformPassword = mnewConformpass.getText().toString().trim();



                if (TextUtils.isEmpty(mpas.getText().toString())) {
                    Log.d("eeeee1", mpas.getText().toString());
                    mpas.setError("Please enter Password");
                    mpas.requestFocus();
                    return;

                }
                if (TextUtils.isEmpty(mnewpas.getText().toString())) {
                    Log.d("tttt1", mnewpas.getText().toString());
                    mnewpas.setError("Please enter NewPassword");
                    mnewpas.requestFocus();
                    return;

                }
              /*  if (mnewpas.getText().toString().equals(mpas.getText().toString())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Enter Different Password than Old Password", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;

                }*/
                if (mnewpas.getText().toString().length() < 6) {
                    Log.d("iiiiii1", mnewpas.getText().toString());
                    mnewpas.setError("Enter minimum six characters");
                    mnewpas.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(mnewConformpass.getText().toString())) {
                    Log.d("yyyyy1", mnewConformpass.getText().toString());
                    mnewConformpass.setError("Please enter Correct Password");
                    mnewConformpass.requestFocus();
                }  if ( !mnewConformpass.getText().toString().matches(mnewpas.getText().toString())) {
                    Log.d("ooooo32", mnewConformpass.getText().toString());
                    mnewConformpass.setError("Please enter Correct Password");
                    mnewConformpass.requestFocus();
                }
                else {
                    // Toast.makeText(getActivity(), "Your password Updated", Toast.LENGTH_SHORT).show();
                    String URL = Constant.APIONE + "/user/changeCurrentPassword?currPass=" + CurrentPassword + "&newPass=" + ConformPassword;
                    StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("jiwjrfijr", String.valueOf(response));
                            Toast.makeText(getApplicationContext(), "Your Password is Updated", Toast.LENGTH_LONG).show();

                          /*  Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.putExtra("finish", true);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                            startActivity(intent);
                            finish();*/


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("ppppppppppp123", String.valueOf(error));
                            Log.d("fjds", String.valueOf(error.networkResponse.statusCode));

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
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}