package com.coeuz.pyscustomer.AdapterClass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coeuz.pyscustomer.R;

import java.util.ArrayList;


public class PaymentOfferAdapter extends RecyclerView.Adapter<PaymentOfferAdapter.MyViewHolder> {

    public Context mcontext;

    private ArrayList<String> mofferTypeList;
    private ArrayList<String> mofferBenefits;



    public PaymentOfferAdapter(Context applicationContext, ArrayList<String> offerTypeList, ArrayList<String> offerBenefits) {
        this.mcontext=applicationContext;
        this.mofferBenefits=offerBenefits;
        this.mofferTypeList=offerTypeList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
         TextView offerFrom,offerTo,mofferCategorytype,mBenefits;

        public MyViewHolder(View itemView) {
            super(itemView);
            offerFrom=itemView.findViewById(R.id.offerfrom);
            offerTo=itemView.findViewById(R.id.offerto);
            mofferCategorytype=itemView.findViewById(R.id.type);
            mBenefits=itemView.findViewById(R.id.benefits);

        }
    }

    @Override
    public PaymentOfferAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_offer_layout,parent,false);

        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final PaymentOfferAdapter.MyViewHolder holder, final int position) {

        holder.mofferCategorytype.setText( mofferTypeList.get(position)+" Discount");
        holder.mBenefits.setText(mofferBenefits.get(position)+"%");

        }

    @Override
    public int getItemCount() {

        return mofferBenefits.size();
    }

}
