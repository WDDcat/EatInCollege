package com.example.eatincollege.category;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eatincollege.R;
import com.example.eatincollege.utils.ActivityUtils;

import java.util.Objects;

public class CategoryActivity extends AppCompatActivity {
    private CategoryFragment mCategoryFragment;
    private CategoryPresenter mCategoryPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();   // 隐藏导航栏
        setContentView(R.layout.activity_fragment);

        mCategoryFragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (mCategoryFragment == null) {
            mCategoryFragment = CategoryFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mCategoryFragment, R.id.contentFrame);
        }

        mCategoryPresenter = new CategoryPresenter(mCategoryFragment);
    }
}
