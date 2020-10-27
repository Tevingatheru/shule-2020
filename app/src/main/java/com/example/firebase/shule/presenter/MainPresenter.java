package com.example.firebase.shule.presenter;

import com.example.firebase.shule.contract.MainContract;

public class MainPresenter implements MainContract.Presenter {
    MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void sayHello() {

        view.shouldSayHello();
    }

    @Override
    public void startSubjectActivity() {
        view.shouldStartSubjectActivity();
    }
}
