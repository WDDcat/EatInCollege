package com.example.eatincollege;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.eatincollege.utils.AttrHelper;

public class NavigationBar extends LinearLayout {
    private Context mContext;
    private int mStatus = 0;
    private final String[] mTitles = {"首页", "食堂", "社区", "我的"};

    public NavigationBar(Context context) {
        this(context, null);
    }

    public NavigationBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        this.setOrientation(HORIZONTAL);
        this.setGravity(Gravity.BOTTOM);
        this.setBaselineAligned(false);
        this.setBackgroundColor(Color.argb(0, 255, 255, 0));
        initView(attrs);
    }

    public NavigationBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.NavigationBar);
        mStatus = typedArray.getInteger(R.styleable.NavigationBar_navigation_status, mStatus);
        typedArray.recycle();

        for (int i = 0; i < 4; i++) {
            TextView childView;
            if (i == mStatus) {
                childView = getSelectedNavigationItem(i);
            } else {
                childView = getUnselectedNavigationItem(i);
            }
            this.addView(childView);
        }
    }

    private TextView getUnselectedNavigationItem(int id) {
        TextView sectionText = new TextView(mContext);
        float mHeight = getResources().getDimension(R.dimen.navigation_bar_height_shorter);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, (int) mHeight, 1);
        sectionText.setLayoutParams(layoutParams);
        sectionText.setText(mTitles[id]);
        sectionText.setTextColor(getResources().getColor(R.color.primary));
        sectionText.setTextSize(AttrHelper.sp2px(mContext, 6));
        sectionText.setGravity(Gravity.CENTER);
        sectionText.setBackgroundResource(R.drawable.background_navigation_item_unselected);

        sectionText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNavigationBarClickedCallback != null) {
                    onNavigationBarClickedCallback.onClickedItem(id);
                }
            }
        });

        return sectionText;
    }

    private TextView getSelectedNavigationItem(int id) {
        TextView sectionText = new TextView(mContext);
        float mHeight = getResources().getDimension(R.dimen.navigation_bar_height);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, (int) mHeight, 1);
        sectionText.setLayoutParams(layoutParams);
        sectionText.setText(mTitles[id]);
        sectionText.setTextColor(Color.WHITE);
        sectionText.setTextSize(AttrHelper.sp2px(mContext, 6));
        sectionText.setGravity(Gravity.CENTER);
        sectionText.setBackgroundResource(R.drawable.background_navigation_item_selected);

        return sectionText;
    }

    public interface OnNavigationBarClickedCallback {
        void onClickedItem(int index);
    }

    private OnNavigationBarClickedCallback onNavigationBarClickedCallback;

    public OnNavigationBarClickedCallback getOnNavigationBarClickedCallback() {
        return onNavigationBarClickedCallback;
    }

    public NavigationBar setOnNavigationBarClickedCallback(OnNavigationBarClickedCallback onNavigationBarClickedCallback) {
        this.onNavigationBarClickedCallback = onNavigationBarClickedCallback;
        return this;
    }
}
