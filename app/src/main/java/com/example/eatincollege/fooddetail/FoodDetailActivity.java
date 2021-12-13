package com.example.eatincollege.fooddetail;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eatincollege.R;
import com.example.eatincollege.utils.ActivityUtils;

import java.util.Objects;

public class FoodDetailActivity extends AppCompatActivity {
    private FoodDetailFragment mFoodDetailFragment;
    private FoodDetailPresenter mFoodDetailPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();   // 隐藏导航栏
        setContentView(R.layout.activity_fragment);

        mFoodDetailFragment = (FoodDetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (mFoodDetailFragment == null) {
            mFoodDetailFragment = FoodDetailFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFoodDetailFragment, R.id.contentFrame);
        }

        mFoodDetailPresenter = new FoodDetailPresenter(mFoodDetailFragment);
    }
}
