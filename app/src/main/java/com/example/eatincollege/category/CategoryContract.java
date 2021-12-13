package com.example.eatincollege.category;

import android.content.Context;
import android.content.Intent;

import com.example.eatincollege.BasePresenter;
import com.example.eatincollege.BaseView;

import java.util.List;
import java.util.Map;

public interface CategoryContract {
    interface View extends BaseView<Presenter> {
        void setTypeName(String name);

        void setFoodGrid(List<Map<String, Object>> dataList);

        void handleActivitySwitch(Intent intent);
    }

    interface Presenter extends BasePresenter {
        void loadData(String typeE, String typeC);

        Intent getFromIntent();

        void foodItemClicked(Context context, int index);

        void handleActivitySwitch(Context context, Class<?> cls);
    }
}
