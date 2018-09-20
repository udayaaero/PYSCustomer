package com.coeuz.pyscustomer.AdapterClass;



import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coeuz.pyscustomer.ModelClass.BookingHistoryModel;
import com.coeuz.pyscustomer.R;
import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;
import org.json.JSONObject;

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
        holder.otp.setText("OTP - "+BookingHistory.get(position).getOtp());
        holder.cancelSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String bookingid=BookingHistory.get(position).getBookingid();
                Log.d("fnuifwr",bookingid);

                final ProgressDialog mProgressDialog;
                mProgressDialog = new ProgressDialog(mcontext);
                mProgressDialog.setMessage("Loading........");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

                String URL = Constant.APIONE +"/slot/cancelBooking";
                StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mProgressDialog.dismiss();
                        Log.d("cdsfsfwe", response);
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
                        Log.d("vfdvdfdsv", error.toString());
                        Log.d("vfdvdfdsv", String.valueOf(error.networkResponse.statusCode));

                        mProgressDialog.dismiss();


                    }
                }) {
                    @Override
                    public byte[] getBody() {

                        HashMap<String, Object> hashMap = new HashMap<>();



                        hashMap.put("bookingId", bookingid);


                        return new JSONObject(hashMap).toString().getBytes();

                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json";
                    }



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
                RequestQueue requestQueue = Volley.newRequestQueue(mcontext);
                requestQueue.add(request);


            }
        });


    }

    @Override
    public int getItemCount() {
        return BookingHistory.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView bookingStatus,booedforDate,bookingtimeStamp,
                personcount,amount1,subActivityType,vendorName,otp;
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


        }
    }
}

