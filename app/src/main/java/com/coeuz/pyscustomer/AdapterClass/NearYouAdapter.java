package com.coeuz.pyscustomer.AdapterClass;

/**
 * Created by vjy on 18-Mar-18.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.coeuz.pyscustomer.AfterSelectVendor;
import com.coeuz.pyscustomer.ModelClass.SubActivityModel;
import com.coeuz.pyscustomer.R;
import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NearYouAdapter extends RecyclerView.Adapter<NearYouAdapter.MyViewHolder> {
    public Context mcontext;
    private ArrayList<SubActivityModel> subActivityModel;
    private TinyDB mtinyDb;

    //private ArrayList<Integer> ImageList=new ArrayList<Integer>();


    public NearYouAdapter(Context applicationContext, ArrayList<SubActivityModel> subActivityModels) {
        this.mcontext=applicationContext;
        this.subActivityModel = subActivityModels;
        mtinyDb=new TinyDB(mcontext);
    }


    @Override
    public NearYouAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate your custom row layout here
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.near_you_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(NearYouAdapter.MyViewHolder holder, final int position) {
        // update your data here

        holder.nNameOfVendor.setText(subActivityModel.get(position).getVendorName());
        holder.nAdressOfvendor.setText(subActivityModel.get(position).getArea());

        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 final ProgressDialog mProgressDialog;
                mProgressDialog = new ProgressDialog(mcontext);
                mProgressDialog.setMessage("Loading........");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

                final String clickedItem = String.valueOf(position);
                final String  mVendorName=subActivityModel.get(position).getVendorName();
                final String  mArea=subActivityModel.get(position).getArea();
                final Integer  mVendorIds=subActivityModel.get(position).getVendorId();

                String vendorId= String.valueOf(mVendorIds);

                String URL = Constant.API +"/general/getVendorByVendorId?vendorId="+vendorId;

                StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ewrtttty", String.valueOf(response));

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.length() == 0) {
                                mProgressDialog.dismiss();
                                Toast toast = Toast.makeText(mcontext, "No Values", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            } else {


                                String latitude = jsonObject.getString("latitude");
                                String longitude = jsonObject.getString("longitude");

                                mtinyDb.putString("latttt",latitude);
                                mtinyDb.putString("longggg",longitude);
                                mtinyDb.putString(Constant.VENDORNAME,mVendorName);
                                Log.d("fnrjnf",latitude);

                                Intent intent=new Intent(mcontext, AfterSelectVendor.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("positionValue",clickedItem);
                                bundle.putString("mVendorName",mVendorName);
                                bundle.putInt("mVendorIds",mVendorIds);
                                bundle.putString("mArea",mArea);
                                intent.putExtras(bundle);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mcontext.startActivity(intent);

                               // notifyDataSetChanged();
                                mProgressDialog.dismiss();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("eqwrwq", String.valueOf(error));
                        mProgressDialog.dismiss();
                        if (error instanceof NetworkError) {
                            Toast.makeText(mcontext, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {

                            Log.d("heuiwirhu1", String.valueOf(error));
                        }  else if (error instanceof ParseError) {
                            Toast.makeText(mcontext, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(mcontext, "NoConnectionError", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(mcontext, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
                RequestQueue requestQueue1 = Volley.newRequestQueue(mcontext);
                requestQueue1.add(request1);





            }});
    }

    @Override
    public int getItemCount() {
        return subActivityModel.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        // view this our custom row layout, so intialize your variables here
        private TextView nNameOfVendor,nAdressOfvendor;
        private ImageView mImage;
        private CardView mLayout;

        MyViewHolder(View view) {
            super(view);

            nNameOfVendor=(TextView)view.findViewById(R.id.NameOfVendor);
            nAdressOfvendor=(TextView)view.findViewById(R.id.AdressOfVendor);
            mLayout=(CardView)view.findViewById(R.id.SelectChard);
            mImage=(ImageView) view.findViewById(R.id.image);



        }
    }
}

