package com.coeuz.pyscustomer;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.coeuz.pyscustomer.AdapterClass.FilterAmenityAdapter;
import com.coeuz.pyscustomer.ModelClass.AmenitiesModel;
import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.VolleySingleton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FilterActivity extends AppCompatActivity {


    SeekBar customSeekBar;

    private ArrayList<AmenitiesModel> amenitiesModel;
    private FilterAmenityAdapter amenityAdapter;

    private RadioGroup radioGroup1,radioGroup2;

    private String nGender;
    private String nRelavance;
    private String progressRate;
    private String filterValues;
    private String progressRates,amenities,fromAmount,toAmount;
    private TextView mprogressValues;
    private String runAnyOne="NotRun";

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // TinyDB mtinyDb = new TinyDB(getApplicationContext());


        mprogressValues =  findViewById(R.id.progressValue);
        radioGroup1 = findViewById(R.id.radioGroup1);
      /*  RadioButton radioMale =  findViewById(R.id.radioMale);
        RadioButton radioFemale =  findViewById(R.id.radioFemale);
        RadioButton radioUnisex =  findViewById(R.id.radioUnisex);
        RadioButton radioLow =  findViewById(R.id.radioLow);
        RadioButton radioHigh =  findViewById(R.id.radioHigh);
        RadioButton amount1 =  findViewById(R.id.amount1);
        RadioButton amount2 =  findViewById(R.id.amount2);
        RadioButton amount3 =  findViewById(R.id.amount3);
        RadioButton amount4 =  findViewById(R.id.amount4);*/

        radioGroup2 = findViewById(R.id.radioGroup2);


        customSeekBar =findViewById(R.id.simpleSeekBar);

        amenitiesModel = new ArrayList<>();
        amenityAdapter = new FilterAmenityAdapter(this,amenitiesModel);

        RecyclerView recyclerView =  this.findViewById(R.id.recyclerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(amenityAdapter);


        customSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                progressRate= String.valueOf(progressChangedValue);
                mprogressValues.setText(progressRate);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                /*Toast.makeText(FilterActivity.this, "Seek bar progress is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();*/
                progressRate= String.valueOf(progressChangedValue);
                mprogressValues.setText(progressRate);
            }
        });
        amenitiesModel.clear();
        String URL = Constant.API+"/general/getAllAmenities";

        StringRequest request1 = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() == 0) {
                        Toast toast = Toast.makeText(FilterActivity.this, "No Values", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }else{
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);

                            Integer amenityId=jsonObject.getInt("amenityId");
                            String amenityType=jsonObject.getString("amenityType");

                            amenitiesModel.add(new AmenitiesModel(amenityId,amenityType));
                            amenityAdapter.notifyDataSetChanged();

                        }
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


                }  else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                }  else if (error instanceof TimeoutError) {
                    Toast.makeText(getApplicationContext(), "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();

                }
            }
        })
        /*{
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers1 = new HashMap<String, String>();

                headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
                return headers1;

            }}*/
        ;
        VolleySingleton.getInstance(FilterActivity.this).addToRequestQueue(request1);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filtermenu, menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Apply) {

            List<Integer> amenitiesList = ( amenityAdapter)
                    .getStudentist();
            if(amenitiesList!=null){
                Collections.sort(amenitiesList, Collections.<Integer>reverseOrder());
                filterValues=String.valueOf(amenitiesList);

                amenities=amenitiesList.toString();
                amenities=amenities.replace("[","");
                amenities=amenities.replace("]","");
                amenities=amenities.replace(" ","");

                if(!amenities.equals("")) {

                    runAnyOne = "run";

                }
            }

            int selectedId1 = radioGroup1 .getCheckedRadioButtonId();
            int selectedId2 = radioGroup2 .getCheckedRadioButtonId();
           // int selectedId3 = radioGroup3 .getCheckedRadioButtonId();


            RadioButton radioButton1 =  findViewById(selectedId1);
            RadioButton radioButton2 = findViewById(selectedId2);
          //  RadioButton radioButton3 = findViewById(selectedId3);
            if(radioButton1!=null){
            nGender=radioButton1.getText().toString();
                filterValues=nGender;
                runAnyOne="run";
            }
            if(radioButton2!=null){
            nRelavance=radioButton2.getText().toString();
                if(nRelavance.equals("Low - High")){
                nRelavance="asc";
                    runAnyOne="run";

                }
                if(nRelavance.equals("High - Low")){
                    nRelavance="desc";
                    runAnyOne="run";

                }

                filterValues=nRelavance;
                }
        /*    if(radioButton3!=null){
                String nRate = radioButton3.getText().toString();
               String[] rate= nRate.split("-");
                fromAmount=rate[0];
                fromAmount=fromAmount.trim();
                toAmount=rate[1];
                toAmount=toAmount.replace("INR","");
                toAmount=toAmount.trim();

                filterValues= nRate;}*/


            if(progressRate!=null){
                progressRates=progressRate;
                filterValues=progressRates;
                runAnyOne="run";

            }

if(runAnyOne.equalsIgnoreCase("run")){
            Intent intent=new Intent(this,SubActivity.class);
            intent.putExtra("FilterValues",filterValues);
            intent.putExtra("nGender",nGender);
            intent.putExtra("nRelavance",nRelavance);
            intent.putExtra("fromAmount",fromAmount);
            intent.putExtra("toAmount",toAmount);
            intent.putExtra("amenitiesList",amenities);
            intent.putExtra("progressRate",progressRates);
            startActivity(intent);
            finish();}
            else{
    Toast.makeText(FilterActivity.this, "Please select any one", Toast.LENGTH_SHORT).show();
}
        }
        if (id == android.R.id.home) {
            Intent intent=new Intent(this,SubActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}