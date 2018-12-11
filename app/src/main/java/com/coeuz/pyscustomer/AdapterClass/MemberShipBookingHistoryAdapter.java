package com.coeuz.pyscustomer.AdapterClass;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;

import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coeuz.pyscustomer.CourseActivity;
import com.coeuz.pyscustomer.ModelClass.MembershipBookingHistoryModel;
import com.coeuz.pyscustomer.R;
import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;
import com.coeuz.pyscustomer.Requiredclass.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MemberShipBookingHistoryAdapter extends RecyclerView.Adapter<MemberShipBookingHistoryAdapter.MyViewHolder> {

    private ArrayList<MembershipBookingHistoryModel> BookingHistory;
    private Context mcontext;
    private Dialog detailDialog;
    private ImageView cancel;
    private String  mToken;
    private TextView mvendorName,msubActivityType,mbookingStatus,mpersonCount,mbookingtimeStamp,mbookedforDate,
            mslotReccurence1,mCourseStartDate1,mCourseEndDate1,mslotStartTime1,mslotEndTime1;



    public MemberShipBookingHistoryAdapter(FragmentActivity activity, ArrayList<MembershipBookingHistoryModel> recyclerModels) {
        this.mcontext=activity;
        this.BookingHistory = recyclerModels;
        TinyDB mTinyDb = new TinyDB(mcontext);
        mToken= mTinyDb.getString(Constant.TOKEN);
      //  TinyDB mTinyDb = new TinyDB(mcontext);
       // String mToken = mTinyDb.getString(Constant.TOKEN);
    }


    @Override
    public MemberShipBookingHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.member_bookinghistoryadapter, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MemberShipBookingHistoryAdapter.MyViewHolder holder, final int position) {

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
        holder.membershipType.setText(String.valueOf(BookingHistory.get(position).getMemberShipType()));
        holder.cancelSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String bookingid= String.valueOf(BookingHistory.get(position).getBookingId());

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
        private TextView bookingStatus,booedforDate,bookingtimeStamp,membershipType,
                personcount,amount1,subActivityType,vendorName,otp;
        private Button cancelSlot;



        MyViewHolder(View view) {
            super(view);

            bookingStatus=view.findViewById(R.id.bookingStatusss);
            booedforDate=view.findViewById(R.id.booedforDate);
            bookingtimeStamp=view.findViewById(R.id.bookingtimeStamp);
            personcount=view.findViewById(R.id.personcount);
            amount1=view.findViewById(R.id.amount1);
            subActivityType=view.findViewById(R.id.subActivityType);
            vendorName=view.findViewById(R.id.vendorName);
            membershipType=view.findViewById(R.id.memberType);
            cancelSlot= view.findViewById(R.id.cancelSlot);
            otp=view.findViewById(R.id.otp);

        }
    }
}

