package com.example.della.djoin;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class MyTrips extends Activity {

    TextView placeholderText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trips);
        placeholderText = (TextView) findViewById(R.id.placeholder);
    }

}
