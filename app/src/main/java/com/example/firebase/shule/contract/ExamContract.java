package com.example.firebase.shule.contract;

import com.example.firebase.shule.model.Answer;
import com.example.firebase.shule.model.Question;

import java.util.Set;

public interface ExamContract {
    interface View {
        void afterGetExam();
        void afterAddExam();
        void removeExam();
        void editExam();
    }

    interface Presenter {
        void getExam();
        void addExam(String topic, String imageUri, String imageName, Question Question, Set<Answer> answers);
        void removeExam();
        void editExam();
        void listenToFb();
    }
}
