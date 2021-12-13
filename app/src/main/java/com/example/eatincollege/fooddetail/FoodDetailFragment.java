package com.example.eatincollege.fooddetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.eatincollege.R;
import com.example.eatincollege.data.Api_FoodBaseInfo;
import com.example.eatincollege.login.LoginActivity;

public class FoodDetailFragment extends Fragment implements FoodDetailContract.View {
    private FoodDetailContract.Presenter mPresenter;

    public static FoodDetailFragment newInstance() {
        return new FoodDetailFragment();
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
    private ImageButton staredBtn;  // 收藏键
    private TextView nameText;      // 菜品名称
    private TextView priceText;     // 菜品价格
    private TextView canteenText;   // 餐厅名称
    private TextView descriptionText;   // 描述信息
    private ImageView imageImage;   // 菜品图片

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_fooddetail, container, false);

        backBtn = root.findViewById(R.id.foodDetail_btn_back);
        staredBtn = root.findViewById(R.id.foodDetail_btn_stared);
        nameText = root.findViewById(R.id.foodDetail_tv_name);
        priceText = root.findViewById(R.id.foodDetail_tv_price);
        canteenText = root.findViewById(R.id.foodDetail_tv_canteen);
        descriptionText = root.findViewById(R.id.foodDetail_tv_description);
        imageImage = root.findViewById(R.id.foodDetail_iv_image);

        // 设置返回键点击事件
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        // 设置收藏见点击事件
        staredBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mPresenter.staredButtonClicked()) {
                    mPresenter.handleActivitySwitch(getContext(), LoginActivity.class);
                }
            }
        });

        // 获取上一个页面点击的菜品id
        Intent fromIntent = mPresenter.getFromIntent();
        int f_id = fromIntent.getIntExtra("f_id", -1);
        mPresenter.loadData(f_id);
        return root;
    }

    @Override
    public void setPresenter(FoodDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    // 显示菜品名称
    @Override
    public void setFoodName(String name) {
        nameText.setText(name);
    }

    // 显示菜品收藏状态
    @Override
    public void setFoodStared(boolean stared) { //TODO: u_id & f_id
        if (stared) {
            staredBtn.setImageResource(R.drawable.ic_star_selected);
        } else {
            staredBtn.setImageResource(R.drawable.ic_star_unselected);
        }
    }

    // 显示菜品图像
    @Override
    public void setFoodImage(String data) {
        Glide.with(getContext()).load(data).into(imageImage);
    }

    // 显示菜品价格
    @Override
    public void setFoodPrice(double price) {
        priceText.setText("￥" + price);
    }

    // 显示所处餐厅的名称
    @Override
    public void setCanteenInfo(String canteen) {
        canteenText.setText(canteen);
    }

    // 显示菜品的描述
    @Override
    public void setFoodDescription(String description) {
        descriptionText.setText(description);
    }

    // 处理页面跳转
    @Override
    public void handleActivitySwitch(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
