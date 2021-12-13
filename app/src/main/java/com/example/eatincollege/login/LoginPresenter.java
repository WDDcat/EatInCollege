package com.example.eatincollege.login;

import android.content.Context;
import android.content.Intent;

import com.example.eatincollege.data.DataLocal;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;
    private final DataLocal mData;

    public LoginPresenter(LoginContract.View view) {
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

    }

    // 处理登录键点击事件
    @Override
    public void login(String name, String pwd) {
        if (mData.get_Api_Login(name, pwd)) {
            mView.loginSuccess();
        } else {
            mView.loginFail();
        }
    }

    // 处理简单的页面跳转数据
    @Override
    public void handleActivitySwitch(Context context, Class<?> cls) {
        Intent newIntent = new Intent(context, cls);
        newIntent.putExtra("u_id", mData.getUid());
        mData.setFromIntent(newIntent);
        mView.handleActivitySwitch(newIntent);
    }
}
