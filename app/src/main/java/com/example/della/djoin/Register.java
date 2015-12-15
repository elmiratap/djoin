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

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Register extends Activity implements View.OnClickListener {

    // Error message
    private TextView tvRegisterError;

    // Placeholder text for username and password
    private EditText etUsername;
    private EditText etPassword;

    // Button to complete registration
    private Button btnRegister;

    // ParseUser object
    public static ParseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initializes all views from the activity_register xml file
        setContentView(R.layout.activity_register);
        tvRegisterError = (TextView) findViewById(R.id.tvRegisterError);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        tvRegisterError.setVisibility(View.GONE);

    }


    @Override
    public void onClick(View v)  {
        if(v == btnRegister) {
            // Check if username and password are in the database
            user = new ParseUser();
            user.setUsername(etUsername.getText().toString());
            user.setPassword(etPassword.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        // The username and password combination is both unique.
                        Intent intent = new Intent(Register.this, MyTrips.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Username and password combination is not unique.
                        tvRegisterError.setVisibility(View.VISIBLE);
                    }
                }
            });
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
