package com.example.eatincollege.mine;

import android.content.Context;
import android.content.Intent;

import com.example.eatincollege.data.Api_UserBaseInfo;
import com.example.eatincollege.data.DataLocal;
import com.example.eatincollege.login.LoginActivity;

public class MinePresenter implements MineContract.Presenter {
    private MineContract.View mView;
    private final DataLocal mData;

    public MinePresenter(MineContract.View view) {
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

    @Override
    public void loadData() {
        // 获取用户名
        Api_UserBaseInfo userBaseInfo = mData.get_Api_UserBaseInfo(mData.getUid());
        mView.setName(userBaseInfo.name);
    }

    // 获取登录信息（是否已登陆）
    @Override
    public boolean getLoginStatus() {
        return !(mData.getUid() == -1);
    }

    // 处理登出键点击事件
    @Override
    public void logout(Context context) {
        mData.get_Api_Logout();
        Intent newIntent = new Intent(context, LoginActivity.class);
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
