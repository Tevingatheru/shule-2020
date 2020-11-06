package com.example.firebase.shule.presenter;

import com.example.firebase.shule.contract.MainContract;

/**
 * This is the {@link com.example.firebase.shule.contract.MainContract.Presenter}
 * for the {@link com.example.firebase.shule.activity.MainActivity}
 */
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
