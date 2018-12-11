package com.coeuz.pyscustomer.AdapterClass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.coeuz.pyscustomer.R;

import java.util.ArrayList;



public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<Bitmap> images = new ArrayList<>();
   /* private Integer[] images={R.drawable.gym,R.drawable.zumba,R.drawable.yoga
            ,R.drawable.aerobics,R.drawable.haircoloring};
    public ViewPagerAdapter(Context context) {
        this.context=context;
    }*/

    public ViewPagerAdapter(Context applicationContext, ArrayList<Bitmap> bitmapsLists) {
        this.context=applicationContext;
        this.images=bitmapsLists;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        @SuppressLint("InflateParams") View view= layoutInflater.inflate(R.layout.viewpageradapter,null);
        ImageView imageView=view.findViewById(R.id.SlideimageView);
        imageView.setImageBitmap(images.get(position));

        ViewPager vp=(ViewPager)container;
        vp.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp=(ViewPager)container;
        View view=(View)object;
        vp.removeView(view);
    }
}
