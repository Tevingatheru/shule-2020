package com.example.firebase.shule.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebase.shule.R;
import com.example.firebase.shule.contract.ExamContract;

public class ExamActivity extends AppCompatActivity implements ExamContract.View {

    public static final int PICTURE_RESULT = 42;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("Menu Option:", "item: " + item.getItemId() + "was selected");
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_insert_menu, menu);

        return true;
    }

    @Override
    public void shouldAddExam() {

    }

    @Override
    public void shouldRemoveExam() {

    }

    @Override
    public void shouldEditExam() {

    }

    @Override
    public void shouldOpenReference() {

    }
}
