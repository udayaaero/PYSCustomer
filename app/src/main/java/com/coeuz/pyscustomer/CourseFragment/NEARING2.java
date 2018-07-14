package com.coeuz.pyscustomer.CourseFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.coeuz.pyscustomer.R;
import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by vjy on 28-Mar-18.
 */

public class NEARING2 extends Fragment{
    String mToken;
    TinyDB mtinyTb;

    private TextView mslotReccurence,mCourseStartDate,mCourseEndDate,mslotStartTime,mslotEndTime;

    private TextView mvendorName,msubActivityType,mbookingStatus,mpersonCount,mbookingtimeStamp,mbookedforDate,
            mslotReccurence1,mCourseStartDate1,mCourseEndDate1,mslotStartTime1,mslotEndTime1;


    private Button getMoreDetails,back;
    private CardView firstView,SecondView;
    private String mBookingid;

    RelativeLayout noValuesLayout;
    private LinearLayout noInternetLayout;
    private RelativeLayout allViewLayout;
    private ProgressBar progressBar;
    public NEARING2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_course_active, container, false);
        mtinyTb = new TinyDB(getActivity());
        mToken = mtinyTb.getString(Constant.TOKEN);

        mslotReccurence=(TextView)view.findViewById(R.id.slotReccurence);
        mCourseStartDate=(TextView)view.findViewById(R.id.CourseStartDate);
        mCourseEndDate=(TextView)view.findViewById(R.id.CourseEndDate);
        mslotStartTime=(TextView)view.findViewById(R.id.slotStartTime);
        mslotEndTime=(TextView)view.findViewById(R.id.slotEndTime);


        mvendorName=(TextView)view.findViewById(R.id.vendorName);
        msubActivityType=(TextView)view.findViewById(R.id.ActivityType);
        mbookingStatus=(TextView)view.findViewById(R.id.bookingStatus);
        mpersonCount=(TextView)view.findViewById(R.id.personCount);
        mbookingtimeStamp=(TextView)view.findViewById(R.id.bookingtimeStamp);
        mbookedforDate=(TextView)view.findViewById(R.id.bookedforDate);

        mslotReccurence1=(TextView)view.findViewById(R.id.slotReccurence1);
        mCourseStartDate1=(TextView)view.findViewById(R.id.CourseStartDate1);
        mCourseEndDate1=(TextView)view.findViewById(R.id.CourseEndDate1);
        mslotStartTime1=(TextView)view.findViewById(R.id.slotStartTime1);
        mslotEndTime1=(TextView)view.findViewById(R.id.slotEndTime1);

        getMoreDetails=(Button) view.findViewById(R.id.getMoreDetails);
        back=(Button)view.findViewById(R.id.backBtn);
        firstView=(CardView) view.findViewById(R.id.firstView);
        SecondView=(CardView) view.findViewById(R.id.SecondView);
        progressBar=(ProgressBar) view.findViewById(R.id.progressbar400);

        noValuesLayout=(RelativeLayout)view.findViewById(R.id.noValuesLayout);
        noValuesLayout.setVisibility(View.GONE);

        noInternetLayout = (LinearLayout)view.findViewById(R.id.NoInternetLayout);
        allViewLayout = (RelativeLayout)view.findViewById(R.id.allViewlayout);

        firstView.setVisibility(View.GONE);
        SecondView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        String URL = Constant.APIONE + "/slot/getCourseBookingHistory?type=COURSE";
        StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("frefgerw", response);

                firstView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.length() == 0) {
                         noValuesLayout.setVisibility(View.VISIBLE);
                        allViewLayout.setVisibility(View.GONE);        /* Toast toast = Toast.makeText(SubActivity.this, "No Values1", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();*/
                    } else {


                        String slotStatus = jsonObject.getString("slotStatus");
                        if(slotStatus.equals("NEARING")) {
                            mBookingid = jsonObject.getString("bookingId");
                            String slotReccurence = jsonObject.getString("slotReccurence");
                            String courseStartDate = jsonObject.getString("courseStartDate");
                            String courseEndDate = jsonObject.getString("courseEndDate");
                            String slotStartTime = jsonObject.getString("slotStartTime");
                            String slotEndTime = jsonObject.getString("slotEndTime");

                            mslotReccurence.setText(slotReccurence);
                            mCourseStartDate.setText(courseStartDate);
                            mCourseEndDate.setText(courseEndDate);
                            mslotStartTime.setText(slotStartTime);
                            mslotEndTime.setText(slotEndTime);
                        }else{ noValuesLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);}
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("fhuwie", String.valueOf(error));

                firstView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

                if (error instanceof NetworkError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button=(Button)view.findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getFragmentManager()
                                    .beginTransaction()
                                    .detach(NEARING2.this)
                                    .attach(NEARING2.this)
                                    .commit();
                        }});
                } else if (error instanceof ServerError) {

                    Log.d("heuiwirhu1", String.valueOf(error));
                } else if (error instanceof ParseError) {
                    Toast.makeText(getActivity(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button=(Button)view.findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getFragmentManager()
                                    .beginTransaction()
                                    .detach(NEARING2.this)
                                    .attach(NEARING2.this)
                                    .commit();
                        }});

                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers1 = new HashMap<String, String>();

                headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
                return headers1;

            }
        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity());
        requestQueue1.add(request1);

        getMoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                String URL = Constant.APIONE + "/slot/getBookingHistoryForCourseAndMembership?bookingId="+mBookingid+"&type=course";
                StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("trct5", response);


                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0) {
                                noValuesLayout.setVisibility(View.VISIBLE);
                                allViewLayout.setVisibility(View.GONE);
                               /* Toast toast = Toast.makeText(SubActivity.this, "No Values1", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();*/
                            } else {
                                firstView.setVisibility(View.GONE);
                                SecondView.setVisibility(View.VISIBLE);
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);

                                    String vendorName = jsonObject.getString("vendorName");
                                    String subActivityType = jsonObject.getString("subActivityType");
                                    String bookingStatus = jsonObject.getString("bookingStatus");
                                    String personCount = jsonObject.getString("personCount");
                                    String slotReccurence = jsonObject.getString("slotReccurence");
                                    String bookingtimeStamp = jsonObject.getString("bookingtimeStamp");
                                    String bookedforDate = jsonObject.getString("bookedforDate");
                                    String courseStartDate = jsonObject.getString("courseStartDate");
                                    String courseEndDate = jsonObject.getString("courseEndDate");
                                    String slotStartTime = jsonObject.getString("slotStartTime");
                                    String slotEndTime = jsonObject.getString("slotEndTime");

                                    mvendorName.setText(vendorName);
                                    msubActivityType.setText(subActivityType);
                                    mbookingStatus.setText(bookingStatus);
                                    mpersonCount.setText(personCount);
                                    mbookingtimeStamp.setText(bookingtimeStamp);
                                    mbookedforDate.setText(bookedforDate);

                                    mslotReccurence1.setText(slotReccurence);
                                    mCourseStartDate1.setText(courseStartDate);
                                    mCourseEndDate1.setText(courseEndDate);
                                    mslotStartTime1.setText(slotStartTime);
                                    mslotEndTime1.setText(slotEndTime);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("yretc", String.valueOf(error));



                        if (error instanceof NetworkError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button=(Button)view.findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    getFragmentManager()
                                            .beginTransaction()
                                            .detach(NEARING2.this)
                                            .attach(NEARING2.this)
                                            .commit();
                                }});
                        } else if (error instanceof ServerError) {

                            Log.d("heuiwirhu1", String.valueOf(error));
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getActivity(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(getActivity(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            noInternetLayout.setVisibility(View.VISIBLE);
                            allViewLayout.setVisibility(View.GONE);
                            Button button=(Button)view.findViewById(R.id.TryAgain);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    getFragmentManager()
                                            .beginTransaction()
                                            .detach(NEARING2.this)
                                            .attach(NEARING2.this)
                                            .commit();
                                }});

                        }
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers1 = new HashMap<String, String>();

                        headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
                        return headers1;

                    }
                };
                RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity());
                requestQueue1.add(request1);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstView.setVisibility(View.VISIBLE);
                SecondView.setVisibility(View.GONE);
            }
        });


        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
}