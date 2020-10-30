package com.example.firebase.shule.contract;

import com.example.firebase.shule.model.Question;

public interface QuestionContract {
    interface View{
        void shouldSetQuestion(Question mQuestion);
        void shouldCheckIfAnswerSelectedIsCorrect(android.view.View v, String answer, String hint);
        void shouldListenToFb();
        void shouldInitializeFields();
        int shouldCountItems();
        void shouldOpenRef();
    }

    interface Presenter{
        void setQuestion(Question mQuestion);
        void isAnswerCorrect(android.view.View v,  String answer, String hint);
        void listenToFb();
        void initializeFields();
        int countItems();
        void openRef();
    }
}
