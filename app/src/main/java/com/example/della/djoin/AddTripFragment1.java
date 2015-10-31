package com.example.della.djoin;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddTripFragment1 extends Fragment {

    View view;
    Fragment addTripFragment2a;
    Button nextButton;
    public AddTripFragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_trip_fragment1, container, false);
        //nextButton = (Button) view.findViewById(R.id.nextButton);
        //nextButton.setOnClickListener(this);
        nextButton = (Button) view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(buttonFragOnClickListener);
        return view;
    }


    Button.OnClickListener buttonFragOnClickListener = new Button.OnClickListener(){
        Fragment nextFrag;
        @Override
        public void onClick(View v) {
            if(v == nextButton) {
                nextFrag = new AddTripFragment2a();
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
}
