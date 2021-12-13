package com.example.eatincollege.MvpExample;

import android.content.Context;
import android.content.Intent;

import com.example.eatincollege.BasePresenter;
import com.example.eatincollege.BaseView;

public interface ExampleContract {
    interface View extends BaseView<Presenter> {
        void handleActivitySwitch(Intent intent);
    }

    interface Presenter extends BasePresenter {
        void loadData();

        void handleActivitySwitch(Context context, Class<?> cls);
    }
}
