package com.example.firebase.shule.contract;

import com.example.firebase.shule.model.Answer;
import com.example.firebase.shule.model.Question;

import java.util.Set;

public interface ExamContract {
    interface View {
        void shouldAddExam();
        void shouldRemoveExam();
        void shouldEditExam();
        void shouldOpenReference();
    }

    interface Presenter {
        void addExam();
        void removeExam();
        void editExam();
        void openReference();
    }
}
