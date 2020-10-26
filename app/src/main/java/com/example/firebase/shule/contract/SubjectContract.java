package com.example.firebase.shule.contract;

public interface SubjectContract {
    interface View {
        void shouldOpenReference();
        void shouldSetView();
    }

    interface Presenter {
        void openReference();
        void setView();
    }
}
