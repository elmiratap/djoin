package com.example.della.djoin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JoinedTab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JoinedTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JoinedTab extends android.support.v4.app.Fragment {
    private TextView tvNoJoinedTrips;
    private List<JoinTripsList> joinTripsList;
    private ListView lvJoinedTrips;
    private Context c;
    private ArrayAdapter<JoinTripsList> adapter;

    private Button btnFindTrip;

    public JoinedTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_joined_tab, container, false);

        tvNoJoinedTrips = (TextView)view.findViewById(R.id.tvNoJoinedTrips);
        tvNoJoinedTrips.setVisibility(View.INVISIBLE);

        joinTripsList = new ArrayList<JoinTripsList>();
        adapter = new JoinedTripsListAdapter(getActivity(), R.layout.list_view, joinTripsList);

        c = getActivity();
        lvJoinedTrips = (ListView) view.findViewById(R.id.lvJoinedTrips);
        lvJoinedTrips.setAdapter(adapter);

        // Only show trips that are in the future that the logged in user has joined.
        Calendar c = Calendar.getInstance();
        Date now = c.getTime();

        ParseQuery<ParseObject> hasJoined = ParseQuery.getQuery("TakesTrip");
        hasJoined.whereEqualTo("username", MainActivity.loggedInUser);

        ParseQuery<ParseObject> tripInfo = ParseQuery.getQuery("Trips");
        tripInfo.whereGreaterThan("departureDateAndTime", now);
        tripInfo.whereNotEqualTo("createdBy", MainActivity.loggedInUser);

        // Only include trips that the user has joined.
        tripInfo.whereMatchesKeyInQuery("objectId", "tripId", hasJoined);

        tripInfo.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    Log.d("num objects: ", String.valueOf(objects.size()));
                    ParseObject result;
                    for (int i = 0; i < objects.size(); i++) {
                        result = objects.get(i);

                        Log.d("this is the id", result.getObjectId());

                        String destination = result.getString("destination");
                        int numSeats = result.getInt("availableSeats");
                        String date = String.valueOf(result.getDate("departureDateAndTime"));
                        String id = result.getObjectId();

                        Log.d("dest ", destination);
                        Log.d("seats ", String.valueOf(numSeats));
                        Log.d("date ", date);
                        Log.d("iddd ", id);


                        adapter.add(new JoinTripsList(destination, numSeats, date, id));
                    }
                } else { // TODO this does not show up, fix it
                }
            }
        });


        btnFindTrip = (Button) view.findViewById(R.id.btnFindTrip);
        btnFindTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), JoinTripActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }



}
