package com.coeuz.pyscustomer.AdapterClass;


import android.annotation.SuppressLint;
import android.content.Context;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coeuz.pyscustomer.ModelClass.MembershipBookingHistoryModel;
import com.coeuz.pyscustomer.R;



import java.util.ArrayList;

public class MemberShipBookingHistoryAdapter1 extends RecyclerView.Adapter<MemberShipBookingHistoryAdapter1.MyViewHolder> {

    private ArrayList<MembershipBookingHistoryModel> BookingHistory;

    public MemberShipBookingHistoryAdapter1(FragmentActivity activity, ArrayList<MembershipBookingHistoryModel> recyclerModels) {
        Context mcontext = activity;
        this.BookingHistory = recyclerModels;
      //  TinyDB mTinyDb = new TinyDB(mcontext);
       // String mToken = mTinyDb.getString(Constant.TOKEN);
    }


    @Override
    public MemberShipBookingHistoryAdapter1.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.member_bookinghistoryadapter1, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MemberShipBookingHistoryAdapter1.MyViewHolder holder, final int position) {

         holder.bookingStatus.setText(BookingHistory.get(position).getBookingStatus());

       holder.booedforDate.setText(BookingHistory.get(position).getBookedforDate());
        holder.bookingtimeStamp.setText(BookingHistory.get(position).getBookingtimeStamp());
        holder.personcount.setText(String.valueOf(BookingHistory.get(position).getPersonCount()));
        holder.amount1.setText("Rs."+String.valueOf(BookingHistory.get(position).getAmount()));
        holder.subActivityType.setText(BookingHistory.get(position).getSubActivityType());
        holder.vendorName.setText(BookingHistory.get(position).getVendorName());
        holder.membershipType.setText(String.valueOf(BookingHistory.get(position).getMemberShipType()));


    }

    @Override
    public int getItemCount() {
        return BookingHistory.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        // view this our custom row layout, so intialize your variables here
        private TextView bookingStatus,booedforDate,bookingtimeStamp,membershipType,
                personcount,amount1,subActivityType,vendorName;




        MyViewHolder(View view) {
            super(view);

            bookingStatus=view.findViewById(R.id.bookingStatusss);
            booedforDate=view.findViewById(R.id.booedforDate);
            bookingtimeStamp=view.findViewById(R.id.bookingtimeStamp);
            personcount=view.findViewById(R.id.personcount);
            amount1=view.findViewById(R.id.amount1);
            subActivityType=view.findViewById(R.id.subActivityType);
            vendorName=view.findViewById(R.id.vendorName);
            membershipType=view.findViewById(R.id.memberType);



        }
    }
}

