package com.coeuz.pyscustomer.AdapterClass;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.coeuz.pyscustomer.BookingHistoryActivity;
import com.coeuz.pyscustomer.ModelClass.BookingHistoryModel;
import com.coeuz.pyscustomer.R;
import com.coeuz.pyscustomer.requiredclass.Constant;
import com.coeuz.pyscustomer.requiredclass.TinyDB;
import com.coeuz.pyscustomer.requiredclass.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.MyViewHolder> {

    private ArrayList<BookingHistoryModel> BookingHistory;
    private Context mcontext;
    private String  mToken;


    public BookingHistoryAdapter(FragmentActivity activity, ArrayList<BookingHistoryModel> recyclerModels) {
        this.mcontext=activity;
        this.BookingHistory = recyclerModels;
        TinyDB mTinyDb = new TinyDB(mcontext);
        mToken= mTinyDb.getString(Constant.TOKEN);
    }

    @Override
    public BookingHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bookinghistoryadapter, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BookingHistoryAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

      holder.bookingStatus.setText(BookingHistory.get(position).getBookingStatus());
      if(holder.bookingStatus.getText().toString().equals("CONFIRMED")){
          holder.cancelSlot.setVisibility(View.VISIBLE);
      }else{
          holder.cancelSlot.setVisibility(View.GONE);
      }
        holder.booedforDate.setText(BookingHistory.get(position).getBooedforDate());
        holder.bookingtimeStamp.setText(BookingHistory.get(position).getBookingtimeStamp());
        holder.personcount.setText(BookingHistory.get(position).getPersoncount());
        holder.amount1.setText("Rs."+BookingHistory.get(position).getAmount1());
        holder.subActivityType.setText(BookingHistory.get(position).getSubActivityType());
        holder.vendorName.setText(BookingHistory.get(position).getVendorName());
        holder.startAndEnd.setText(BookingHistory.get(position).getStartTime()+" to "+BookingHistory.get(position).getEndTime());
        holder.otp.setText("OTP - "+BookingHistory.get(position).getOtp());
        Log.d("gregetg","---"+String.valueOf(BookingHistory.get(position).getOtp()));
        holder.cancelSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String bookingid=BookingHistory.get(position).getBookingid();
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

                        Intent intent = new Intent(mcontext, BookingHistoryActivity.class);
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

        private TextView bookingStatus,booedforDate,bookingtimeStamp,
                personcount,amount1,subActivityType,vendorName,otp,startAndEnd;
        private Button cancelSlot;



        MyViewHolder(View view) {
            super(view);

            bookingStatus= view.findViewById(R.id.bookingStatusss);
            booedforDate= view.findViewById(R.id.booedforDate);
            bookingtimeStamp= view.findViewById(R.id.bookingtimeStamp);
            personcount= view.findViewById(R.id.personcount);
            amount1= view.findViewById(R.id.amount1);
            subActivityType= view.findViewById(R.id.subActivityType);
            vendorName= view.findViewById(R.id.vendorName);
            otp= view.findViewById(R.id.otp);
            cancelSlot= view.findViewById(R.id.cancelSlot);
            startAndEnd= view.findViewById(R.id.startAndEnd);


        }
    }
}

