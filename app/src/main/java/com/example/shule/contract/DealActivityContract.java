package com.example.firebase.basics.contract;

/**
 *
 */
public interface DealActivityContract {
    interface View {
        void afterGetDeal();
        void afterAddDeal();
        void removeDeal();
        void editDeal();
    }

    interface Presenter {
        void getDeal();
        void addDeal();
        void removeDeal();
        void editDeal();
    }
}
