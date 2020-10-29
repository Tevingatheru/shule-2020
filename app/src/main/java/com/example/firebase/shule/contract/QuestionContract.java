package com.example.firebase.shule.contract;

import com.example.firebase.shule.model.Question;

public interface QuestionContract {
    interface View{
        void shouldSetQuestion(Question question);
        boolean shouldCheckIfAnswerSelectedIsCorrect();
        void shouldListenToFb();
    }

    interface Presenter{
        void setQuestion(Question question);
        boolean isAnswerCorrect();
        void listenToFb();
    }
}
