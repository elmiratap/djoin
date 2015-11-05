package com.example.della.djoin;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

        String TAG = "DBHelper";
        /**
         * Helper function that parses a given table into a string
         * and returns it for easy printing. The string consists of
         * the table name and then each row is iterated through with
         * column_name: value pairs printed out.
         *
         * @param db the database to get the table from
         * @param tableName the the name of the table to parse
         * @return the table tableName as a string
         */
        public String getTableAsString(SQLiteDatabase db, String tableName) {
            int count = 0;
            Log.d(TAG, "getTableAsString called");
            String tableString = String.format("Table %s:\n", tableName);
            Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
            if (allRows.moveToFirst() ){
                count++;
                String[] columnNames = allRows.getColumnNames();
                do {
                    for (String name: columnNames) {
                        tableString += String.format("%s: %s\n", name,
                                allRows.getString(allRows.getColumnIndex(name)));
                    }
                    tableString += "\n";

                } while (allRows.moveToNext());
            }

            return tableString;
        }




}
