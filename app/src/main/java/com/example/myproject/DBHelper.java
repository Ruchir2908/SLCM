package com.example.myproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper instance;

    public static DBHelper getInstance(Context context){
        if(instance==null){
            instance = new DBHelper(context.getApplicationContext());
        }
        return instance;
    }

    public final static String CONSUMER_DATABASE_NAME = "StreetLight";
    public final static String CONSUMER_TABLE_NAME = "consumer";
    public final static String CONSUMER_ID = "id";
    public final static String CONSUMER_NAME = "name";
    public final static String CONSUMER_PHONE = "phone";
    public final static String CONSUMER_USERNAME = "username";
    public final static String CONSUMER_PASSWORD = "password";

    public DBHelper(@Nullable Context context) {
        super(context, "StreetLight", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + CONSUMER_TABLE_NAME + " ( " + CONSUMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CONSUMER_NAME + " TEXT, " + CONSUMER_PHONE + " TEXT, " + CONSUMER_USERNAME + " TEXT, " + CONSUMER_PASSWORD + " TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
