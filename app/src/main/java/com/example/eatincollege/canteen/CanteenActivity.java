package com.example.eatincollege.canteen;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eatincollege.R;
import com.example.eatincollege.utils.ActivityUtils;

import java.util.Objects;

public class CanteenActivity extends AppCompatActivity {
    private CanteenFragment mCanteenFragment;
    private CanteenPresenter mCanteenPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();   // 隐藏导航栏
        setContentView(R.layout.activity_fragment);

        mCanteenFragment = (CanteenFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (mCanteenFragment == null) {
            mCanteenFragment = CanteenFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mCanteenFragment, R.id.contentFrame);
        }

        mCanteenPresenter = new CanteenPresenter(mCanteenFragment);
    }
}
