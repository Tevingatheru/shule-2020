package com.example.firebase.shule.presenter;

import com.example.firebase.shule.contract.QuestionContract;

public class QuestionPresenter implements QuestionContract.Presenter {
    QuestionContract.View view;

    public QuestionPresenter(QuestionContract.View view) {
        this.view = view;
    }

    @Override
    public void initializeFields() {

    }

    @Override
    public boolean isAnswerCorrect() {
        return false;
    }

    @Override
    public void setView() {
        view.shouldSetView();
    }

}
