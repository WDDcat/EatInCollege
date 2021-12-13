package com.example.eatincollege.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eatincollege.R;
import com.example.eatincollege.utils.SimpleAdapterBindView;

import java.util.List;
import java.util.Map;

public class CategoryFragment extends Fragment implements CategoryContract.View {
    private CategoryContract.Presenter mPresenter;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
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

    private ImageButton backBtn;    // 返回键
    private TextView nameText;      // 菜品种类
    private GridView foodGrid;      // 菜品列表

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category, container, false);

        backBtn = root.findViewById(R.id.category_btn_back);
        nameText = root.findViewById(R.id.category_tv_name);
        foodGrid = root.findViewById(R.id.category_gv_food);

        // 设置返回键点击事件
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        // 设置菜品点击事件
        foodGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.foodItemClicked(getContext(), position);
            }
        });

        // 获取上一个页面点击的菜品种类
        Intent fromIntent = mPresenter.getFromIntent();
        String typeE = fromIntent.getStringExtra("typeE");
        String typeC = fromIntent.getStringExtra("typeC");
        mPresenter.loadData(typeE, typeC);
        return root;
    }

    @Override
    public void setPresenter(CategoryContract.Presenter presenter) {
        mPresenter = presenter;
    }

    // 显示菜品种类
    @Override
    public void setTypeName(String name) {
        nameText.setText(name);
    }

    // 显示菜品列表
    @Override
    public void setFoodGrid(List<Map<String, Object>> dataList) {
        SimpleAdapter adapter = new SimpleAdapter(getContext(), dataList, R.layout.item_food_info,
                new String[]{"image", "name", "canteen"},
                new int[]{R.id.item_foodInfo_image, R.id.item_foodInfo_name, R.id.item_foodInfo_canteen});
        SimpleAdapterBindView.setImageViewBinder(getContext(), adapter);
        foodGrid.setAdapter(adapter);
    }

    // 处理页面跳转
    @Override
    public void handleActivitySwitch(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
