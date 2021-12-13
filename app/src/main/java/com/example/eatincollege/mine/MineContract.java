package com.example.eatincollege.mine;

import android.content.Context;
import android.content.Intent;

import com.example.eatincollege.BasePresenter;
import com.example.eatincollege.BaseView;

public interface MineContract {
    interface View extends BaseView<Presenter> {
        void setName(String name);

        void handleActivitySwitch(Intent intent);
    }

    interface Presenter extends BasePresenter {
        void loadData();

        boolean getLoginStatus();

        void logout(Context context);

        void handleActivitySwitch(Context context, Class<?> cls);
    }
}
