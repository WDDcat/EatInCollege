package com.example.eatincollege.favourite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eatincollege.R;
import com.example.eatincollege.utils.SimpleAdapterBindView;

import java.util.List;
import java.util.Map;

public class FavouriteFragment extends Fragment implements FavouriteContract.View {
    private FavouriteContract.Presenter mPresenter;

    public static FavouriteFragment newInstance() {
        return new FavouriteFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
        mPresenter.loadData();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    private ImageButton backBtn;    // 返回按钮
    private ListView favouriteList; // 收藏菜品列表

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favourite, container, false);

        backBtn = root.findViewById(R.id.favourite_btn_back);
        favouriteList = root.findViewById(R.id.favourite_lv_favourite);

        // 设置返回键点击事件
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        // 收藏菜品点击事件
        favouriteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.favouriteItemClicked(getContext(), position);
            }
        });

        mPresenter.loadData();
        return root;
    }

    @Override
    public void setPresenter(FavouriteContract.Presenter presenter) {
        mPresenter = presenter;
    }

    // 显示收藏菜品列表
    @Override
    public void setFavouriteFoodList(List<Map<String, Object>> dataList) {
        SimpleAdapter adapter = new SimpleAdapter(getContext(), dataList, R.layout.item_favourite_food,
                new String[]{"image", "name"},
                new int[]{R.id.item_favouriteFood_image, R.id.item_favouriteFood_name});
        SimpleAdapterBindView.setImageViewBinder(getContext(), adapter);
        favouriteList.setAdapter(adapter);
    }

    // 处理页面跳转
    @Override
    public void handleActivitySwitch(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
