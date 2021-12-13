package com.example.eatincollege.community;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eatincollege.R;
import com.example.eatincollege.utils.ActivityUtils;

import java.util.Objects;

public class CommunityActivity extends AppCompatActivity {
    private CommunityFragment mCommunityFragment;
    private CommunityPresenter mCommunityPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();   // 隐藏导航栏
        setContentView(R.layout.activity_fragment);

        mCommunityFragment = (CommunityFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (mCommunityFragment == null) {
            mCommunityFragment = CommunityFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mCommunityFragment, R.id.contentFrame);
        }

        mCommunityPresenter = new CommunityPresenter(mCommunityFragment);
    }
}
