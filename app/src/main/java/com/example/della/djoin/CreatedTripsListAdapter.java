package com.example.della.djoin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


/**
 * Created by Della on 12/1/2015.
 */
public class CreatedTripsListAdapter extends ArrayAdapter<CreatedTripList> {

    private int resource;
    private LayoutInflater inflater;
    private Context context;
    private List<CreatedTripList> trips;
    private Button btnCancel;
    private TextView tvTripId;

    public CreatedTripsListAdapter(Context c, int resourceId, List<CreatedTripList> trips) {
        super(c, resourceId, trips);
        resource = resourceId;
        inflater = LayoutInflater.from(c);
        context = c;
        this.trips = trips;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View itemView;
        if (convertView != null) {
            itemView = convertView;
        } else {
            itemView = inflater.inflate(resource, null);
        }
        final CreatedTripList tripList = getItem(position);
        TextView tvTripId = (TextView) itemView.findViewById(R.id.tvTripId);
        tvTripId.setText(tripList.getId());
        TextView tvDestination = (TextView) itemView.findViewById(R.id.tvDestination);
        tvDestination.setText(tripList.getDestination());
        TextView tvTripDateTime = (TextView) itemView.findViewById(R.id.tvTripDateTime);
        tvTripDateTime.setText(tripList.getDate());
        TextView tvSeats = (TextView) itemView.findViewById(R.id.tvSeats);
        tvSeats.setText(tripList.getNumSeats() + " seats left");
        btnCancel = (Button) itemView.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // view saved for access from inner loops
                final View view = v;
                AlertDialog.Builder cancelDialogBuilder = new AlertDialog.Builder(context);
                cancelDialogBuilder
                        .setTitle("Are you sure you want to delete this trip?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // views saved to access specific components of the list view
                                ListView lv = (ListView) itemView.getParent();
                                RelativeLayout rl = (RelativeLayout) view.getParent();
                                final TextView tvTripId = (TextView) rl.findViewById(R.id.tvTripId);
                                final int taggedPosition = lv.getPositionForView(itemView);
                                // query that finds a trip by its id for deletion
                                ParseQuery<ParseObject> query = ParseQuery.getQuery("Trips");
                                query.whereEqualTo("objectId", tvTripId.getText().toString());
                                query.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> objects, ParseException e) {
                                        if (e == null) {
                                            for(ParseObject object : objects) {
                                                // removes the trip from the database
                                                object.deleteInBackground(new DeleteCallback() {
                                                    @Override
                                                    public void done(ParseException e) {
                                                        // removes the trip from the list view
                                                        trips.remove(taggedPosition);
                                                        notifyDataSetChanged();
                                                    }
                                                });
                                            }
                                        } else {
                                            Log.d("no trip found", "cry a lot");
                                        }

                                    }
                                });
                            }
                        })

                        // when the user doesn't want to delete their trip
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog cancelDialog = cancelDialogBuilder.create();
                cancelDialog.show();
            }
        });

        return itemView;
    }


}
