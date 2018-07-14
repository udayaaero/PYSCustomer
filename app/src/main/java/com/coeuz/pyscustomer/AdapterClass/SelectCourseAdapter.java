package com.coeuz.pyscustomer.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.coeuz.pyscustomer.CourseBookingSummary;
import com.coeuz.pyscustomer.MembershipBookingSummary;
import com.coeuz.pyscustomer.R;
import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;
import com.coeuz.pyscustomer.SlotPages;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by vjy on 01-Jul-18.
 */

public class SelectCourseAdapter extends RecyclerView.Adapter<SelectCourseAdapter.MyViewHolder> {

    public Context mcontext;
    TinyDB mtinyDb;


    ArrayList<String> nslotId=new ArrayList<>();
    ArrayList<String> nslotStartTimeList=new ArrayList<>();
    ArrayList<String> nslotEndTimeList=new ArrayList<>();
    ArrayList<String> nmaxAllowedList=new ArrayList<>();
    ArrayList<String> nslotReccurenceList=new ArrayList<>();
    ArrayList<String> ncourseStartDateList=new ArrayList<>();
    ArrayList<String> ncourseEndDateList=new ArrayList<>();
    ArrayList<String> ncourseRegistrationEndDateList=new ArrayList<>();
    ArrayList<String> nCourseCostList=new ArrayList<>();




    public SelectCourseAdapter(Context applicationContext, ArrayList<String> mslotidsList, ArrayList<String> mslotStartTimeList,
                               ArrayList<String> mslotEndTimeList, ArrayList<String> mmaxAllowedList,
                               ArrayList<String> mslotReccurenceList, ArrayList<String> mcourseStartDateList, ArrayList<String> mcourseEndDateList,
                               ArrayList<String> mcourseRegistrationEndDateList, ArrayList<String> mCourseCostList) {
        mcontext=applicationContext;
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
        Log.d("fhwuhfw", String.valueOf(nCourseCostList.size()));

        mtinyDb=new TinyDB(mcontext);


    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView types,mcost;
        private Button mbook;

        public MyViewHolder(View itemView) {
            super(itemView);

            types=(TextView)itemView.findViewById(R.id.typesOfmembership);
            mcost=(TextView)itemView.findViewById(R.id.Bookcost);
            mbook=(Button)itemView.findViewById(R.id.book);

        }
    }

    @Override
    public SelectCourseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.member_course_layout,parent,false);

        MyViewHolder viewss = new MyViewHolder(view);
        return viewss;
    }

    @Override
    public void onBindViewHolder(final SelectCourseAdapter.MyViewHolder holder, final int position) {




        holder.types.setText("Id - "+nslotId.get(position));
        holder.mcost.setText("Rs."+nCourseCostList.get(position));
        holder.mbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseSlotId=nslotId.get(position);
                String courseStartTime=nslotStartTimeList.get(position);
                String courseendTime=nslotEndTimeList.get(position);
                String courseMaxAllowed=nmaxAllowedList.get(position);
                String courseReccurence=nslotReccurenceList.get(position);
                String courseStartDate=ncourseStartDateList.get(position);
                String courseEndDate=ncourseEndDateList.get(position);
                String courseRegistrationEndDate=ncourseRegistrationEndDateList.get(position);
                String courseBookinCost=nCourseCostList.get(position);


                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("hh:mmaa");
                String bookingTime = mdformat.format(calendar.getTime());
                bookingTime = bookingTime.replace(".", "");
                mtinyDb.putString(Constant.COURSESLOTID,courseSlotId);
                mtinyDb.putString("SlotbookingTime",bookingTime);
                mtinyDb.putString("SlotbookingCost",courseBookinCost);

                mtinyDb.putString("courseSlotId",courseSlotId);
                mtinyDb.putString("courseStartTime",courseStartTime);
                mtinyDb.putString("courseendTime",courseendTime);
                mtinyDb.putString("courseMaxAllowed",courseMaxAllowed);
                mtinyDb.putString("courseReccurence",courseReccurence);
                mtinyDb.putString("courseStartDate",courseStartDate);
                mtinyDb.putString("courseEndDate",courseEndDate);
                mtinyDb.putString("courseRegistrationEndDate",courseRegistrationEndDate);
                Intent intent=new Intent(mcontext,CourseBookingSummary.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mcontext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {

        return nslotId.size();
    }
}
