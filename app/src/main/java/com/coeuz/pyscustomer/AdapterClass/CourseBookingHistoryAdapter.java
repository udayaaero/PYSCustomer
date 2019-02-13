package com.coeuz.pyscustomer.AdapterClass;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.FragmentActivity;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.coeuz.pyscustomer.CourseActivity;
import com.coeuz.pyscustomer.ModelClass.CourseBookingHistoryModel;
import com.coeuz.pyscustomer.R;
import com.coeuz.pyscustomer.requiredclass.Constant;
import com.coeuz.pyscustomer.requiredclass.TinyDB;
import com.coeuz.pyscustomer.requiredclass.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CourseBookingHistoryAdapter extends RecyclerView.Adapter<CourseBookingHistoryAdapter.MyViewHolder> {

    private ArrayList<CourseBookingHistoryModel> BookingHistory;
    private Context mcontext;
    private Dialog detailDialog;
    private ImageView cancel;
    private String  mToken;
    private TextView mvendorName,msubActivityType,mbookingStatus,mpersonCount,mbookingtimeStamp,mbookedforDate,
            mslotReccurence1,mCourseStartDate1,mCourseEndDate1,mslotStartTime1,mslotEndTime1;


    public CourseBookingHistoryAdapter(FragmentActivity activity, ArrayList<CourseBookingHistoryModel> recyclerModels) {
        this.mcontext=activity;
        this.BookingHistory = recyclerModels;
        TinyDB mTinyDb = new TinyDB(mcontext);
        mToken= mTinyDb.getString(Constant.TOKEN);
       // TinyDB mTinyDb = new TinyDB(mcontext);
     //   String mToken = mTinyDb.getString(Constant.TOKEN);

    }




    @Override
    public CourseBookingHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.course_bookinghistoryadapter, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(CourseBookingHistoryAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {


      holder.bookingStatus.setText(BookingHistory.get(position).getBookingStatus());
      if(holder.bookingStatus.getText().toString().equals("CONFIRMED")){
          holder.cancelSlot.setVisibility(View.VISIBLE);
          holder.otp.setText(String.valueOf(BookingHistory.get(position).getOtp()));
      }else{
          holder.cancelSlot.setVisibility(View.GONE);
      }
       holder.booedforDate.setText(BookingHistory.get(position).getBookedforDate());
        holder.bookingtimeStamp.setText(BookingHistory.get(position).getBookingtimeStamp());
       holder.personcount.setText(String.valueOf(BookingHistory.get(position).getPersonCount()));
        holder.amount1.setText("Rs."+String.valueOf(BookingHistory.get(position).getAmount()));
        holder.subActivityType.setText(BookingHistory.get(position).getSubActivityType());
        holder.vendorName.setText(BookingHistory.get(position).getVendorName());
        holder.viewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailDialog = new Dialog(mcontext,R.style.AppTheme);

                detailDialog.setContentView(R.layout.course_moredetail_dialog);

                detailDialog.setCancelable(true);

                cancel =  detailDialog.findViewById(R.id.cancels);

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
        holder.cancelSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String bookingid= String.valueOf(BookingHistory.get(position).getBookingid());
                final ProgressDialog mProgressDialog;
                mProgressDialog = new ProgressDialog(mcontext);
                mProgressDialog.setMessage("Loading........");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

                String URL = Constant.APIONE +"/slot/cancelBooking?bookingId="+bookingid;
                StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mProgressDialog.dismiss();
                    /*    BookingHistory.remove(position);
                        notifyDataSetChanged();*/

                        Intent intent = new Intent(mcontext, CourseActivity.class);
                        mcontext.startActivity(intent);
                        ((Activity)mcontext).finish();
                    /*    try {
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        mProgressDialog.dismiss();


                    }}){





                    @Override
                    public Map<String, String> getHeaders() {
                        HashMap<String, String> headers1 = new HashMap<>();

                        headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
                        return headers1;

                    }
                };
                request.setRetryPolicy(new RetryPolicy() {
                    @Override
                    public int getCurrentTimeout() {
                        return 200000;
                    }

                    @Override
                    public int getCurrentRetryCount() {
                        return 200000;
                    }

                    @Override
                    public void retry(VolleyError error) {

                    }
                });
                VolleySingleton.getInstance(mcontext).addToRequestQueue(request);


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
                personcount,amount1,subActivityType,vendorName,otp;
        private Button cancelSlot,viewmore;



        MyViewHolder(View view) {
            super(view);

            bookingStatus=view.findViewById(R.id.bookingStatusss);
            booedforDate=view.findViewById(R.id.booedforDate);
            bookingtimeStamp=view.findViewById(R.id.bookingtimeStamp);
            personcount=view.findViewById(R.id.personcount);
            amount1=view.findViewById(R.id.amount1);
            subActivityType=view.findViewById(R.id.subActivityType);
            vendorName=view.findViewById(R.id.vendorName);
            cancelSlot= view.findViewById(R.id.cancelSlot);
            viewmore=view.findViewById(R.id.viewmore);
            otp=view.findViewById(R.id.otp);


        }
    }
}

