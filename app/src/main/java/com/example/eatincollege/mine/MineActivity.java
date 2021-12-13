package com.example.eatincollege.mine;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eatincollege.R;
import com.example.eatincollege.utils.ActivityUtils;

import java.util.Objects;

public class MineActivity extends AppCompatActivity {
    private MineFragment mMineFragment;
    private MinePresenter mMinePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();   // 隐藏导航栏
        setContentView(R.layout.activity_fragment);

        mMineFragment = (MineFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (mMineFragment == null) {
            mMineFragment = MineFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mMineFragment, R.id.contentFrame);
        }

        mMinePresenter = new MinePresenter(mMineFragment);
    }
}
