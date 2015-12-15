package com.example.della.djoin;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


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
    private EditText etCarModel;
    private EditText etCarMake;
    private EditText etCarColor;
    private CheckBox cbSavedCar;
    private Button doneButton;
    private EditText etDetails;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
//

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
        etCarModel = (EditText) view.findViewById(R.id.etCarModel);
        etCarColor = (EditText) view.findViewById(R.id.etCarColor);
        cbSavedCar = (CheckBox) view.findViewById(R.id.cbSavedCar);
        etDetails = (EditText) view.findViewById(R.id.etDetails);
        doneButton = (Button) view.findViewById(R.id.doneButton);
        doneButton.setOnClickListener(buttonFragmentOnClickListener);
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

    Button.OnClickListener buttonFragmentOnClickListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(v == doneButton) {
                if (etAvailableSeats.getText().toString().matches("") || etCarMake.getText().toString().matches("")
                        || etCarModel.getText().toString().matches("") || etCarColor.getText().toString().matches("")
                        || etDetails.getText().toString().matches("")) {
                    Toast.makeText(getActivity(), "Please fill in all fields.", Toast.LENGTH_LONG).show();
                } else {
                    // Add all information to the database and switch to the MyTrips screen.
                    Intent intent = new Intent(getActivity(), MyTrips.class);
                    AddTripFragment1.trips.put("availableSeats", Integer.parseInt(etAvailableSeats.getText().toString()));
                    AddTripFragment1.trips.put("carMake", etCarMake.getText().toString());
                    AddTripFragment1.trips.put("carModel", etCarModel.getText().toString());
                    AddTripFragment1.trips.put("carColor", etCarColor.getText().toString());
                    AddTripFragment1.trips.put("details", etDetails.getText().toString());

                    // Persist information from all steps to the database.
                    // Do not persist if the user has not gone through all the steps.
                    AddTripFragment1.trips.saveInBackground();

                    startActivity(intent);
                }
            }
        }

    };



}
