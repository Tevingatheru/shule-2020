package com.example.firebase.shule.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.firebase.shule.R;
import com.example.firebase.shule.contract.SubjectContract;

public class SubjectActivity extends AppCompatActivity implements SubjectContract.View {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

    }

}