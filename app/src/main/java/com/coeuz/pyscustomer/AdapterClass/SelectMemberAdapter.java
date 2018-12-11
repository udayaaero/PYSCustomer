package com.coeuz.pyscustomer.AdapterClass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.coeuz.pyscustomer.MembershipBookingSummary;
import com.coeuz.pyscustomer.R;
import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class SelectMemberAdapter extends RecyclerView.Adapter<SelectMemberAdapter.MyViewHolder> {

    public Context mcontext;
    TinyDB mtinyDb;


    private ArrayList<String> typeList;
    private ArrayList<String> CostList;
    private ArrayList<String> idList;
    private ArrayList<String> personList;


    public SelectMemberAdapter(Context applicationContext, ArrayList<String> memberbookingType, ArrayList<String> memberbookingCost, ArrayList<String> memberbookingSlotId, ArrayList<String> memberbookingPerson) {
        mcontext=applicationContext;
        this.typeList =memberbookingType;
        this.CostList =memberbookingCost;
        this.idList =memberbookingSlotId;
        personList=memberbookingPerson;

        mtinyDb=new TinyDB(mcontext);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView types,mcost;
        private Button mbook;

        public MyViewHolder(View itemView) {
            super(itemView);

            types=itemView.findViewById(R.id.typesOfmembership);
            mcost=itemView.findViewById(R.id.Bookcost);
            mbook=itemView.findViewById(R.id.book);

        }
    }

    @Override
    public SelectMemberAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.member_course_layout,parent,false);

        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final SelectMemberAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.types.setText(typeList.get(position));
        holder.mcost.setText("Rs."+CostList.get(position));
        holder.mbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String clickedItem = String.valueOf(position);
                String bookingCost=CostList.get(position);
                String membershipType=typeList.get(position);
                String membershipslotID=idList.get(position);
                String personCount=personList.get(position);

                mtinyDb.putString(Constant.PERSONCOUNT,personCount);
                mtinyDb.putString("SlotbookingCost",bookingCost);
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("hh:mmaa", Locale.getDefault());
                String bookingTime = mdformat.format(calendar.getTime());
                bookingTime = bookingTime.replace(".", "");

                mtinyDb.putString(Constant.MEMSLOTID,membershipslotID);
                mtinyDb.putString("SlotbookingTime",bookingTime);
                mtinyDb.putString("membershipType",membershipType);
                mtinyDb.putString(Constant.SELECTEDTYPE,"MEMBERSHIP");

                Intent intent=new Intent(mcontext,MembershipBookingSummary.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mcontext.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {

        return typeList.size();
    }
}
