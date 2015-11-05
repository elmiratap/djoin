package com.example.della.djoin;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Della Anjeh and Elmira Tapkanova
 */

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText etUsername;
    private EditText etPassword;
    private TextView tvTitle;
    private Button btnLogin;
    private Button btnRegister;
    // Error message
    private TextView loginError;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String[] userColumns;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userColumns = new String[] {dbHelper.USERNAME, dbHelper.PASSWORD, dbHelper.NAME,
                dbHelper.USER_CAR_MAKE, dbHelper.USER_CAR_MODEL, dbHelper.USER_CAR_COLOR};
        setContentView(R.layout.activity_main);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        loginError = (TextView) findViewById(R.id.loginError);
        dbHelper = new DBHelper(this);
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
        db = dbHelper.getWritableDatabase();
        cursor = db.query(dbHelper.TABLE_USER, userColumns, null, null, null, null, null, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        db.close();
    }

    @Override
    public void onClick(View v) {
//        ContentValues cv = new ContentValues(2);
//        cv.put(dbHelper.USERNAME, etUsername.getText().toString());
//        cv.put(dbHelper.PASSWORD, etPassword.getText().toString());
        String enteredUserName = etUsername.getText().toString();
        String enteredPassword = etPassword.getText().toString();

        Cursor mCursor = db.rawQuery("SELECT * FROM " + dbHelper.TABLE_USER + " WHERE    username=username AND password=?", new String[]{enteredUserName,enteredPassword});

        if (mCursor != null)
        {
           Log.d("Valid!", "yay"); // Let them log in - go to my trips

/* record exist */
        }
        else {
            loginError.setVisibility(View.VISIBLE);
            //cursor = db.query(dbHelper.TABLE_USER, userColumns, null, usernameAndPassword, null, null, null, null);

            etUsername.setText(null); // TODO transition, redirect
        }

//            Cursor queryres = db.query(dbHelper.TABLE_USER, new String[]{dbHelper.USERNAME},
//                    "username = ?", new String[]{"della"}, null, null, null);
//            queryres.moveToFirst();
        // if primary key constraint is violated
//        try {
//            // makes sure that primary key constraint isn't violated
//            db.insertOrThrow(dbHelper.TABLE_USER, null, cv);
//            cursor = db.query(dbHelper.TABLE_USER, userColumns, null, null, null, null, null, null);
//
//            etUsername.setText(null); // TODO transition, redirect
//            etPassword.setText(null);
//            loginError.setVisibility(View.GONE);
////            Cursor queryres = db.query(dbHelper.TABLE_USER, new String[]{dbHelper.USERNAME},
////                    "username = ?", new String[]{"della"}, null, null, null);
////            queryres.moveToFirst();
//            // if primary key constraint is violated
//        } catch (SQLiteConstraintException e) {
//            // tells user the username they entered is already taken
//            loginError.setVisibility(View.VISIBLE); // TODO decide if we want to conserve space
//            return;
//        }
    }


}
