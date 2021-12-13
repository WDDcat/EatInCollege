package com.example.eatincollege.canteen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eatincollege.NavigationBar;
import com.example.eatincollege.R;
import com.example.eatincollege.community.CommunityActivity;
import com.example.eatincollege.homepage.HomePageActivity;
import com.example.eatincollege.mine.MineActivity;
import com.example.eatincollege.utils.SimpleAdapterBindView;

import java.util.List;
import java.util.Map;

public class CanteenFragment extends Fragment implements CanteenContract.View {
    private CanteenContract.Presenter mPresenter;

    public static CanteenFragment newInstance() {
        return new CanteenFragment();
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

    private GridView typeGrid;  // 上方菜品种类 网格试图
    private GridView foodGrid;  // 右下菜品列表
    private ListView restaurantList;    // 左下餐厅列表
    private NavigationBar navigationBar;    // 导航栏

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_canteen, container, false);

        typeGrid = root.findViewById(R.id.canteen_grid_type);
        foodGrid = root.findViewById(R.id.canteen_grid_food);
        restaurantList = root.findViewById(R.id.canteen_list_restaurant);
        navigationBar = root.findViewById(R.id.canteen_navigationBar);

        // 设置导航栏跳转
        navigationBar.setOnNavigationBarClickedCallback(new NavigationBar.OnNavigationBarClickedCallback() {
            @Override
            public void onClickedItem(int index) {
                switch (index) {
                    case 0:
                        mPresenter.handleActivitySwitch(getContext(), HomePageActivity.class);
                        break;
                    case 2:
                        mPresenter.handleActivitySwitch(getContext(), CommunityActivity.class);
                        break;
                    case 3:
                        mPresenter.handleActivitySwitch(getContext(), MineActivity.class);
                        break;
                    default:
                        break;
                }
            }
        });

        // 设置餐厅项点击事件
        restaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.restaurantItemClicked(position);
            }
        });

        // 设置菜品种类项点击事件
        typeGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.typeItemClicked(getContext(), position);
            }
        });

        // 设置菜品点击事件
        foodGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.foodItemClicked(getContext(), position);
            }
        });

        mPresenter.loadData();
        return root;
    }

    @Override
    public void setPresenter(CanteenContract.Presenter presenter) {
        mPresenter = presenter;
    }

    // 显示菜品种类
    @Override
    public void setTypeGrid(List<Map<String, Object>> dataList) {
        SimpleAdapter adapter = new SimpleAdapter(getContext(), dataList, R.layout.item_button,
                new String[]{"name"},
                new int[]{R.id.item_name});
        typeGrid.setAdapter(adapter);
    }

    // 显示菜品列表
    @Override
    public void setFoodGrid(List<Map<String, Object>> dataList) {
        SimpleAdapter adapter = new SimpleAdapter(getContext(), dataList, R.layout.item_food_with_name,
                new String[]{"image", "name"},
                new int[]{R.id.item_foodWithName_image, R.id.item_foodWithName_name});
        SimpleAdapterBindView.setImageViewBinder(getContext(), adapter);
        foodGrid.setAdapter(adapter);
//        View view = new View(getContext());
//        GridView.LayoutParams params = new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                (int) (getResources().getDimension(R.dimen.navigation_bar_height) + 5));
//        view.setLayoutParams(params);
    }

    //显示餐厅列表
    @Override
    public void setRestaurantList(List<Map<String, Object>> dataList) {
        SimpleAdapter adapter = new SimpleAdapter(getContext(), dataList, R.layout.item_button,
                new String[]{"name"},
                new int[]{R.id.item_name});
        restaurantList.setAdapter(adapter);
    }

    // 将某个餐厅列表项 设为激活状态
    @Override
    public void setActiveRestaurantList(int index) {
        TextView newView = (TextView) restaurantList.getChildAt(index);
        if (newView != null) {
            newView.setBackground(getResources().getDrawable(R.drawable.background_item_rounded_square_selected));
            newView.setTextColor(getResources().getColor(R.color.white));
        }
    }

    // 取消激活 某个餐厅列表项
    @Override
    public void setUnActiveRestaurantList(int index) {
        TextView newView = (TextView) restaurantList.getChildAt(index);
        if (newView != null) {
            newView.setBackground(getResources().getDrawable(R.drawable.background_item_rounded_square_unselected));
            newView.setTextColor(getResources().getColor(R.color.black));
        }
    }

    // 将某个菜品种类项 设为激活状态
    @Override
    public void setActiveTypeGrid(int index) {
        TextView newView = (TextView) typeGrid.getChildAt(index);
        if (newView != null) {
            newView.setBackground(getResources().getDrawable(R.drawable.background_item_rounded_square_selected));
            newView.setTextColor(getResources().getColor(R.color.white));
        }
    }

    // 取消激活 某个菜品种类项
    @Override
    public void setUnActiveTypeGrid(int index) {
        TextView newView = (TextView) typeGrid.getChildAt(index);
        if (newView != null) {
            newView.setBackground(getResources().getDrawable(R.drawable.background_item_rounded_square_unselected));
            newView.setTextColor(getResources().getColor(R.color.black));
        }
    }

    // 处理页面跳转
    @Override
    public void handleActivitySwitch(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
