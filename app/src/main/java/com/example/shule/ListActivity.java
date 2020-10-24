package com.example.firebase.basics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase.basics.R;
import com.example.firebase.basics.adapter.DealAdapter;
import com.example.firebase.basics.domain.TravelDeal;
import com.example.firebase.basics.model.MenuModel;
import com.example.firebase.basics.util.FirebaseUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    ArrayList<TravelDeal> dealList;

    private MenuModel menuModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        FirebaseUtil.openFbReference("traveldeals", this);
        setView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_list_menu, menu);

        MenuItem insertMenu = menu.findItem(R.id.add_option);

        if (FirebaseUtil.isMember) {
            insertMenu.setVisible(false);
        } else {
            insertMenu.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.add_option:
                intent = new Intent(this, DealActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout_option:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        menuModel.logoutOption(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.detachListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setView();
        FirebaseUtil.attachListener();
    }

    public void setView(){

//        final DealAdapter adapter = new DealAdapter();
//        final LinearLayoutManager dealLinearLayout =
//                new LinearLayoutManager( this, LinearLayoutManager.VERTICAL, false);
//
//        RecyclerView rvDeals = (RecyclerView) findViewById(R.id.rvDealList);
//        rvDeals.setAdapter(adapter);
//        rvDeals.setLayoutManager(dealLinearLayout);
    }

    public void showMenu() {
        invalidateOptionsMenu();
    }
}