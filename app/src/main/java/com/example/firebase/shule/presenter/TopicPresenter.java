package com.example.firebase.shule.presenter;

import com.example.firebase.shule.contract.TopicContract;

public class TopicPresenter implements TopicContract.Presenter {
    TopicContract.View view;

    public TopicPresenter(TopicContract.View view) {
        this.view = view;
    }

    @Override
    public void openReference()  {
        view.shouldOpenReference();
    }


    @Override
    public void setView() {
        view.shouldSetView();
    }
}
