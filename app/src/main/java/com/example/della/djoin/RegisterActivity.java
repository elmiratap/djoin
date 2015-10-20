package com.example.della.djoin;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends Activity implements View.OnClickListener {

    // Title at the top of the page
    private TextView tvTitle;

    // Error message
    private TextView tvRegisterError;

    // Placeholder text for username and password
    private EditText etUsername;
    private EditText etPassword;

    // Button to complete registration
    private Button btnRegister;

    private String[] userColumns;


    DBHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userColumns = new String[] {dbHelper.USERNAME, dbHelper.PASSWORD, dbHelper.NAME,
                dbHelper.USER_CAR_MAKE, dbHelper.USER_CAR_MODEL, dbHelper.USER_CAR_COLOR};

        // initializes all views from the activity_register xml file
        setContentView(R.layout.activity_register);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvRegisterError = (TextView) findViewById(R.id.tvRegisterError);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        dbHelper = new DBHelper(this);

        tvRegisterError.setVisibility(View.GONE);
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
    public void onClick(View v)  {
        ContentValues cv = new ContentValues(2);
        cv.put(dbHelper.USERNAME, etUsername.getText().toString());
        cv.put(dbHelper.PASSWORD, etPassword.getText().toString());

        try {
            // makes sure that primary key constraint isn't violated
            db.insertOrThrow(dbHelper.TABLE_USER, null, cv);
            cursor = db.query(dbHelper.TABLE_USER, userColumns, null, null, null, null, null, null);

            etUsername.setText(null); // TODO transition, redirect
            etPassword.setText(null);
            tvRegisterError.setVisibility(View.GONE);
//            Cursor queryres = db.query(dbHelper.TABLE_USER, new String[]{dbHelper.USERNAME},
//                    "username = ?", new String[]{"della"}, null, null, null);
//            queryres.moveToFirst();
        // if primary key constraint is violated
        } catch (SQLiteConstraintException e) {
            // tells user the username they entered is already taken
            tvRegisterError.setVisibility(View.VISIBLE); // TODO decide if we want to conserve space
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_activty, menu);
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
}
