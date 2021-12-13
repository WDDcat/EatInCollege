package com.example.eatincollege.homepage;

import android.content.Context;
import android.content.Intent;

import com.example.eatincollege.data.Api_FoodBaseInfo;
import com.example.eatincollege.data.DataLocal;
import com.example.eatincollege.fooddetail.FoodDetailActivity;
import com.example.eatincollege.mine.MineActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePagePresenter implements HomePageContract.Presenter {
    private HomePageContract.View mView;
    private final DataLocal mData;

    public HomePagePresenter(HomePageContract.View view) {
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

    private List<Map<String, Object>> foodList = new ArrayList<>(); // 当前显示的菜品列表的数据

    @Override
    public void loadData() {
        List<Api_FoodBaseInfo> recommendFoodList = mData.get_Api_RecommendFood();

        // banner 轮播图
        List<String> imagesUri = new ArrayList<>();
        for (Api_FoodBaseInfo food : recommendFoodList) {
            imagesUri.add(food.image);
        }
        mView.setBannerImages(imagesUri);

        // 推荐菜品
        for (Api_FoodBaseInfo food : recommendFoodList) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", food.image);
            map.put("name", food.name);
            map.put("f_id", food.f_id);
            foodList.add(map);
        }
        mView.setGridView(foodList);
    }

    // 处理轮播图点击事件
    @Override
    public void bannerItemClicked(Context context, int index) {
        Intent newIntent = new Intent(context, FoodDetailActivity.class);
        newIntent.putExtra("f_id", (int) foodList.get(index).get("f_id"));
        mData.setFromIntent(newIntent);
        mView.handleActivitySwitch(newIntent);
    }

    // 菜品项被点击，跳转至菜品详情页
    @Override
    public void recommendItemClicked(Context context, int index) {
        Intent newIntent = new Intent(context, FoodDetailActivity.class);
        newIntent.putExtra("f_id", (int) foodList.get(index).get("f_id"));
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
