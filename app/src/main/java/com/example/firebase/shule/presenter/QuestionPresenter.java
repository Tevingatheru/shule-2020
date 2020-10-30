package com.example.firebase.shule.presenter;

import com.example.firebase.shule.contract.QuestionContract;
import com.example.firebase.shule.model.Question;

public class QuestionPresenter implements QuestionContract.Presenter {
    QuestionContract.View view;
    Question question;

    public QuestionPresenter(QuestionContract.View view) {
        this.view = view;
        question = new Question("data question", "01","02","02","03","04");
    }

    @Override
    public void setQuestion() {
        view.shouldSetQuestion();
    }

    @Override
    public boolean isAnswerCorrect() {
        return view.shouldCheckIfAnswerSelectedIsCorrect();
    }



    @Override
    public void listenToFb() {
        view.shouldListenToFb();
    }

    @Override
    public void initializeFields() {
        view.shouldInitializeFields();
    }

    @Override
    public int countItems() {
        return view.shouldCountItems();
    }

    @Override
    public void openRef() {
        view.shouldOpenRef();
    }
}
