package com.coeuz.pyscustomer.AdapterClass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.coeuz.pyscustomer.CourseBookingSummary;
import com.coeuz.pyscustomer.R;
import com.coeuz.pyscustomer.requiredclass.Constant;
import com.coeuz.pyscustomer.requiredclass.TinyDB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class SelectCourseAdapter extends RecyclerView.Adapter<SelectCourseAdapter.MyViewHolder> {

    public Context mcontext;
    TinyDB mtinyDb;


    private ArrayList<String> nslotId;
    private ArrayList<String> nslotStartTimeList;
    private ArrayList<String> nslotEndTimeList;
    private ArrayList<String> nmaxAllowedList;
    private ArrayList<String> nslotReccurenceList;
    private ArrayList<String> ncourseStartDateList;
    private ArrayList<String> ncourseEndDateList;
    private ArrayList<String> ncourseRegistrationEndDateList;
    private ArrayList<String> nCourseCostList;
    private ArrayList<String> nCourseDurationList;
    private ArrayList<String> nCoursePersonList;




    public SelectCourseAdapter(Context applicationContext, ArrayList<String> mslotidsList, ArrayList<String> mslotStartTimeList,
                               ArrayList<String> mslotEndTimeList, ArrayList<String> mmaxAllowedList,
                               ArrayList<String> mslotReccurenceList, ArrayList<String> mcourseStartDateList, ArrayList<String> mcourseEndDateList,
                               ArrayList<String> mcourseRegistrationEndDateList, ArrayList<String> mCourseCostList, ArrayList<String> mCourseDurationList, ArrayList<String> mCoursePersonList) {
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
        this.nCourseDurationList=mCourseDurationList;
        this.nCoursePersonList=mCoursePersonList;


        mtinyDb=new TinyDB(mcontext);


    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView cstartDate,cendDate,cstartTime,cendTime,cbookingCost,cRegEndDate;
        private LinearLayout selectCourse;


        public MyViewHolder(View itemView) {
            super(itemView);

            cstartDate=itemView.findViewById(R.id.cStartDates);
            cendDate=itemView.findViewById(R.id.cEndDates);
            cstartTime=itemView.findViewById(R.id.cStartTime);
            cendTime=itemView.findViewById(R.id.cEndTime);
            cbookingCost=itemView.findViewById(R.id.cbookCost);
            cRegEndDate=itemView.findViewById(R.id.cRegEndDate);
            selectCourse=itemView.findViewById(R.id.select_course);

        }
    }

    @Override
    public SelectCourseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.select_course_layout,parent,false);

        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final SelectCourseAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {


        holder.cstartDate.setText(ncourseStartDateList.get(position));
        holder.cendDate.setText(ncourseEndDateList.get(position));
        holder.cstartTime.setText(nslotStartTimeList.get(position)+" - "+nslotEndTimeList.get(position));
        holder.cendTime.setText(nCourseDurationList.get(position)+" days");
        holder.cbookingCost.setText("Rs."+nCourseCostList.get(position));
        holder.cRegEndDate.setText(ncourseRegistrationEndDateList.get(position));
        holder.selectCourse.setOnClickListener(new View.OnClickListener() {
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
                String courseDuration=nCourseDurationList.get(position);
                String personCount=nCoursePersonList.get(position);
                mtinyDb.putString(Constant.PERSONCOUNT,personCount);


                try {
                    final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",Locale.getDefault());
                    final Date dateObj = sdf.parse(courseStartTime );
                    String timein12Format=new SimpleDateFormat("hh:mm a",Locale.getDefault()).format(dateObj);

                    courseStartTime=String.valueOf(timein12Format);

                    courseStartTime = courseStartTime.replace(".", "");
                } catch (final ParseException e) {
                    e.printStackTrace();
                }
                try {
                    final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",Locale.getDefault());
                    final Date dateObj = sdf.parse(courseendTime );
                    String timein12Format=new SimpleDateFormat("hh:mm a",Locale.getDefault()).format(dateObj);

                    courseendTime=String.valueOf(timein12Format);
                    courseendTime = courseendTime.replace(".", "");
                } catch (final ParseException e) {
                    e.printStackTrace();
                }
                mtinyDb.putString(Constant.PAYMENTSTARTTIME,courseStartTime);
                mtinyDb.putString(Constant.PAYMENTENDTIME,courseendTime);


                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("hh:mmaa", Locale.getDefault());
                String bookingTime = mdformat.format(calendar.getTime());
                bookingTime = bookingTime.replace(".", "");
                mtinyDb.putString(Constant.COURSESLOTID,courseSlotId);
                mtinyDb.putString("SlotbookingTime",bookingTime);
                mtinyDb.putString("SlotbookingCost",courseBookinCost);
                mtinyDb.putString(Constant.SELECTEDTYPE,"COURSE");

                mtinyDb.putString("courseSlotId",courseSlotId);
                mtinyDb.putString("courseStartTime",courseStartTime);
                Log.d("fwrewfrewf",courseStartTime+"----"+courseendTime+"----"+bookingTime);
                mtinyDb.putString("courseendTime",courseendTime);
                mtinyDb.putString("courseMaxAllowed",courseMaxAllowed);
                mtinyDb.putString("courseReccurence",courseReccurence);
                mtinyDb.putString("courseStartDate",courseStartDate);
                mtinyDb.putString("courseEndDate",courseEndDate);
                mtinyDb.putString("courseRegistrationEndDate",courseRegistrationEndDate);
                mtinyDb.putString("courseDuration",courseDuration);
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
