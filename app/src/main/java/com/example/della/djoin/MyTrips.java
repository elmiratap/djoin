package com.example.della.djoin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseUser;

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
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return ContextCompat.getColor(getApplicationContext(), R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem myTrips = menu.findItem(R.id.my_trips);
        myTrips.setOnMenuItemClickListener(
                new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = new Intent(MyTrips.this, MyTrips.class);
                        startActivity(intent);
                        finish();
                        return false;
                    }
                });
        MenuItem addTrip = menu.findItem(R.id.add_trip);
        addTrip.setOnMenuItemClickListener(
                new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = new Intent(MyTrips.this, AddTrip.class);
                        startActivity(intent);
                        finish();
                        return false;
                    }
                });
        MenuItem findTrips = menu.findItem(R.id.find_trips);
        findTrips.setOnMenuItemClickListener(
                new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = new Intent(MyTrips.this, SearchTrips.class);
                        startActivity(intent);
                        finish();
                        return false;
                    }
                });
        MenuItem myInformation = menu.findItem(R.id.my_information);
        myInformation.setOnMenuItemClickListener(
                new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = new Intent(MyTrips.this, MyInformation.class);
                        startActivity(intent);
                        finish();
                        return false;
                    }
                });
        MenuItem logout = menu.findItem(R.id.logout);
        logout.setOnMenuItemClickListener(
                new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        ParseUser.getCurrentUser().logOutInBackground();
                        Intent intent = new Intent(MyTrips.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                        finish();
                        return false;
                    }
                });
        return true;
    }
}


