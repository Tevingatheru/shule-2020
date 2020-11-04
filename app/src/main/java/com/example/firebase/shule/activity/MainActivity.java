package com.example.firebase.shule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebase.shule.R;
import com.example.firebase.shule.contract.MainContract;
import com.example.firebase.shule.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private TextView hello;
    private MainPresenter presenter;
    private Handler handler = Handler.createAsync(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hello = findViewById(R.id.hello);

        presenter = new MainPresenter(this);
        presenter.sayHello();
        presenter.startSubjectActivity();
    }


    @Override
    public void shouldStartSubjectActivity() {
        Intent insertActivity = new Intent(MainActivity.this, AnswerActivity.class);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(insertActivity);
            }
        }, 2000);
    }

    @Override
    public void shouldSayHello() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hello.setText("Welcome");
            }
        }, 2000);
    }
}