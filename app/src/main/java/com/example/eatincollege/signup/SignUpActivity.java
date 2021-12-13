package com.example.eatincollege.signup;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eatincollege.R;
import com.example.eatincollege.utils.ActivityUtils;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    private SignUpFragment mSignUpFragment;
    private SignUpPresenter mSignUpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();   // 隐藏导航栏
        setContentView(R.layout.activity_fragment);

        mSignUpFragment = (SignUpFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (mSignUpFragment == null) {
            mSignUpFragment = SignUpFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mSignUpFragment, R.id.contentFrame);
        }

        mSignUpPresenter = new SignUpPresenter(mSignUpFragment);
    }
}
