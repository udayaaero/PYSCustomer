package com.coeuz.pyscustomer.AdapterClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coeuz.pyscustomer.AfterSelectVendor;
import com.coeuz.pyscustomer.ModelClass.OfferModel;
import com.coeuz.pyscustomer.R;

import java.util.ArrayList;

/**
 * Created by Udayakumar on 19-11-2017.
 */

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> {

    public Context mcontext;

    //private ArrayList<OfferModel> offerModel;
    ArrayList<String> mofferStartList=new ArrayList<>();
    ArrayList<String> mofferEndList=new ArrayList<>();
    ArrayList<String> mofferTypeList=new ArrayList<>();
    ArrayList<String> mofferBenefits=new ArrayList<>();



    public OfferAdapter(Context applicationContext, ArrayList<String> offerStartList, ArrayList<String> offerEndList,
                        ArrayList<String> offerTypeList, ArrayList<String> offerBenefits) {
        this.mcontext=applicationContext;
        this.mofferStartList=offerStartList;
        this.mofferEndList=offerEndList;
        this.mofferTypeList=offerTypeList;
        this.mofferBenefits=offerBenefits;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView offerFrom,offerTo,mofferCategorytype,mBenefits;

        public MyViewHolder(View itemView) {
            super(itemView);
            offerFrom=(TextView)itemView.findViewById(R.id.offerfrom);
            offerTo=(TextView)itemView.findViewById(R.id.offerto);
            mofferCategorytype=(TextView)itemView.findViewById(R.id.offerCategorytype);
            mBenefits=(TextView)itemView.findViewById(R.id.benefits);

        }
    }

    @Override
    public OfferAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_adapter_layout,parent,false);

        MyViewHolder viewss = new MyViewHolder(view);
        return viewss;
    }

    @Override
    public void onBindViewHolder(final OfferAdapter.MyViewHolder holder, final int position) {


        holder.offerFrom.setText(mofferStartList.get(position));
        holder.offerTo.setText(mofferEndList.get(position));
        holder.mofferCategorytype.setText( mofferTypeList.get(position));
        holder.mBenefits.setText(mofferBenefits.get(position));

        }

    @Override
    public int getItemCount() {

        return mofferStartList.size();
    }

}
