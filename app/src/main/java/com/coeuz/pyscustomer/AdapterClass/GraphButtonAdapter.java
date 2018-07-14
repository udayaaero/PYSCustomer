package com.coeuz.pyscustomer.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.coeuz.pyscustomer.R;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;
import com.coeuz.pyscustomer.SubActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by Udayakumar on 19-11-2017.
 */

public class GraphButtonAdapter extends RecyclerView.Adapter<GraphButtonAdapter.MyViewHolder> {

    public Context mcontext;
    TinyDB mtinyDb;

    ArrayList<BarEntry> barValueList = new ArrayList<>();
    ArrayList<Integer> mCountList = new ArrayList<>();

    private ArrayList<Integer> ImageList=new ArrayList<Integer>();

    private ArrayList<String> subActivityIdList1=new ArrayList<>();
    private ArrayList<String> subActivityTypeList1=new ArrayList<>();





    public class MyViewHolder extends RecyclerView.ViewHolder {
        private BarChart barChart;
        private Button graphname;

        public MyViewHolder(View itemView) {
            super(itemView);

            graphname=(Button) itemView.findViewById(R.id.buttonnn);
            barChart=(BarChart) itemView.findViewById(R.id.barchart);

            barChart.animateY(5000);

            barChart.setDescription("Set Bar Chart Description");
            barValueList.clear();
            for(int j=0;j<mCountList.size();j++){
                barValueList.add(new BarEntry(mCountList.get(j), j));
            }

            BarDataSet barDataSet1 = new BarDataSet(barValueList, "Person Count");

            barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

            ArrayList<String> labels = new ArrayList<String>();
            labels.add("Sun");
            labels.add("Mon");
            labels.add("Tue");
            labels.add("Wed");
            labels.add("Thu");
            labels.add("Fri");
            labels.add("Sat");

            BarData data = new BarData(labels,  barDataSet1);
            barChart.setData(data);

        }
    }

    @Override
    public GraphButtonAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.graph_adapter,parent,false);

        MyViewHolder viewss = new MyViewHolder(view);
        return viewss;
    }

    @Override
    public void onBindViewHolder(final GraphButtonAdapter.MyViewHolder holder, final int position) {

     /*   holder.image.setImageResource(ImageList.get(position));
        holder.nameOfActivity.setText(subActivityTypeList1.get(position));*/

        }

    @Override
    public int getItemCount() {

        return subActivityTypeList1.size();
    }

}
