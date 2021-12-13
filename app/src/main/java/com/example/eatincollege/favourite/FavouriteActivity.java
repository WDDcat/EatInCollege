package com.example.eatincollege.favourite;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eatincollege.R;
import com.example.eatincollege.utils.ActivityUtils;

import java.util.Objects;

public class FavouriteActivity extends AppCompatActivity {
    private FavouriteFragment mFavouriteFragment;
    private FavouritePresenter mFavouritePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();   // 隐藏导航栏
        setContentView(R.layout.activity_fragment);

        mFavouriteFragment = (FavouriteFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (mFavouriteFragment == null) {
            mFavouriteFragment = FavouriteFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFavouriteFragment, R.id.contentFrame);
        }

        mFavouritePresenter = new FavouritePresenter(mFavouriteFragment);
    }
}
