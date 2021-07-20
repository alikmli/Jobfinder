package com.example.stdjob;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends PersianActivity  {

    private ViewPager mainPager;
    private TabLayout tabs;
    private List<Fragment> slides=new LinkedList<>();
    private MainViewPagerAdaprter adapter;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        int type=App.prefs.getIntValue(AppPreferences.PreferencesKeys.USERTYPE);




        if(type == App.STUDENT_TYPE_CODE) {


            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            mainPager = findViewById(R.id.view_pager);
            tabs = findViewById(R.id.tabs);

            toolbar.setVisibility(View.GONE);

            prepareSlides();
            mainPager.setAdapter(adapter);
            tabs.setupWithViewPager(mainPager);
            setStudentIcons();

            mainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 0)
                        toolbar.setVisibility(View.GONE);
                    else {
                        toolbar.setVisibility(View.VISIBLE);
                        toolbar.setTitle("");
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }else{
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            mainPager = findViewById(R.id.view_pager);
            tabs = findViewById(R.id.tabs);

            toolbar.setVisibility(View.GONE);

            prepareCompanySlides();
            mainPager.setAdapter(adapter);
            tabs.setupWithViewPager(mainPager);
            setCompanyIcon();

            mainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 0 || position==2)
                        toolbar.setVisibility(View.GONE);
                    else {
                        toolbar.setVisibility(View.VISIBLE);
                        toolbar.setTitle("");
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

    }

    private void setCompanyIcon(){
        tabs.getTabAt(0).setIcon(R.drawable.profile);
        tabs.getTabAt(1).setIcon(R.drawable.history);
        tabs.getTabAt(2).setIcon(R.drawable.requestwork);
        tabs.getTabAt(3).setIcon(R.drawable.plantutorial);
    }

    private void  prepareCompanySlides(){
        String[] titles=getResources().getStringArray(R.array.company_tabs);
        for(int i=0;i<4;i++){
            switch (i){
                case 0:
                    slides.add(new CompanyProfileFragment());
                    break;
                case 1:
                    slides.add(new CompanyHistoryFragment());
                    break;
                case 2:
                    slides.add(new CompanyJobRequestFragment());
                    break;
                case 3:
                    slides.add(new CompanyRequestPlanFragment());
            }
        }


        adapter=new MainViewPagerAdaprter(getSupportFragmentManager(),0,slides,titles);
    }

    private void setStudentIcons() {
        tabs.getTabAt(0).setIcon(R.drawable.profile);
        tabs.getTabAt(1).setIcon(R.drawable.history);
        tabs.getTabAt(2).setIcon(R.drawable.respon);
        tabs.getTabAt(3).setIcon(R.drawable.jobs);
    }

    private void prepareSlides() {
        String[] titles=getResources().getStringArray(R.array.sudent_tabs);
        for(int i=0;i<4;i++){
            switch (i){
                case 0:
                    slides.add(ProfileFragment.getInstance());
                    break;
                case 1:
                    slides.add(new StudentResponsibilitiesFragment());
                    break;
                case 2:
                    slides.add(new StudentHistoryFragment());
                    break;
                case 3:
                    slides.add(new JobSearchFragment());
            }
        }

        adapter=new MainViewPagerAdaprter(getSupportFragmentManager(),0,slides,titles);

    }


    public class MainViewPagerAdaprter extends FragmentPagerAdapter{
        private List<Fragment> slides;
        private String[] titles;
        public MainViewPagerAdaprter(@NonNull FragmentManager fm, int behavior,List<Fragment> items,String[] titles) {
            super(fm, behavior);
            this.slides=items;
            this.titles=titles;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return slides.get(position);
        }



        @Override
        public int getCount() {
            return slides.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
