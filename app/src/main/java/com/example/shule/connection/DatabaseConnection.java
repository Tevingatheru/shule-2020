package com.example.shule.connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * This class facilitates a connection to the database
 */
public class DatabaseConnection extends SQLiteOpenHelper {
    private static final String name = "shule";
    private static final int version = 1;

    public DatabaseConnection(@Nullable Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
