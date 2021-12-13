package com.example.eatincollege.MvpExample;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eatincollege.R;

public class ExampleFragment extends Fragment implements ExampleContract.View {
    private ExampleContract.Presenter mPresenter;

    public static ExampleFragment newInstance() {
        return new ExampleFragment();
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
        View root = inflater.inflate(R.layout.fragment_example, container, false);

        return root;
    }

    @Override
    public void setPresenter(ExampleContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void handleActivitySwitch(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
