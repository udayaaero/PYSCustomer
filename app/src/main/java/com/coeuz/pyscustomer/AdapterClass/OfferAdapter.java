package com.coeuz.pyscustomer.AdapterClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.coeuz.pyscustomer.R;
import java.util.ArrayList;


public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> {

    public Context mcontext;

    //private ArrayList<OfferModel> offerModel;
    private ArrayList<String> mofferStartList;
    private ArrayList<String> mofferEndList;
    private ArrayList<String> mofferTypeList;
    private ArrayList<String> mofferBenefits;



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
            offerFrom=itemView.findViewById(R.id.offerfrom);
            offerTo=itemView.findViewById(R.id.offerto);
            mofferCategorytype=itemView.findViewById(R.id.offerCategorytype);
            mBenefits=itemView.findViewById(R.id.benefits);

        }
    }

    @Override
    public OfferAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_adapter_layout,parent,false);

        return new MyViewHolder(view);
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
