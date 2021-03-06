package com.coeuz.pyscustomer.AdapterClass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coeuz.pyscustomer.OnItemClick;
import com.coeuz.pyscustomer.R;
import com.coeuz.pyscustomer.requiredclass.TinyDB;
import java.util.ArrayList;

public class OtherServiceAdapter extends RecyclerView.Adapter<OtherServiceAdapter.MyViewHolder> {

    public Context mcontext;
    TinyDB mtinyDb;

    private int row_index=-1;

    private OnItemClick mCallback;
    private ArrayList<String> serviceList;
    private ArrayList<String> subActivityIdList;
    private String subActivityId;
    private boolean isFirstRun=true;

    public OtherServiceAdapter(Context applicationContext, ArrayList<String> otherServiceid, ArrayList<String> motherServiceList, OnItemClick listener) {

        mcontext=applicationContext;
        this.serviceList =motherServiceList;
        this.subActivityIdList =otherServiceid;
        this.mCallback=listener;

        mtinyDb=new TinyDB(mcontext);
        subActivityId=mtinyDb.getString("activityId");

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nameOfActivity;
         RelativeLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            nameOfActivity=itemView.findViewById(R.id.nameofService);
            layout=itemView.findViewById(R.id.serviceLayout);

        }
    }

    @Override
    public OtherServiceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.other_service_adapter,parent,false);

        return new MyViewHolder(view);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onBindViewHolder(final OtherServiceAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {




       holder.nameOfActivity.setText(serviceList.get(position));


        holder.nameOfActivity.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
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
                row_index=-1;
            } else {
                holder.nameOfActivity.setBackground(ContextCompat.getDrawable(mcontext, R.drawable.button_service1));
                mCallback.onClick(subActivityIdList.get(position));
                holder.nameOfActivity.setTextColor(Color.WHITE);
                row_index=-1;
            }
          /*  selectedSlotCost.add(slotModels.get(position).getBookingCost());
            selectedSlotId.add(slotModels.get(position).getSlotId());
            selectedStartTime.add(slotModels.get(position).getSlotStartTime());
            selectedEndTime.add(slotModels.get(position).getSlotEndTime());
            String Date66=slotModels.get(position).getSlotStartTime();
            String Start66=slotModels.get(position).getSlotEndTime();
         */

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
