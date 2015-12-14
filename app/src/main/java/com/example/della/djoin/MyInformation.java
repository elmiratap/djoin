package com.example.della.djoin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class MyInformation extends AppCompatActivity {

    private TextView tvName;
    private TextView tvCarColor;
    private TextView tvCarMake;
    private Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);
        tvName = (TextView) findViewById(R.id.tvName);
        tvCarColor = (TextView) findViewById(R.id.tvCarColor);
        tvCarMake = (TextView) findViewById(R.id.tvCarMake);
        btnEdit = (Button) findViewById(R.id.btnEdit);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", MainActivity.loggedInUser);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                        ParseUser result = objects.get(i);
                        String name = result.getString("name");
                        String carColor = result.getString("carColor");
                        String carMake = result.getString("carMake");
                        tvName.setText(name);
                        tvCarColor.setText(carColor);
                        tvCarMake.setText(carMake);
                    }
                } else {
                    // Error
                }
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInformation.this, EditInformation.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_information, menu);
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
