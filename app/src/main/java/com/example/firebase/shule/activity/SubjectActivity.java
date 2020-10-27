package com.example.firebase.shule.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase.shule.R;
import com.example.firebase.shule.adapter.SubjectAdapter;
import com.example.firebase.shule.contract.SubjectContract;
import com.example.firebase.shule.presenter.SubjectPresenter;
import com.example.firebase.shule.util.FirebaseUtil;

public class SubjectActivity extends AppCompatActivity implements SubjectContract.View {

    private SubjectPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        presenter = new SubjectPresenter(this);
        presenter.openReference();
    }

    @Override
    public void shouldOpenReference() {
        FirebaseUtil.openFbReference("subject");
    }

    @Override
    public void shouldSetView() {
        final SubjectAdapter adapter = new SubjectAdapter();
        final LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvSubjectList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void shouldTopicActivity() {
        Intent intent = new Intent(this, TopicActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.detachListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

        final SubjectAdapter adapter = new SubjectAdapter();
        final LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvSubjectList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        FirebaseUtil.attachListener();
    }
}