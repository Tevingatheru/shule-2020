package com.example.firebase.shule.contract;

public interface MainContract {
    interface View {
        void shouldStartSubjectActivity();
        void shouldSayHello();
    }

    interface Presenter {
        void sayHello();
        void startSubjectActivity();
    }
}
