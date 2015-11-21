package com.example.della.djoin;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddTripFragment2b.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddTripFragment2b#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTripFragment2b extends Fragment {

    View view;
    Button nextButton;
    Fragment addTripFragment3;
    private EditText etDepartureDate;
    private EditText etDepartureTime;
    private EditText etReturnDate;
    private EditText etReturnTime;
    private Calendar cal;
    private CheckBox cbSameAsDepart;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    boolean isChecked;

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AddTripFragment2b.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AddTripFragment2b newInstance(String param1, String param2) {
//        AddTripFragment2b fragment = new AddTripFragment2b();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    public AddTripFragment2b() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_trip_fragment2b, container, false);
        nextButton = (Button) view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(buttonFragmentOnClickListener);
        etDepartureDate = (EditText) view.findViewById(R.id.etDepartureDate);
        etDepartureTime = (EditText) view.findViewById(R.id.etDepartureTime);
        etReturnTime = (EditText) view.findViewById(R.id.etReturnTime);
        etReturnDate = (EditText) view.findViewById(R.id.etReturnDate);
        cbSameAsDepart = (CheckBox) view.findViewById(R.id.cbSameAsDepart);
        cbSameAsDepart.setOnCheckedChangeListener(myCheckedListener);
        dbHelper = new DBHelper(getActivity());

        etReturnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDatePicker();
            }
        });
        etDepartureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDatePicker();
            }
        });
        etDepartureTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });
        etReturnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showTimePicker();
            }
        });
        this.cal = Calendar.getInstance();

        return view;
    }

    CheckBox.OnCheckedChangeListener myCheckedListener = new CheckBox.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                Log.d("check box", "is checked");
                etReturnDate.setText(etDepartureDate.getText().toString());
                }
        }
    };


    Button.OnClickListener buttonFragmentOnClickListener = new Button.OnClickListener(){
        Fragment nextFrag;


        @Override
        public void onClick(View v) {
            if(v == nextButton) {

                // Create new transaction
                FragmentTransaction trans = getFragmentManager().beginTransaction();

                ContentValues cv = new ContentValues(4);
                cv.put(dbHelper.DEPARTURE_DATE, etDepartureDate.getText().toString());
                cv.put(dbHelper.DEPARTURE_TIME, etDepartureTime.getText().toString());
                cv.put(dbHelper.RETURN_DATE, etReturnDate.getText().toString());
                cv.put(dbHelper.RETURN_TIME, etReturnTime.getText().toString());

                nextFrag = new AddTripFragment3();

                try {

                    db.beginTransaction();
                    // Log.d("content values", String.valueOf(cv));
                    db.insertOrThrow(dbHelper.TABLE_TRIP, null, cv);
//                //cursor = db.query(dbHelper.TABLE_USER, userColumns, null, null, null, null, null, null);

//                tvLocationError.setVisibility(View.GONE);
//                dbHelper.getTableAsString(db, dbHelper.TABLE_USER);
                    // Log.d("results catch", dbHelper.getTableAsString(db, dbHelper.TABLE_TRIP));
                    db.setTransactionSuccessful();
                    nextFrag = new AddTripFragment3();
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


                // Replace whatever is in the fragment container view with this fragment
                // and add the transaction to the back stack.
                trans.replace(R.id.addTrip, nextFrag);
                trans.addToBackStack(null);
                trans.commit();
            }
        }

    };

    private void showTimePicker() {
        TimePickerFragment time = new TimePickerFragment();
        Calendar calendar = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("hour", calendar.get(Calendar.HOUR));
        args.putInt("minute", calendar.get(Calendar.MINUTE));
        time.setArguments(args);
        time.setCallBack(ontime);
        time.show(getFragmentManager(), "Time Picker");
    }

    TimePickerDialog.OnTimeSetListener ontime = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            etDepartureTime.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
        }
    };

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            etDepartureDate.setText(String.valueOf(monthOfYear+1) + "-" + String.valueOf(dayOfMonth)
                    + "-" + String.valueOf(year));
        }
    };

    Button.OnClickListener buttonFragOnClickListener = new Button.OnClickListener(){
        Fragment nextFrag;
        @Override
        public void onClick(View v) {
            if(v == nextButton) {
                nextFrag = new AddTripFragment2b();
                // Create new transaction
                FragmentTransaction trans = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment container view with this fragment
                // and add the transaction to the back stack.
                trans.replace(R.id.addTrip, nextFrag);
                trans.addToBackStack(null);
                trans.commit();
            }
        }

    };

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p/>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }

}
