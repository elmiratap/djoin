package com.example.della.djoin;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JoinedTab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JoinedTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JoinedTab extends android.support.v4.app.Fragment {

    public JoinedTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_joined_tab, container, false);
    }


}