package com.example.eatincollege.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eatincollege.NavigationBar;
import com.example.eatincollege.R;
import com.example.eatincollege.canteen.CanteenActivity;
import com.example.eatincollege.community.CommunityActivity;
import com.example.eatincollege.login.LoginActivity;
import com.example.eatincollege.mine.MineActivity;
import com.example.eatincollege.utils.GlideImageLoader;
import com.example.eatincollege.utils.SimpleAdapterBindView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;
import java.util.Map;

public class HomePageFragment extends Fragment implements HomePageContract.View {
    private HomePageContract.Presenter mPresenter;

    public static HomePageFragment newInstance() {
        return new HomePageFragment();
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

    private Banner banner;  // 轮播图
    private GridView recommendGrid; // 推荐菜品列表
    private NavigationBar navigationBar;    // 导航栏

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_homepage, container, false);

        banner = root.findViewById(R.id.homepage_banner);
        recommendGrid = root.findViewById(R.id.homepage_grid_recommend);
        navigationBar = root.findViewById(R.id.homepage_navigationBar);

        // 设置导航栏跳转
        navigationBar.setOnNavigationBarClickedCallback(new NavigationBar.OnNavigationBarClickedCallback() {
            @Override
            public void onClickedItem(int index) {
                switch (index) {
                    case 1:
                        mPresenter.handleActivitySwitch(getContext(), CanteenActivity.class);
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

        // 设置轮播图点击事件
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                mPresenter.bannerItemClicked(getContext(), position);
            }
        });

        // 设置推荐菜品项点击事件
        recommendGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.recommendItemClicked(getContext(), position);
            }
        });

        mPresenter.loadData();
        return root;
    }

    @Override
    public void setPresenter(HomePageContract.Presenter presenter) {
        mPresenter = presenter;
    }

    // 显示轮播图图片
    @Override
    public void setBannerImages(List<String> imageList) {
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(imageList);
        banner.start();
    }

    // 显示推荐菜品列表
    @Override
    public void setGridView(List<Map<String, Object>> dataList) {
        SimpleAdapter adapter = new SimpleAdapter(getContext(), dataList, R.layout.item_food_with_name,
                new String[]{"image", "name"},
                new int[]{R.id.item_foodWithName_image, R.id.item_foodWithName_name});
        SimpleAdapterBindView.setImageViewBinder(getContext(), adapter);
        recommendGrid.setAdapter(adapter);
    }

    // 处理页面跳转
    @Override
    public void handleActivitySwitch(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}

