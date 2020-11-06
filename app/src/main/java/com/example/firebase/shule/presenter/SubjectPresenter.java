package com.example.firebase.shule.presenter;

import com.example.firebase.shule.contract.SubjectContract;

/**
 * This is the {@link com.example.firebase.shule.contract.SubjectContract.Presenter}
 * for the {@link com.example.firebase.shule.activity.SubjectActivity}
 */
public class SubjectPresenter implements SubjectContract.Presenter {

    SubjectContract.View view;

    public SubjectPresenter(SubjectContract.View view) {
        this.view = view;
    }

    @Override
    public void openReference() {
        view.shouldOpenReference();
    }

    @Override
    public void setView() {
        view.shouldSetView();
    }

    @Override
    public void startTopicActivity() {
    }
}
