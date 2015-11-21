package com.example.della.djoin;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


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

                // Create new transaction
                //FragmentTransaction trans = getFragmentManager().beginTransaction();
                Intent intent = new Intent(getActivity(), MyTrips.class);

                ContentValues cv = new ContentValues(4);
                cv.put(dbHelper.NUM_PASSENGERS, etAvailableSeats.getText().toString());
                cv.put(dbHelper.CAR_MAKE, etCarMake.getText().toString());
                cv.put(dbHelper.CAR_MODEL, etCarModel.getText().toString());
                cv.put(dbHelper.CAR_COLOR, etCarColor.getText().toString());
                //TODO: implement autofill for car info
                cv.put(dbHelper.DESCRIPTION, etDetails.getText().toString());


                try {

                    db.beginTransaction();
                    // Log.d("content values", String.valueOf(cv));
                    db.insertOrThrow(dbHelper.TABLE_TRIP, null, cv);
//                //cursor = db.query(dbHelper.TABLE_USER, userColumns, null, null, null, null, null, null);

//                tvLocationError.setVisibility(View.GONE);
//                dbHelper.getTableAsString(db, dbHelper.TABLE_USER);
                    // Log.d("results catch", dbHelper.getTableAsString(db, dbHelper.TABLE_TRIP));
                    db.setTransactionSuccessful();
                    Log.d("results try", dbHelper.getTableAsString(db, dbHelper.TABLE_TRIP));


                } catch (SQLiteConstraintException e) {
                    Log.d("results catch", dbHelper.getTableAsString(db, dbHelper.TABLE_TRIP));
//                // tells user the username they entered is already taken
                    //Log.d("results catch", dbHelper.getTableAsString(db, dbHelper.TABLE_TRIP));
//                tvLocationError.setVisibility(View.VISIBLE); // TODO decide if we want to conserve space
//                return;
                } finally {
                    db.endTransaction();
                }

                startActivity(intent);
                // Replace whatever is in the fragment container view with this fragment
                // and add the transaction to the back stack.
//                trans.replace(R.id.addTrip, nextFrag);
//                trans.addToBackStack(null);
//                trans.commit();
            }
        }

    };



}
