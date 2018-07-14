package com.coeuz.pyscustomer.AdapterClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coeuz.pyscustomer.FilterActivity;
import com.coeuz.pyscustomer.ModelClass.AmenitiesModel;
import com.coeuz.pyscustomer.ModelClass.SubActivityModel;
import com.coeuz.pyscustomer.R;

import java.util.ArrayList;

/**
 * Created by vjy on 17-Apr-18.
 */

public class FilterAmenityAdapter  extends RecyclerView.Adapter<FilterAmenityAdapter.MyViewHolder> {

    public Context mcontext;


    private ArrayList<AmenitiesModel> amenityModels;

    ArrayList<Integer> selectedStrings = new ArrayList<Integer>();

    public FilterAmenityAdapter(FilterActivity filterActivity, ArrayList<AmenitiesModel> amenitiesModel) {
       this.mcontext=filterActivity;
       this.amenityModels=amenitiesModel;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mtext;
        CheckBox checkbox;
        public MyViewHolder(View itemView) {
            super(itemView);

            mtext=(TextView)itemView.findViewById(R.id.checkedtext);
            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);



        }



        }


    @Override
    public FilterAmenityAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_amenities_adapter,parent,false);

        FilterAmenityAdapter.MyViewHolder viewss = new FilterAmenityAdapter.MyViewHolder(view);
        return viewss;
    }

    @Override
    public void onBindViewHolder(final FilterAmenityAdapter.MyViewHolder holder, final int position) {


        holder.mtext.setText(amenityModels.get(position).getAmenityType());

        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {

                    selectedStrings.add(amenityModels.get(position).getAmenityId());
                } else {

                    selectedStrings.remove(amenityModels.get(position).getAmenityId());
                }
            }
        });


    }

    @Override
    public int getItemCount() {

        return amenityModels.size();
    }
    public ArrayList<Integer> getStudentist() {
        return selectedStrings;
    }

}
