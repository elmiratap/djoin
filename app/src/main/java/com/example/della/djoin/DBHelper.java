package com.example.della.djoin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by elmira on 10/19/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    // Log cat tag
    private static final String LOG = "DBHelper";

    // Databse version and name information
    private final static int DB_VERSION = 1;
    private final static String DB_NAME = "djoinDatabase";

    // Table names
    public static final String TABLE_USER = "user";
    public static final String TABLE_TRIP = "trip";
    public static final String TABLE_TAKES = "takes";

    // User table column names
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String USER_CAR_MAKE = "user_carMake";
    public static final String USER_CAR_MODEL = "user_carModel";
    public static final String USER_CAR_COLOR = "user_carColor";

    // Table create statements
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER
            + "(" + USERNAME + " TEXT PRIMARY KEY,"
            + PASSWORD + " TEXT,"
            + NAME + " TEXT,"
            + USER_CAR_MAKE + " TEXT,"
            + USER_CAR_MODEL + " TEXT,"
            + USER_CAR_COLOR + " TEXT"
            + ")";

    // Database context
    Context context;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }
    public void onCreate(SQLiteDatabase db) {
        // Create required tables
        db.execSQL(CREATE_TABLE_USER);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // On upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        //Create new tables
        onCreate(db);
    }


}
