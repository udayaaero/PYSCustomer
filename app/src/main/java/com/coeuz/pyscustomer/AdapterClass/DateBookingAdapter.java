package com.coeuz.pyscustomer.AdapterClass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coeuz.pyscustomer.ModelClass.SlotModel;
import com.coeuz.pyscustomer.R;
import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;
import com.coeuz.pyscustomer.SlotPages;

import java.util.ArrayList;


public class DateBookingAdapter extends RecyclerView.Adapter<DateBookingAdapter.MyViewHolder> {

    public Context context;
    private TinyDB tinyDB;


    private ArrayList<String> selectedStartTime= new ArrayList<>();
    private ArrayList<String> selectedEndTime= new ArrayList<>();
    private ArrayList<SlotModel> slotModels;



    public DateBookingAdapter(Context applicationContext, ArrayList<SlotModel> slotModel) {
        this.context=applicationContext;
        this.slotModels=slotModel;
        tinyDB=new TinyDB(context);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView startTime,Endtime,cost;
        private LinearLayout layout;
        public int position=0;
        public MyViewHolder(View itemView) {
            super(itemView);
            startTime=itemView.findViewById(R.id.Starttimee);
            Endtime=itemView.findViewById(R.id.Endtimee);
            cost=itemView.findViewById(R.id.cost);
            layout=itemView.findViewById(R.id.timemorning);
        }
    }

    @Override
    public DateBookingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.datebookingadapter,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DateBookingAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.startTime.setText(slotModels.get(position).getSlotStartTime());
        holder.Endtime.setText(slotModels.get(position).getSlotEndTime());
        Log.d("fhewuifhew,", String.valueOf(slotModels.get(position).getBookingCost()));
        holder.cost.setText(String.valueOf(slotModels.get(position).getBookingCost()));


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookingStartTime=slotModels.get(position).getSlotStartTime();
                String bookingEndTime=slotModels.get(position).getSlotEndTime();
                String bookingCost= String.valueOf(slotModels.get(position).getBookingCost());
                String slotIds= String.valueOf(slotModels.get(position).getSlotId());
                tinyDB.putString("SlotbookingStartTime",bookingStartTime);
                tinyDB.putString("SlotbookingEndTime",bookingEndTime);
                tinyDB.putString("SlotbookingCost",bookingCost);
                tinyDB.putString(Constant.PRESLOTID,slotIds);
                tinyDB.putString(Constant.SELECTEDTYPE,"PRE-DEFINED");

                tinyDB.putString(Constant.PAYMENTSTARTTIME,bookingStartTime);
                tinyDB.putString(Constant.PAYMENTENDTIME,bookingEndTime);

                Intent intent=new Intent(context,SlotPages.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);

    }
        });}


    @Override
    public int getItemCount() {

        return slotModels.size();
    }


    public ArrayList<String> getSlotStartTime() {
        return selectedStartTime;
    }
    public ArrayList<String> getSlotEndTime() {
        return selectedEndTime;
    }

}
/*
if(row_index==position){
    row_index=-1;
    final int sdk = android.os.Build.VERSION.SDK_INT;
    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
        holder.layout.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.backtiming) );

    } else {
        holder.layout.setBackground(ContextCompat.getDrawable(context, R.drawable.backtiming));

    }
    selectedSlotId.remove(slotModels.get(position).getSlotId());
    selectedSlotCost.remove(slotModels.get(position).getBookingCost());
    selectedStartTime.remove(slotModels.get(position).getSlotStartTime());
    selectedEndTime.remove(slotModels.get(position).getSlotEndTime());
}else {
    row_index = position;
    notifyDataSetChanged();


}
            }
        });
        if(row_index==position){
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.layout.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.backbutton) );
            } else {
                holder.layout.setBackground(ContextCompat.getDrawable(context, R.drawable.backbutton));
            }
            selectedSlotCost.add(slotModels.get(position).getBookingCost());
            selectedSlotId.add(slotModels.get(position).getSlotId());
            selectedStartTime.add(slotModels.get(position).getSlotStartTime());
            selectedEndTime.add(slotModels.get(position).getSlotEndTime());
            String Date66=slotModels.get(position).getSlotStartTime();
            String Start66=slotModels.get(position).getSlotEndTime();
            Log.d("hfiwrjfiw",Date66);

            //holder.layout.setBackgroundColor(Color.parseColor("#567845"));

        }
        else
        {
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.layout.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.backtiming) );
            } else {
                holder.layout.setBackground(ContextCompat.getDrawable(context, R.drawable.backtiming));
            }
            selectedSlotCost.remove(slotModels.get(position).getBookingCost());
            selectedSlotId.remove(slotModels.get(position).getSlotId());
            selectedStartTime.remove(slotModels.get(position).getSlotStartTime());
            selectedEndTime.remove(slotModels.get(position).getSlotEndTime());
        }

         List<Integer> selectedSlotIdList = ((DateBookingAdapter) SlotDateAdapter)
                    .getSelectedSlotId();
            List<Integer> selectedSlotCostList = ((DateBookingAdapter) SlotDateAdapter)
                    .getSelectedSlotCost();
            List<String> selectedStartList = ((DateBookingAdapter) SlotDateAdapter)
                    .getSlotStartTime();
            List<String> selectedEndList = ((DateBookingAdapter) SlotDateAdapter)
                    .getSlotEndTime();

            if(personCounts.equals("")){
                Toast.makeText(SlotPages.this, "Please select person count", Toast.LENGTH_SHORT).show();

            }
            else if(selectedSlotIdList.size()==0){
                Toast.makeText(SlotPages.this, "Please select any slot", Toast.LENGTH_SHORT).show();
            }else {
                Log.d("hureitr", personCounts);


                selectedSlotId = selectedSlotIdList.toString();
                selectedSlotId = selectedSlotId.replace("[", "");
                selectedSlotId = selectedSlotId.replace("]", "");
                selectedSlotId = selectedSlotId.replace(" ", "");

                selectedSlotCost = selectedSlotCostList.toString();
                selectedSlotCost = selectedSlotCost.replace("[", "");
                selectedSlotCost = selectedSlotCost.replace("]", "");
                selectedSlotCost = selectedSlotCost.replace(" ", "");
                Log.d("jfwiejfiwre", selectedSlotCost);

                selectedStarttime = selectedStartList.toString();
                selectedStarttime = selectedStarttime.replace("[", "");
                selectedStarttime = selectedStarttime.replace("]", "");
                selectedStarttime = selectedStarttime.replace(" ", "");

                selectedEndtime = selectedEndList.toString();
                selectedEndtime = selectedEndtime.replace("[", "");
                selectedEndtime = selectedEndtime.replace("]", "");
                selectedEndtime = selectedEndtime.replace(" ", "");

*/

