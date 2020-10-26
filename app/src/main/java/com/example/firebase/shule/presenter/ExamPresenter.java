package com.example.firebase.shule.presenter;

import com.example.firebase.shule.contract.ExamContract;

public class ExamPresenter  implements ExamContract.Presenter {

    ExamContract.View view;

    public ExamPresenter(ExamContract.View view) {
        this.view = view;
    }

    @Override
    public void addExam() {

    }

    @Override
    public void removeExam() {

    }

    @Override
    public void editExam() {

    }

    @Override
    public void openReference() {

    }
}
