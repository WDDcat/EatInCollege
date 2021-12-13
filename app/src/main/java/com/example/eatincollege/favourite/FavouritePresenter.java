package com.example.eatincollege.favourite;

import android.content.Context;
import android.content.Intent;

import com.example.eatincollege.data.Api_FoodBaseInfo;
import com.example.eatincollege.data.DataLocal;
import com.example.eatincollege.fooddetail.FoodDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavouritePresenter implements FavouriteContract.Presenter {
    private FavouriteContract.View mView;
    private final DataLocal mData;

    public FavouritePresenter(FavouriteContract.View view) {
        mData = DataLocal.getInstance();
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    private List<Api_FoodBaseInfo> foodList = new ArrayList<>();    // 当前显示的菜品列表数据

    @Override
    public void loadData() {
        // 收藏菜品数据
        foodList = mData.get_Api_FavouriteList();
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (Api_FoodBaseInfo food : foodList) {
            Map<String, Object> map = new HashMap<>();
            map.put("f_id", food.f_id);
            map.put("image", food.image);
            map.put("name", food.name);
            dataList.add(map);
        }
        mView.setFavouriteFoodList(dataList);
    }

    // 菜品项被点击，跳转至菜品详情页
    @Override
    public void favouriteItemClicked(Context context, int index) {
        Intent newIntent = new Intent(context, FoodDetailActivity.class);
        newIntent.putExtra("f_id", foodList.get(index).f_id);
        mData.setFromIntent(newIntent);
        mView.handleActivitySwitch(newIntent);
    }

    // 处理简单的页面跳转数据
    @Override
    public void handleActivitySwitch(Context context, Class<?> cls) {
        Intent newIntent = new Intent(context, cls);
        mData.setFromIntent(newIntent);
        mView.handleActivitySwitch(newIntent);
    }
}
