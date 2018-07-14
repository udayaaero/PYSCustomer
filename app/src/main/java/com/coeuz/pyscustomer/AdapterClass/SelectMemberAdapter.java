package com.coeuz.pyscustomer.AdapterClass;

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
import com.coeuz.pyscustomer.SlotPages;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by vjy on 01-Jul-18.
 */

public class SelectMemberAdapter extends RecyclerView.Adapter<SelectMemberAdapter.MyViewHolder> {

    public Context mcontext;
    TinyDB mtinyDb;




    private ArrayList<String> typeList=new ArrayList<>();
    private ArrayList<String> CostList=new ArrayList<>();
    private ArrayList<String> idList=new ArrayList<>();

    public SelectMemberAdapter(Context applicationContext, ArrayList<String> memberbookingType, ArrayList<String> memberbookingCost) {


    }

    public SelectMemberAdapter(Context applicationContext, ArrayList<String> memberbookingType, ArrayList<String> memberbookingCost, ArrayList<String> memberbookingSlotId) {
        mcontext=applicationContext;
        this.typeList =memberbookingType;
        this.CostList =memberbookingCost;
        this.idList =memberbookingSlotId;

        mtinyDb=new TinyDB(mcontext);
    }

   /* public SelectMemberAdapter(Context applicationContext, ArrayList<String> otherServiceid, ArrayList<String> motherServiceList) {

        mcontext=applicationContext;
        this.serviceList =motherServiceList;
        this.subActivityIdList =otherServiceid;

        mtinyDb=new TinyDB(mcontext);

    }*/



    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView types,mcost;
        private Button mbook;

        public MyViewHolder(View itemView) {
            super(itemView);

            types=(TextView)itemView.findViewById(R.id.typesOfmembership);
            mcost=(TextView)itemView.findViewById(R.id.Bookcost);
            mbook=(Button)itemView.findViewById(R.id.book);

        }
    }

    @Override
    public SelectMemberAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.member_course_layout,parent,false);

        MyViewHolder viewss = new MyViewHolder(view);
        return viewss;
    }

    @Override
    public void onBindViewHolder(final SelectMemberAdapter.MyViewHolder holder, final int position) {




        holder.types.setText(typeList.get(position));
        holder.mcost.setText("Rs."+CostList.get(position));
        holder.mbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clickedItem = String.valueOf(position);
                String bookingCost=CostList.get(position);
                String membershipType=typeList.get(position);
                String membershipslotID=idList.get(position);


                mtinyDb.putString("SlotbookingCost",bookingCost);
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("hh:mmaa");
                String bookingTime = mdformat.format(calendar.getTime());
                bookingTime = bookingTime.replace(".", "");

                mtinyDb.putString(Constant.MEMSLOTID,membershipslotID);
                mtinyDb.putString("SlotbookingTime",bookingTime);
                mtinyDb.putString("membershipType",membershipType);

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
