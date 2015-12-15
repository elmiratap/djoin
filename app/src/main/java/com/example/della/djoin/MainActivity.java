package com.example.della.djoin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by Della Anjeh and Elmira Tapkanova
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsername;
    public static String loggedInUser;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;
    // Error message
    private TextView loginError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        // Enable Local Datastore.
//        Parse.enableLocalDatastore(this);
//
//        Parse.initialize(this, "2Cj7clB0aDfkYtjM3D8CovDduojXw6775Iz9cxP3", "lbEX2zvla4bT91sgq7uStIuDyY4aqcG7CsGFYYAA");

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        loginError = (TextView) findViewById(R.id.loginError);
//        dbHelper = new DBHelper(this);
        loginError.setVisibility(View.GONE);
    }

    public void registerScreen(View view) {
        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        db = dbHelper.getWritableDatabase();
//        cursor = db.query(dbHelper.TABLE_USER, userColumns, null, null, null, null, null, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        db.close();
    }

    @Override
    public void onClick(View v) {
        loggedInUser = etUsername.getText().toString();
        ParseUser.logInInBackground(etUsername.getText().toString(), etPassword.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // Valid username and password. Redirect to My Trips.
                    Intent intent = new Intent(MainActivity.this, MyTrips.class);
                    startActivity(intent);
                    finish();
                } else {
                    loginError.setVisibility(View.VISIBLE);
                }
            }
        });

    }

}
