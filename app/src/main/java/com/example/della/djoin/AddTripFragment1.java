package com.example.della.djoin;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddTripFragment1 extends Fragment implements View.OnClickListener {

    View view;
    Button nextButton;
    Fragment addTripFragment2;
    public AddTripFragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_trip_fragment1, container, false);
        nextButton = (Button) view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        // Switch to the next fragment upon tapping the next button
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        addTripFragment2 = new AddTripFragment2a();
        ft.replace(R.id.addTrip1, addTripFragment2);
        ft.addToBackStack(null);
        ft.commit();
    }
}
