package com.coeuz.pyscustomer.AdapterClass;

/**
 * Created by vjy on 18-Mar-18.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.coeuz.pyscustomer.ModelClass.BookingHistoryModel;
import com.coeuz.pyscustomer.R;

import java.util.ArrayList;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.MyViewHolder> {

    private ArrayList<BookingHistoryModel> BookingHistory;

    public BookingHistoryAdapter(ArrayList<BookingHistoryModel> recyclerModels) {
        this.BookingHistory = recyclerModels;
    }

    @Override
    public BookingHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bookinghistoryadapter, parent, false));
    }

    @Override
    public void onBindViewHolder(BookingHistoryAdapter.MyViewHolder holder, int position) {

     // holder.bookingStatus.setText(BookingHistory.get(position).getBookingStatus());
        holder.bookingType.setText(BookingHistory.get(position).getBookingType());
        holder.booedforDate.setText(BookingHistory.get(position).getBooedforDate());
        holder.bookingtimeStamp.setText(BookingHistory.get(position).getBookingtimeStamp());
       // holder.bookingid.setText(BookingHistory.get(position).getBookingid());
        holder.personcount.setText(BookingHistory.get(position).getPersoncount());
        holder.amount1.setText("Amount - "+BookingHistory.get(position).getAmount1());
       // holder.slotid.setText(BookingHistory.get(position).getSlotid());
        holder.subActivityType.setText(BookingHistory.get(position).getSubActivityType());
        holder.vendorName.setText(BookingHistory.get(position).getVendorName());


    }

    @Override
    public int getItemCount() {
        return BookingHistory.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        // view this our custom row layout, so intialize your variables here
        private TextView bookingType,booedforDate,bookingtimeStamp,
                personcount,amount1,subActivityType,vendorName;



        MyViewHolder(View view) {
            super(view);

           // bookingStatus=(TextView)view.findViewById(R.id.bookingStatus);
            bookingType=(TextView)view.findViewById(R.id.bookingType);
            booedforDate=(TextView)view.findViewById(R.id.booedforDate);
            bookingtimeStamp=(TextView)view.findViewById(R.id.bookingtimeStamp);
          //  bookingid=(TextView)view.findViewById(R.id.bookingid);
            personcount=(TextView)view.findViewById(R.id.personcount);
            amount1=(TextView)view.findViewById(R.id.amount1);
          //  slotid=(TextView)view.findViewById(R.id.slotid);
            subActivityType=(TextView)view.findViewById(R.id.subActivityType);
            vendorName=(TextView)view.findViewById(R.id.vendorName);


        }
    }
}

