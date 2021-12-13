package com.example.eatincollege.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eatincollege.R;

public class SignUpFragment extends Fragment implements SignUpContract.View {
    private SignUpContract.Presenter mPresenter;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
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

    private EditText nameText;      // 用户名输入框
    private EditText pwdText;       // 密码输入框
    private EditText pwdConfirmText;// 密码确认输入框
    private Button signUpBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signup, container, false);

        nameText = root.findViewById(R.id.signup_et_name);
        pwdText = root.findViewById(R.id.signup_et_pwd);
        pwdConfirmText = root.findViewById(R.id.signup_et_pwdConfirm);
        signUpBtn = root.findViewById(R.id.signup_btn_signup);

        signUpBtn.setOnClickListener(v -> {
            String name = nameText.getText().toString();
            String pwd = pwdText.getText().toString();
            String pwdConfirm = pwdConfirmText.getText().toString();
            mPresenter.signUp(name, pwd, pwdConfirm);
        });

        return root;
    }

    @Override
    public void setPresenter(SignUpContract.Presenter presenter) {
        mPresenter = presenter;
    }

    // 注册成功提示 并跳转页面
    @Override
    public void signUpSuccess() {
        Toast toast = Toast.makeText(getContext(), "注册成功！", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        getActivity().finish();
    }

    // 注册失败提示，并清空输入框
    @Override
    public void signUpFail(String text) {
        Toast toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
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
