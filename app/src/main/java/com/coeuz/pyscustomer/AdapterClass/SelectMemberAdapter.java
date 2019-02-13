package com.coeuz.pyscustomer.AdapterClass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.coeuz.pyscustomer.MembershipBookingSummary;
import com.coeuz.pyscustomer.R;
import com.coeuz.pyscustomer.requiredclass.Constant;
import com.coeuz.pyscustomer.requiredclass.TinyDB;
import com.coeuz.pyscustomer.requiredclass.VolleySingleton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class SelectMemberAdapter extends RecyclerView.Adapter<SelectMemberAdapter.MyViewHolder> {

    public Context mcontext;
    TinyDB mtinyDb;
    private String  mToken;
private String vendorId,subActivityId,selectedDate;

    private ArrayList<String> typeList;
    private ArrayList<String> CostList;
    private ArrayList<String> idList;
    private ArrayList<String> personList;




    public SelectMemberAdapter(Context applicationContext, ArrayList<String> memberbookingType, ArrayList<String> memberbookingCost, ArrayList<String> memberbookingSlotId, ArrayList<String> memberbookingPerson, String vendorId, String msubActivityId, String mCalenderdate) {

        mcontext=applicationContext;
        this.typeList =memberbookingType;
        this.CostList =memberbookingCost;
        this.idList =memberbookingSlotId;
        personList=memberbookingPerson;
        this.vendorId=vendorId;
        this.subActivityId=msubActivityId;
        this.selectedDate=mCalenderdate;

        mtinyDb=new TinyDB(mcontext);
        mToken=mtinyDb.getString(Constant.TOKEN);

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
Log.d("jregitrh",vendorId+"----"+subActivityId+"----"+selectedDate);

                                String URL10 = Constant.APIONE +"/slot/checkMembershipAvailability?vendorId="+vendorId+"&subActivityId="+subActivityId+"&date="+selectedDate;

                                StringRequest request10 = new StringRequest(Request.Method.GET, URL10, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {


                                Log.d("gthythyt",response);


                                            if (response.equalsIgnoreCase("false")) {

                                                Intent intent=new Intent(mcontext,MembershipBookingSummary.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                mcontext.startActivity(intent);

                                            } else {
                                                Intent intent=new Intent(mcontext,MembershipBookingSummary.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                mcontext.startActivity(intent);



                                            }


                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        if (error instanceof NetworkError) {
                                            Toast.makeText(mcontext, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                                        } else if (error instanceof ServerError) {

                                        }  else if (error instanceof ParseError) {
                                            Toast.makeText(mcontext, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                                        }  else if (error instanceof TimeoutError) {
                                            Toast.makeText(mcontext, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }){   @Override
                                public Map<String, String> getHeaders() {
                                    HashMap<String, String> headers1 = new HashMap<>();

                                    headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
                                    return headers1;

                                }};
                                VolleySingleton.getInstance(mcontext).addToRequestQueue(request10);







            }
        });


    }

    @Override
    public int getItemCount() {

        return typeList.size();
    }
}
