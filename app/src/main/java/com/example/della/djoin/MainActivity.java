package com.example.della.djoin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by Della Anjeh and Elmira Tapkanova
 */

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText etUsername;
    public static String loggedInUser;
    private EditText etPassword;
    private TextView tvTitle;
    private Button btnLogin;
    private Button btnRegister;
    // Error message
    private TextView loginError;
    private DBHelper dbHelper;
//    private SQLiteDatabase db;
//    private Cursor cursor;
    private String[] userColumns;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "2Cj7clB0aDfkYtjM3D8CovDduojXw6775Iz9cxP3", "lbEX2zvla4bT91sgq7uStIuDyY4aqcG7CsGFYYAA");



        tvTitle = (TextView) findViewById(R.id.tvTitle);
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
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void addTripScreen(View view) {
        Intent intent = new Intent(MainActivity.this, AddTrip.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                } else {
                    loginError.setVisibility(View.VISIBLE);
                }
            }
        });

    }

}
