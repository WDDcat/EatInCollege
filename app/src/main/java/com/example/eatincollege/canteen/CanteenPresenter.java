package com.example.eatincollege.canteen;

import android.content.Context;
import android.content.Intent;

import com.example.eatincollege.category.CategoryActivity;
import com.example.eatincollege.data.Api_FoodBaseInfo;
import com.example.eatincollege.data.DataLocal;
import com.example.eatincollege.fooddetail.FoodDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CanteenPresenter implements CanteenContract.Presenter {
    private CanteenContract.View mView;
    private final DataLocal mData;

    public CanteenPresenter(CanteenContract.View view) {
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

    // 自定义的菜品种类（包括数据库中用于检索的英文） 以及 餐厅名称
    private static final String[] typeName = {"盖浇饭", "面食", "西餐", "饮品", "糕点", "小吃"};
    private static final String[] restaurantName = {"美食园", "风味餐厅", "奥运一层", "奥运二层", "天天餐厅", "清真餐厅"};
    private static final String[] typeNameEnglish = {"Donburi", "Noodle", "Western", "Drink", "Cakes", "Snack"};

    private int restaurantListActiveIndex = -1;     // 记录当前激活的餐厅下标，没有则为-1
    private int typeGridActiveIndex = -1;           // 记录当前激活的种类下表，没有则为-1
    private List<Api_FoodBaseInfo> foodList = new ArrayList<>();    // 记录当前显示的菜品列表的数据

    @Override
    public void loadData() {
        // 菜品种类
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (String s : typeName) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", s);
            dataList.add(map);
        }
        mView.setTypeGrid(dataList);

        // 餐厅名称
        dataList = new ArrayList<>();
        for (String s : restaurantName) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", s);
            dataList.add(map);
        }
        mView.setRestaurantList(dataList);

        // 菜品数据
        dataList = new ArrayList<>();
        foodList = mData.get_Api_FoodListByCondition("", "");
        for (Api_FoodBaseInfo food : foodList) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", food.image);
            map.put("name", food.name);
            dataList.add(map);
        }
        mView.setFoodGrid(dataList);
    }

    // 处理餐厅列表项点击事件
    @Override
    public void restaurantItemClicked(int index) {
        // 默认为 空， 筛选时为无条件
        String type = typeGridActiveIndex == -1 ?
                "" : typeNameEnglish[typeGridActiveIndex];

        if (restaurantListActiveIndex != index) {
            // 如果与上次点击的不同，则切换激活的餐厅选项，同时重新获取数据
            mView.setActiveRestaurantList(index);
            mView.setUnActiveRestaurantList(restaurantListActiveIndex);
            restaurantListActiveIndex = index;
            foodList = mData.get_Api_FoodListByCondition(restaurantName[index], type);
        } else {
            // 如果与上次点击的相同，则取消激活当前激活的餐厅选项，同时重新获取所有菜品的数据
            mView.setUnActiveRestaurantList(index);
            restaurantListActiveIndex = -1;
            foodList = mData.get_Api_FoodListByCondition("", type);
        }

        // 处理菜品数据
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (Api_FoodBaseInfo food : foodList) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", food.image);
            map.put("name", food.name);
            dataList.add(map);
        }
        mView.setFoodGrid(dataList);
    }

    // 处理菜品种类项点击事件
    @Override
    public void typeItemClicked(Context context, int index) {
//        String restaurant = restaurantListActiveIndex == -1 ?
//                "" : restaurantName[restaurantListActiveIndex];
//
//        if (typeGridActiveIndex != index) {
//            mView.setActiveTypeGrid(index);
//            mView.setUnActiveTypeGrid(typeGridActiveIndex);
//            typeGridActiveIndex = index;
//            foodList = mData.get_Api_FoodListByCondition(restaurant, typeNameEnglish[index]);
//        } else {
//            mView.setUnActiveTypeGrid(index);
//            typeGridActiveIndex = -1;
//            foodList = mData.get_Api_FoodListByCondition(restaurant, "");
//        }
//
//        List<Map<String, Object>> dataList = new ArrayList<>();
//        for (Api_FoodBaseInfo food : foodList) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("image", food.image);
//            map.put("name", food.name);
//            dataList.add(map);
//        }
//        mView.setFoodGrid(dataList);

        // 直接跳转至菜品种类筛选页面
        Intent newIntent = new Intent(context, CategoryActivity.class);
        newIntent.putExtra("typeE", typeNameEnglish[index]);
        newIntent.putExtra("typeC", typeName[index]);
        mData.setFromIntent(newIntent);
        mView.handleActivitySwitch(newIntent);
    }

    // 菜品项被点击，跳转至菜品详情页
    @Override
    public void foodItemClicked(Context context, int index) {
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
