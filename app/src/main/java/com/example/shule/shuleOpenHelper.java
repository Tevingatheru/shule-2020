package com.example.shule;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shule.contract.ShuleDatabaseContract;


public class shuleOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "NoteKeeper.db";
    public static final int DATABASE_VERSION = 1;
    public shuleOpenHelper(Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(ShuleDatabaseContract.SubjectInfo.SQL_CREATE_TABLE);
        database.execSQL(ShuleDatabaseContract.QuestionInfo.SQL_CREATE_TABLE);
        database.execSQL(ShuleDatabaseContract.GradeInfo.SQL_CREATE_TABLE);
        database.execSQL(ShuleDatabaseContract.TopicInfo.SQL_CREATE_TABLE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
