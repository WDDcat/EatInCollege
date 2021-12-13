package com.example.eatincollege.homepage;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eatincollege.R;
import com.example.eatincollege.utils.ActivityUtils;

import java.util.Objects;

public class HomePageActivity extends AppCompatActivity {
    private HomePageFragment mHomePageFragment;
    private HomePagePresenter mHomePagePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();   // 隐藏导航栏
        setContentView(R.layout.activity_fragment);

        mHomePageFragment = (HomePageFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (mHomePageFragment == null) {
            mHomePageFragment = HomePageFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mHomePageFragment, R.id.contentFrame);
        }

        mHomePagePresenter = new HomePagePresenter(mHomePageFragment);
    }
}
