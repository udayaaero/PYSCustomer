package com.coeuz.pyscustomer;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.coeuz.pyscustomer.AdapterClass.OfferAdapterBookSummary;
import com.coeuz.pyscustomer.requiredclass.Constant;
import com.coeuz.pyscustomer.requiredclass.TinyDB;
import com.coeuz.pyscustomer.requiredclass.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ApplyOffer extends AppCompatActivity {
    private String mToken;

    ViewGroup progressView;

    RecyclerView offerRecycler;
    boolean isProgressShowing = false;
    private String mVendorId,msubActivityId,selectedSlotIds, offCode,offAmount,offPercentage;

    private LinearLayout noInternetLayout;
    private RelativeLayout allViewLayout,noValuesLayout;
    private TextView lightApply,brightApply;
    private EditText enterOfferCode;
    private  TinyDB mTinyDb;
    private AlertDialog alert;
    private String offerStart,offerEnd;
    ArrayList<String> offerStartList=new ArrayList<>();
    ArrayList<String> offerEndList=new ArrayList<>();
    ArrayList<String> offerTypeList=new ArrayList<>();
    ArrayList<String> offerBenefits=new ArrayList<>();
    ArrayList<Integer> offerDiscount=new ArrayList<>();
    ArrayList<String> offerCodeList=new ArrayList<>();


    JSONArray offerList = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_offer);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

          mTinyDb = new TinyDB(getApplicationContext());
        mToken= mTinyDb.getString(Constant.TOKEN);
         mVendorId = mTinyDb.getString(Constant.VENDORID);
        msubActivityId=mTinyDb.getString(Constant.PREDEFINEDSUBACTIVITYID);
        selectedSlotIds=mTinyDb.getString(Constant.PRESLOTID);


        noInternetLayout = findViewById(R.id.NoInternetLayout);
        allViewLayout = findViewById(R.id.allViewlayout);
        noValuesLayout = findViewById(R.id.noValuesLayout);
        offerRecycler=findViewById(R.id.RecyclerOffer);
        lightApply=findViewById(R.id.lightApply);
        brightApply=findViewById(R.id.brightApply);

      /*  LinearLayout moreDetails=findViewById(R.id.MoreDetails);
        moreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isProgressShowing = true;
                progressView = (ViewGroup) getLayoutInflater().inflate(R.layout.offer_details, null);
                View v = ApplyOffer.this.findViewById(android.R.id.content).getRootView();
                ViewGroup viewGroup = (ViewGroup) v;
                viewGroup.addView(progressView);
            }   });*/

        viewOffer();

        brightApply.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
                    }
                } catch (Exception e) {
                }
                offCode="";
                offAmount="";
                offPercentage="";
                offCode=enterOfferCode.getText().toString().trim();
                Log.d("t4t54t54",offCode);
                Log.d("geget", String.valueOf(offerList.length())+" grgthrt");

                for (int i = 0; i < offerList.length(); i++) {
                    try {
                    JSONObject  jsonObject = offerList.getJSONObject(i);
                    String offerCode = jsonObject.getString("offerCode");
                    Log.d("gethtr",offerCode);
                    if(offerCode.equalsIgnoreCase(offCode)){

                    String maxDiscountAmount = jsonObject.getString("maxDiscountAmount");
                    String discountPercentage = jsonObject.getString("discountPercentage");

                        offCode=offerCode;
                        offAmount=maxDiscountAmount;
                        offPercentage=discountPercentage;
                        break;
                    }else{
                        offCode="";
                        offAmount="";
                        offPercentage="";
                    }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }}


             if(offAmount!=null & !offAmount.isEmpty()){
               Log.d("thy5ju6",offCode+"---"+offAmount+"--"+offPercentage);
                mTinyDb.putString(Constant.OFFERCODE,offCode);
                mTinyDb.putString(Constant.OFFERAMOUNT,offAmount);
                mTinyDb.putString(Constant.OFFERPERCENTAGE,offPercentage);
                mTinyDb.putString("APPLIED","true");
                mTinyDb.putString("ALERT",offAmount);
                finish();}else{

                 AlertDialog.Builder builder = new AlertDialog.Builder(ApplyOffer.this);
                 LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                 View customView = inflater.inflate(R.layout.no_offer_alert, null);
                 builder.setView(customView);

                 builder.setCancelable(true);
                 builder.setInverseBackgroundForced(true);


                 Button cancel = customView.findViewById(R.id.frmOk);
                 TextView offerDetails = customView.findViewById(R.id.text1);


                 offerDetails.setText("Oops! This Coupon code is not Valid!");

                 cancel.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {

                         alert.dismiss();
                     }
                 });
                 alert = builder.create();

                 alert.show();
             }
            }
        });

        enterOfferCode=findViewById(R.id.enterCode);
        enterOfferCode.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().trim().length()==0){
                    lightApply.setVisibility(View.VISIBLE);
                    brightApply.setVisibility(View.GONE);
                } else {
                    lightApply.setVisibility(View.GONE);
                    brightApply.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });

    }

    private void viewOffer() {

        offerStartList.clear();
        offerEndList.clear();
        offerTypeList.clear();
        offerBenefits.clear();
        offerCodeList.clear();

        String URL3 = Constant.APIONE +"/general/viewOffers?vendorId="+mVendorId+"&subActivityId="+msubActivityId+"&slotId="+selectedSlotIds;

        StringRequest request3 = new StringRequest(Request.Method.GET, URL3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nvirer",response);

                try {

                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() == 0) {
                        noValuesLayout.setVisibility(View.VISIBLE);

                    } else {
                        noValuesLayout.setVisibility(View.GONE);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String startDate = jsonObject.getString("offerStartDate");
                            String expiryDate = jsonObject.getString("offerExpiryDate");
                            String offerCode = jsonObject.getString("offerCode");
                            String maxDiscountAmount = jsonObject.getString("maxDiscountAmount");
                            String discountPercentage = jsonObject.getString("discountPercentage");
                            //  String type = jsonObject.getString("type");
                            offerList.put(jsonObject);
                            Integer discount1 = jsonObject.getInt("maxDiscountAmount");

                            Long timestamp10 = Long.parseLong(startDate);
                            Long timestamp20 = Long.parseLong(expiryDate);
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                Date netDate = (new Date(timestamp10));
                                offerStart = sdf.format(netDate);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
                                Date netDate = (new Date(timestamp20));
                                offerEnd = sdf.format(netDate);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            offerDiscount.add(discount1);
                            offerStartList.add(offerStart);
                            offerEndList.add(offerEnd);
                            offerTypeList.add(maxDiscountAmount);
                            offerBenefits.add(discountPercentage);
                            offerCodeList.add(offerCode);
                            int s=0;
                            s+=discount1;

                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ApplyOffer.this);
                            offerRecycler.setLayoutManager(layoutManager);
                            RecyclerView.Adapter adapter = new OfferAdapterBookSummary(ApplyOffer.this,offerTypeList,offerEndList,offerBenefits,offerCodeList);
                            offerRecycler.setAdapter(adapter);


                        }



                      /*  for(int j = 0; j < offerDiscount.size(); j++){
                            if(offerDiscount.get(j)!=null){
                                sum += offerDiscount.get(j);}}

                        mTotalDiscount.setText(String.valueOf(sum));*/

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
                    Button button= findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});
                } else if (error instanceof ServerError) {

                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                } else if (error instanceof TimeoutError) {

                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button= findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});

                }
            }
        }){   @Override
        public Map<String, String> getHeaders() {
            HashMap<String, String> headers1 = new HashMap<>();

            headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
            return headers1;

        }};
        ;
        VolleySingleton.getInstance(ApplyOffer.this).addToRequestQueue(request3);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
    public void hideProgressingView() {
        View v = ApplyOffer.this.findViewById(android.R.id.content).getRootView();
        ViewGroup viewGroup = (ViewGroup) v;
        viewGroup.removeView(progressView);
        isProgressShowing = false;
    }

 /*
 private HashMap<String, List<JSONObject>> dateMap = new HashMap<String, List<JSONObject>>();
 Log.d("fwefw", String.valueOf(dateMap));
                Set<String> keySet = dateMap.keySet();
                for (Map.Entry<String, List<JSONObject>> entry : dateMap.entrySet()) {
                    String key = entry.getKey();
                    ArrayList<String> value = entry.getValue();
                    for(String aString : value){
                        System.out.println("key : " + key + " value : " + aString);
                    }
                }*/
}
