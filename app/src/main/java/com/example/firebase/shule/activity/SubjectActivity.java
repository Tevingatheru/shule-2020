package com.example.firebase.shule.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.firebase.shule.R;

public class SubjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
    }

    public void showMenu() {
        invalidateOptionsMenu();
    }
}