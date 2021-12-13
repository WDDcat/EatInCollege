package com.example.eatincollege.launcher;

public class LauncherPresenter implements LauncherContract.Presenter {
    private LauncherContract.View mView;

    public LauncherPresenter(LauncherContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
