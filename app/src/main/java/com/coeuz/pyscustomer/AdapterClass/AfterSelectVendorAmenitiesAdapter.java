package com.coeuz.pyscustomer.AdapterClass;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coeuz.pyscustomer.R;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;


import java.util.ArrayList;


public class AfterSelectVendorAmenitiesAdapter extends RecyclerView.Adapter<AfterSelectVendorAmenitiesAdapter.MyViewHolder> {

    public Context mcontext;
    TinyDB mtinyDb;


    private ArrayList<Integer> ImageList=new ArrayList<>();

    private ArrayList<String> amenitiesAdapter1;



    public AfterSelectVendorAmenitiesAdapter(Context applicationContext, ArrayList<String> amenitiesList) {

        mcontext=applicationContext;
        amenitiesAdapter1=amenitiesList;

        mtinyDb=new TinyDB(mcontext);
        ImageList.add(R.drawable.ac_icon);
        ImageList.add(R.drawable.locker_icon);
        ImageList.add(R.drawable.parking_icon);
        ImageList.add(R.drawable.wifi_icon);
        ImageList.add(R.drawable.jacuzzi_icon);
        ImageList.add(R.drawable.music_icon);
        ImageList.add(R.drawable.salad_icon);
        ImageList.add(R.drawable.locker_icon);
        ImageList.add(R.drawable.locker_icon);

        Log.d("yeruwiuifwi2", String.valueOf(amenitiesAdapter1.size()));
        for (int i = 0; i <amenitiesAdapter1.size() ; i++) {
            ImageList.add(R.drawable.locker_icon);
        }


    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView nameOfAmenity;

        public MyViewHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.imageview300);
            nameOfAmenity=itemView.findViewById(R.id.nameofAmenity);

        }
    }

    @Override
    public AfterSelectVendorAmenitiesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.after_select_amenity_adapter,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AfterSelectVendorAmenitiesAdapter.MyViewHolder holder, final int position) {

        holder.image.setImageResource(ImageList.get(position));
        holder.nameOfAmenity.setText(amenitiesAdapter1.get(position));

        }

    @Override
    public int getItemCount() {

        return amenitiesAdapter1.size();
    }

}
