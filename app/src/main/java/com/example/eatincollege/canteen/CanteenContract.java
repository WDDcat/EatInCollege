package com.example.eatincollege.canteen;

import android.content.Context;
import android.content.Intent;

import com.example.eatincollege.BasePresenter;
import com.example.eatincollege.BaseView;

import java.util.List;
import java.util.Map;

public interface CanteenContract {
    interface View extends BaseView<Presenter> {
        void setTypeGrid(List<Map<String, Object>> dataList);

        void setFoodGrid(List<Map<String, Object>> dataList);

        void setRestaurantList(List<Map<String, Object>> dataList);

        void setActiveRestaurantList(int index);

        void setUnActiveRestaurantList(int index);

        void setActiveTypeGrid(int index);

        void setUnActiveTypeGrid(int index);

        void handleActivitySwitch(Intent intent);
    }

    interface Presenter extends BasePresenter {
        void loadData();

        void restaurantItemClicked(int index);

        void typeItemClicked(Context context, int index);

        void foodItemClicked(Context context, int index);

        void handleActivitySwitch(Context context, Class<?> cls);
    }
}
