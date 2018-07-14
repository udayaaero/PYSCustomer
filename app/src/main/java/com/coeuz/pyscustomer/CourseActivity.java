package com.coeuz.pyscustomer;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.coeuz.pyscustomer.CourseFragment.ACTIVE2;
import com.coeuz.pyscustomer.CourseFragment.COMPLETED2;
import com.coeuz.pyscustomer.CourseFragment.NEARING2;
import com.coeuz.pyscustomer.MembeshipFragment.ACTIVE1;
import com.coeuz.pyscustomer.MembeshipFragment.COMPLETED1;
import com.coeuz.pyscustomer.MembeshipFragment.NEARING1;
import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;

import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String mToken;
    TinyDB mtinyTb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        mtinyTb=new TinyDB(getApplicationContext());
        mToken=mtinyTb.getString(Constant.TOKEN);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        viewPager = (ViewPager) findViewById(R.id.viewpager);

        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

   /*     Bundle bundle1 = new Bundle();
      //  bundle1.putSerializable("var",userName);

        Fragment fragment = new AVAILED();
        fragment.setArguments(bundle1);
        adapter.addFragment(fragment,"AVAILED");*/
        //adapter.addFragment(new AVAILED(), "Booking AVAILED");
        adapter.addFragment(new NEARING2(), "NEARING");
        adapter.addFragment(new ACTIVE2(), "AVAILED");
        adapter.addFragment(new COMPLETED2(), "COMPLETED");





        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
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

        public void addFragment(Fragment fragment, String title) {
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
        }
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}