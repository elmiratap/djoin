package com.example.della.djoin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JoinedTab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JoinedTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JoinedTab extends android.support.v4.app.Fragment {

    private Button btnFindTrip;

    public JoinedTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_joined_tab, container, false);

        btnFindTrip = (Button) view.findViewById(R.id.btnFindTrip);
        btnFindTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), JoinTripActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }



}
