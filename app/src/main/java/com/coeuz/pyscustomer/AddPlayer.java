package com.coeuz.pyscustomer;

import android.app.AlertDialog;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.coeuz.pyscustomer.requiredclass.TinyDB;

public class AddPlayer extends AppCompatActivity {
    private CardView select;
    ViewGroup progressView;
    boolean isProgressShowing = false;
    private AlertDialog alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TinyDB mTinyDb = new TinyDB(getApplicationContext());


        select=findViewById(R.id.SelectedLayout);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder builder = new AlertDialog.Builder(AddPlayer.this);
                LayoutInflater inflater = (LayoutInflater) AddPlayer.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = inflater.inflate(R.layout.add_player, null);
                builder.setView(customView);

                builder.setCancelable(true);
                builder.setInverseBackgroundForced(true);


                ImageView cancel = customView.findViewById(R.id.skipBtn);


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alert.dismiss();
                    }
                });
                alert = builder.create();

                alert.show();

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if(isProgressShowing){
                hideProgressingView();
            }else{
                this.finish();}
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(isProgressShowing){
            hideProgressingView();
        }else{
            super.onBackPressed();
        }}
    public void hideProgressingView() {
        View v = AddPlayer.this.findViewById(android.R.id.content).getRootView();
        ViewGroup viewGroup = (ViewGroup) v;
        viewGroup.removeView(progressView);
        isProgressShowing = false;
    }
}
