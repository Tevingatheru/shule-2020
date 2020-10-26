package com.example.firebase.shule.contract;

public interface MainContract {
    interface View {
        void shouldStartSubjectActivity();
    }

    interface Presenter {
        void startSubjectActivity();
    }
}
