package com.example.firebase.basics.contract;

import android.widget.TextView;

public interface MainActivityContract {
    interface View{
        void onSuccess(String message);
        void onError(String message);
    }

    interface Presenter{

        void doLoad(String message);
    }
}
