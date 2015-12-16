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
public class SearchTripsListAdapter extends ArrayAdapter<SearchTripsList> {

    private int resource;
    private LayoutInflater inflater;
    private Context context;
    private List<SearchTripsList> trips;
    private Button btnJoin;
    public static ParseObject takes;

    public SearchTripsListAdapter(Context c, int resourceId, List<SearchTripsList> trips) {
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
        final SearchTripsList tripList = getItem(position);
        final TextView tvTripId = (TextView) itemView.findViewById(R.id.tvTripId);
        tvTripId.setText(tripList.getId());
        TextView tvDestination = (TextView) itemView.findViewById(R.id.tvDestination);
        tvDestination.setText(tripList.getDestination());
        TextView tvTripDateTime = (TextView) itemView.findViewById(R.id.tvTripDateTime);
        tvTripDateTime.setText(tripList.getDate());
        TextView tvSeats = (TextView) itemView.findViewById(R.id.tvSeats);
        tvSeats.setText(tripList.getNumSeats() + " seats left");
        itemView.findViewById(R.id.btnCancel).setVisibility(View.INVISIBLE);
        TextView tvStartLocation = (TextView) itemView.findViewById(R.id.tvStartLocation);
        tvStartLocation.setText(tripList.getStart());
        TextView tvDriver = (TextView) itemView.findViewById(R.id.tvDriver);
        tvDriver.setText(tripList.getDriver());
        TextView tvCar = (TextView) itemView.findViewById(R.id.tvCar);
        tvCar.setText(tripList.getCar());
        final TextView tvReturnTime = (TextView) itemView.findViewById(R.id.tvReturnTime);

        ParseQuery<ParseObject> roundTrip = new ParseQuery<ParseObject>("Trips");
        roundTrip.whereEqualTo("objectId", tvTripId.getText());
        roundTrip.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                for (int i = 0; i < objects.size(); i++) {
                    ParseObject result = objects.get(i);
                    if (result.getBoolean("roundTripBool")) {
                        Log.d("return time", "this exists");
                        tvReturnTime.setText(tripList.getReturnTime());
                        tvReturnTime.setVisibility(View.VISIBLE);
                    } else {
                        Log.d("there is no return", "from school");
                    }
                }
            }
        });

        TextView tvDetails = (TextView) itemView.findViewById(R.id.tvDetails);
        if (tripList.getDetails() != null) {
            tvDetails.setText(tripList.getDetails());
            tvDetails.setVisibility(View.VISIBLE);
        }
        tvStartLocation.setVisibility(View.VISIBLE);
        tvCar.setVisibility(View.VISIBLE);
        tvDriver.setVisibility(View.VISIBLE);
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
                                // TODO need finish here?
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
