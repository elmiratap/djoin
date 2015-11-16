package com.example.della.djoin;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.TextView;

public class AddTrip extends Activity {


    private TextView tvTitle;
    // Declare the add trip screen fragments
    public Fragment addTripFrag1;
    public Fragment addTripFrag2a;
    public Fragment addTripFrag3;


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
        ft.addToBackStack(null); // nothing to add yet
        ft.commit(); // Ready to show fragment


    }
}
