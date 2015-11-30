package com.example.della.djoin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A placeholder fragment containing a simple view.
 */
public class CreatedTab extends android.support.v4.app.Fragment  {
    private Button btnAddTrip;
    private Button btnTest;
    public CreatedTab() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        final String ARG_OBJECT = "object";
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View view =  inflater.inflate(R.layout.fragment_created_tab, container, false);
        btnAddTrip = (Button) view.findViewById(R.id.btnAddTrip);
        btnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTrip.class);
                startActivity(intent);
            }
        });
        btnTest = (Button) view.findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyInformation.class);
                startActivity(intent);
            }
        });
        return view;
//
    }
}


