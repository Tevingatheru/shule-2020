package com.example.firebase.shule.presenter;

import com.example.firebase.shule.contract.ExamContract;
import com.example.firebase.shule.model.Exam;

/**
 * This is the {@link com.example.firebase.shule.contract.ExamContract.Presenter}
 * for the {@link com.example.firebase.shule.activity.ExamActivity}
 */
public class ExamPresenter  implements ExamContract.Presenter {

    ExamContract.View view;

    public ExamPresenter(ExamContract.View view) {
        this.view = view;
    }

    @Override
    public Exam addExam() {
        return null;
    }

    @Override
    public Exam removeExam() {
        return null;

    }

    @Override
    public Exam editExam() {
        return null;
    }

    @Override
    public void openReference() {

    }
}
