package com.example.della.djoin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class EditInformation extends Activity implements View.OnClickListener{
    private EditText etName;
    private EditText etCarColor;
    private EditText etCarMake;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);
        etName = (EditText) findViewById(R.id.etName);
        etCarColor = (EditText) findViewById(R.id.etCarColor);
        etCarMake = (EditText) findViewById(R.id.etCarMake);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ParseUser currentUser = ParseUser.getCurrentUser();
        currentUser.put("name", etName.getText().toString());
        currentUser.put("carColor", etCarColor.getText().toString());
        currentUser.put("carMake", etCarMake.getText().toString());
        currentUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Intent intent = new Intent(EditInformation.this, MyInformation.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Save failed.
                    Toast.makeText(getApplicationContext(), "Information not saved. Please try again.", Toast.LENGTH_LONG).show();

                }

            }
        });

    }
}