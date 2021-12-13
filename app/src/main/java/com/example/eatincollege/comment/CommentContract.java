package com.example.eatincollege.comment;

import android.content.Context;
import android.content.Intent;

import com.example.eatincollege.BasePresenter;
import com.example.eatincollege.BaseView;

public interface CommentContract {
    interface View extends BaseView<Presenter> {
        void handleActivitySwitch(Intent intent);
    }

    interface Presenter extends BasePresenter {
        void loadData();

        void handleActivitySwitch(Context context, Class<?> cls);
    }
}
