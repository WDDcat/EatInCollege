package com.example.eatincollege.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eatincollege.NavigationBar;
import com.example.eatincollege.R;
import com.example.eatincollege.canteen.CanteenActivity;
import com.example.eatincollege.community.CommunityActivity;
import com.example.eatincollege.favourite.FavouriteActivity;
import com.example.eatincollege.homepage.HomePageActivity;
import com.example.eatincollege.login.LoginActivity;

public class MineFragment extends Fragment implements MineContract.View {
    private MineContract.Presenter mPresenter;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
        if (!mPresenter.getLoginStatus()) {
            mPresenter.handleActivitySwitch(getContext(), LoginActivity.class);
            getActivity().finish();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    private NavigationBar navigationBar;    // 导航栏
    private TextView nameText;  // 用户名
    private TextView favouriteText; // 收藏按钮
    private TextView logoutText;    // 登出按钮

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mine, container, false);

        // 如果未登录，直接跳转至登陆界面
        if (!mPresenter.getLoginStatus()) {
            mPresenter.handleActivitySwitch(getContext(), LoginActivity.class);
            return root;
        }

        navigationBar = root.findViewById(R.id.mine_navigationBar);
        nameText = root.findViewById(R.id.mine_tv_name);
        favouriteText = root.findViewById(R.id.mine_tv_favourite);
        logoutText = root.findViewById(R.id.mine_tv_logout);

        // 设置导航栏跳转
        navigationBar.setOnNavigationBarClickedCallback(new NavigationBar.OnNavigationBarClickedCallback() {
            @Override
            public void onClickedItem(int index) {
                switch (index) {
                    case 0:
                        mPresenter.handleActivitySwitch(getContext(), HomePageActivity.class);
                        break;
                    case 1:
                        mPresenter.handleActivitySwitch(getContext(), CanteenActivity.class);
                        break;
                    case 2:
                        mPresenter.handleActivitySwitch(getContext(), CommunityActivity.class);
                        break;
                    default:
                        break;
                }
            }
        });

        // 设置收藏键点击事件
        favouriteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.handleActivitySwitch(getContext(), FavouriteActivity.class);
            }
        });

        // 设置登出键点击事件
        logoutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.logout(getContext());
            }
        });

        mPresenter.loadData();
        return root;
    }

    @Override
    public void setPresenter(MineContract.Presenter presenter) {
        mPresenter = presenter;
    }

    // 显示用户名
    @Override
    public void setName(String name) {
        nameText.setText(name);
    }

    @Override
    public void handleActivitySwitch(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
