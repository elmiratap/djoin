package com.example.della.djoin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends Activity implements View.OnClickListener {

    // Error message
    private TextView tvRegisterError;

    // Placeholder text for username and password
    private EditText etUsername;
    private EditText etPassword;

    // Button to complete registration
    private Button btnRegister;

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
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
                        Intent intent = new Intent(RegisterActivity.this, MyTrips.class);
                        startActivity(intent);
                    } else {
                        // Username and password combination is not unique.
                        tvRegisterError.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

    }
}
