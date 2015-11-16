package com.example.della.djoin;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.database.Cursor;
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
import android.widget.TextView;

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
                nextFrag = new AddTripFragment2a();
                // Create new transaction
                FragmentTransaction trans = getFragmentManager().beginTransaction();

                // Put first Add Trip page's content (start location and destination) into the database
                ContentValues cv = new ContentValues(4);
//            Log.d("etstartlocation ", etStartLocation.getText().toString());
//            Log.d("etdest ", etDestination.getText().toString());
//            Log.d("ischecked ", String.valueOf(cbRoundTrip.isChecked()));
                cv.put(dbHelper.START_LOCATION, etStartLocation.getText().toString());
                cv.put(dbHelper.DESTINATION, etDestination.getText().toString());
                cv.put(dbHelper.ROUND_TRIP, cbRoundTrip.isChecked());
                cv.put(dbHelper.CREATED_BY, createdBy);
//
               // db.insert(dbHelper.TABLE_TRIP, null, cv);
                //Log.d("trip results", dbHelper.getTableAsString(db, dbHelper.TABLE_TRIP));
////            Cursor queryres = db.query(dbHelper.TABLE_USER, new String[]{dbHelper.USERNAME},
////                    "username = ?", new String[]{"della"}, null, null, null);
////            queryres.moveToFirst();
            try {

                // makes sure that primary key constraint isn't violated

                Log.d("content values", String.valueOf(cv));
                 db.insertOrThrow(dbHelper.TABLE_TRIP, null, cv);
//                //cursor = db.query(dbHelper.TABLE_USER, userColumns, null, null, null, null, null, null);
//
                etStartLocation.setText(null); // TODO transition, redirect
                etDestination.setText(null);

//                tvLocationError.setVisibility(View.GONE);
//                dbHelper.getTableAsString(db, dbHelper.TABLE_USER);
                Log.d("results try", dbHelper.getTableAsString(db, dbHelper.TABLE_TRIP));
                db.setTransactionSuccessful();
////            Cursor queryres = db.query(dbHelper.TABLE_USER, new String[]{dbHelper.USERNAME},
////                    "username = ?", new String[]{"della"}, null, null, null);
////            queryres.moveToFirst();
//                // if primary key constraint is violated
            } catch (SQLiteConstraintException e) {
//                // tells user the username they entered is already taken
                Log.d("results catch", dbHelper.getTableAsString(db, dbHelper.TABLE_TRIP));
//                tvLocationError.setVisibility(View.VISIBLE); // TODO decide if we want to conserve space
//                return;
            } finally {
                db.endTransaction();
            }

                // Replace whatever is in the fragment container view with this fragment
                // and add the transaction to the back stack.
                trans.replace(R.id.addTrip, nextFrag);
                trans.addToBackStack(null);
                trans.commit();
            }

        }

    };
}
