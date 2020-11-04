package com.example.firebase.shule.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase.shule.R;
import com.example.firebase.shule.ViewHolder;
import com.example.firebase.shule.adapter.SubjectAdapter;
import com.example.firebase.shule.contract.SubjectContract;
import com.example.firebase.shule.model.Subject;
import com.example.firebase.shule.presenter.SubjectPresenter;
import com.example.firebase.shule.util.FirebaseUtil;
import com.example.firebase.shule.util.MenuUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubjectActivity extends AppCompatActivity implements SubjectContract.View {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SubjectPresenter presenter;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        presenter = new SubjectPresenter(this);
        presenter.openReference();

        //Actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Subject");

        //RecyclerView
        setContentView(R.layout.subject_rv_row);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvSubjectList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);


        //send query to firebase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("subject");

    }
    //Load data into recycler view onStart
    @Override
    protected void onStart() {

        super.onStart();

    }

    @Override
    public void shouldOpenReference() {
        FirebaseUtil.openFbReference("subject", this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_list_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout_option:
                logout();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        MenuUtil menuUtil = new MenuUtil();
        menuUtil.logoutOption(this);
    }

    @Override
    public void shouldTopicActivity() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.detachListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView();
        FirebaseUtil.attachListener();
    }
}