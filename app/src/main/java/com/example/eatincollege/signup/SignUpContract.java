package com.example.eatincollege.signup;

import android.content.Context;
import android.content.Intent;

import com.example.eatincollege.BasePresenter;
import com.example.eatincollege.BaseView;

public interface SignUpContract {
    interface View extends BaseView<Presenter> {
        void signUpSuccess();

        void signUpFail(String text);

        void handleActivitySwitch(Intent intent);
    }

    interface Presenter extends BasePresenter {
        void loadData();

        void signUp(String name, String pwd, String pwdConfirm);

        void handleActivitySwitch(Context context, Class<?> cls);
    }
}
