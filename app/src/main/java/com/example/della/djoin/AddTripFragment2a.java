package com.example.della.djoin;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddTripFragment2a.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddTripFragment2a#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTripFragment2a extends Fragment  {

    private Button nextButton;
    public Fragment addTripFragment3;
    private EditText etDepartureDate;
    private EditText etDepartureTime;
    private Calendar cal;

    public AddTripFragment2a() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.fragment_add_trip_fragment2a, container, false);

        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        nextButton = (Button) view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(buttonFragOnClickListener);
        etDepartureDate = (EditText) view.findViewById(R.id.etDepartureDate);
        etDepartureTime = (EditText) view.findViewById(R.id.etDepartureTime);
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
        this.cal = Calendar.getInstance();

        return view;
    }

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

                // Make sure the user filled in all of the fields
                if (etDepartureDate.getText().toString().matches("") || etDepartureTime.getText().toString().matches("")) {
                    Toast.makeText(getActivity(), "Please fill in all fields.", Toast.LENGTH_LONG).show();
                }

                // Check that the date for the trip is in the future.
                Calendar c = Calendar.getInstance();
                Date now = c.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm");
                String dateTime = etDepartureDate.getText().toString() + " " + etDepartureTime.getText().toString();

                try {
                    Date dateTimeAsDate = dateFormat.parse(dateTime);
                    if (dateTimeAsDate.before(now)) {
                        Toast.makeText(getActivity(), "Please enter a date in the future.", Toast.LENGTH_LONG).show();
                    } else { // All inputs are valid.
                        // Create new transaction
                        FragmentTransaction trans = getFragmentManager().beginTransaction();

                        AddTripFragment1.trips.put("departureDateAndTime", dateTimeAsDate);

                        // Proceed to the next step.
                        nextFrag = new AddTripFragment3();

                        // Replace whatever is in the fragment container view with this fragment
                        // and add the transaction to the back stack.
                        trans.replace(R.id.addTrip, nextFrag);
                        trans.addToBackStack(null);
                        trans.commit();
                    }
                } catch (ParseException e) {
                    Log.e("Error: " , e.getMessage());
                }

            }
        }

    };
}
