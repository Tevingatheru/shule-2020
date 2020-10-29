package com.example.firebase.shule.presenter;

import android.util.ArraySet;

import com.example.firebase.shule.contract.QuestionContract;
import com.example.firebase.shule.model.Question;

public class QuestionPresenter implements QuestionContract.Presenter {
    QuestionContract.View view;
    Question question;

    public QuestionPresenter(QuestionContract.View view) {
        this.view = view;
        question = new Question();
    }

    @Override
    public void setQuestion(Question question) {
        view.shouldSetQuestion(question);
    }

    @Override
    public boolean isAnswerCorrect() {

        return view.shouldCheckIfAnswerSelectedIsCorrect();
    }



    @Override
    public void listenToFb() {
        view.shouldListenToFb();
    }
}
