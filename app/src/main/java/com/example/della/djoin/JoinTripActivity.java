package com.example.della.djoin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JoinTripActivity extends AppCompatActivity {

    private EditText etSearch;
    private ListView lvSearchableTrips;
    private List<JoinTripsList> joinTripList;
    private ArrayAdapter<JoinTripsList> adapter;
    private Button btnAddTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_trip);

        etSearch = (EditText) findViewById(R.id.etSearch);
        joinTripList = new ArrayList<JoinTripsList>();
        adapter = new JoinTripsListAdapter(JoinTripActivity.this, R.layout.list_view, joinTripList);
        lvSearchableTrips = (ListView) findViewById(R.id.lvSearchableTrips);
        lvSearchableTrips.setAdapter(adapter);

        Calendar c = Calendar.getInstance();
        Date now = c.getTime();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Trips");
        query.whereNotEqualTo("createdBy", MainActivity.loggedInUser);
        query.whereGreaterThan("availableSeats", 0);
        query.whereGreaterThan("departureDateAndTime", now);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    ParseObject result;
                    for (int i = 0; i < objects.size(); i++) {
                        Log.d("SUCCCCCCEESSS", "SSSSSS");
                        result = objects.get(i);
                        String destination = result.getString("destination");
                        int numSeats = result.getInt("availableSeats");
                        String date = String.valueOf(result.getDate("departureDateAndTime"));
                        String id = result.getObjectId();
                        adapter.add(new JoinTripsList(destination, numSeats, date, id));
                    }
                } else { // TODO this does not show up, fix it
                    Log.d("FAILUREEEEEE", "EEEE");
                    // If there are no results, display appropriate message.
                }
            }
        });
//        query.setLimit(5);

        btnAddTrip = (Button) findViewById(R.id.btnAddTrip);
        btnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinTripActivity.this, AddTrip.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_join_trip, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
