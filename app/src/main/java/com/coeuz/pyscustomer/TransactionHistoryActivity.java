package com.coeuz.pyscustomer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.coeuz.pyscustomer.AdapterClass.BookingHistoryAdapter;
import com.coeuz.pyscustomer.ModelClass.BookingHistoryModel;
import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;
import com.google.gson.Gson;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransactionHistoryActivity extends AppCompatActivity {


    private String mToken;
    TinyDB mtinyTb;

    private RecyclerView recyclerView;
    private ProgressBar mprogressBar;
    ProgressWheel progressWheel;

    private boolean itShouldLoadMore = true;
    private ArrayList<BookingHistoryModel> recyclerModels;
    private BookingHistoryAdapter recyclerAdapter;

    int mOffset=0;
    int mLimit=5;
    int temp=5;

    private LinearLayout noInternetLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mtinyTb = new TinyDB(getApplicationContext());
        mToken = mtinyTb.getString(Constant.TOKEN);

        noInternetLayout = (LinearLayout) findViewById(R.id.NoInternetLayout);
        mprogressBar=(ProgressBar)findViewById(R.id.progressbar100);
        mprogressBar.setVisibility(View.VISIBLE);
        progressWheel = (ProgressWheel)findViewById(R.id.progress_wheel);
        recyclerModels = new ArrayList<>();
        recyclerAdapter = new BookingHistoryAdapter(recyclerModels);

        recyclerView = (RecyclerView)findViewById(R.id.RecyclerHistoryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(recyclerAdapter);
        firstLoadData();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            // for this tutorial, this is the ONLY method that we need, ignore the rest
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy >0) {
                    // Recycle view scrolling downwards...
                    // this if statement detects when user reaches the end of recyclerView, this is only time we should load more
                    if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                        // remember "!" is the same as "== false"
                        // here we are now allowed to load more, but we need to be careful
                        // we must check if itShouldLoadMore variable is true [unlocked]
                        if (itShouldLoadMore) {

                            loadMore();
                        }
                    }

                }
            }
        });


    }

    private void firstLoadData() {
        recyclerModels.clear();
        String URL = Constant.APIONE+"/slot/getBookingHistory?offset=0&limit=5";
        itShouldLoadMore = false;

        StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("hgerithjiow", String.valueOf(response));
                mprogressBar.setVisibility(View.GONE);
                itShouldLoadMore = true;
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() == 0) {
                        Toast toast = Toast.makeText(getApplicationContext(), "No History", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }else{
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);

                            String bookingStatus=jsonObject.getString("bookingStatus");
                            String bookingType=jsonObject.getString("bookingType");
                            String booedforDate=jsonObject.getString("booedforDate");
                            String bookingtimeStamp=jsonObject.getString("bookingtimeStamp");
                            String bookingid=jsonObject.getString("bookingid");
                            String personcount=jsonObject.getString("personcount");
                            String amount1=jsonObject.getString("amount1");
                            String slotid=jsonObject.getString("slotid");
                            String subActivityType=jsonObject.getString("subActivityType");
                            String vendorName=jsonObject.getString("vendorName");


                            recyclerModels.add(new BookingHistoryModel(bookingStatus, bookingType,booedforDate,bookingtimeStamp,bookingid
                                    ,personcount,amount1,slotid,subActivityType,vendorName));
                            recyclerAdapter.notifyDataSetChanged();



                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("frwgtw", String.valueOf(error));

                itShouldLoadMore = true;
                mprogressBar.setVisibility(View.GONE);

                if (error instanceof NetworkError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    Button button=(Button)findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});
                } else if (error instanceof ServerError) {

                    Log.d("heuiwirhu1", String.valueOf(error));
                }  else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    Button button=(Button)findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});

                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers1 = new HashMap<String, String>();

                headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
                return headers1;

            }
        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
        requestQueue1.add(request1);

    }
    private void loadMore() {

        mOffset=temp;
        mLimit=temp+5;
        temp=mLimit;

        String URL = Constant.APIONE+"/slot/getBookingHistory?offset="+String.valueOf(mOffset)+"&limit="+String.valueOf(mLimit);

        itShouldLoadMore = false; // lock this until volley completes processing

        // progressWheel is just a loading spinner, please see the content_main.xml

        progressWheel.setVisibility(View.VISIBLE);

        StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("hgerithjiow", String.valueOf(response));

                progressWheel.setVisibility(View.GONE);


                itShouldLoadMore = true;


                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() == 0) {
                        Toast.makeText(getApplicationContext(), "Your search is over", Toast.LENGTH_SHORT).show();
                    }else{
                        Gson gson = new Gson();
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            // item list = gson.fromJson(jsonObject.toString(), item.class);

                            String bookingStatus=jsonObject.getString("bookingStatus");
                            String bookingType=jsonObject.getString("bookingType");
                            String booedforDate=jsonObject.getString("booedforDate");
                            String bookingtimeStamp=jsonObject.getString("bookingtimeStamp");
                            String bookingid=jsonObject.getString("bookingid");
                            String personcount=jsonObject.getString("personcount");
                            String amount1=jsonObject.getString("amount1");
                            String slotid=jsonObject.getString("slotid");
                            String subActivityType=jsonObject.getString("subActivityType");
                            String vendorName=jsonObject.getString("vendorName");


                            recyclerModels.add(new BookingHistoryModel(bookingStatus, bookingType,booedforDate,bookingtimeStamp,bookingid
                                    ,personcount,amount1,slotid,subActivityType,vendorName));
                            recyclerAdapter.notifyDataSetChanged();




                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("frwgtw", String.valueOf(error));
                progressWheel.setVisibility(View.GONE);

                itShouldLoadMore = true;


                if (error instanceof NetworkError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    Button button=(Button)findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});
                } else if (error instanceof ServerError) {

                    Log.d("heuiwirhu1", String.valueOf(error));
                }  else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    Button button=(Button)findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});

                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers1 = new HashMap<String, String>();

                headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
                return headers1;

            }
        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
        requestQueue1.add(request1);

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