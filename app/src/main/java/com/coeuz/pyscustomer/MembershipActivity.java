package com.coeuz.pyscustomer;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;

import com.coeuz.pyscustomer.MembeshipFragment.ACTIVE1;
import com.coeuz.pyscustomer.MembeshipFragment.COMPLETED1;
import com.coeuz.pyscustomer.MembeshipFragment.NEARING1;
import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;



import java.util.ArrayList;

import java.util.List;
import java.util.Objects;


public class MembershipActivity extends AppCompatActivity {
    String mToken;
    ViewPagerAdapter adapter;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);

        TinyDB mtinyTb = new TinyDB(getApplicationContext());
        mToken= mtinyTb.getString(Constant.TOKEN);


        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        ViewPager viewPager = findViewById(R.id.viewpager);

        setupViewPager(viewPager);


        TabLayout tabLayout =  findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
         adapter = new ViewPagerAdapter(getSupportFragmentManager());

   /*     Bundle bundle1 = new Bundle();
      //  bundle1.putSerializable("var",userName);

        Fragment fragment = new AVAILED();
        fragment.setArguments(bundle1);
        adapter.addFragment(fragment,"AVAILED");*/
        //adapter.addFragment(new AVAILED(), "Booking AVAILED");
        adapter.addFragment(new NEARING1(), "NEARING");
        adapter.addFragment(new ACTIVE1(), "ACTIVE");
        adapter.addFragment(new COMPLETED1(), "COMPLETED");



        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent=new Intent(MembershipActivity.this,MainActivity.class);
            startActivity(intent);
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(MembershipActivity.this,MainActivity.class);
        startActivity(intent);
        MembershipActivity.this.finish();
    }


}