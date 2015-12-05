package com.example.della.djoin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


/**
 * Created by Della on 12/1/2015.
 */
public class CreatedTripsListAdapter extends ArrayAdapter<CreatedTripList> {

    private int resource;
    private LayoutInflater inflater;
    private Context context;

    public CreatedTripsListAdapter(Context c, int resourceId, List<CreatedTripList> trips) {
        super(c, resourceId, trips);
        resource = resourceId;
        inflater = LayoutInflater.from(c);
        context = c;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(resource, null);
        CreatedTripList tripList = getItem(position);
        TextView tvDestination = (TextView) convertView.findViewById(R.id.tvDestination);
        //tvDestination.setText(tripList.getDestination());
        TextView tvTripDateTime = (TextView) convertView.findViewById(R.id.tvTripDateTime);
        //tvTripDateTime.setText(tripList.getDate());
        TextView tvSeats = (TextView) convertView.findViewById(R.id.tvSeats);
        //tvSeats.setText(tripList.getNumSeats());
        return convertView;
    }


}
