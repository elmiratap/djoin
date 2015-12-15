package com.example.della.djoin;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MyTrips extends AppCompatActivity {

    Toolbar toolbar;
    TripsPagerAdapter adapter;
    ViewPager pager;
    CharSequence titles[]={"Created","Joined"};
    int numOfTabs = 2;
    SlidingTabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trips);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        adapter = new TripsPagerAdapter(getSupportFragmentManager(), titles, numOfTabs);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        //TODO check up on how to define a context
        //http://www.android4devs.com/2015/01/how-to-make-material-design-sliding-tabs.html
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return ContextCompat.getColor(getApplicationContext(), R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
//
//        myTripsPagerAdapter =
//                new TripsPagerAdapter(
//                        getSupportFragmentManager());
//        mViewPager = (ViewPager) findViewById(R.id.pager);
//        mViewPager.setAdapter(myTripsPagerAdapter);

    }

}


