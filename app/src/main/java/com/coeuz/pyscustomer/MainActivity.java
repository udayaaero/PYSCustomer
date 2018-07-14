package com.coeuz.pyscustomer;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
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
import com.coeuz.pyscustomer.AdapterClass.MainAdapter;
import com.coeuz.pyscustomer.AdapterClass.NearYouAdapter;
import com.coeuz.pyscustomer.AdapterClass.SubActivityAdapter;
import com.coeuz.pyscustomer.AdapterClass.ViewPagerAdapter;
import com.coeuz.pyscustomer.ModelClass.SubActivityModel;
import com.coeuz.pyscustomer.Requiredclass.AnimatingRefreshButtonManager;
import com.coeuz.pyscustomer.Requiredclass.ConnectionDetector;
import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.InternetMessageActivity;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;
import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.transitionseverywhere.Rotate;
import com.transitionseverywhere.TransitionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ConnectionDetector cd;
    private final static int MY_PERMISSION_REQUEST_lOCATION = 1;
    private String provider;


    ArrayList<String> subActivityList=new ArrayList<>();
    ArrayList<String> subActivityIdList=new ArrayList<>();

    String locatonValues,initialLat,intialLong;
    private ProgressBar mprogressBar;
    RecyclerView mRecyclerView,nearRecycler;
    private TinyDB tinyDB;
    String mToken;
    NavigationView navigationView;
    TextView nav_user;

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;


    private String muserId,mcustomerName,mmobileNumber,memailId;

    private LinearLayout noInternetLayout;
    private RelativeLayout allViewLayout;

    private AnimatingRefreshButtonManager mRefreshButtonManager;
    private LinearLayout mViewMore;
    private TextView mviewMoretext;
    private ArrayList<SubActivityModel> subActivityModels;
    private NearYouAdapter nearYouAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        tinyDB=new TinyDB(getApplicationContext());
        mcustomerName=tinyDB.getString(Constant.CUSTOMERNAME);

        mToken=tinyDB.getString(Constant.TOKEN);
        initialLat=tinyDB.getString(Constant.INITIALLAT);
        intialLong=tinyDB.getString(Constant.INITIALLONG);
        if(!mToken.equals("")){
            Log.d("fwuiffewew",mToken);
        }
        locatonValues=tinyDB.getString(Constant.INITIALLOCATION);
        //locatonValues = getIntent().getStringExtra("EXTRA_SESSION_ID");

         noInternetLayout = (LinearLayout) findViewById(R.id.NoInternetLayout);
         allViewLayout = (RelativeLayout) findViewById(R.id.allViewlayout);

        mprogressBar=(ProgressBar)findViewById(R.id.progressbar100);
        mRecyclerView=(RecyclerView)findViewById(R.id.recycler);
        mRecyclerView.setNestedScrollingEnabled(false);

        nearRecycler=(RecyclerView)findViewById(R.id.galleryrecycle);

        ViewGroup.LayoutParams params1=mRecyclerView.getLayoutParams();
        params1.height= 400;
        mRecyclerView.setLayoutParams(params1);

       /* final ViewFlipper flipper = (ViewFlipper)findViewById(R.id.flipper1);
        flipper.startFlipping();

        Timer t1 = new Timer(false);
        t1.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        flipper.stopFlipping();
                    }
                });
            }
        }, 8000);*/



        mViewMore = (LinearLayout) findViewById(R.id.viewMore);
        mviewMoretext = (TextView) findViewById(R.id.viewMoreText);
        final boolean[] showingFirst = {true};
        mViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (showingFirst[0]) {
                    ViewGroup.LayoutParams params=mRecyclerView.getLayoutParams();
                    params.height= RecyclerView.LayoutParams.WRAP_CONTENT;
                    mRecyclerView.setLayoutParams(params);
                    showingFirst[0] = false;
                    mviewMoretext.setText("View Less Categories");
                } else {
                    ViewGroup.LayoutParams params=mRecyclerView.getLayoutParams();
                    params.height= 400;
                    mRecyclerView.setLayoutParams(params);
                    showingFirst[0] = true;
                    mviewMoretext.setText("View More Categories");
                }


            }
        });

        subActivityModels = new ArrayList<>();
        nearYouAdapter = new NearYouAdapter(MainActivity.this,subActivityModels);


        nearRecycler.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false));
        nearRecycler.setHasFixedSize(true);
        nearRecycler.setAdapter(nearYouAdapter);
       /* cd = new ConnectionDetector(getApplicationContext());
        if (!cd.isConnected()) {
            noInternetLayout.setVisibility(View.VISIBLE);
            allViewLayout.setVisibility(View.GONE);
            Button button=(Button)findViewById(R.id.TryAgain);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recreate();
                }
            });
        }*/

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        //Todo
        View hView =  navigationView.getHeaderView(0);
        //ImageView imgvw = (ImageView)hView.findViewById(R.id.imageView);
         nav_user = (TextView)hView.findViewById(R.id.textView);
        //imgvw .setImageResource(R.drawable.ic_today_black_24dp);
        if(!mcustomerName.equals("")){
        nav_user.setText(mcustomerName.toUpperCase());}else{
            nav_user.setText("PYS");
        }
        navigationView.setNavigationItemSelectedListener(this);
        if(mToken.equals("")){
            Log.d("ndjn","huihi");
            navigationView.getMenu().getItem(1).setVisible(false);
            navigationView.getMenu().getItem(2).setVisible(false);
            navigationView.getMenu().getItem(3).setVisible(false);
            navigationView.getMenu().getItem(4).setVisible(false);
            navigationView.getMenu().getItem(5).setVisible(false);
            navigationView.getMenu().getItem(6).setVisible(false);
            navigationView.getMenu().getItem(7).setVisible(false);//if you want to hide first item
           // navigationView.getMenu().getItem(1).setVisible(true); // if you want to show second menu item should be visible
        }



        viewPager=(ViewPager)findViewById(R.id.viewPager);
        sliderDotspanel=(LinearLayout)findViewById(R.id.SliderDots);
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getApplicationContext());
        viewPager.setAdapter(viewPagerAdapter);

        dotscount=viewPagerAdapter.getCount();
        dots=new ImageView[dotscount];
        for(int i=0;i<dotscount;i++){
            dots[i]=new ImageView(getApplicationContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.non_active_dot));
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,8,0);
            sliderDotspanel.addView(dots[i],params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));

        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),2000,3000);



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });





        mprogressBar.setVisibility(View.VISIBLE);
        subActivityList.clear();
        subActivityIdList.clear();
        String URL = Constant.API+"/general/getAllSubActivities";
        StringRequest request1 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jjtirjtit9i3oti3", String.valueOf(response));


                mprogressBar.setVisibility(View.GONE);


                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);


                        String subActivityId = jsonObject.getString("subActivityId");
                        String subActivityType = jsonObject.getString("subActivityType");

                        subActivityList.add(subActivityType);
                        subActivityIdList.add(subActivityId);

                        tinyDB.putListString(Constant.SubActivityIdList,subActivityIdList);
                        //tinyDB.putListString(Constant.VendorIdList,vendorIdList);
                        Log.d("yeruwiuifwi1", String.valueOf(subActivityList.size()));


                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this,3);
                        mRecyclerView.setLayoutManager(layoutManager);
                        RecyclerView.Adapter adapter = new MainAdapter(getApplicationContext(), subActivityList,subActivityIdList);
                        mRecyclerView.setAdapter(adapter);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("bsduf", String.valueOf(error));
                mprogressBar.setVisibility(View.GONE);

                if (error instanceof NetworkError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button=(Button)findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});
                } else if (error instanceof ServerError) {

                    Log.d("heuiwirhu1", String.valueOf(error));
                }  else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button=(Button)findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});

                }
            }
        }) {

        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
        requestQueue1.add(request1);

        if(mToken!=null) {
            String URL1 = Constant.APIONE + "/user/getCurrentUser";

            StringRequest request = new StringRequest(Request.Method.GET, URL1, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("mvefn1", String.valueOf(response));
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        muserId = jsonObject.getString("userId");
                        mcustomerName = jsonObject.getString("customerName");
                        mmobileNumber = jsonObject.getString("mobileNumber");
                        memailId = jsonObject.getString("emailId");
                        tinyDB.putString(Constant.CUSTOMERNAME, mcustomerName);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("miremio", String.valueOf(error));

                 /*   if (error instanceof NetworkError) {
                        Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ServerError) {

                        Log.d("heuiwirhu1", String.valueOf(error));
                    } else if (error instanceof ParseError) {
                        Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                    } else if (error instanceof NoConnectionError) {
                        Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof TimeoutError) {
                        Toast.makeText(getApplicationContext(), "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();

                    }*/
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers1 = new HashMap<String, String>();

                    headers1.put("X-Auth-Token", String.valueOf(mToken).replaceAll("\"", ""));
                    return headers1;

                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);

    }
    if(intialLong!=null){
            Log.d("fjiewjfiw",intialLong);
            Log.d("fjiewjfiw1",initialLat);
    }
        String URL5 = Constant.API + "/user/getVendorsByNearingYou?&lat="+initialLat+"&long="+intialLong+"&offset=0&limit=5";


        StringRequest request5 = new StringRequest(Request.Method.GET, URL5, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("hgerithjiow", String.valueOf(response));


                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() == 0) {
                        Log.d("hgerithjiow", String.valueOf(response));
                    } else {
                        Gson gson = new Gson();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String vendorName = jsonObject.getString("vendorName");
                            Integer vendorId = jsonObject.getInt("vendorId");
                            String area = jsonObject.getString("area");

                            subActivityModels.add(new SubActivityModel(vendorName, area,vendorId));
                            nearYouAdapter.notifyDataSetChanged();

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("frwgtw", String.valueOf(error));



                if (error instanceof NetworkError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button=(Button)findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});
                } else if (error instanceof ServerError) {

                    Log.d("heuiwirhu1", String.valueOf(error));
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    allViewLayout.setVisibility(View.GONE);
                    Button button=(Button)findViewById(R.id.TryAgain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }});

                }
            }
        });
        RequestQueue requestQueue5 = Volley.newRequestQueue(getApplicationContext());
        requestQueue5.add(request5);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(true);
            builder.setIcon(R.drawable.doorexiticon);
            builder.setTitle("Do you want to Exit?");
            builder.setInverseBackgroundForced(true);
            builder.setPositiveButton("Yes",new DialogInterface.OnClickListener()
            {

                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    LoginManager.getInstance().logOut();
                    finish();

                }
            });
            builder.setNegativeButton("No",new DialogInterface.OnClickListener()
            {

                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            });
            AlertDialog alert=builder.create();
            alert.show();
            // super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem refreshItem = menu.findItem(R.id.mlocation);

  /*      TransitionManager.beginDelayedTransition(refreshItem, new Rotate());
        R.id.LocationChange.setRotation(isRotated ? 135 : 0);*/



        if(locatonValues!=null){
            menu.findItem(R.id.mlocation).setTitle(locatonValues);}
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
      /*  if (id == R.id.mlocation) {

        }*/


        //noinspection SimplifiableIfStatement

        if (id == R.id.LocationChange) {

            Intent intent=new Intent(this,LocationChangeActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.Home) {
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.BookingHistory) {
            if(!mToken.equals("")){
                Intent intent=new Intent(this,BookingHistoryActivity.class);
                startActivity(intent);
            }else {
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);}

        } else if (id == R.id.TransactionHistory) {
            if(!mToken.equals("")){
                Intent intent=new Intent(this,TransactionHistoryActivity.class);
                startActivity(intent);
            }else {
                Intent intent=new Intent(this,LoginActivity.class);
                startActivity(intent);}

        } else if (id == R.id.Settings) {
            if(!mToken.equals("")){
                Intent intent=new Intent(this,SettingsActivity.class);
                startActivity(intent);
            }else {
                Intent intent=new Intent(this,LoginActivity.class);
                startActivity(intent);}

        } else if (id == R.id.Notifications) {
            Intent intent=new Intent(this,NotificationActivitys.class);
            startActivity(intent);

        } else if (id == R.id.Memberships) {

            if(!mToken.equals("")){
                Intent intent=new Intent(this,MembershipActivity.class);
                startActivity(intent);
            }else {
                Intent intent=new Intent(this,LoginActivity.class);
                startActivity(intent);}

        }
        else if (id == R.id.Course) {

            if(!mToken.equals("")){
                Intent intent=new Intent(this,CourseActivity.class);
                startActivity(intent);
            }else {
                Intent intent=new Intent(this,LoginActivity.class);
                startActivity(intent);}

        }
        else if (id == R.id.LogOut) {
            tinyDB.putString(Constant.TOKEN,"");
            tinyDB.putString(Constant.CUSTOMERNAME, "");
            nav_user.setText("PYS");
            LoginManager.getInstance().logOut();
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();

        }
        else if (id == R.id.nav_share) {
            String shareBody = "https://play.google.com/store/apps/details?id=************************";
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "APP NAME (Open it in Google Play Store to Download the Application)");

            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));



        }
        else if (id == R.id.nav_send) {
          


        } else if (id == R.id.AboutUs) {

                Intent intent=new Intent(this,AboutPys.class);
                startActivity(intent);
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            if (MainActivity.this==null){
                return;
            }else {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(viewPager.getCurrentItem()==0){
                            viewPager.setCurrentItem(1);
                        }else if(viewPager.getCurrentItem()==1){
                            viewPager.setCurrentItem(2);
                        }else if(viewPager.getCurrentItem()==2){
                            viewPager.setCurrentItem(3);
                        }else if(viewPager.getCurrentItem()==3){
                            viewPager.setCurrentItem(4);
                        }else if(viewPager.getCurrentItem()==4){
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }}
    }
}
   /* implementation fileTree(include: ['*.jar'], dir: 'libs')
    implementation 'com.android.support:appcompat-v7:27.0.2'
            implementation 'com.android.support:design:27.0.2'
            implementation 'com.android.support.constraint:constraint-layout:1.0.2'
            implementation 'com.android.support:support-v4:27.0.2'
            compile 'com.android.support:cardview-v7:27.0.2'
            compile 'com.android.support:recyclerview-v7:27.0.2'
            testImplementation 'junit:junit:4.12'
            androidTestImplementation 'com.android.support.test:runner:1.0.1'
            androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.1'

            compile 'com.google.android.gms:play-services-auth:11.8.0'
            compile 'com.google.firebase:firebase-core:11.8.0'
            compile 'com.firebaseui:firebase-ui-auth:3.1.0'
            compile 'com.mcxiaoke.volley:library:1.0.19'
            compile 'de.hdodenhof:circleimageview:2.2.0'

            implementation 'com.facebook.android:facebook-login:4.31.0'
            compile 'com.google.code.gson:gson:2.8.2'
            compile 'com.pnikosis:materialish-progress:1.7'
            compile 'de.hdodenhof:circleimageview:2.1.0'
            compile 'devs.mulham.horizontalcalendar:horizontalcalendar:1.2.0'
            compile 'com.google.android.gms:play-services-maps:11.8.0'
            compile 'com.google.code.gson:gson:2.8.2'
            compile 'com.github.jhonnyx2012:horizontal-picker:1.0.6'
            compile 'com.google.android.gms:play-services-ads:11.8.0'
            compile 'com.google.android.gms:play-services-identity:11.8.0'
            compile 'com.google.android.gms:play-services-gcm:11.8.0'
            compile 'com.onesignal:OneSignal:3.7.1'
            compile 'com.google.android.gms:play-services-analytics:11.8.0'
            compile 'com.google.android.gms:play-services-location:11.8.0'
            compile 'com.android.support:customtabs:27.0.2'
            compile 'com.craftman.cardform:cardform:0.0.2'*/