package com.coeuz.pyscustomer.AdapterClass;



import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.coeuz.pyscustomer.ModelClass.CourseBookingHistoryModel;
import com.coeuz.pyscustomer.R;


import java.util.ArrayList;

public class CourseBookingHistoryAdapter1 extends RecyclerView.Adapter<CourseBookingHistoryAdapter1.MyViewHolder> {

    private ArrayList<CourseBookingHistoryModel> BookingHistory;
    private Context mcontext;
    private Dialog detailDialog;
    private ImageView cancel;
    private TextView mvendorName,msubActivityType,mbookingStatus,mpersonCount,mbookingtimeStamp,mbookedforDate,
            mslotReccurence1,mCourseStartDate1,mCourseEndDate1,mslotStartTime1,mslotEndTime1;


    public CourseBookingHistoryAdapter1(FragmentActivity activity, ArrayList<CourseBookingHistoryModel> recyclerModels) {
        this.mcontext=activity;
        this.BookingHistory = recyclerModels;
  /*      TinyDB mTinyDb = new TinyDB(mcontext);
        String mToken = mTinyDb.getString(Constant.TOKEN);*/

    }




    @Override
    public CourseBookingHistoryAdapter1.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.course_bookinghistoryadapter1, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(CourseBookingHistoryAdapter1.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

      holder.bookingStatus.setText(BookingHistory.get(position).getBookingStatus());
       holder.booedforDate.setText(BookingHistory.get(position).getBookedforDate());
        holder.bookingtimeStamp.setText(BookingHistory.get(position).getBookingtimeStamp());
       holder.personcount.setText(String.valueOf(BookingHistory.get(position).getPersonCount()));
        holder.amount1.setText("Rs."+String.valueOf(BookingHistory.get(position).getAmount()));
        holder.subActivityType.setText(BookingHistory.get(position).getSubActivityType());
        holder.vendorName.setText(BookingHistory.get(position).getVendorName());
        holder.viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailDialog = new Dialog(mcontext,R.style.AppTheme);

                detailDialog.setContentView(R.layout.course_moredetail_dialog);

                detailDialog.setCancelable(true);

                cancel = detailDialog.findViewById(R.id.cancels);

                mvendorName=detailDialog.findViewById(R.id.vendorName);
                msubActivityType=detailDialog.findViewById(R.id.ActivityType);
                mbookingStatus=detailDialog.findViewById(R.id.bookingStatus);
                mpersonCount=detailDialog.findViewById(R.id.personCount);
                mbookingtimeStamp=detailDialog.findViewById(R.id.bookingtimeStamp);
                mbookedforDate=detailDialog.findViewById(R.id.bookedforDate);

                mslotReccurence1=detailDialog.findViewById(R.id.slotReccurence1);
                mCourseStartDate1=detailDialog.findViewById(R.id.CourseStartDate1);
                mCourseEndDate1=detailDialog.findViewById(R.id.CourseEndDate1);
                mslotStartTime1=detailDialog.findViewById(R.id.slotStartTime1);
                mslotEndTime1=detailDialog.findViewById(R.id.slotEndTime1);

                detailDialog.show();

                cancel.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        detailDialog.dismiss();
                    }
                });
                mvendorName.setText(BookingHistory.get(position).getVendorName());
                msubActivityType.setText(BookingHistory.get(position).getSubActivityType());
                mbookingStatus.setText(BookingHistory.get(position).getBookingStatus());
                mpersonCount.setText(String.valueOf(BookingHistory.get(position).getPersonCount()));
                mslotReccurence1.setText(BookingHistory.get(position).getSlotReccurence());
                mbookingtimeStamp.setText(BookingHistory.get(position).getBookingtimeStamp());
                mbookedforDate.setText(BookingHistory.get(position).getBookedforDate());
                mCourseStartDate1.setText(BookingHistory.get(position).getCourseStartDate());
                mCourseEndDate1.setText(BookingHistory.get(position).getCourseEndDate());
                mslotStartTime1.setText(BookingHistory.get(position).getSlotStartTime());
                mslotEndTime1.setText(BookingHistory.get(position).getSlotEndTime());

            }
        });


    }

    @Override
    public int getItemCount() {
        return BookingHistory.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        // view this our custom row layout, so intialize your variables here
        private TextView bookingStatus,booedforDate,bookingtimeStamp,
                personcount,amount1,subActivityType,vendorName;
        private Button viewMore;



        MyViewHolder(View view) {
            super(view);

            bookingStatus=view.findViewById(R.id.bookingStatusss);
            booedforDate=view.findViewById(R.id.booedforDate);
            bookingtimeStamp=view.findViewById(R.id.bookingtimeStamp);
            personcount=view.findViewById(R.id.personcount);
            amount1=view.findViewById(R.id.amount1);
            subActivityType=view.findViewById(R.id.subActivityType);
            vendorName=view.findViewById(R.id.vendorName);
            viewMore= view.findViewById(R.id.viewmore);


        }
    }
}

