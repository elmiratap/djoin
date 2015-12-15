package com.example.della.djoin;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
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
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    boolean isChecked;


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

        etReturnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDatePicker(etReturnDate);
            }
        });
        etDepartureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDatePicker(etDepartureDate);
            }
        });
        etDepartureTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(etDepartureTime);
            }
        });
        etReturnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showTimePicker(etReturnTime);
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
                if (etDepartureDate.getText().toString().matches("") || etDepartureTime.getText().toString().matches("")
                        || etReturnDate.getText().toString().matches("") || etReturnTime.getText().toString().matches("")) {
                    Toast.makeText(getActivity(), "Please fill in all fields.", Toast.LENGTH_LONG).show();
                }

                // Check that the dates are valid.
                Calendar c = Calendar.getInstance();
                Date now = c.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm");
                String departureDateAsString = etDepartureDate.getText().toString() + " " + etDepartureTime.getText().toString();
                String returnDateAsString = etReturnDate.getText().toString() + " " + etReturnTime.getText().toString();

                try {
                    // Conver the strings to Date types to compare them.
                    Date departureDateAsDate = dateFormat.parse(departureDateAsString);
                    Date returnDateAsDate = dateFormat.parse(returnDateAsString);

                    // The departure date is in the past or the return date is before the departure date.
                    if (departureDateAsDate.before(now) || returnDateAsDate.before(departureDateAsDate)) {
                        Toast.makeText(getActivity(), "Please enter valid dates.", Toast.LENGTH_LONG).show();
                    } else { // All inputs are valid.
                        // Create new transaction
                        FragmentTransaction trans = getFragmentManager().beginTransaction();
                        AddTripFragment1.trips.put("departureDateAndTime", departureDateAsDate);
                        AddTripFragment1.trips.put("returnDateAndTime", returnDateAsDate);
                        nextFrag = new AddTripFragment3();

                        // Replace whatever is in the fragment container view with this fragment
                        // and add the transaction to the back stack.
                        trans.replace(R.id.addTrip, nextFrag);
                        trans.addToBackStack(null);
                        trans.commit();
                    }
                } catch (ParseException e) {

                }
            }
        }
    };

    private void showTimePicker(EditText et) {
        TimePickerFragment time = new TimePickerFragment();
        Calendar calendar = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("hour", calendar.get(Calendar.HOUR));
        args.putInt("minute", calendar.get(Calendar.MINUTE));
        time.setArguments(args);

        if (et == etDepartureTime) {
            time.setCallBack(onTime1);
            time.show(getFragmentManager(), "Time Picker");
        } else {
            time.setCallBack(onTime2);
            time.show(getFragmentManager(), "Time Picker");
        }
    }

    TimePickerDialog.OnTimeSetListener onTime1 = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            etDepartureTime.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
        }
    };

    TimePickerDialog.OnTimeSetListener onTime2 = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            etReturnTime.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
        }
    };

    private void showDatePicker(EditText et) {
        DatePickerFragment dateFrag = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */

        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        dateFrag.setArguments(args);
        /**
         * Set Call back to capture selected date
         */

        if (et == etDepartureDate) {
            dateFrag.setCallBack(onDate1);
            dateFrag.show(getFragmentManager(), "Date Picker");
        } else {
            dateFrag.setCallBack(onDate2);
            dateFrag.show(getFragmentManager(), "Date Picker");
        }
    }

    DatePickerDialog.OnDateSetListener onDate2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            etReturnDate.setText(String.valueOf(monthOfYear+1) + "-" + String.valueOf(dayOfMonth)
                    + "-" + String.valueOf(year));
        }
    };

    DatePickerDialog.OnDateSetListener onDate1 = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            etDepartureDate.setText(String.valueOf(monthOfYear+1) + "-" + String.valueOf(dayOfMonth)
                    + "-" + String.valueOf(year));
        }
    };
}
