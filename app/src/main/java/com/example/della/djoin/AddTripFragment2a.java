package com.example.della.djoin;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddTripFragment2a.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddTripFragment2a#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTripFragment2a extends Fragment implements TimePickerDialog.OnTimeSetListener {

    private TextView tvAddFrag2;

    private View view;
    private Button nextButton;
    public Fragment addTripFragment3;
    private EditText etDepartureDate;
    private EditText etDepartureTime;
    private Calendar cal;

    public AddTripFragment2a() {
    }
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
////    // TODO: Rename and change types of parameters
////    private String mParam1;
////    private String mParam2;
////
////    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddTripFragment2a.
     */
//    // TODO: Rename and change types and number of parameters
//    public static AddTripFragment2a newInstance(String param1, String param2) {
//        AddTripFragment2a fragment = new AddTripFragment2a();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.fragment_add_trip_fragment2a, container, false);

    /*here is an implementation*/

        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        nextButton = (Button) view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(buttonFragOnClickListener);
//        etDepartureDate.setOnClickListener(this);
//        etDepartureTime.setOnClickListener(this);
        etDepartureDate = (EditText) view.findViewById(R.id.etDepartureDate);
        etDepartureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDatePicker();
            }
        });
        this.cal = Calendar.getInstance();

        //return view;
        return view;
    }
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



    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }

    DatePickerDialog.OnDateSetListener datePickerListener=new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {

            cal.set(Calendar.YEAR,year);
            cal.set(Calendar.MONTH,month);
            cal.set(Calendar.DAY_OF_MONTH,day);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }

}
