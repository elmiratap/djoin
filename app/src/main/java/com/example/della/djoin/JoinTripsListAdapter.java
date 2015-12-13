package com.example.della.djoin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by Della on 12/6/2015.
 */
public class JoinTripsListAdapter extends ArrayAdapter<JoinTripsList> {

    private int resource;
    private LayoutInflater inflater;
    private Context context;
    private List<JoinTripsList> trips;
    private Button btnJoin;
    private TextView tvTripId;
    public static ParseObject takes;

    public JoinTripsListAdapter(Context c, int resourceId, List<JoinTripsList> trips) {
        super(c, resourceId, trips);
        resource = resourceId;
        inflater = LayoutInflater.from(c);
        context = c;
        this.trips = trips;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View itemView;
        if (convertView != null) {
            itemView = convertView;
        } else {
            itemView = inflater.inflate(resource, null);
        }
        final JoinTripsList tripList = getItem(position);
        final TextView tvTripId = (TextView) itemView.findViewById(R.id.tvTripId);
        tvTripId.setText(tripList.getId());
        TextView tvDestination = (TextView) itemView.findViewById(R.id.tvDestination);
        tvDestination.setText(tripList.getDestination());
        TextView tvTripDateTime = (TextView) itemView.findViewById(R.id.tvTripDateTime);
        tvTripDateTime.setText(tripList.getDate());
        TextView tvSeats = (TextView) itemView.findViewById(R.id.tvSeats);
        tvSeats.setText(tripList.getNumSeats() + " seats left");
        itemView.findViewById(R.id.btnCancel).setVisibility(View.INVISIBLE);
        btnJoin = (Button) itemView.findViewById(R.id.btnJoin);
        btnJoin.setVisibility(View.VISIBLE);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> tripIdQuery = ParseQuery.getQuery("Trips");
                tripIdQuery.whereEqualTo("objectId", tvTripId.getText().toString());
                tripIdQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) { // TODO do extra check to make sure the trip is still valid
                            for (ParseObject object : objects) {
                                object.put("availableSeats", tripList.getNumSeats() - 1);

                                object.saveInBackground();
                            }

                            // Add the trip to the Takes table.
                            takes = new ParseObject("TakesTrip");
                            takes.put("tripId", tvTripId.getText().toString());
                            takes.put("username", MainActivity.loggedInUser);
                            takes.saveInBackground();

                        } else {
                            Log.d("you weren't added", "you can't go");
                        }

                    }
                });
                AlertDialog.Builder joinedDialogBuilder = new AlertDialog.Builder(context);
                joinedDialogBuilder
                        .setView(R.layout.joined_dialog_layout)
                        // Go to the My Trips screen.
                        .setPositiveButton("See My Trips", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(context, MyTrips.class);
                                context.startActivity(intent);
                            }
                        })

                        // Go back to the list of trips available to join.
                        .setNegativeButton("Go back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Remove the trip the user just joined from the list.
                                remove(getItem(position));
                                dialog.cancel();
                            }
                        });

                AlertDialog joinedDialog = joinedDialogBuilder.create();
                joinedDialog.show();
            }
        });

        return itemView;
    }

}
