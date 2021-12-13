package com.example.eatincollege.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eatincollege.R;
import com.example.eatincollege.mine.MineActivity;
import com.example.eatincollege.signup.SignUpActivity;

public class LoginFragment extends Fragment implements LoginContract.View {
    private LoginContract.Presenter mPresenter;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    private EditText nameText;  // 用户名输入框
    private EditText pwdText;   // 密码输入框
    private Button loginBtn;    // 登录按钮
    private TextView signUpText;    // 注册按钮
    private TextView cancelText;    // 取消登录按钮

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        nameText = root.findViewById(R.id.login_et_name);
        pwdText = root.findViewById(R.id.login_et_pwd);
        loginBtn = root.findViewById(R.id.login_btn_login);
        signUpText = root.findViewById(R.id.login_tv_signUp);
        cancelText = root.findViewById(R.id.login_tv_cancel);

        // 设置登录键点击事件
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                String pwd = pwdText.getText().toString();
                mPresenter.login(name, pwd);
            }
        });

        // 设置注册键点击事件
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.handleActivitySwitch(getContext(), SignUpActivity.class);
            }
        });

        // 设置取消键点击事件
        cancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return root;
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    // 登陆成功提示 并跳转页面
    @Override
    public void loginSuccess() {
        Toast toast = Toast.makeText(getContext(), "登录成功！", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        getActivity().finish();
    }

    // 登录失败提示，并清空输入框
    @Override
    public void loginFail() {
        Toast toast = Toast.makeText(getContext(), "用户名或密码错误！", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        nameText.setText("");
        pwdText.setText("");
    }

    // 处理页面跳转
    @Override
    public void handleActivitySwitch(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
