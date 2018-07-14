package com.coeuz.pyscustomer.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coeuz.pyscustomer.OnItemClick;
import com.coeuz.pyscustomer.R;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;
import com.coeuz.pyscustomer.SubActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Udayakumar on 19-11-2017.
 */

public class OtherServiceAdapter extends RecyclerView.Adapter<OtherServiceAdapter.MyViewHolder> {

    public Context mcontext;
    TinyDB mtinyDb;

    private int row_index=-1;

    private OnItemClick mCallback;
    private ArrayList<String> serviceList=new ArrayList<>();
    private ArrayList<String> subActivityIdList=new ArrayList<>();
    private String subActivityId;
    private boolean isFirstRun=true;

    public OtherServiceAdapter(Context applicationContext, ArrayList<String> otherServiceid, ArrayList<String> motherServiceList, OnItemClick listener) {

        mcontext=applicationContext;
        this.serviceList =motherServiceList;
        this.subActivityIdList =otherServiceid;
        this.mCallback=listener;

        mtinyDb=new TinyDB(mcontext);
        subActivityId=mtinyDb.getString("activityId");
        Log.d("jwirjo",subActivityId);

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nameOfActivity;
        private RelativeLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            nameOfActivity=(TextView)itemView.findViewById(R.id.nameofService);
            layout=(RelativeLayout)itemView.findViewById(R.id.serviceLayout);

        }
    }

    @Override
    public OtherServiceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.other_service_adapter,parent,false);

        MyViewHolder viewss = new MyViewHolder(view);
        return viewss;
    }

    @Override
    public void onBindViewHolder(final OtherServiceAdapter.MyViewHolder holder, final int position) {




       holder.nameOfActivity.setText(serviceList.get(position));

        Log.d("dfewrfew", String.valueOf(subActivityIdList));
        Log.d("dfewrfew1",subActivityId);
        holder.nameOfActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(row_index==position){
                    row_index=-1;
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        holder.nameOfActivity.setBackgroundDrawable(ContextCompat.getDrawable(mcontext, R.drawable.button_service) );
                        holder.nameOfActivity.setTextColor(Color.BLACK);

                    } else {
                        mCallback.onClick(subActivityIdList.get(position));
                        holder.nameOfActivity.setBackground(ContextCompat.getDrawable(mcontext, R.drawable.button_service));
                        holder.nameOfActivity.setTextColor(Color.BLACK);
                    }
                /*    selectedSlotId.remove(slotModels.get(position).getSlotId());
                    selectedSlotCost.remove(slotModels.get(position).getBookingCost());
                    selectedStartTime.remove(slotModels.get(position).getSlotStartTime());
                    selectedEndTime.remove(slotModels.get(position).getSlotEndTime());*/
                }else {
                    row_index = position;
                    notifyDataSetChanged();


                }
            }
        });
        if(row_index==position){
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.nameOfActivity.setBackgroundDrawable(ContextCompat.getDrawable(mcontext, R.drawable.button_service1) );
                mCallback.onClick(subActivityIdList.get(position));
                holder.nameOfActivity.setTextColor(Color.WHITE);
            } else {
                holder.nameOfActivity.setBackground(ContextCompat.getDrawable(mcontext, R.drawable.button_service1));
                mCallback.onClick(subActivityIdList.get(position));
                holder.nameOfActivity.setTextColor(Color.WHITE);
            }
          /*  selectedSlotCost.add(slotModels.get(position).getBookingCost());
            selectedSlotId.add(slotModels.get(position).getSlotId());
            selectedStartTime.add(slotModels.get(position).getSlotStartTime());
            selectedEndTime.add(slotModels.get(position).getSlotEndTime());
            String Date66=slotModels.get(position).getSlotStartTime();
            String Start66=slotModels.get(position).getSlotEndTime();
            Log.d("hfiwrjfiw",Date66);*/

            //holder.layout.setBackgroundColor(Color.parseColor("#567845"));

        }
        else
        {
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.nameOfActivity.setBackgroundDrawable(ContextCompat.getDrawable(mcontext, R.drawable.button_service) );
                holder.nameOfActivity.setTextColor(Color.BLACK);
            } else {
                holder.nameOfActivity.setBackground(ContextCompat.getDrawable(mcontext, R.drawable.button_service));
                holder.nameOfActivity.setTextColor(Color.BLACK);
            }
         /*   selectedSlotCost.remove(slotModels.get(position).getBookingCost());
            selectedSlotId.remove(slotModels.get(position).getSlotId());
            selectedStartTime.remove(slotModels.get(position).getSlotStartTime());
            selectedEndTime.remove(slotModels.get(position).getSlotEndTime());*/
        }

        if(subActivityIdList.get(position).contains(subActivityId)){
            Log.d("frufwefwe1", String.valueOf(subActivityIdList));
            Log.d("frufwefwe",subActivityId);
            if (isFirstRun){
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {

                holder.nameOfActivity.setBackgroundDrawable(ContextCompat.getDrawable(mcontext, R.drawable.button_service1) );
                holder.nameOfActivity.setTextColor(Color.WHITE);
            } else {
                holder.nameOfActivity.setBackground(ContextCompat.getDrawable(mcontext, R.drawable.button_service1));
                holder.nameOfActivity.setTextColor(Color.WHITE);
            }
                isFirstRun=false;
            }

        }

    }






    @Override
    public int getItemCount() {

        return subActivityIdList.size();
    }
}
