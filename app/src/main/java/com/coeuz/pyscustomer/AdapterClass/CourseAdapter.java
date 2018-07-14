package com.coeuz.pyscustomer.AdapterClass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coeuz.pyscustomer.LoginActivity;
import com.coeuz.pyscustomer.PaymentActivity;
import com.coeuz.pyscustomer.R;
import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;
import com.coeuz.pyscustomer.SlotPages;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Udayakumar on 19-11-2017.
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {

    public Context mcontext;
    private TinyDB tinyDB;
    private String  mToken;
    ArrayList<String> nslotId=new ArrayList<>();
    ArrayList<String> nslotStartTimeList=new ArrayList<>();
    ArrayList<String> nslotEndTimeList=new ArrayList<>();
    ArrayList<String> nmaxAllowedList=new ArrayList<>();
    ArrayList<String> nslotReccurenceList=new ArrayList<>();
    ArrayList<String> ncourseStartDateList=new ArrayList<>();
    ArrayList<String> ncourseEndDateList=new ArrayList<>();
    ArrayList<String> ncourseRegistrationEndDateList=new ArrayList<>();
    ArrayList<String> nCourseCostList=new ArrayList<>();




    public CourseAdapter(Context applicationContext, ArrayList<String> mslotidsList, ArrayList<String> mslotStartTimeList, ArrayList<String> mslotEndTimeList, ArrayList<String> mmaxAllowedList,
                         ArrayList<String> mslotReccurenceList, ArrayList<String> mcourseStartDateList,
                         ArrayList<String> mcourseEndDateList, ArrayList<String> mcourseRegistrationEndDateList, ArrayList<String> mCourseCostList) {

        this.mcontext=applicationContext;
        this.nslotId=mslotidsList;
        this.nslotStartTimeList=mslotStartTimeList;
        this.nslotEndTimeList=mslotEndTimeList;
        this.nmaxAllowedList=mmaxAllowedList;
        this.nslotReccurenceList=mslotReccurenceList;
        this.ncourseStartDateList=mcourseStartDateList;
        this.ncourseEndDateList=mcourseEndDateList;
        this.ncourseRegistrationEndDateList=mcourseRegistrationEndDateList;
        this.nCourseCostList=mCourseCostList;


        tinyDB=new TinyDB(mcontext);
        mToken=tinyDB.getString(Constant.TOKEN);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView CourseCost,mslotStartTime,mslotEndTime
                ,mmaxAllowed,mslotReccurence,mcourseStartDate,mcourseEndDate,mcourseRegistrationEndDate;

        Button mgetCourse;
        Spinner personCount;

        public MyViewHolder(View itemView) {
            super(itemView);
            mslotStartTime = (TextView)itemView.findViewById(R.id.slotStartTime);
            mslotEndTime = (TextView)itemView.findViewById(R.id.slotEndTime);
            mmaxAllowed = (TextView)itemView.findViewById(R.id.maxAllowed);
            mslotReccurence = (TextView)itemView.findViewById(R.id.slotReccurence);
            mcourseStartDate = (TextView)itemView.findViewById(R.id.courseStartDate);
            mcourseEndDate = (TextView)itemView.findViewById(R.id.courseEndDate);
            mcourseRegistrationEndDate = (TextView)itemView.findViewById(R.id.courseRegistrationEndDate);
            CourseCost= (TextView)itemView.findViewById(R.id.CourseCost);
            mgetCourse=(Button) itemView.findViewById(R.id.getCourse);


        }
    }

    @Override
    public CourseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.course_adapter_layout,parent,false);
        MyViewHolder viewss = new MyViewHolder(view);
        return viewss;
    }
    @Override
    public void onBindViewHolder(final CourseAdapter.MyViewHolder holder, final int position) {
        holder.mslotStartTime.setText(nslotStartTimeList.get(position));
        holder.mslotEndTime.setText(nslotEndTimeList.get(position));
        holder.mmaxAllowed.setText( nmaxAllowedList.get(position));
        holder.mslotReccurence.setText(nslotReccurenceList.get(position));
        holder.mcourseStartDate.setText(ncourseStartDateList.get(position));
        holder.mcourseEndDate.setText(ncourseEndDateList.get(position));
        holder.mcourseRegistrationEndDate.setText( ncourseRegistrationEndDateList.get(position));
        holder.CourseCost.setText(nCourseCostList.get(position));


        holder.mgetCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String registrationEndDate=ncourseRegistrationEndDateList.get(position);
                final String slotId=nslotId.get(position);
                final String bookedDate=tinyDB.getString(Constant.CALENDERDATECOURSE);

                                String URL = Constant.API + "/slot/validateSlotBooking";
                                StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("fhhuiefreh", response);

                                        try {
                                            JSONObject jsonObject=new JSONObject(response);
                                            String ResponseStatus=jsonObject.getString("ResponseStatus");
                                            String status=jsonObject.getString("status");
                                            if(status.equals("false")){
                                                Intent intent = new Intent(mcontext, SlotPages.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                mcontext.startActivity(intent);
                                                ((Activity)mcontext).finish();
                                                Toast.makeText(mcontext, "Please select Another date", Toast.LENGTH_SHORT).show();

                                            }else{
                                                Intent intent = new Intent(mcontext, PaymentActivity.class);
                                                Bundle bundle = new Bundle();
                                            /*    bundle.putString("positionValue",clickedItem);
                                                bundle.putString("mVendorName",mVendorName);
                                                bundle.putInt("mVendorIds",mVendorIds);
                                                bundle.putString("mArea",mArea);*/
                                                intent.putExtras(bundle);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                mcontext.startActivity(intent);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("ryeuiryweq", error.toString());


                                    }
                                }) {
                                    @Override
                                    public byte[] getBody() throws AuthFailureError {

                                        HashMap<String, Object> hashMap = new HashMap<>();

                                        hashMap.put("bookedForDate", bookedDate);
                                        hashMap.put("bookingType", "COURSE");
                                        hashMap.put("courseRegistrationEndDate", registrationEndDate);
                                        hashMap.put("personCount", "1");
                                        hashMap.put("slotId", slotId);



                                        return new JSONObject(hashMap).toString().getBytes();

                                    }

                                    @Override
                                    public String getBodyContentType() {
                                        return "application/json";
                                    }


                                };
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
                                    public void retry(VolleyError error) throws VolleyError {

                                    }
                                });
                                RequestQueue requestQueue = Volley.newRequestQueue(mcontext);
                                requestQueue.add(request);}

                            });






        }



    @Override
    public int getItemCount() {

        return nslotStartTimeList.size();
    }

}
