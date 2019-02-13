package com.coeuz.pyscustomer.AdapterClass;

import android.annotation.SuppressLint;
import android.graphics.Color;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coeuz.pyscustomer.ModelClass.BookingHistoryModel;
import com.coeuz.pyscustomer.R;


import java.util.ArrayList;

public class BookingHistoryAdapter1 extends RecyclerView.Adapter<BookingHistoryAdapter1.MyViewHolder> {

    private ArrayList<BookingHistoryModel> BookingHistory;


    public BookingHistoryAdapter1(FragmentActivity activity, ArrayList<BookingHistoryModel> recyclerModels) {
        this.BookingHistory = recyclerModels;
       // TinyDB mTinyDb = new TinyDB(activity);
       // String mToken = mTinyDb.getString(Constant.TOKEN);
    }

    @Override
    public BookingHistoryAdapter1.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bookinghistoryadapter1, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BookingHistoryAdapter1.MyViewHolder holder, final int position) {

      holder.bookingStatus.setText(BookingHistory.get(position).getBookingStatus());
        if(holder.bookingStatus.getText().toString().equals("CANCELLLED")){
            holder.bookingStatus.setTextColor(Color.parseColor("#FF0000"));
        }else{
            holder.bookingStatus.setTextColor(Color.parseColor("#228B22"));
        }

        holder.booedforDate.setText(BookingHistory.get(position).getBooedforDate());
        holder.bookingtimeStamp.setText(BookingHistory.get(position).getBookingtimeStamp());
        holder.personcount.setText(BookingHistory.get(position).getPersoncount());
        holder.amount1.setText("Rs."+BookingHistory.get(position).getAmount1());
        holder.startAndEnd.setText(BookingHistory.get(position).getStartTime()+" to "+BookingHistory.get(position).getEndTime());
        holder.subActivityType.setText(BookingHistory.get(position).getSubActivityType());
        holder.vendorName.setText(BookingHistory.get(position).getVendorName());


    }

    @Override
    public int getItemCount() {
        return BookingHistory.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        // view this our custom row layout, so intialize your variables here
        private TextView bookingStatus,booedforDate,bookingtimeStamp,
                personcount,amount1,subActivityType,vendorName,startAndEnd;




        MyViewHolder(View view) {
            super(view);

            bookingStatus=view.findViewById(R.id.bookingStatusss);
            booedforDate=view.findViewById(R.id.booedforDate);
            bookingtimeStamp=view.findViewById(R.id.bookingtimeStamp);
            personcount=view.findViewById(R.id.personcount);
            amount1=view.findViewById(R.id.amount1);
            subActivityType=view.findViewById(R.id.subActivityType);
            vendorName=view.findViewById(R.id.vendorName);
            startAndEnd= view.findViewById(R.id.startAndEnd);
                   }
    }
}

