
package com.example.firebase.shule.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase.shule.R;
import com.example.firebase.shule.adapter.TopicAdapter;
import com.example.firebase.shule.contract.TopicContract;
import com.example.firebase.shule.presenter.TopicPresenter;
import com.example.firebase.shule.util.FirebaseUtil;
import com.example.firebase.shule.util.MenuUtil;

public class TopicActivity extends AppCompatActivity implements TopicContract.View {

    private TopicPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        presenter = new TopicPresenter(this);
        presenter.openReference();
    }

    @Override
    public void shouldOpenReference() {
        FirebaseUtil.openFbReference("topic", new SubjectActivity());
    }

    @Override
    public void shouldSetView() {
        final TopicAdapter adapter = new TopicAdapter();
        final LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvTopicList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void shouldTopicActivity() {

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