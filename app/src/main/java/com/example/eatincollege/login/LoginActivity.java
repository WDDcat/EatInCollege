package com.example.eatincollege.login;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eatincollege.R;
import com.example.eatincollege.utils.ActivityUtils;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private LoginFragment mLoginFragment;
    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();   // 隐藏导航栏
        setContentView(R.layout.activity_fragment);

        mLoginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (mLoginFragment == null) {
            mLoginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mLoginFragment, R.id.contentFrame);
        }

        mLoginPresenter = new LoginPresenter(mLoginFragment);
    }
}
