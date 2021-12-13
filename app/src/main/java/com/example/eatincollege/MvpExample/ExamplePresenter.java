package com.example.eatincollege.MvpExample;

import android.content.Context;
import android.content.Intent;

import com.example.eatincollege.data.DataLocal;

public class ExamplePresenter implements ExampleContract.Presenter {
    private ExampleContract.View mView;
    private final DataLocal mData;

    public ExamplePresenter(ExampleContract.View view) {
        mData = DataLocal.getInstance();
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void loadData() {
        // 处理 数据
    }

    @Override
    public void handleActivitySwitch(Context context, Class<?> cls) {
        Intent newIntent = new Intent(context, cls);
        mData.setFromIntent(newIntent);
        mView.handleActivitySwitch(newIntent);
    }
}
