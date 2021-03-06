package com.coeuz.pyscustomer.BookingHistoryFragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.coeuz.pyscustomer.AdapterClass.BookingHistoryAdapter1;
import com.coeuz.pyscustomer.ModelClass.BookingHistoryModel;
import com.coeuz.pyscustomer.R;
import com.coeuz.pyscustomer.requiredclass.Constant;
import com.coeuz.pyscustomer.requiredclass.TinyDB;
import com.coeuz.pyscustomer.requiredclass.VolleySingleton;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;


/**
 * Created by vjy on 28-Mar-18.
 */

public class NEARING extends Fragment{

    private String mToken;
    TinyDB mtinyTb;

    private ProgressBar mprogressBar;
     ProgressWheel progressWheel;

    private boolean itShouldLoadMore = true;
    private ArrayList<BookingHistoryModel> recyclerModels;
    private BookingHistoryAdapter1 recyclerAdapter;

    int mOffset=0;
    int mLimit=5;
    int temp=5;


    RelativeLayout noValuesLayout;
    private RelativeLayout allViewLayout;
    private LinearLayout noInternetLayout;
    Button button;
    RelativeLayout mainLayout;


    public NEARING() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_availed, container, false);
        if(Objects.requireNonNull(getActivity()).getIntent().getStringExtra("var")!=null)
        {
            String var_value=getActivity().getIntent().getStringExtra("var");

        }


        mtinyTb = new TinyDB(getActivity());
        mToken = mtinyTb.getString(Constant.TOKEN);
        mainLayout =view.findViewById(R.id.mainLayouts);

        noValuesLayout=view.findViewById(R.id.noValuesLayout);
        noValuesLayout.setVisibility(View.GONE);

        allViewLayout = view.findViewById(R.id.allViewlayout);

        noInternetLayout = view.findViewById(R.id.NoInternetLayout);
         button=view.findViewById(R.id.TryAgain);

        mprogressBar=view.findViewById(R.id.progressbar100);
        mprogressBar.setVisibility(View.VISIBLE);
        progressWheel =  view.findViewById(R.id.progress_wheel);
        recyclerModels = new ArrayList<>();
        recyclerAdapter = new BookingHistoryAdapter1(getActivity(),recyclerModels);

        RecyclerView recyclerView = view.findViewById(R.id.RecyclerHistoryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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

        return view;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void firstLoadData() {
        recyclerModels.clear();
        String URL = Constant.APIONE+"/slot/getBookingHistory?offset=0&limit=5";
        itShouldLoadMore = false;

        StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nvjrnier",response);
                mprogressBar.setVisibility(View.GONE);
                itShouldLoadMore = true;
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() == 0) {
                        View view = getLayoutInflater().inflate(R.layout.no_values_booking, mainLayout,false);
                        view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        mainLayout.addView(view);
                       /* Toast toast = Toast.makeText(getActivity(), "No History", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();*/
                    }else{
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);

                            String bookingStatus=jsonObject.getString("bookingStatus");
                            String bookingType = jsonObject.getString("bookingType");
                            String booedforDate = jsonObject.getString("booedforDate");
                            String bookingtimeStamp = jsonObject.getString("bookingtimeStamp");
                            String bookingid = jsonObject.getString("bookingid");
                            String personcount = jsonObject.getString("personcount");
                            String amount1 = jsonObject.getString("amount1");
                            String slotid = jsonObject.getString("slotid");
                            String startTime = jsonObject.getString("startTime");
                            String endTime = jsonObject.getString("endTime");
                            String subActivityType = jsonObject.getString("subActivityType");
                            String vendorName = jsonObject.getString("vendorName");
                            bookingtimeStamp = bookingtimeStamp.substring(11, 16);


                            try {
                                SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm", Locale.getDefault());
                                SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a",Locale.getDefault());
                                Date _24HourDt = _24HourSDF.parse(bookingtimeStamp);
                                bookingtimeStamp=_12HourSDF.format(_24HourDt);
                                bookingtimeStamp=bookingtimeStamp.replaceAll("\\.","");

                            } catch (final ParseException e) {
                                e.printStackTrace();
                            }


                            try {

                                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
                                Date date = formatter.parse(booedforDate);
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy",Locale.getDefault());
                                booedforDate = sdf.format(date);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            recyclerModels.add(new BookingHistoryModel(bookingStatus, bookingType, booedforDate, bookingtimeStamp, bookingid
                                    , personcount, amount1, slotid, subActivityType, vendorName,startTime,endTime));
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

                itShouldLoadMore = true;
                mprogressBar.setVisibility(View.GONE);

                if (error instanceof NetworkError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (getFragmentManager() != null) {
                                getFragmentManager()
                                        .beginTransaction()
                                        .detach(NEARING.this)
                                        .attach(NEARING.this)
                                        .commit();
                            }
                        }});
                } else if (error instanceof ServerError) {

                }  else if (error instanceof ParseError) {
                    Toast.makeText(getActivity(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                } else if (error instanceof TimeoutError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (getFragmentManager() != null) {
                                getFragmentManager()
                                        .beginTransaction()
                                        .detach(NEARING.this)
                                        .attach(NEARING.this)
                                        .commit();
                            }
                        }});

                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers1 = new HashMap<>();

                headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
                return headers1;

            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(request1);

    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
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

                progressWheel.setVisibility(View.GONE);


                itShouldLoadMore = true;


                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() == 0) {


                    }else{
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);

                            String bookingStatus=jsonObject.getString("bookingStatus");
                            String bookingType = jsonObject.getString("bookingType");
                            String booedforDate = jsonObject.getString("booedforDate");
                            String bookingtimeStamp = jsonObject.getString("bookingtimeStamp");
                            String bookingid = jsonObject.getString("bookingid");
                            String personcount = jsonObject.getString("personcount");
                            String amount1 = jsonObject.getString("amount1");
                            String startTime = jsonObject.getString("startTime");
                            String endTime = jsonObject.getString("endTime");
                            String slotid = jsonObject.getString("slotid");
                            String subActivityType = jsonObject.getString("subActivityType");
                            String vendorName = jsonObject.getString("vendorName");
                            bookingtimeStamp = bookingtimeStamp.substring(11, 16);


                            try {
                                SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm",Locale.getDefault());
                                SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a",Locale.getDefault());
                                Date _24HourDt = _24HourSDF.parse(bookingtimeStamp);
                                bookingtimeStamp=_12HourSDF.format(_24HourDt);
                                bookingtimeStamp=bookingtimeStamp.replaceAll("\\.","");

                            } catch (final ParseException e) {
                                e.printStackTrace();
                            }


                            try {

                                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
                                Date date = formatter.parse(booedforDate);
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy",Locale.getDefault());
                                booedforDate = sdf.format(date);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            recyclerModels.add(new BookingHistoryModel(bookingStatus, bookingType, booedforDate, bookingtimeStamp, bookingid
                                    , personcount, amount1, slotid, subActivityType, vendorName,startTime,endTime));
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

                progressWheel.setVisibility(View.GONE);

                itShouldLoadMore = true;


                if (error instanceof NetworkError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (getFragmentManager() != null) {
                                getFragmentManager()
                                        .beginTransaction()
                                        .detach(NEARING.this)
                                        .attach(NEARING.this)
                                        .commit();
                            }
                        }});
                } else if (error instanceof ServerError) {

                }  else if (error instanceof ParseError) {
                    Toast.makeText(getActivity(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                }  else if (error instanceof TimeoutError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (getFragmentManager() != null) {
                                getFragmentManager()
                                        .beginTransaction()
                                        .detach(NEARING.this)
                                        .attach(NEARING.this)
                                        .commit();
                            }
                        }});

                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers1 = new HashMap<>();

                headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
                return headers1;

            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(request1);

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (getFragmentManager() != null) {
                getFragmentManager().beginTransaction().detach(this).attach(this).commit();
            }
        }
    }

}