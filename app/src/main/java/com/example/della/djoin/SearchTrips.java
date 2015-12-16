package com.example.della.djoin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class SearchTrips extends AppCompatActivity {

    private EditText etSearch;
    private ListView lvSearchableTrips;
    private List<SearchTripsList> searchTripsList;
    private ArrayAdapter<SearchTripsList> adapter;
    private Button btnAddTrip;
    private Button btnSearch;
    private HashSet searchResultHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_trip);
        searchResultHash = new HashSet();

        etSearch = (EditText) findViewById(R.id.etSearch);
        // When the user taps the search bar, delete the auto-populated list entries
        etSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                adapter.clear();
                etSearch.setFocusableInTouchMode(true);
                return false;
            }
        });
        searchTripsList = new ArrayList<SearchTripsList>();
        adapter = new SearchTripsListAdapter(SearchTrips.this, R.layout.list_view, searchTripsList);
        lvSearchableTrips = (ListView) findViewById(R.id.lvSearchableTrips);
        lvSearchableTrips.setAdapter(adapter);

        btnSearch = (Button) findViewById(R.id.btnSearch);

        // Begin a parse query with the search term entered by the user once they tap "go."
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //TODO need to hide the keyboard once "go" tapped
                //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                adapter.clear();

                // Get the current date and time.
                Calendar c = Calendar.getInstance();
                Date now = c.getTime();

                ParseQuery<ParseObject> alreadyJoined = ParseQuery.getQuery("TakesTrip");
                alreadyJoined.whereEqualTo("username", MainActivity.loggedInUser);

                ParseQuery<ParseObject> searchQuery = ParseQuery.getQuery("Trips");
                searchQuery.whereContains("destination", etSearch.getText().toString());
                searchQuery.whereNotEqualTo("createdBy", MainActivity.loggedInUser);
                searchQuery.whereGreaterThan("availableSeats", 0);
                searchQuery.whereGreaterThan("departureDateAndTime", now);

                // Only include trips that the user has not already joined.
                searchQuery.whereDoesNotMatchKeyInQuery("objectId", "tripId", alreadyJoined);

                searchQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            ParseObject result;
                            for (int i = 0; i < objects.size(); i++) {
                                result = objects.get(i);

                                Log.d("this is the id", result.getObjectId());

                                String destination = result.getString("destination");
                                int numSeats = result.getInt("availableSeats");
                                String date = String.valueOf(result.getDate("departureDateAndTime"));
                                String id = result.getObjectId();
                                String start = "Start location: " + String.valueOf(result.getString("startLocation"));
                                String driver = "Driver: " + String.valueOf(result.getString("createdBy"));
                                String car = "Car: " + String.valueOf(result.getString("carColor")) + " " + String.valueOf(result.getString("carMake"));
                                String returnTime = "Return time: " + String.valueOf(result.getDate("returnDateAndTime"));
                                String details = String.valueOf(result.getString("details"));
                                if (result.getBoolean("roundTripBool")) {
                                    if (details != null) {
                                        Log.d(returnTime, details);
                                        adapter.add(new SearchTripsList(destination, numSeats, date, id, returnTime, start, driver, car, details));
                                    } else {
                                        Log.d(returnTime, "N/A");
                                        adapter.add(new SearchTripsList(destination, date, numSeats, id, returnTime, start, driver, car));
                                    }
                                } else {
                                    Log.d("in the else", "yess");
                                    if (details != null) {
                                        Log.d("one way trip", details);
                                        adapter.add(new SearchTripsList(destination, numSeats, date, id, start, driver, car, details));
                                    } else {
                                        Log.d("one way trip", "N/A");
                                        adapter.add(new SearchTripsList(destination, numSeats, date, id, start, driver, car));
                                    }
                                }
                                    adapter.notifyDataSetChanged();
                            }
                        } else { // TODO this does not show up, fix it
                        }
                    }
                });
            }
        });

        // Get the current date and time.
        Calendar c = Calendar.getInstance();
        Date now = c.getTime();

        final ParseQuery<ParseObject> alreadyJoined = ParseQuery.getQuery("TakesTrip");
        alreadyJoined.whereEqualTo("username", MainActivity.loggedInUser);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Trips");
        query.whereNotEqualTo("createdBy", MainActivity.loggedInUser);
        query.whereGreaterThan("availableSeats", 0);
        query.whereGreaterThan("departureDateAndTime", now);

        // Only include trips that the user hasn't already joined.
        query.whereDoesNotMatchKeyInQuery("objectId", "tripId", alreadyJoined);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    Log.d("This works, thank Jesus", "yessss");
                    Log.d(String.valueOf(objects.size()), "kleenex");
                    ParseObject result;
                    for (int i = 0; i < objects.size(); i++) {
                        final List<ParseObject> objectsCopy = objects;
                        result = objects.get(i);

                        Log.d("this is the id", result.getObjectId());

                        final String destination = result.getString("destination");
                        final int numSeats = result.getInt("availableSeats");
                        final String date = String.valueOf(result.getDate("departureDateAndTime"));
                        final String id = result.getObjectId();
                        String start = "Start location: " + String.valueOf(result.getString("startLocation"));
                        String driver = "Driver: " + String.valueOf(result.getString("createdBy"));
                        String car = "Car: " + String.valueOf(result.getString("carColor")) + " " + String.valueOf(result.getString("carMake"));
                        String returnTime = "Return time: " + String.valueOf(result.getDate("returnDateAndTime"));
                        String details = String.valueOf(result.getString("details"));
                        if (result.getBoolean("roundTripBool")) {
                            if (details != null) {
                                Log.d(returnTime, details);
                                adapter.add(new SearchTripsList(destination, numSeats, date, id, returnTime, start, driver, car, details));
                            } else {
                                Log.d(returnTime, "N/A");
                                adapter.add(new SearchTripsList(destination, date, numSeats, id, returnTime, start, driver, car));
                            }
                        } else {
                            Log.d("in the else", "yess");
                            if (details != null) {
                                Log.d("one way trip", details);
                                adapter.add(new SearchTripsList(destination, numSeats, date, id, start, driver, car, details));
                            } else {
                                Log.d("one way trip", "N/A");
                                adapter.add(new SearchTripsList(destination, numSeats, date, id, start, driver, car));
                            }
                        }
                    }
                } else { // TODO this does not show up, fix it
                    Log.d("FAILUREEEEEE", "EEEE" + e.getMessage());
                    // If there are no results, display appropriate message.
                }
            }
        });

//        query.setLimit(5);

        btnAddTrip = (Button) findViewById(R.id.btnAddTrip);
        btnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchTrips.this, AddTrip.class);
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
                        Intent intent = new Intent(SearchTrips.this, MyTrips.class);
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
                        Intent intent = new Intent(SearchTrips.this, AddTrip.class);
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
                        Intent intent = new Intent(SearchTrips.this, SearchTrips.class);
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
                        Intent intent = new Intent(SearchTrips.this, MyInformation.class);
                        startActivity(intent);
                        finish();
                        return false;
                    }
                });
        MenuItem logout = menu.findItem(R.id.logout);
        logout.setOnMenuItemClickListener(
                new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {// TODO how to log out
                        ParseUser.getCurrentUser().logOutInBackground();
                        Intent intent = new Intent(SearchTrips.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                        finish();
                        return false;
                    }
                });
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
