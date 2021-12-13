package com.example.eatincollege.fooddetail;

import android.content.Context;
import android.content.Intent;


import com.example.eatincollege.data.Api_FoodBaseInfo;
import com.example.eatincollege.data.DataLocal;

public class FoodDetailPresenter implements FoodDetailContract.Presenter {
    private FoodDetailContract.View mView;
    private final DataLocal mData;

    public FoodDetailPresenter(FoodDetailContract.View view) {
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

    private boolean stared;

    @Override
    public void loadData(int f_id) {
        // 菜品基本信息
        mData.setFid(f_id);
        Api_FoodBaseInfo foodBaseInfo = mData.get_Api_FoodBaseInfo(f_id);
        mView.setFoodName(foodBaseInfo.name);
        mView.setFoodStared(foodBaseInfo.stared);
        stared = foodBaseInfo.stared;
        mView.setFoodPrice(foodBaseInfo.price);
        mView.setCanteenInfo(foodBaseInfo.canteen);
        mView.setFoodImage(foodBaseInfo.image);

        // 菜品描述
        String description = foodBaseInfo.name + "原料：";
        for (int i = 0; i < foodBaseInfo.material.size(); i++) {
            description += foodBaseInfo.material.get(i) + "、";
        }
        description = description.substring(0, description.length() - 1) + "。";
        mView.setFoodDescription(description);
    }

    @Override
    public Intent getFromIntent() {
        return mData.getFromIntent();
    }

    // 处理收藏键点击事件
    @Override
    public boolean staredButtonClicked() {
        stared = !stared;
        if (mData.getUid() == -1) {
            return false;
        }
        mData.set_Api_StarFood(mData.getFid(), stared);
        mView.setFoodStared(stared);
        return true;
    }

    // 处理简单的页面跳转数据
    @Override
    public void handleActivitySwitch(Context context, Class<?> cls) {
        Intent newIntent = new Intent(context, cls);
        mData.setFromIntent(newIntent);
        mView.handleActivitySwitch(newIntent);
    }
}
