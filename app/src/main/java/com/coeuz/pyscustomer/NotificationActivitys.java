package com.coeuz.pyscustomer;


import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.arturogutierrez.Badges;
import com.github.arturogutierrez.BadgesNotSupportedException;

import java.util.Objects;

public class NotificationActivitys extends AppCompatActivity {

    NotificationCompat.Builder notification;
    private static final int uniqueID=45618;
    String getData,getdata1;

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_activitys);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getData= Objects.requireNonNull(getIntent().getExtras()).getString("datam");
        getdata1=getIntent().getExtras().getString("data");

        TextView text =findViewById(R.id.textView);
        TextView text1 =  findViewById(R.id.textView1);

   /*     text.setText(getData);
        text1.setText(getdata1);*/


        notification=new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);
    }
    public void buttonClick(View view){

        notification.setTicker("ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("title");
        notification.setContentText("Description");

        Intent intent=new Intent(this,NotificationActivitys.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(uniqueID,notification.build());


    }
    public void tenCount(View view) {

        try {
            Badges.setBadge(getApplicationContext(),10);
        } catch (BadgesNotSupportedException e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }


}