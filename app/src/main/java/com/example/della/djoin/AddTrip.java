package com.example.della.djoin;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.ParseUser;

public class AddTrip extends Activity {


    private TextView tvTitle;
    // Declare the add trip screen fragments
    public Fragment addTripFrag1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initializes all views from the activity_add_trip xml file
        setContentView(R.layout.activity_add_trip);

        tvTitle = (TextView) findViewById(R.id.tvTitle);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        addTripFrag1 = new AddTripFragment1();
        ft.add(R.id.addTrip, addTripFrag1); // Display the first fragment
        ft.commit(); // Ready to show fragment
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem myTrips = menu.findItem(R.id.my_trips);
        myTrips.setOnMenuItemClickListener(
                new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = new Intent(AddTrip.this, MyTrips.class);
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
                        Intent intent = new Intent(AddTrip.this, AddTrip.class);
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
                        Intent intent = new Intent(AddTrip.this, SearchTrips.class);
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
                        Intent intent = new Intent(AddTrip.this, MyInformation.class);
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
                        ParseUser.logOutInBackground();
                        Intent intent = new Intent(AddTrip.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                        finish();
                        return false;
                    }
                });
        return true;
    }
}
