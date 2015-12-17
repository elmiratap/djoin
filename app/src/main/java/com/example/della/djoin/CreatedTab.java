package com.example.della.djoin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View view =  inflater.inflate(R.layout.fragment_created_tab, container, false);

        tvNoCreatedTrips = (TextView)view.findViewById(R.id.tvNoCreatedTrips);
        tvNoCreatedTrips.setVisibility(View.INVISIBLE);

        createdTripList = new ArrayList<CreatedTripList>();
        adapter = new CreatedTripsListAdapter(getActivity(), R.layout.list_view, createdTripList);

        c = getActivity();
        lvCreatedTrips = (ListView) view.findViewById(R.id.lvCreatedTrips);
        lvCreatedTrips.setAdapter(adapter);

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
                            if (!details.equals("null")) {
                                adapter.add(new CreatedTripList(destination, numSeats, date, id, returnTime, start, driver, car, details));
                            } else {
                                adapter.add(new CreatedTripList(destination, date, numSeats, id, returnTime, start, driver, car));
                            }
                        } else {
                            if (!details.equals("null")) {
                                adapter.add(new CreatedTripList(destination, numSeats, date, id, start, driver, car, details));
                            } else {
                                adapter.add(new CreatedTripList(destination, date, numSeats, id, start, driver, car));
                            }
                        }

                    }

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
}


