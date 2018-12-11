package com.coeuz.pyscustomer.AdapterClass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.coeuz.pyscustomer.R;
import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;
import com.coeuz.pyscustomer.SubActivity;
import java.util.ArrayList;
import java.util.HashMap;



public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    public Context mcontext;
    TinyDB mtinyDb;


    private ArrayList<Integer> ImageList=new ArrayList<>();
    private ArrayList<Bitmap> subActivityImageList1=new ArrayList<>();
    HashMap<String,Bitmap> subActivityImageHashMap1 = new HashMap<>();

    private ArrayList<String> subActivityIdList1;
    private ArrayList<String> subActivityTypeList1;



    public MainAdapter(Context applicationContext, ArrayList<String> subActivityList, ArrayList<String> subActivityIdList, ArrayList<Bitmap> subActivityImageList, HashMap<String, Bitmap> subActivityImageHashMap) {
        mcontext=applicationContext;
        subActivityTypeList1=subActivityList;
        subActivityIdList1=subActivityIdList;
        subActivityImageList1=subActivityImageList;
        mtinyDb=new TinyDB(mcontext);
        subActivityImageHashMap1=subActivityImageHashMap;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView nameOfActivity;

        public MyViewHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.imageview100);
            nameOfActivity=itemView.findViewById(R.id.nameofActivity);

        }
    }

    @Override
    public MainAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mainadapter,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MainAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        String id=subActivityIdList1.get(position);
        Bitmap value = subActivityImageHashMap1.get(id);

        holder.image.setImageBitmap(value);
        holder.nameOfActivity.setText(subActivityTypeList1.get(position));
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clickedItem = String.valueOf(position);
                String subActivityName=subActivityTypeList1.get(position);
                String subActivityId=subActivityIdList1.get(position);
                mtinyDb.putString(Constant.SUBACTIVITYNAME,subActivityName);
                mtinyDb.putString("activityName",subActivityName);
                mtinyDb.putString("activityId",subActivityId);



                Intent intent=new Intent(mcontext, SubActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("positionValue",clickedItem);
                bundle.putString("subActivityName",subActivityName);
                bundle.putString("subActivityId",subActivityId);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);

                }});
        }

    @Override
    public int getItemCount() {

        return subActivityTypeList1.size();
    }

}
