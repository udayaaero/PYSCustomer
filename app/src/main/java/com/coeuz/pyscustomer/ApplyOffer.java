package com.coeuz.pyscustomer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;

import java.util.Objects;

public class ApplyOffer extends AppCompatActivity {
    private String mToken;

    ViewGroup progressView;
    boolean isProgressShowing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_offer);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        TinyDB mTinyDb = new TinyDB(getApplicationContext());
        mToken= mTinyDb.getString(Constant.TOKEN);
    

        LinearLayout moreDetails=findViewById(R.id.MoreDetails);
        moreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isProgressShowing = true;
                progressView = (ViewGroup) getLayoutInflater().inflate(R.layout.offer_details, null);
                View v = ApplyOffer.this.findViewById(android.R.id.content).getRootView();
                ViewGroup viewGroup = (ViewGroup) v;
                viewGroup.addView(progressView);
            }   });

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
        View v = ApplyOffer.this.findViewById(android.R.id.content).getRootView();
        ViewGroup viewGroup = (ViewGroup) v;
        viewGroup.removeView(progressView);
        isProgressShowing = false;
    }
}
