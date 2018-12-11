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
import com.coeuz.pyscustomer.Requiredclass.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class UpdatePasswordActivity extends AppCompatActivity {

    private EditText mMobile,mPassword;
    private String mobileNumber,mPass;
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPassword=findViewById(R.id.newPassword);
        mMobile=findViewById(R.id.mMobile);
        Button mRecover = findViewById(R.id.recover);

        mRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String MobilePattern = "[0-9]{10}";
                mPass = mPassword.getText().toString().trim();
                mobileNumber=mMobile.getText().toString().trim();
                if (!mMobile.getText().toString().matches(MobilePattern)) {
                    mMobile.setError("Please enter valid input ");
                    mMobile.requestFocus();
                }else
                if (mPassword.getText().toString().length() < 6) {
                    mPassword.setError("Enter Valid password");
                    mPassword.requestFocus();

                }else{
                String URL = Constant.API+"/base/updatePassword?mobileNumber="+mobileNumber+"&pass="+mPass;
                StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObject= new JSONObject(response);
                            String status=jsonObject.getString("status");
                            if(status.equalsIgnoreCase("SUCCESS"))  {
                                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                                finish();
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


                        if (error instanceof NetworkError) {
                            Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(getApplicationContext(), "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getApplicationContext(), "AuthFailureError", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        }  else if (error instanceof TimeoutError) {
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
                    VolleySingleton.getInstance(UpdatePasswordActivity.this).addToRequestQueue(request);
            }
            }
        });
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
