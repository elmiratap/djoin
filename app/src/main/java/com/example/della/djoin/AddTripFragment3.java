package com.example.della.djoin;

import android.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddTripFragment3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddTripFragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTripFragment3 extends Fragment {

    // TODO done button

    View view;
    private EditText etAvailableSeats;
    private EditText etCarMake;
    private EditText etCarColor;
    private CheckBox cbSavedCar;
    private Button doneButton;
    private EditText etDetails;

    public AddTripFragment3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_trip_fragment3, container, false);
        etAvailableSeats = (EditText) view.findViewById(R.id.etAvailableSeats);
        etAvailableSeats.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        etCarMake = (EditText) view.findViewById(R.id.etCarMake);
        etCarColor = (EditText) view.findViewById(R.id.etCarColor);
        cbSavedCar = (CheckBox) view.findViewById(R.id.cbSavedCar);
        cbSavedCar.setOnCheckedChangeListener(myCheckedListener);
        etDetails = (EditText) view.findViewById(R.id.etDetails);
        doneButton = (Button) view.findViewById(R.id.doneButton);
        doneButton.setOnClickListener(buttonFragmentOnClickListener);
        return view;
    }

    CheckBox.OnCheckedChangeListener myCheckedListener = new CheckBox.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                Log.d("check box", "is checked");
                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.whereEqualTo("username", MainActivity.loggedInUser);
                query.findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(List<ParseUser> objects, ParseException e) {
                        if (e == null) {
                            for (int i = 0; i < objects.size(); i++) {
                                ParseUser result = objects.get(i);
                                String carColor = result.getString("carColor");
                                String carMake = result.getString("carMake");
                                etCarColor.setText(carColor);
                                etCarMake.setText(carMake);
                            }
                        } else {
                            // Error
                        }
                    }
                });
            }
        }
    };

    Button.OnClickListener buttonFragmentOnClickListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(v == doneButton) {
                if (etAvailableSeats.getText().toString().matches("") || etCarMake.getText().toString().matches("")
                        || etCarColor.getText().toString().matches("")) {
                    Toast.makeText(getActivity(), "Please fill in all fields.", Toast.LENGTH_LONG).show();
                } else {
                    // Add all information to the database and switch to the MyTrips screen.
                    Intent intent = new Intent(getActivity(), MyTrips.class);
                    AddTripFragment1.trips.put("availableSeats", Integer.parseInt(etAvailableSeats.getText().toString()));
                    AddTripFragment1.trips.put("carMake", etCarMake.getText().toString());
                    AddTripFragment1.trips.put("carColor", etCarColor.getText().toString());
                    AddTripFragment1.trips.put("details", etDetails.getText().toString());

                    // Persist information from all steps to the database.
                    // Do not persist if the user has not gone through all the steps.
                    AddTripFragment1.trips.saveInBackground();

                    startActivity(intent);
                    getActivity().finish();
                }
            }
        }

    };



}
