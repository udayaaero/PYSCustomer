package com.coeuz.pyscustomer.AdapterClass;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.coeuz.pyscustomer.AfterSelectVendor;
import com.coeuz.pyscustomer.ModelClass.SubActivityModel;
import com.coeuz.pyscustomer.R;
import com.coeuz.pyscustomer.requiredclass.Constant;
import com.coeuz.pyscustomer.requiredclass.TinyDB;
import com.coeuz.pyscustomer.requiredclass.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NearVendorSLotsAdapter extends RecyclerView.Adapter<NearVendorSLotsAdapter.MyViewHolder> {
    public Context mcontext;
    private ArrayList<SubActivityModel> subActivityModel;
    private ArrayList<String> slotTypeList=new ArrayList<>();
    private TinyDB mtinyDb;
    private String mBookingType,mcBookingType;



    public NearVendorSLotsAdapter(Context applicationContext, ArrayList<SubActivityModel> subActivityModels) {
        this.mcontext=applicationContext;
        this.subActivityModel = subActivityModels;
        mtinyDb=new TinyDB(mcontext);
    }


    @Override
    public NearVendorSLotsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(subActivityModel.size()==1){
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.near_you_adapter1, parent, false));
        }else{
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.near_you_adapter, parent, false));
        }}
        // inflate your custom row layout here


    @Override
    public void onBindViewHolder(NearVendorSLotsAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        // update your data here

        holder.nNameOfVendor.setText(subActivityModel.get(position).getVendorName());
        holder.nAdressOfvendor.setText(subActivityModel.get(position).getArea());
        final String backGroundimage=subActivityModel.get(position).getVendorShopImage();
        if(!backGroundimage.equals("")){
            byte[] images= Base64.decode(backGroundimage,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(images,0,images.length);
            holder.mImage.setImageBitmap(bitmap);
        }

        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String backGroundImages=subActivityModel.get(position).getVendorShopImage();
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


                final String vendorId= String.valueOf(mVendorIds);

                String URL = Constant.API +"/general/getVendorByVendorId?vendorId="+vendorId;

                StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



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
                                mtinyDb.putString(Constant.VENDORAREA,mArea);
                                String msubActivityId=mtinyDb.getString("activityId");

                                slotTypeList.clear();
                                String URL10 = Constant.API +"/user/getSlotTypesBySubActivityIdAndVendorId?subActivityId="+msubActivityId+"&vendorId="+vendorId;

                                StringRequest request10 = new StringRequest(Request.Method.GET, URL10, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        mtinyDb.putString(Constant.BACKGROUNDIMAGE,backGroundImages);

                                        try {
                                            JSONArray jsonArray = new JSONArray(response);

                                            if (jsonArray.length() == 0) {

                                                mProgressDialog.dismiss();
                                                mBookingType="";
                                                mtinyDb.putString(Constant.BOOKINGTYPE,mBookingType);
                                                mcBookingType="";
                                                mtinyDb.putString(Constant.MCBOOKINGTYPE,mcBookingType);

                                                Intent intent=new Intent(mcontext, AfterSelectVendor.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putString("positionValue",clickedItem);
                                                bundle.putString("mVendorName",mVendorName);
                                                bundle.putInt("mVendorIds",mVendorIds);
                                                bundle.putString("mArea",mArea);
                                                intent.putExtras(bundle);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                mcontext.startActivity(intent);

                                            } else {
                                                for (int i = 0; i < jsonArray.length(); i++) {
                                                    String slotTypes = String.valueOf(jsonArray.get(i));


                                                    slotTypeList.add(slotTypes);
                                                }
                                                mBookingType="";
                                                mtinyDb.putString(Constant.BOOKINGTYPE,mBookingType);
                                                mcBookingType="";
                                                mtinyDb.putString(Constant.MCBOOKINGTYPE,mcBookingType);


                                                if(slotTypeList.contains("PRE_DEFINED_SLOT")){
                                                    mBookingType="PRE_DEFINED_SLOT";
                                                    mtinyDb.putString(Constant.BOOKINGTYPE,mBookingType);
                                                }else if(slotTypeList.contains("CONSECUTIVE")){
                                                    mBookingType="CONSECUTIVE";
                                                    mtinyDb.putString(Constant.BOOKINGTYPE,mBookingType);
                                                }
                                                if(slotTypeList.contains("MEMBERSHIP")){
                                                    mcBookingType="MEMBERSHIP";
                                                    mtinyDb.putString(Constant.MCBOOKINGTYPE,mcBookingType);


                                                }else if(slotTypeList.contains("COURSE")){

                                                    mcBookingType="COURSE";
                                                    mtinyDb.putString(Constant.MCBOOKINGTYPE,mcBookingType);


                                                }
                                                mProgressDialog.dismiss();
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


                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        mProgressDialog.dismiss();
                                        if (error instanceof NetworkError) {
                                            Toast.makeText(mcontext, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                                        } else if (error instanceof ServerError) {

                                        }  else if (error instanceof ParseError) {
                                            Toast.makeText(mcontext, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                                        }  else if (error instanceof TimeoutError) {
                                            Toast.makeText(mcontext, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                                VolleySingleton.getInstance(mcontext).addToRequestQueue(request10);



                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        mProgressDialog.dismiss();
                        if (error instanceof NetworkError) {
                            Toast.makeText(mcontext, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {

                        }  else if (error instanceof ParseError) {
                            Toast.makeText(mcontext, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(mcontext, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
                VolleySingleton.getInstance(mcontext).addToRequestQueue(request1);


            }});
    }

    @Override
    public int getItemCount() {
        return subActivityModel.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        // view this our custom row layout, so intialize your variables here
        private TextView nNameOfVendor,nAdressOfvendor;
        private CardView mLayout;
        private ImageView mImage;

        MyViewHolder(View view) {
            super(view);

            nNameOfVendor=view.findViewById(R.id.NameOfVendor);
            nAdressOfvendor=view.findViewById(R.id.AdressOfVendor);
            mLayout=view.findViewById(R.id.SelectChard);
             mImage=view.findViewById(R.id.image);



        }
    }
}

