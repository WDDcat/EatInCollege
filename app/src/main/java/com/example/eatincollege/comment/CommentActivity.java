package com.example.eatincollege.comment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eatincollege.R;
import com.example.eatincollege.utils.ActivityUtils;

import java.util.Objects;

public class CommentActivity extends AppCompatActivity {
    private CommentFragment mCommentFragment;
    private CommentPresenter mCommentPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();   // 隐藏导航栏
        setContentView(R.layout.activity_fragment);

        mCommentFragment = (CommentFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (mCommentFragment == null) {
            mCommentFragment = CommentFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mCommentFragment, R.id.contentFrame);
        }

        mCommentPresenter = new CommentPresenter(mCommentFragment);
    }
}
