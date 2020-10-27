package com.example.firebase.shule.contract;

import com.example.firebase.shule.model.Exam;

public interface ExamContract {
    interface View {
        void shouldAddExam();
        void shouldRemoveExam();
        void shouldEditExam();
        void shouldOpenReference();
    }

    interface Presenter {
        Exam addExam();
        Exam removeExam();
        Exam editExam();
        void openReference();
    }
}
