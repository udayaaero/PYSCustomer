package com.coeuz.pyscustomer;


import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;

import com.coeuz.pyscustomer.BookingHistoryFragment.AVAILED;
import com.coeuz.pyscustomer.BookingHistoryFragment.NEARING;


import java.util.ArrayList;

import java.util.List;
import java.util.Objects;


public class BookingHistoryActivity extends AppCompatActivity {



    String mToken;

    String userName;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);



        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            userName = extras.getString("jfiwo");
            if(userName!=null){
         }
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle bundle1 = new Bundle();
        bundle1.putSerializable("var",userName);

        Fragment fragment = new AVAILED();
        fragment.setArguments(bundle1);
        adapter.addFragment(fragment,"Future");
        //adapter.addFragment(new AVAILED(), "Booking AVAILED");
        adapter.addFragment(new NEARING(), "History");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            Intent intent=new Intent(BookingHistoryActivity.this,MainActivity.class);
            startActivity(intent);
            this.finish();
        }


        return super.onOptionsItemSelected(item);
    }
 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.payment, menu);

        menu.findItem(R.id.all);
        menu.findItem(R.id.selectActivity);
        return true;
    }
*/
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(BookingHistoryActivity.this,MainActivity.class);
        startActivity(intent);
        BookingHistoryActivity.this.finish();
    }

}