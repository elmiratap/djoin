package com.example.della.djoin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class MyInformation extends AppCompatActivity {

    private TextView tvCarColorLabel;
    private TextView tvCarMakeLabel;
    private TextView tvCarColor;
    private TextView tvCarMake;
    private Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);
        tvCarColor = (TextView) findViewById(R.id.tvCarColor);
        tvCarMake = (TextView) findViewById(R.id.tvCarMake);
        btnEdit = (Button) findViewById(R.id.btnEdit);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", MainActivity.loggedInUser);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                        ParseUser result = objects.get(i);
                        String carColor = result.getString("carColor");
                        String carMake = result.getString("carMake");
                        tvCarMake.setText("Car make: " + carMake);
                        tvCarColor.setText("Car color: " + carColor);
                        tvCarMake.setVisibility(View.VISIBLE);
                        tvCarColor.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInformation.this, EditInformation.class);
                startActivity(intent);
                finish();
            }
        });
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
                        Intent intent = new Intent(MyInformation.this, MyTrips.class);
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
                        Intent intent = new Intent(MyInformation.this, AddTrip.class);
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
                        Intent intent = new Intent(MyInformation.this, SearchTrips.class);
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
                        Intent intent = new Intent(MyInformation.this, MyInformation.class);
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
                        Intent intent = new Intent(MyInformation.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                        finish();
                        return false;
                    }
                });
        return true;
    }
}