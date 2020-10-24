package com.example.firebase.basics.presenter;

import com.example.firebase.basics.contract.DealActivityContract;

public class DealActivityPresenter implements DealActivityContract.Presenter {

    DealActivityContract.View view;

    public DealActivityPresenter(DealActivityContract.View view) {
        this.view = view;
    }


    @Override
    public void getDeal() {

    }

    @Override
    public void addDeal() {

    }

    @Override
    public void removeDeal() {

    }

    @Override
    public void editDeal() {

    }
}
