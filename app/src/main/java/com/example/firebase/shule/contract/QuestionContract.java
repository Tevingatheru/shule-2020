package com.example.firebase.shule.contract;

import com.example.firebase.shule.model.Question;

public interface QuestionContract {
    interface View{
        void shouldSetView();
        void shouldInitialize(Question question);
    }

    interface Presenter{
        void initializeFields();
        boolean isAnswerCorrect();
        void setView();
    }
}
