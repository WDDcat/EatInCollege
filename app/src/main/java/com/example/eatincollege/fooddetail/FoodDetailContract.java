package com.example.eatincollege.fooddetail;

import android.content.Context;
import android.content.Intent;

import com.example.eatincollege.BasePresenter;
import com.example.eatincollege.BaseView;

public interface FoodDetailContract {
    interface View extends BaseView<Presenter> {
        void setFoodName(String name);

        void setFoodStared(boolean stared);

        void setFoodImage(String data);

        void setFoodPrice(double price);

        void setCanteenInfo(String canteen);

        void setFoodDescription(String description);

        void handleActivitySwitch(Intent intent);
    }

    interface Presenter extends BasePresenter {
        void loadData(int f_id);

        Intent getFromIntent();

        boolean staredButtonClicked();

        void handleActivitySwitch(Context context, Class<?> cls);
    }
}
