package com.coeuz.pyscustomer.AdapterClass;

import android.content.Context;

import android.graphics.Bitmap;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coeuz.pyscustomer.R;
import com.coeuz.pyscustomer.requiredclass.TinyDB;


import java.util.ArrayList;
import java.util.HashMap;


public class AfterSelectVendorAmenitiesAdapter extends RecyclerView.Adapter<AfterSelectVendorAmenitiesAdapter.MyViewHolder> {

    public Context mcontext;
    TinyDB mtinyDb;



    private ArrayList<String> amenitiesAdapter1;
    private ArrayList<String>  amenitiesIdList1=new ArrayList<>();
    private HashMap<String,Bitmap> imagesHasmap=new HashMap<>();

    private  Bitmap bit;






    public AfterSelectVendorAmenitiesAdapter(Context applicationContext, ArrayList<String> amenitiesIdList, ArrayList<String> amenitiesList, HashMap<String, Bitmap> amenitiesImageList) {
        mcontext=applicationContext;
        amenitiesAdapter1=amenitiesList;
        amenitiesIdList1=amenitiesIdList;
        imagesHasmap=amenitiesImageList;
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
        String id=amenitiesIdList1.get(position);
       Bitmap bitmap= imagesHasmap.get(id);
        holder.image.setImageBitmap(bitmap);
        holder.nameOfAmenity.setText(amenitiesAdapter1.get(position));

        }

    @Override
    public int getItemCount() {

        return amenitiesAdapter1.size();
    }

}
