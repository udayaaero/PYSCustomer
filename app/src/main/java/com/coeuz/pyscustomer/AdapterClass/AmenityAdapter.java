package com.coeuz.pyscustomer.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coeuz.pyscustomer.R;
import com.coeuz.pyscustomer.SubActivity;

import java.util.ArrayList;

/**
 * Created by Udayakumar on 19-11-2017.
 */

public class AmenityAdapter extends RecyclerView.Adapter<AmenityAdapter.MyViewHolder> {

    public Context mcontext;


    private ArrayList<Integer> ImageList=new ArrayList<Integer>();

    private ArrayList<String> subActivityIdList1=new ArrayList<>();
    private ArrayList<String> subActivityTypeList1=new ArrayList<>();



    public AmenityAdapter(Context applicationContext, ArrayList<String> subActivityList, ArrayList<String> subActivityIdList) {

        mcontext=applicationContext;
        subActivityTypeList1=subActivityList;
        subActivityIdList1=subActivityIdList;

        ImageList.add(R.drawable.asaloon);
        ImageList.add(R.drawable.agymsquar);
        ImageList.add(R.drawable.azumbasquar);
        ImageList.add(R.drawable.aaerobicssquar);
        ImageList.add(R.drawable.adancesquar);
        ImageList.add(R.drawable.agymsquar);
        ImageList.add(R.drawable.aparlar);
        ImageList.add(R.drawable.apilatessquar);
        ImageList.add(R.drawable.aspa);
        Log.d("yeruwiuifwi2", String.valueOf(subActivityTypeList1.size()));
        for (int i = 0; i <subActivityTypeList1.size() ; i++) {
            ImageList.add(R.drawable.asaloon);
        }
    }

    public AmenityAdapter(Context applicationContext, ArrayList<String> subActivityList) {
        mcontext=applicationContext;
        subActivityTypeList1=subActivityList;


        ImageList.add(R.drawable.asaloon);
        ImageList.add(R.drawable.agymsquar);
        ImageList.add(R.drawable.azumbasquar);
        ImageList.add(R.drawable.aaerobicssquar);
        ImageList.add(R.drawable.adancesquar);
        ImageList.add(R.drawable.agymsquar);
        ImageList.add(R.drawable.aparlar);
        ImageList.add(R.drawable.apilatessquar);
        ImageList.add(R.drawable.aspa);
        Log.d("yeruwiuifwi2", String.valueOf(subActivityTypeList1.size()));
        for (int i = 0; i <subActivityTypeList1.size() ; i++) {
            ImageList.add(R.drawable.asaloon);
        }
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView nameOfActivity;

        public MyViewHolder(View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.imageview100);
            nameOfActivity=(TextView)itemView.findViewById(R.id.nameofActivity);

        }
    }

    @Override
    public AmenityAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mainadapter,parent,false);

        MyViewHolder viewss = new MyViewHolder(view);
        return viewss;
    }

    @Override
    public void onBindViewHolder(final AmenityAdapter.MyViewHolder holder, final int position) {

        holder.image.setImageResource(ImageList.get(position));
        holder.nameOfActivity.setText(subActivityTypeList1.get(position));
    /*    holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clickedItem = String.valueOf(position);
                String subActivityName=subActivityTypeList1.get(position);
                String subActivityId=subActivityIdList1.get(position);


                Intent intent=new Intent(mcontext, SubActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("positionValue",clickedItem);
                bundle.putString("subActivityName",subActivityName);
                bundle.putString("subActivityId",subActivityId);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);

                }});*/
        }

    @Override
    public int getItemCount() {

        return subActivityTypeList1.size();
    }

}
