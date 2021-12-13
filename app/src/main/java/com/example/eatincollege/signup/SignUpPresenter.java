package com.example.eatincollege.signup;

import android.content.Context;
import android.content.Intent;

import com.example.eatincollege.data.DataLocal;

public class SignUpPresenter implements SignUpContract.Presenter {
    private SignUpContract.View mView;
    private final DataLocal mData;

    public SignUpPresenter(SignUpContract.View view) {
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
        // 处理 数据
    }

    @Override
    public void signUp(String name, String pwd, String pwdConfirm) {
        if (!pwd.equals(pwdConfirm)) {
            mView.signUpFail("密码输入不一致！");
        } else if (pwd.equals("")) {
            mView.signUpFail("密码不能为空！");
        } else if (!mData.get_Api_UserNameValid(name)) {
            mView.signUpFail("该用户名已存在");
        } else if (name.equals("")) {
            mView.signUpFail("用户名不能为空！");
        } else {
            mData.set_Api_SignUp(name, pwd);
            mView.signUpSuccess();
        }
    }

    @Override
    public void handleActivitySwitch(Context context, Class<?> cls) {
        Intent newIntent = new Intent(context, cls);
        mData.setFromIntent(newIntent);
        mView.handleActivitySwitch(newIntent);
    }
}
