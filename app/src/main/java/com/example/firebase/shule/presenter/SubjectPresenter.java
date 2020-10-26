package com.example.firebase.shule.presenter;

import com.example.firebase.shule.contract.MainContract;
import com.example.firebase.shule.contract.SubjectContract;

public class SubjectPresenter implements SubjectContract.Presenter {

    MainContract.View view;

    public SubjectPresenter(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void openReference() {

    }

    @Override
    public void setView() {

    }
}
