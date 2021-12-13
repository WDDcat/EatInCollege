package com.example.eatincollege.community;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.eatincollege.NavigationBar;
import com.example.eatincollege.R;
import com.example.eatincollege.canteen.CanteenActivity;
import com.example.eatincollege.homepage.HomePageActivity;
import com.example.eatincollege.login.LoginActivity;
import com.example.eatincollege.mine.MineActivity;
import com.example.eatincollege.utils.AttrHelper;
import com.example.eatincollege.utils.SimpleAdapterBindView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommunityFragment extends Fragment implements CommunityContract.View {
    private CommunityContract.Presenter mPresenter;

    public static CommunityFragment newInstance() {
        return new CommunityFragment();
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

    private NavigationBar navigationBar;    // 导航栏
    private ListView commentList;           // 评论列表

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_community, container, false);

        commentList = root.findViewById(R.id.community_lv_comment);
        navigationBar = root.findViewById(R.id.community_navigationBar);

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
                    case 3:
                        mPresenter.handleActivitySwitch(getContext(), MineActivity.class);
                        break;
                    default:
                        break;
                }
            }
        });

        mPresenter.loadData();
        return root;
    }

    @Override
    public void setPresenter(CommunityContract.Presenter presenter) {
        mPresenter = presenter;
    }

    // 显示评论
    @Override
    public void setCommentList(List<Map<String, Object>> dataList) {
        // 自定义适配器 适配 评论中的各项操作及数据适配
        CommentListAdapter adapter = new CommentListAdapter(getContext(), dataList);
        commentList.setAdapter(adapter);
        TextView footerView = new TextView(getContext());
        ListView.LayoutParams params = new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) (AttrHelper.dp2px(getContext(), 85) +
                        getResources().getDimension(R.dimen.navigation_bar_height)));
        footerView.setLayoutParams(params);
        footerView.setText("\n\n已经到底啦~");
        footerView.setGravity(Gravity.TOP | Gravity.CENTER);
        commentList.addFooterView(footerView);
    }

    @Override
    public Resources getRes() {
        return getResources();
    }

    // 更新回复列表
    @Override
    public void updateDiscussList(ListView discussListView, List<SpannableString> discussList) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (SpannableString content : (ArrayList<SpannableString>) discussList) {
            Map<String, Object> map = new HashMap<>();
            map.put("content", content);
            dataList.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), dataList, R.layout.item_discuss,
                new String[]{"content"}, new int[]{R.id.item_discuss_tv_content});
        SimpleAdapterBindView.setTextViewSpannableBinder(getContext(), simpleAdapter);
        discussListView.setAdapter(simpleAdapter);
    }

    // 处理页面跳转
    @Override
    public void handleActivitySwitch(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    // 自定义 ListView 适配器
    private class CommentListAdapter extends BaseAdapter {
        private final Context mContext;
        private List<? extends Map<String, ?>> mData;

        CommentListAdapter(Context context, List<? extends Map<String, ?>> data) {
            mContext = context;
            mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false);
            Map<String, Object> data = (Map<String, Object>) mData.get(position);

            TextView nameText = convertView.findViewById(R.id.item_comment_tv_name);        // 用户名
            nameText.setText(data.get("name").toString());

            TextView contentText = convertView.findViewById(R.id.item_comment_tv_content);  // 评论内容
            contentText.setText(data.get("content").toString());

            ImageView imageView = convertView.findViewById(R.id.item_comment_iv_image);     // 配图
            if (data.get("image").toString().equals("")) {
                // 无图评论
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
                imageView.setLayoutParams(params);
            } else {
                Glide.with(mContext).load(data.get("image").toString()).into(imageView);
            }

            TextView likeText = convertView.findViewById(R.id.item_comment_tv_like);        // 点赞数量
            likeText.setText(data.get("like").toString());

            TextView starText = convertView.findViewById(R.id.item_comment_tv_star);        // 收藏数量
            starText.setText(data.get("star").toString());

            ListView discussListView = convertView.findViewById(R.id.item_comment_lv_discuss);  // 回复列表
            updateDiscussList(discussListView, (List<SpannableString>) data.get("discuss"));

            EditText discussEditText = convertView.findViewById(R.id.item_comment_et_discuss);  // 回复输入框
            discussEditText.setImeOptions(EditorInfo.IME_ACTION_SEND);  // 将键盘的换行键 替换为 发送
            discussEditText.setOnEditorActionListener((v, actionId, event) -> { // 设置单击发送的响应事件
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    boolean success = mPresenter.sendClicked(discussListView,
                                                    (List<SpannableString>) data.get("discuss"),
                                                     discussEditText.getText().toString());
                    if (!success) {
                        // 如未登录则返回 false 跳转至登陆界面
                        mPresenter.handleActivitySwitch(getContext(), LoginActivity.class);
                    } else {
                        // 收起键盘
                        InputMethodManager manager = ((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
                        if (manager != null)
                            manager.hideSoftInputFromWindow(discussEditText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        // 清空输入框
                        discussEditText.setText("");
                    }
                }
                return true;
            });

            ImageView sendButton = convertView.findViewById(R.id.item_comment_btn_send);    // 发送按钮
            sendButton.setOnClickListener(v -> {    // 设置监听事件
                if (!discussEditText.getText().equals("")) {
                    // 收起键盘
                    InputMethodManager manager = ((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
                    if (manager != null)
                        manager.hideSoftInputFromWindow(sendButton.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    boolean success = mPresenter.sendClicked(discussListView,
                            (List<SpannableString>) data.get("discuss"),
                            discussEditText.getText().toString());
                    if (!success) {
                        // 如未登录则返回 false 跳转至登陆界面
                        mPresenter.handleActivitySwitch(getContext(), LoginActivity.class);
                    }
                }
            });

            return convertView;
        }
    }
}
