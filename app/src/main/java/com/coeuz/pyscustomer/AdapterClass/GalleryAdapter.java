package com.coeuz.pyscustomer.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.coeuz.pyscustomer.GalleryActivity;
import com.coeuz.pyscustomer.R;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;

import java.util.ArrayList;


public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {

    public Context mcontext;
    TinyDB mtinyDb;


    private ArrayList<Integer> ImageList=new ArrayList<>();







    public GalleryAdapter(Context applicationContext) {
        mcontext=applicationContext;

        mtinyDb=new TinyDB(mcontext);
        ImageList.add(R.drawable.gym);
        ImageList.add(R.drawable.haircoloring);
        ImageList.add(R.drawable.asaloon);
        ImageList.add(R.drawable.agymsquar);
        ImageList.add(R.drawable.azumbasquar);
        ImageList.add(R.drawable.aaerobicssquar);
        ImageList.add(R.drawable.adancesquar);
        ImageList.add(R.drawable.agymsquar);
        ImageList.add(R.drawable.aparlar);
        ImageList.add(R.drawable.apilatessquar);
        ImageList.add(R.drawable.aspa);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;


        public MyViewHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.imageview300);


        }
    }

    @Override
    public GalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_adapter,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GalleryAdapter.MyViewHolder holder, final int position) {

        holder.image.setImageResource(ImageList.get(position));


        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> images = new ArrayList<>();
                images.add(R.drawable.gym);
                images.add(R.drawable.zumba);
                images.add(R.drawable.yoga);
                images.add(R.drawable.haircoloring);
                images.add(R.drawable.adancesquar);
                images.add(R.drawable.agymsquar);
                images.add(R.drawable.aparlar);
                images.add(R.drawable.apilatessquar);
                images.add(R.drawable.aspa);

                Intent intent = new Intent(mcontext, GalleryActivity.class);
                intent.putIntegerArrayListExtra(GalleryActivity.EXTRA_NAME, images);
                mcontext.startActivity(intent);
            }
        });
        }

    @Override
    public int getItemCount() {

        return ImageList.size();
    }

}
