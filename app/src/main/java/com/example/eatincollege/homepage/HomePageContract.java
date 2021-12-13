package com.example.eatincollege.homepage;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eatincollege.BasePresenter;
import com.example.eatincollege.BaseView;

import java.util.List;
import java.util.Map;

public interface HomePageContract {
    interface View extends BaseView<Presenter> {
        void setBannerImages(List<String> imageList);

        void setGridView(List<Map<String, Object>> dataList);

        void handleActivitySwitch(Intent intent);
    }

    interface Presenter extends BasePresenter {
        void loadData();

        void bannerItemClicked(Context context, int index);

        void recommendItemClicked(Context context, int index);

        void handleActivitySwitch(Context context, Class<?> cls);
    }
}
