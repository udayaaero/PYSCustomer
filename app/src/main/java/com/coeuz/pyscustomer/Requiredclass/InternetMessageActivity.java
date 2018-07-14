package com.coeuz.pyscustomer.Requiredclass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.coeuz.pyscustomer.MainActivity;
import com.coeuz.pyscustomer.R;

public class InternetMessageActivity extends Activity {
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_message);
        Toast.makeText(this, "Please turning on your internet ", Toast.LENGTH_SHORT).show();
        Button button=(Button)findViewById(R.id.TryAgain);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

      /*  mHandler.postDelayed(new Runnable() {
            public void run() {
                doStuff();
            }
        }, 500);*/
    }

    private void doStuff() {
        Intent intent = new Intent(InternetMessageActivity.this, MainActivity.class);

        startActivity(intent);
    }
    }

