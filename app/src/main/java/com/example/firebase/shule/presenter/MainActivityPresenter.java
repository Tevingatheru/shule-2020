package com.example.firebase.basics.presenter;

import com.example.firebase.basics.contract.MainActivityContract;

public class MainActivityPresenter implements MainActivityContract.Presenter {
    MainActivityContract.View view;

    public MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void doLoad(String message) {

    }
}
