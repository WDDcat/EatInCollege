package com.example.eatincollege.launcher;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eatincollege.R;
import com.example.eatincollege.utils.ActivityUtils;

public class LauncherActivity extends AppCompatActivity {
    private LauncherFragment mLauncherFragment;
    private LauncherPresenter mLauncherPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        mLauncherFragment = (LauncherFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (mLauncherFragment == null) {
            mLauncherFragment = LauncherFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mLauncherFragment, R.id.contentFrame);
        }

        mLauncherPresenter = new LauncherPresenter(mLauncherFragment);
    }
}
