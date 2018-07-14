package com.coeuz.pyscustomer.AdapterClass;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.coeuz.pyscustomer.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CustomAdapter extends BaseAdapter {
    Context context;
   String countryList[];
    int flags[];
    LayoutInflater inflter;
    ArrayList<String> motherServiceList1=new ArrayList<>();

    public CustomAdapter(Context applicationContext, String[] countryList, int[] flags) {
        this.context = context;
        this.countryList = countryList;
        this.flags = flags;
        inflter = (LayoutInflater.from(applicationContext));
    }
/*    CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), motherServiceList);
                    listView.setAdapter(customAdapter);*/

    public CustomAdapter(Context applicationContext, ArrayList<String> motherServiceList) {
        this.context = applicationContext;
        this.motherServiceList1 = motherServiceList;

        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return motherServiceList1.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_listview, null);
        TextView country = (TextView)           view.findViewById(R.id.textView);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        country.setText(motherServiceList1.get(i));
       // icon.setImageResource(flags[i]);
        return view;
    }
}