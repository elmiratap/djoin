package com.example.della.djoin;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class CreatedTab extends android.support.v4.app.Fragment  {
    private Button btnAddTrip;
    private Button btnTest;
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

        tvNoCreatedTrips = (TextView)view.findViewById(R.id.tvNoCreatedTrips);
        tvNoCreatedTrips.setVisibility(View.INVISIBLE);

        adapter = new ArrayAdapter<CreatedTripList>(getActivity(), R.layout.list_view);

        createdTripList = new ArrayList<CreatedTripList>();
//        createdTripList.add(new CreatedTripList("Target", "4 seats left", "Today 6:00 PM"));
        c = getActivity();
        lvCreatedTrips = (ListView) view.findViewById(R.id.lvCreatedTrips);
        lvCreatedTrips.setAdapter(adapter);
        //lvCreatedTrips.setAdapter(new CreatedTripsListAdapter(c, R.layout.list_view, createdTripList));
        lvCreatedTrips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CreatedTripList t = (CreatedTripList) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(), "You're going to " + t.getDestination().toString(), Toast.LENGTH_LONG).show();
            }
        });
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Trips");
        query.whereEqualTo("createdBy", MainActivity.loggedInUser);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    ParseObject result;
                    for (int i=0; i<objects.size(); i++) {
                        Log.d("SUCCCCCCEESSS", "SSSSSS");
                        result = objects.get(i);
                        String destination = result.getString("destination");
                        int numSeats = result.getInt("availableSeats");
                        String date = result.getString("departureDate");
                        createdTripList.add(new CreatedTripList(destination, numSeats, date));
                    }
                    adapter.notifyDataSetChanged();

                } else { // TODO exception always raised if no results?
                    Log.d("FAILUREEEEEE", "EEEE");
                    // If there are no results, display appropriate message.
                    //tvNoCreatedTrips.setVisibility(View.VISIBLE);
                }
            }
        });

//        btnAddTrip = (Button) view.findViewById(R.id.btnAddTrip);
//        btnAddTrip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), AddTrip.class);
//                startActivity(intent);
//            }
//        });
//        btnTest = (Button) view.findViewById(R.id.btnTest);
//        btnTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), MyInformation.class);
//                startActivity(intent);
//            }
//        });

        return view;
    }

}


