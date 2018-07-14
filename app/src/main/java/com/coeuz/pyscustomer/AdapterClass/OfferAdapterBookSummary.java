package com.coeuz.pyscustomer.AdapterClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coeuz.pyscustomer.R;

import java.util.ArrayList;

/**
 * Created by Udayakumar on 19-11-2017.
 */

public class OfferAdapterBookSummary extends RecyclerView.Adapter<OfferAdapterBookSummary.MyViewHolder> {

    public Context mcontext;

    //private ArrayList<OfferModel> offerModel;
    ArrayList<String> mofferStartList=new ArrayList<>();
    ArrayList<String> mofferEndList=new ArrayList<>();
    ArrayList<String> mofferTypeList=new ArrayList<>();
    ArrayList<String> mofferBenefits=new ArrayList<>();




    public OfferAdapterBookSummary(Context applicationContext, ArrayList<String> offerTypeList, ArrayList<String> offerBenefits) {
        this.mcontext=applicationContext;

        this.mofferTypeList=offerTypeList;
        this.mofferBenefits=offerBenefits;
        Log.d("fjeriujre1", String.valueOf(offerBenefits));
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mofferCategorytype,mBenefits;

        public MyViewHolder(View itemView) {
            super(itemView);

            mofferCategorytype=(TextView)itemView.findViewById(R.id.offerCategorytype);
            mBenefits=(TextView)itemView.findViewById(R.id.benefits);

        }
    }

    @Override
    public OfferAdapterBookSummary.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_book_summary,parent,false);

        MyViewHolder viewss = new MyViewHolder(view);
        return viewss;
    }

    @Override
    public void onBindViewHolder(final OfferAdapterBookSummary.MyViewHolder holder, final int position) {


        holder.mofferCategorytype.setText( mofferTypeList.get(position));
        holder.mBenefits.setText(mofferBenefits.get(position));

        }

    @Override
    public int getItemCount() {

        return mofferTypeList.size();
    }

}
