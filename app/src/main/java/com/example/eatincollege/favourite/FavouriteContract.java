package com.example.eatincollege.favourite;

import android.content.Context;
import android.content.Intent;

import com.example.eatincollege.BasePresenter;
import com.example.eatincollege.BaseView;

import java.util.List;
import java.util.Map;

public interface FavouriteContract {
    interface View extends BaseView<Presenter> {
        void setFavouriteFoodList(List<Map<String, Object>> dataList);

        void handleActivitySwitch(Intent intent);
    }

    interface Presenter extends BasePresenter {
        void loadData();

        void favouriteItemClicked(Context context, int index);

        void handleActivitySwitch(Context context, Class<?> cls);
    }
}
