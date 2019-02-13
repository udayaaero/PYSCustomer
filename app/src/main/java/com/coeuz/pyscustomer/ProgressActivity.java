package com.coeuz.pyscustomer;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.coeuz.pyscustomer.requiredclass.Constant;
import com.coeuz.pyscustomer.requiredclass.TinyDB;
import com.coeuz.pyscustomer.requiredclass.VolleySingleton;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProgressActivity extends AppCompatActivity implements PaymentResultListener {

    DilatingDotsProgressBar mDilatingDotsProgressBar;
    TinyDB mTinyDb;
    private String  mToken,subTotalCost,bookingIds;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTinyDb=new TinyDB(getApplicationContext());
        mToken=mTinyDb.getString(Constant.TOKEN);

     Intent intent=getIntent();
     if(intent!=null){
         bookingIds= Objects.requireNonNull(intent.getExtras()).getString("bookingId");
         subTotalCost=intent.getExtras().getString("subTotalCost");
         Log.d("grrwg",bookingIds+"---"+subTotalCost);

     }
        mDilatingDotsProgressBar =findViewById(R.id.progress);


        mDilatingDotsProgressBar.showNow();
        startPayment();


    }

    private void startPayment(){

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
                Intent intent=new Intent(ProgressActivity.this,LoginActivity.class);
                startActivity(intent);

            }


    }

    @Override
    public void onPaymentSuccess(String s) {
        final String offerCode=mTinyDb.getString(Constant.OFFERCODE);

        String URL1 = Constant.APIONE+"/slot/confirmBooking?paymentId="+s+"&bookingIdList="+bookingIds;

        StringRequest request = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("fwegwr",response);
                mDilatingDotsProgressBar.hideNow();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status=jsonObject.getString("status");

                    if(status.equals("SUCCESS")){

                        Intent intent = new Intent(ProgressActivity.this, ConfirmationActivity.class);
                        startActivity(intent);}
                        else{
                        Toast.makeText(ProgressActivity.this, "Your Payment failed", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProgressActivity.this, "Your Payment failed", Toast.LENGTH_SHORT).show();

                mDilatingDotsProgressBar.hideNow();
                finish();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers1 = new HashMap<>();

                headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
                headers1.put("offerCode",offerCode);
                headers1.put("offerType","Slot_offer");
                return headers1;

            }
        };
        VolleySingleton.getInstance(ProgressActivity.this).addToRequestQueue(request);


    }

    @Override
    public void onPaymentError(int i, String s) {


        Toast.makeText(this, "Your Payment failed", Toast.LENGTH_SHORT).show();

        String URL1 = Constant.APIONE+"/slot/reverseBooking?bookingIdList="+bookingIds;

        StringRequest request = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("rjfirnfr",response);
                mDilatingDotsProgressBar.hideNow();
                finish();
                // Toast.makeText(getApplicationContext(), "Your Payment is failure,please try again", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mDilatingDotsProgressBar.hideNow();
                finish();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers1 = new HashMap<>();

                headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
                return headers1;

            }
        };
        VolleySingleton.getInstance(ProgressActivity.this).addToRequestQueue(request);

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
