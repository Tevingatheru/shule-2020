package com.example.firebase.shule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebase.shule.R;
import com.example.firebase.shule.contract.MainContract;
import com.example.firebase.shule.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private TextView hello;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hello = findViewById(R.id.hello);
        presenter = new MainPresenter(this);
        presenter.startSubjectActivity();
    }


    @Override
    public void shouldStartSubjectActivity() {
        Intent insertActivity = new Intent(MainActivity.this, TopicActivity.class);
        startActivity(insertActivity);
    }
}