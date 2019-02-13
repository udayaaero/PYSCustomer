package com.coeuz.pyscustomer.AdapterClass;




import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.coeuz.pyscustomer.MainActivity;
import com.coeuz.pyscustomer.R;

import java.util.ArrayList;


public class BookingRememberAdapter extends RecyclerView.Adapter<BookingRememberAdapter.MyViewHolder> {
    public Context mcontext;
    private PopupWindow mPopupWindow;

    private ArrayList<String> rememberVendorName1;
    private ArrayList<String> rememberBookedDate1;
    private ArrayList<String> rememberType1;
    private ArrayList<String> rememberbookingType1;
    private ArrayList<String> rememberOtp1;
    private ArrayList<String> rememberstarttime1;
    private ArrayList<String> rememberendtime1;
    private ArrayList<String> rememberArea1;
    private ArrayList<String> remembercontactno1;
    private ArrayList<String> rememberNotes1;
    private ArrayList<String> rememberpersonCount1;



    public BookingRememberAdapter(MainActivity mainActivity, ArrayList<String> rememberVendorName, ArrayList<String> rememberBookedDate,
                                  ArrayList<String> rememberType, ArrayList<String> rememberbookingType, ArrayList<String> rememberOtp,
                                  ArrayList<String> rememberstarttime, ArrayList<String> rememberendtime, ArrayList<String> rememberArea,
                                  ArrayList<String> remembercontactno, ArrayList<String> rememberpersonCount, ArrayList<String> rememberNotes) {
        this.mcontext=mainActivity;
        this.rememberVendorName1=rememberVendorName;
        this.rememberBookedDate1=rememberBookedDate;
        this.rememberType1=rememberType;
        this.rememberbookingType1=rememberbookingType;
        this.rememberOtp1=rememberOtp;
        this.rememberstarttime1 =rememberstarttime;
        this.rememberendtime1 = rememberendtime;
        this.rememberArea1 =rememberArea;
        this.remembercontactno1 =remembercontactno;
        this.rememberpersonCount1 = rememberpersonCount;
        this.rememberNotes1 =rememberNotes;
       // TinyDB mtinyDb = new TinyDB(mcontext);
       // String mToken = mtinyDb.getString(Constant.TOKEN);
    }


    @Override
    public BookingRememberAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate your custom row layout here
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_remember_adapter_layout, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BookingRememberAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        // update your data here

        holder.nVendorname.setText(rememberType1.get(position)+" "+rememberbookingType1.get(position)+" at "+rememberVendorName1.get(position)
        +" Scheduled for "+rememberBookedDate1.get(position));

        holder.ntotalCount.setText(String.valueOf(position+1)+"/"+String.valueOf(rememberBookedDate1.size()));

        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
Log.d("ergtert","rt35t3");

                LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                assert inflater != null;
                @SuppressLint("InflateParams") final View customView = inflater.inflate(R.layout.remember_booked_layout, null);


                mPopupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
                mPopupWindow.setOutsideTouchable(true);
                if (Build.VERSION.SDK_INT >= 21) {
                    mPopupWindow.setElevation(5.0f);
                }
                new Handler().postDelayed(new Runnable() {
                    public void run() {

                        mPopupWindow.showAtLocation(customView, Gravity.BOTTOM, 0, -50);
                    }
                }, 100);
                LinearLayout timeLayout=customView.findViewById(R.id.timeLayouts);
                TextView personCounts=customView.findViewById(R.id.personCounts);
                TextView otptext =customView.findViewById(R.id.otp);
                TextView skipButton =customView.findViewById(R.id.skipBtn);
                TextView vendorName1 = customView.findViewById(R.id.vendorName1);
                TextView booedforDate1 =  customView.findViewById(R.id.booedforDate1);
                TextView startTime1 = customView.findViewById(R.id.startTime1);
                TextView endTime1 = customView.findViewById(R.id.endTime1);
                TextView subActivityType1 = customView.findViewById(R.id.subActivityType1);
                TextView area1 = customView.findViewById(R.id.area1);
                TextView contactNo1 = customView.findViewById(R.id.contactNo1);
                LinearLayout mContactNumberLayout = customView.findViewById(R.id.ContactNumberLayout);
                LinearLayout notesLayout = customView.findViewById(R.id.notesLayout);
                TextView note = customView.findViewById(R.id.note);
                vendorName1.setText(rememberVendorName1.get(position));
                personCounts.setText(rememberpersonCount1.get(position));
                otptext.setText(rememberOtp1.get(position));
                booedforDate1.setText(rememberBookedDate1.get(position));
                String values=String.valueOf(rememberstarttime1.get(position));

                if(values.equals("null")){
                    timeLayout.setVisibility(View.GONE);
                }
                else{
                startTime1.setText(rememberstarttime1.get(position));
                endTime1.setText(rememberendtime1.get(position));}
                subActivityType1.setText(rememberType1.get(position));
                area1.setText(rememberArea1.get(position));
                String contactNumber=remembercontactno1.get(position);
                if(contactNumber!=null && !contactNumber.isEmpty() && !contactNumber.equalsIgnoreCase("0")){
                    mContactNumberLayout.setVisibility(View.VISIBLE);
                    contactNo1.setText(contactNumber);
                }else{
                    mContactNumberLayout.setVisibility(View.GONE);
                }
                String notes=rememberNotes1.get(position);
                if(notes!=null && !notes.isEmpty()&& !notes.equalsIgnoreCase("null")){
                    note.setText(notes);
                    notesLayout.setVisibility(View.VISIBLE);
                }else{
                    notesLayout.setVisibility(View.GONE);

                }


                skipButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mPopupWindow.dismiss();
                    }
                });

            }  });}



    @Override
    public int getItemCount() {
        return rememberVendorName1.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nVendorname,ntotalCount;
        private LinearLayout mLayout;

        MyViewHolder(View view) {
            super(view);

            nVendorname=view.findViewById(R.id.bookedVendorName);
            ntotalCount=view.findViewById(R.id.totalCount);
            mLayout= view.findViewById(R.id.selectLayouts);




        }
    }
}

