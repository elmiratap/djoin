package com.example.della.djoin;

import com.parse.Parse;

/**
 * Created by elmira on 12/14/2015.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "2Cj7clB0aDfkYtjM3D8CovDduojXw6775Iz9cxP3", "lbEX2zvla4bT91sgq7uStIuDyY4aqcG7CsGFYYAA");
    }
}
