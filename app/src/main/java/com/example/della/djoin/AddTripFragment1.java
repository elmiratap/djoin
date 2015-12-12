package com.example.della.djoin;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddTripFragment1 extends Fragment {

    private View view;
    private Fragment addTripFragment2a;
    private Button nextButton;
    private EditText etStartLocation;
    private EditText etDestination;
    private CheckBox cbRoundTrip;
    public static String createdBy;
    private String tripId;

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    // Error message
    private TextView tvLocationError;
    public static ParseObject trips;

    public AddTripFragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_trip_fragment1, container, false);
        nextButton = (Button) view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(buttonFragOnClickListener);
        etStartLocation = (EditText) view.findViewById(R.id.etStartLocation);
        etDestination = (EditText) view.findViewById(R.id.etDestination);
        cbRoundTrip = (CheckBox) view.findViewById(R.id.cbRoundTrip);
        createdBy = MainActivity.loggedInUser;
        dbHelper = new DBHelper(getActivity());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public void onPause() {
        super.onPause();
        db.close();
    }

    Button.OnClickListener buttonFragOnClickListener = new Button.OnClickListener(){
        Fragment nextFrag;
        @Override
        public void onClick(View v) {

            // Click next button
            if(v == nextButton) {
                if (etDestination.getText().toString().matches("") || etStartLocation.getText().toString().matches("")) {
                    Toast.makeText(getActivity(), "Please fill in all fields.", Toast.LENGTH_LONG).show();
                } else {
                    // Create new transaction
                    FragmentTransaction trans = getFragmentManager().beginTransaction();

                    // Add the first page's trip information to the database.
                    trips = new ParseObject("Trips");
                    trips.put("createdBy", createdBy);
                    trips.put("startLocation", etStartLocation.getText().toString());
                    trips.put("destination", etDestination.getText().toString());
                    trips.put("roundTripBool", cbRoundTrip.isChecked());


                    // Further required user input depends on whether or not the trip is tround trip.
                    if (cbRoundTrip.isChecked()) {
                        nextFrag = new AddTripFragment2b();
                    }
                    else {
                        nextFrag = new AddTripFragment2a();
                    }

                    // Replace whatever is in the fragment container view with this fragment
                    // and add the transaction to the back stack.
                    trans.replace(R.id.addTrip, nextFrag);
                    trans.addToBackStack(null);
                    trans.commit();
                }
            }
        }
    };
}
