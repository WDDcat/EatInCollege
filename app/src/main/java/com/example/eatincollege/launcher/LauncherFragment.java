package com.example.eatincollege.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eatincollege.R;
import com.example.eatincollege.canteen.CanteenActivity;
import com.example.eatincollege.homepage.HomePageActivity;

public class LauncherFragment extends Fragment implements LauncherContract.View {
    private LauncherContract.Presenter mPresenter;

    public static LauncherFragment newInstance() {
        return new LauncherFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_launcher, container, false);

        Handler handler = new Handler();
        //当计时结束时，跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentHomePage = new Intent(getContext(), HomePageActivity.class);
                startActivity(intentHomePage);
                getActivity().finish();
            }
        }, 100);   //持续时间为3秒   //TODO:3000

        return root;
    }

    @Override
    public void setPresenter(LauncherContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
