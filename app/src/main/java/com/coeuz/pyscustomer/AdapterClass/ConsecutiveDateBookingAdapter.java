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
import com.coeuz.pyscustomer.ConsecutiveBookingSummary;
import com.coeuz.pyscustomer.R;
import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;
import java.util.ArrayList;



public class ConsecutiveDateBookingAdapter extends RecyclerView.Adapter<ConsecutiveDateBookingAdapter.MyViewHolder> {

    public Context context;
    private TinyDB tinyDB;
/*    private ArrayList<Integer> selectedSlotId= new ArrayList<>();
    private ArrayList<Integer> selectedSlotCost= new ArrayList<>();*/
    private ArrayList<String> selectedStartTime= new ArrayList<>();
    private ArrayList<String> selectedEndTime= new ArrayList<>();
    private ArrayList<String> consecutiveSlotModels;
    private ArrayList<String> consecutiveSlotModelsCost;
    private ArrayList<String> consecutiveSlotModelsTime;
    private ArrayList<String> sendconsecutiveSlotModelsTime;






    public ConsecutiveDateBookingAdapter(Context applicationContext, ArrayList<String> consecutiveslotModel,
                                         ArrayList<String> consecutiveslotModelCost, ArrayList<String> consecutiveslotModelTiming,
                                         ArrayList<String> sendconsecutiveslotModelTiming) {
        this.context=applicationContext;
        this.consecutiveSlotModels=consecutiveslotModel;
        this.consecutiveSlotModelsTime=consecutiveslotModelTiming;
        this.consecutiveSlotModelsCost=consecutiveslotModelCost;
        this.sendconsecutiveSlotModelsTime=sendconsecutiveslotModelTiming;
        tinyDB=new TinyDB(context);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView startTime,cost;
        TextView Endtime;
        private LinearLayout layout,costlayout;
        public int position=0;
        @SuppressLint("CutPasteId")
        public MyViewHolder(View itemView) {
            super(itemView);
            startTime=itemView.findViewById(R.id.Starttimee);
            Endtime=itemView.findViewById(R.id.Endtimee);
            cost=itemView.findViewById(R.id.cost);
            layout=itemView.findViewById(R.id.timemorning);
            costlayout=itemView.findViewById(R.id.timemorning);
        }
    }

    @Override
    public ConsecutiveDateBookingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.datebookingadapter,parent,false);
       /* return new MyViewHolder(view);*/
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ConsecutiveDateBookingAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        String bookTime=String.valueOf(consecutiveSlotModelsTime.get(position));
        if(bookTime.equals("null")){
            holder.startTime.setText("No slots");
            holder.costlayout.setVisibility(View.GONE);
        }else {
        holder.startTime.setText(consecutiveSlotModelsTime.get(position));
       // holder.Endtime.setText(String.valueOf(consecutiveSlotModelsTime.get(position).getEndTime()));
       Log.d("vregre,", String.valueOf(consecutiveSlotModelsTime.get(position)));
       holder.cost.setText(String.valueOf(consecutiveSlotModelsCost.get(position)));


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String bookingTime= String.valueOf(consecutiveSlotModelsTime.get(position));
                String sendbookingTime= String.valueOf(sendconsecutiveSlotModelsTime.get(position));
                String bookingCost=String.valueOf(consecutiveSlotModelsCost.get(position));
                String slotIds=String.valueOf(consecutiveSlotModels.get(position));
          /*      try {
                    final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    final Date dateObj = sdf.parse(bookingTime );
                    String timein12Format=new SimpleDateFormat("hh:mmaa").format(dateObj);
                    Log.d("mcmcemciqc", String.valueOf(timein12Format));
                    bookingTime=String.valueOf(timein12Format);
                    bookingTime = bookingTime.replace(".", "");
                } catch (final ParseException e) {
                    e.printStackTrace();
                }*/
                tinyDB.putString("SlotbookingTime",bookingTime);
                tinyDB.putString(Constant.PAYMENTSTARTTIME,sendbookingTime);
               tinyDB.putString("SlotbookingCost",bookingCost);

                tinyDB.putString(Constant.CONSLOTID,slotIds);
                tinyDB.putString(Constant.SELECTEDTYPE,"CONSECUTIVE");

                Intent intent=new Intent(context,ConsecutiveBookingSummary.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);

    }
        });}}


    @Override
    public int getItemCount() {
        Log.d("hshdhgwiogjow", String.valueOf(consecutiveSlotModelsTime.size()));
        Log.d("hshdhgwiogjow1", String.valueOf(consecutiveSlotModels.size()));
        return consecutiveSlotModelsTime.size();
    }

   /* public ArrayList<Integer> getSelectedSlotId() {
        return selectedSlotId;
    }
    public ArrayList<Integer> getSelectedSlotCost() {
        return selectedSlotCost;
    }*/
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
*/

