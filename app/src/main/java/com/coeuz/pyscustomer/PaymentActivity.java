    package com.coeuz.pyscustomer;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coeuz.pyscustomer.AdapterClass.OfferAdapter;
import com.coeuz.pyscustomer.AdapterClass.PaymentOfferAdapter;
import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

    public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {


    Button pay;
    int payamount;
    TinyDB mTinyDb;
        private String  mToken;

    private TextView startDateText;
    private LinearLayout noInternetLayout,allViewLayout;

        TextView mSessionBookedFor,mSessionStartDate,mSessionEndDate,mSessionStartTime,mSessionEndTime,
                sessionCost,sessionOffer,mAddress,applyAmount,saveAmount;
        String vvendorName,vvendorArea,vsessionDate,vsessionStartTime,vsessionEndTime,vsessionCost,newDates;
        String newvsessionStartTime,newvsessionEndTime;

        String sessionStartDate,sessionEndDate,subTotalCost,offerCost,sessionStartTime,sessionEndTime,
                applyCost,offercosts,saveCost;
        private  LinearLayout endDateLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTinyDb=new TinyDB(getApplicationContext());
        mToken=mTinyDb.getString(Constant.TOKEN);
        noInternetLayout = (LinearLayout) findViewById(R.id.NoInternetLayout);
        allViewLayout = (LinearLayout) findViewById(R.id.allViewlayout);

        vvendorName=mTinyDb.getString(Constant.VENDORNAME);
        vvendorArea=mTinyDb.getString(Constant.VENDORAREA);
        vsessionDate=mTinyDb.getString(Constant.CALENDERDATE);

        sessionStartDate=mTinyDb.getString(Constant.PAYSDATE);
        sessionEndDate=mTinyDb.getString(Constant.PAYEDATE);
        subTotalCost=mTinyDb.getString(Constant.PAYCOST);
        offerCost=mTinyDb.getString(Constant.PAYOFFER);
        sessionStartTime=mTinyDb.getString(Constant.PAYSTIME);
        sessionEndTime=mTinyDb.getString(Constant.PAYETIME);

        Integer offerCosts=Integer.valueOf(offerCost);
        Log.d("dhewuidfhewi", String.valueOf(offerCosts));
        Integer subTotalCosts=Integer.valueOf(subTotalCost);
        Log.d("dhewuidfhewi1", String.valueOf(subTotalCosts));
        Integer total=(subTotalCosts*offerCosts)/100;
        Log.d("dhewuidfhewi2", String.valueOf(total));
        offercosts= String.valueOf(total);
        Integer totalcost=subTotalCosts-total;
        Log.d("dhewuidfhewi3", String.valueOf(totalcost));
        applyCost= String.valueOf(totalcost);
        saveCost= String.valueOf(total);





        pay=(Button)findViewById(R.id.razorpay);

        mSessionBookedFor=(TextView) findViewById(R.id.SessionBookedFor);
        mSessionStartDate=(TextView) findViewById(R.id.startDates1);
        mSessionEndDate=(TextView) findViewById(R.id.endDates1);
        mSessionStartTime=(TextView) findViewById(R.id.DurationStart);
        mSessionEndTime=(TextView) findViewById(R.id.DurationEnd);
        sessionCost=(TextView) findViewById(R.id.subtotal);
        sessionOffer=(TextView) findViewById(R.id.offerReduction);
        mAddress=(TextView) findViewById(R.id.Address);
        applyAmount=(TextView) findViewById(R.id.netAmount);
        saveAmount=(TextView) findViewById(R.id.saveAmount);
        startDateText=(TextView) findViewById(R.id.startDateText);
        endDateLayout=(LinearLayout) findViewById(R.id.endDateLayout);


        try {
            Log.d("fhruifhruei1",vsessionDate);
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = (Date)formatter.parse(vsessionDate);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
            newDates = sdf.format(date);

            Log.d("fhruifhruei",newDates);


        } catch (Exception e) {
            e.printStackTrace();
        }

        mSessionBookedFor.setText(vvendorName);
        mAddress.setText(vvendorArea);
        mSessionStartDate.setText(sessionStartDate);
        Log.d("nenioer1",sessionEndDate);
        if(sessionEndDate.equals("")){
            endDateLayout.setVisibility(View.GONE);
            startDateText.setText("Session Date");
        }else{
            String frgsessionEndDate = sessionEndDate.substring(2);
            sessionEndDate=frgsessionEndDate;
        }
        mSessionEndDate.setText(sessionEndDate);
        mSessionStartTime.setText(sessionStartTime);
        mSessionEndTime.setText(sessionEndTime);
        sessionCost.setText("₹ "+subTotalCost);
        sessionOffer.setText("₹ "+offercosts);
        applyAmount.setText("₹ "+applyCost);
        saveAmount.setText(" ₹"+saveCost);




        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });
    }

        private void startPayment() {
            if(!mToken.equals("")) {

            payamount=Integer.parseInt(applyCost);

            Checkout checkout=new Checkout();
            checkout.setImage(R.drawable.icon);
            final Activity activity=this;

            try {
                JSONObject options=new JSONObject();
                options.put("description","Booking Sessions");
                options.put("currency","INR");
                options.put("amount",payamount*100);
                checkout.open(activity,options);
            }catch (JSONException e){
                e.printStackTrace();
            }
            }else{
                Intent intent=new Intent(PaymentActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        }

        @Override
        public void onPaymentSuccess(String s) {

            Toast.makeText(this, "Your Payment is Successful", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaymentError(int i, String s) {

            Toast.makeText(this, "Your Payment failed", Toast.LENGTH_SHORT).show();
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
       /* Raghu (to udaya) 5:35 PM>
            {
            "type":"course",
            "vendorId":"2",
            "slotId":"221",
            "subActivityId":"2",
            "personCount":"1",
            "slotStartTime":"10:40",
            "slotEndTime":"17:50",
            "bookedForDate":"2018-06-20",
            "userId":"3",
            "paymentId":"pay_92zUQM8FifZb1r"
            }
            Raghu (to udaya) 5:36 PM> {
            "type":"consecutive",
            "vendorId":"2",
            "slotId":"221",
            "subActivityId":"2",
            "personCount":"1",
            "slotStartTime":"17:40",
            "bookedForDate":"2018-06-20",
            "userId":"3",
            "paymentId":"pay_92zUQM8FifZb1r"
            }
            Raghu (to udaya) 5:38 PM> {
            "bookingCost" : "2222",
            "bookingType" : "MEMBERSHIP",
            "currencyCode":"INR",
            "maxAllowed":" 2",
            "membershipType":"MONTHLY",
            "subActivityId":"4",
            "vendorId":"1",
            "userId":"3"
            }
            Raghu (to udaya) 5:40 PM> {
            "type":"single",
            "vendorId":"2",
            "slotId":"221",
            "subActivityId":"2",
            "personCount":"1",
            "slotStartTime":"10:40",
            "slotEndTime":"17:50",
            "bookedForDate":"2018-06-20",
            "userId":"3",
            "paymentId":"pay_92zUQM8FifZb1r"
            }
            Raghu (to udaya) 6:34 PM> http://localhost:8080/UPass/api/service/slot/slotBooking
            Raghu (to udaya) 6:35 PM>  {
            "bookingCost" : "2222",
            "bookingType" : "MEMBERSHIP",
            "currencyCode":"INR",
            "maxAllowed":" 2",
            "membershipType":"MONTHLY",
            "subActivityId":"4",
            "vendorId":"1",
            "userId":"3"
            }
            Raghu (to udaya) 6:35 PM> {
            "type":"consecutive",
            "vendorId":"2",
            "slotId":"221",
            "subActivityId":"2",
            "personCount":"1",
            "slotStartTime":"17:40",
            "bookedForDate":"2018-06-20",
            "userId":"3",
            "paymentId":"pay_92zUQM8FifZb1r"
            }
            Raghu (to udaya) 6:36 PM> {
            "type":"course",
            "vendorId":"2",
            "slotId":"221",
            "subActivityId":"2",
            "personCount":"1",
            "slotStartTime":"10:40",
            "slotEndTime":"17:50",
            "bookedForDate":"2018-06-20",
            "userId":"3",
            "paymentId":"pay_92zUQM8FifZb1r"
            }
*/