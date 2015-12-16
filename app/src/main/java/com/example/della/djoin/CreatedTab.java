package com.example.della.djoin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class CreatedTab extends android.support.v4.app.Fragment  {
    private Button btnAddTrip;
    private ListView lvCreatedTrips;
    private List<CreatedTripList> createdTripList;
    private Context c;
    private TextView tvNoCreatedTrips;
    private ArrayAdapter<CreatedTripList> adapter;

    public CreatedTab() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        final String ARG_OBJECT = "object";
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View view =  inflater.inflate(R.layout.fragment_created_tab, container, false);

//        setHasOptionsMenu(true);
        tvNoCreatedTrips = (TextView)view.findViewById(R.id.tvNoCreatedTrips);
        tvNoCreatedTrips.setVisibility(View.INVISIBLE);

        createdTripList = new ArrayList<CreatedTripList>();
        adapter = new CreatedTripsListAdapter(getActivity(), R.layout.list_view, createdTripList);

        c = getActivity();
        lvCreatedTrips = (ListView) view.findViewById(R.id.lvCreatedTrips);
//        lvCreatedTrips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                CreatedTripsListAdapter.tvStartLocation.setVisibility(View.VISIBLE);
//            }
//        });
        lvCreatedTrips.setAdapter(adapter);
        //lvCreatedTrips.setAdapter(new CreatedTripsListAdapter(c, R.layout.list_view, createdTripList));

        // Only show trips that are in the future and created by the logged in user.
        Calendar c = Calendar.getInstance();
        Date now = c.getTime();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Trips");
        query.whereEqualTo("createdBy", MainActivity.loggedInUser);
        query.whereGreaterThan("departureDateAndTime", now);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    ParseObject result;
                    for (int i = 0; i < objects.size(); i++) {
                        result = objects.get(i);
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
                                adapter.add(new CreatedTripList(destination, numSeats, date, id, returnTime, start, driver, car, details));
                            } else {
                                Log.d(returnTime, "N/A");
                                adapter.add(new CreatedTripList(destination, date, numSeats, id, returnTime, start, driver, car));
                            }
                        } else {
                            Log.d("in the else", "yess");
                            if (details != null) {
                                Log.d("one way trip", details);
                                adapter.add(new CreatedTripList(destination, numSeats, date, id, start, driver, car, details));
                            } else {
                                Log.d("one way trip", "N/A");
                                adapter.add(new CreatedTripList(destination, date, numSeats, id, start, driver, car));
                            }
                        }

                    }

                } else { // TODO this does not show up, fix it
                    // If there are no results, display appropriate message.
                    tvNoCreatedTrips.setVisibility(View.VISIBLE);
                }
            }
        });

        btnAddTrip = (Button) view.findViewById(R.id.btnAddTrip);
        btnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTrip.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.my_trips:
//                Log.d("tapped ", "my trips");
//                return true;
//            case R.id.create_trip:
//                Log.d("tapped ", "create trips");
//                return true;
//            case R.id.join_trip:
//                Log.d("tapped ", "join trips");
//                return true;
//            case R.id.my_information:
//                Log.d("tapped ", "my information");
//                return true;
//            case R.id.logout:
//                Log.d("tapped ", "logout");
//                return true;
//            default:
//                // If we got here, the user's action was not recognized.
//                // Invoke the superclass to handle it.
//                return super.onOptionsItemSelected(item);
//
//        }
//    }
}


