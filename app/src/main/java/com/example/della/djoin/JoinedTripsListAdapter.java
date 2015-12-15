package com.example.della.djoin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by elmira on 12/14/2015.
 */
public class JoinedTripsListAdapter extends ArrayAdapter<JoinTripsList> {
    private int resource;
    private LayoutInflater inflater;
    private Context context;
    private List<JoinTripsList> trips;
    private Button btnLeave;
    private TextView tvTripId;

    public JoinedTripsListAdapter(Context c, int resourceId, List<JoinTripsList> trips) {
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
        final JoinTripsList tripList = getItem(position);
        TextView tvTripId = (TextView) itemView.findViewById(R.id.tvTripId);
        tvTripId.setText(tripList.getId());
        TextView tvDestination = (TextView) itemView.findViewById(R.id.tvDestination);
        tvDestination.setText(tripList.getDestination());
        TextView tvTripDateTime = (TextView) itemView.findViewById(R.id.tvTripDateTime);
        tvTripDateTime.setText(tripList.getDate());
        TextView tvSeats = (TextView) itemView.findViewById(R.id.tvSeats);
        tvSeats.setText(tripList.getNumSeats() + " seats left");
        btnLeave = (Button) itemView.findViewById(R.id.btnLeave);
        btnLeave.setVisibility(View.VISIBLE);
        btnLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View view = v;
                AlertDialog.Builder cancelDialogBuilder = new AlertDialog.Builder(context);
                cancelDialogBuilder
                        .setTitle("Are you sure you want to leave this trip?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Views saved to access specific components of the list view
                                ListView lv = (ListView) itemView.getParent();
                                RelativeLayout rl = (RelativeLayout) view.getParent();
                                final TextView tvTripId = (TextView) rl.findViewById(R.id.tvTripId);
                                final int taggedPosition = lv.getPositionForView(itemView);
                                String tripId = tvTripId.getText().toString();
                                String position = ""+taggedPosition;
                                //use async task to delete objects on a background thread and
                                //update ui when finished, much simpler than using locks
                                new DeleteTripAndRelatedTakes().execute(tripId, position);


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
    class DeleteTripAndRelatedTakes extends AsyncTask<String,Void,String> {
        int taggedPosition;
        @Override
        protected String doInBackground(String... params) {
            String tripId = params[0];
            taggedPosition = Integer.parseInt(params[1]);
            ParseQuery<ParseObject> tripIdQ = ParseQuery.getQuery("Trips");
            tripIdQ.whereEqualTo("objectId", tripId);

            ParseQuery<ParseObject> takesTrip = ParseQuery.getQuery("TakesTrip");
            takesTrip.whereEqualTo("tripId", tripId);

            try {

                List<ParseObject> tripObjects = tripIdQ.find();
                List<ParseObject> takes = takesTrip.find();

                //ParseObject.deleteAll(tripObjects);
                for (ParseObject object : tripObjects) {
                    object.increment("availableSeats");
                    object.saveEventually();
                }
                ParseObject.deleteAll(takes);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            return "Cool";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){
                trips.remove(taggedPosition);
                notifyDataSetChanged();
            }
        }
    }
}
