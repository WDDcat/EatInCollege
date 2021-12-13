package com.example.eatincollege.login;

import android.content.Context;
import android.content.Intent;

import com.example.eatincollege.BasePresenter;
import com.example.eatincollege.BaseView;

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void loginSuccess();

        void loginFail();

        void handleActivitySwitch(Intent intent);
    }

    interface Presenter extends BasePresenter {
        void loadData();

        void login(String name, String pwd);

        void handleActivitySwitch(Context context, Class<?> cls);
    }
}
