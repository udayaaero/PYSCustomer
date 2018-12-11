package com.coeuz.pyscustomer.MembeshipFragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.coeuz.pyscustomer.AdapterClass.MemberShipBookingHistoryAdapter1;
import com.coeuz.pyscustomer.ModelClass.MembershipBookingHistoryModel;
import com.coeuz.pyscustomer.R;
import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;
import com.coeuz.pyscustomer.Requiredclass.VolleySingleton;
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

public class ACTIVE1 extends Fragment{

    String mToken;
    TinyDB mtinyTb;


    RelativeLayout noValuesLayout;
    private LinearLayout noInternetLayout;
    Button button;
    private RelativeLayout allViewLayout;


    private ProgressBar mprogressBar;
    ProgressWheel progressWheel;

    private boolean itShouldLoadMore = true;
    private ArrayList<MembershipBookingHistoryModel> recyclerModels;
    private MemberShipBookingHistoryAdapter1 recyclerAdapter;
    private RelativeLayout mainLayout;

    int mOffset=0;
    int mLimit=5;
    int temp=5;

    public ACTIVE1() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_membership_active, container, false);
        mtinyTb = new TinyDB(getActivity());
        mToken = mtinyTb.getString(Constant.TOKEN);
        mainLayout=view.findViewById(R.id.availedLayout);

        button=view.findViewById(R.id.TryAgain);


        noValuesLayout=view.findViewById(R.id.noValuesLayout);
        noValuesLayout.setVisibility(View.GONE);

        noInternetLayout = view.findViewById(R.id.NoInternetLayout);
        allViewLayout = view.findViewById(R.id.allViewlayout);


        mprogressBar=view.findViewById(R.id.progressbar100);
        mprogressBar.setVisibility(View.VISIBLE);
        progressWheel =  view.findViewById(R.id.progress_wheel);
        recyclerModels = new ArrayList<>();
        recyclerAdapter = new MemberShipBookingHistoryAdapter1(getActivity(),recyclerModels);

        RecyclerView recyclerView =  view.findViewById(R.id.RecyclerHistoryList);
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

        String URL = Constant.APIONE+"/slot/getCourseAndMembrShipBookingHistory?type=MEM&offset=0&limit=5&status=active";
        itShouldLoadMore = false;

        StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                           // String slotStatus=jsonObject.getString("slotStatus");

                                String bookingStatus=jsonObject.getString("bookingStatus");
                                String bookingType = jsonObject.getString("bookingType");
                                String bookedforDate = jsonObject.getString("bookedforDate");
                                String bookingtimeStamp = jsonObject.getString("bookingtimeStamp");
                                Integer personCount = jsonObject.getInt("personCount");
                                Double amount = jsonObject.getDouble("amount");
                                String subActivityType = jsonObject.getString("subActivityType");
                                String vendorName = jsonObject.getString("vendorName");
                                Integer bookingId = jsonObject.getInt("bookingId");
                                String memberShipType = jsonObject.getString("memberShipType");
                                bookingtimeStamp = bookingtimeStamp.substring(11, 16);
                              /*
                                String slotid = jsonObject.getString("slotid");
                                String subActivityId = jsonObject.getString("subActivityId");
                                String days = jsonObject.getString("days");

                                String ACTIVE = jsonObject.getString("ACTIVE");*/

                                try {
                                    SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm", Locale.getDefault());
                                    SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                                    Date _24HourDt = _24HourSDF.parse(bookingtimeStamp);
                                    bookingtimeStamp=_12HourSDF.format(_24HourDt);
                                    bookingtimeStamp=bookingtimeStamp.replaceAll("\\.","");

                                } catch (final ParseException e) {
                                    e.printStackTrace();
                                }

                                try {

                                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                    Date date = formatter.parse(bookedforDate);
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy", Locale.getDefault());
                                    bookedforDate = sdf.format(date);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                recyclerModels.add(new MembershipBookingHistoryModel(bookingStatus, bookingType, bookedforDate, bookingtimeStamp, bookingId
                                        , personCount, amount, subActivityType, vendorName,memberShipType));
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
                                        .detach(ACTIVE1.this)
                                        .attach(ACTIVE1.this)
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
                                        .detach(ACTIVE1.this)
                                        .attach(ACTIVE1.this)
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

        String URL = Constant.APIONE+"/slot/getCourseAndMembrShipBookingHistory?type=MEM&offset="+String.valueOf(mOffset)+"&limit="+String.valueOf(mLimit)+"&status=active";

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
                        Toast.makeText(getActivity(), "Your search is over", Toast.LENGTH_SHORT).show();
                    }else{

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            // item list = gson.fromJson(jsonObject.toString(), item.class);

                           // String slotStatus=jsonObject.getString("slotStatus");

                                String bookingStatus=jsonObject.getString("bookingStatus");
                                String bookingType = jsonObject.getString("bookingType");
                                String bookedforDate = jsonObject.getString("bookedforDate");
                                String bookingtimeStamp = jsonObject.getString("bookingtimeStamp");
                                Integer personCount = jsonObject.getInt("personCount");
                                Double amount = jsonObject.getDouble("amount");
                                String subActivityType = jsonObject.getString("subActivityType");
                                String vendorName = jsonObject.getString("vendorName");
                                Integer bookingId = jsonObject.getInt("bookingId");
                              /*  String bookingid = jsonObject.getString("bookingid");
                                String slotid = jsonObject.getString("slotid");
                                String subActivityId = jsonObject.getString("subActivityId");
                                String days = jsonObject.getString("days");
                                 String ACTIVE = jsonObject.getString("ACTIVE");*/
                                String memberShipType = jsonObject.getString("memberShipType");

                                bookingtimeStamp = bookingtimeStamp.substring(11, 16);

                                try {
                                    SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm", Locale.getDefault());
                                    SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                                    Date _24HourDt = _24HourSDF.parse(bookingtimeStamp);
                                    bookingtimeStamp=_12HourSDF.format(_24HourDt);
                                    bookingtimeStamp=bookingtimeStamp.replaceAll("\\.","");
                                } catch (final ParseException e) {
                                    e.printStackTrace();
                                }


                                try {

                                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                    Date date = formatter.parse(bookedforDate);
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy", Locale.getDefault());
                                    bookedforDate = sdf.format(date);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                recyclerModels.add(new MembershipBookingHistoryModel(bookingStatus, bookingType, bookedforDate, bookingtimeStamp, bookingId
                                        , personCount, amount, subActivityType, vendorName,memberShipType));
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
                                        .detach(ACTIVE1.this)
                                        .attach(ACTIVE1.this)
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
                                        .detach(ACTIVE1.this)
                                        .attach(ACTIVE1.this)
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
 /*   @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }*/

}