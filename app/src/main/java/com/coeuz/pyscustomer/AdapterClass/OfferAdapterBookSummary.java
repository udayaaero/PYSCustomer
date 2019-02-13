package com.coeuz.pyscustomer.AdapterClass;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coeuz.pyscustomer.R;
import com.coeuz.pyscustomer.requiredclass.Constant;
import com.coeuz.pyscustomer.requiredclass.TinyDB;

import java.util.ArrayList;

/**
 * Created by Udayakumar on 19-11-2017.
 */

public class OfferAdapterBookSummary extends RecyclerView.Adapter<OfferAdapterBookSummary.MyViewHolder> {

    public Context mcontext;


    private ArrayList<String> mofferTypeList;
    private ArrayList<String> mofferBenefits;
    private ArrayList<String> mofferCode;
    private ArrayList<String> offerEndList;
    private ViewGroup progressView;
    private PopupWindow mPopupWindow;
    private TinyDB mtinyDb;




    public OfferAdapterBookSummary(Context applicationContext, ArrayList<String> offerTypeList, ArrayList<String> offerBenefits) {
        this.mcontext=applicationContext;

        this.mofferTypeList=offerTypeList;
        this.mofferBenefits=offerBenefits;

    }

    public OfferAdapterBookSummary(Context applicationContext, ArrayList<String> offerTypeList,ArrayList<String> offerEndList ,ArrayList<String> offerBenefits, ArrayList<String> offerCodeList) {
        this.mcontext=applicationContext;

        this.mofferTypeList=offerTypeList;
        this.mofferBenefits=offerBenefits;
        this.mofferCode=offerCodeList;
        this.offerEndList=offerEndList;
        this.mtinyDb=new TinyDB(mcontext);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView offerPersentage,offerCodes,offerDetails,moreDetails,applyOffer;
        private RelativeLayout layoutPopup;

        public MyViewHolder(View itemView) {
            super(itemView);

            offerPersentage=itemView.findViewById(R.id.offerPersentage);
            offerCodes=itemView.findViewById(R.id.offerCode);
            offerDetails=itemView.findViewById(R.id.offerDetails);
            moreDetails=itemView.findViewById(R.id.MoreDetails);
            layoutPopup=itemView.findViewById((R.id.popUpLayout));
            applyOffer=itemView.findViewById(R.id.applyOffer);

        }
    }

    @Override
    public OfferAdapterBookSummary.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_book_summary,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OfferAdapterBookSummary.MyViewHolder holder, final int position) {


        holder.offerCodes.setText(mofferCode.get(position));
        holder.offerPersentage.setText("Get "+mofferBenefits.get(position));
        holder.offerDetails.setText("Use Code "+mofferCode.get(position)+" and get "+mofferBenefits.get(position)+" discount upto Rs."+
        mofferTypeList.get(position)+" on your booking");
        holder.applyOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mtinyDb.putString("APPLIED","true");
                String offerCode=mofferCode.get(position);
                String offervalue=mofferTypeList.get(position);
                 String spercentage=mofferBenefits.get(position);
                mtinyDb.putString(Constant.OFFERCODE,offerCode);
                mtinyDb.putString(Constant.OFFERAMOUNT,offervalue);
                mtinyDb.putString(Constant.OFFERPERCENTAGE,spercentage);
                mtinyDb.putString("ALERT",offervalue);
                ((Activity)mcontext).finish();
            }
        });
        holder.moreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String sOfferCode=mofferCode.get(position);
                final String sPercentage=mofferBenefits.get(position);
                final String sAmount=mofferTypeList.get(position);
                String endDate=offerEndList.get(position);

                LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View customView = null;
                if (inflater != null) {
                    customView = inflater.inflate(R.layout.offer_details, null);
                }


                mPopupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                if (Build.VERSION.SDK_INT >= 21) {
                    mPopupWindow.setElevation(5.0f);
                }
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mPopupWindow.showAtLocation(holder.layoutPopup, Gravity.BOTTOM, 0, 0);
                    }
                }, 100);

                assert customView != null;

                Button submitButton = customView.findViewById(R.id.applyOffer);
                TextView offer_Code = customView.findViewById(R.id.offer_Code);
                TextView offerPersentage = customView.findViewById(R.id.offer_persentage);
                TextView offerDetails = customView.findViewById(R.id.offerDetails);
                TextView validTill = customView.findViewById(R.id.valid_till);

                offer_Code.setText(sOfferCode);
                offerPersentage.setText("Get "+sOfferCode+" discount using this Code");
                offerDetails.setText("Use Code "+sOfferCode+" and get "+sPercentage+" discount upto Rs."+
                       sAmount+" on your booking");
                validTill.setText("offer valid till "+endDate+ " ,23.59 PM");


                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mtinyDb.putString("APPLIED","true");
                        String offervalue=mofferTypeList.get(position);
                        mtinyDb.putString("ALERT",offervalue);
                        mtinyDb.putString(Constant.OFFERCODE,sOfferCode);
                        mtinyDb.putString(Constant.OFFERAMOUNT,sAmount);
                        mtinyDb.putString(Constant.OFFERPERCENTAGE,sPercentage);
                        ((Activity)mcontext).finish();

                    }
                });


            }
        });

        }

    @Override
    public int getItemCount() {

        return mofferTypeList.size();
    }

}
