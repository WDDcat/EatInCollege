package com.example.eatincollege.community;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.ListView;

import androidx.annotation.ColorInt;

import com.example.eatincollege.R;
import com.example.eatincollege.data.Api_CommentBaseInfo;
import com.example.eatincollege.data.Api_DiscussBaseInfo;
import com.example.eatincollege.data.DataLocal;
import com.example.eatincollege.login.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommunityPresenter implements CommunityContract.Presenter {
    private CommunityContract.View mView;
    private final DataLocal mData;

    public CommunityPresenter(CommunityContract.View view) {
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
        // 评论数据
        List<Map<String, Object>> dataList = new ArrayList<>();
        List<Api_CommentBaseInfo> commentList = mData.get_Api_CommentList();
        for (Api_CommentBaseInfo comment : commentList) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", comment.name);
            map.put("content", comment.content);
            map.put("image", comment.image);
            map.put("like", comment.like);
            map.put("star", comment.star);

            List<SpannableString> discussList = new ArrayList<>();
            for(Api_DiscussBaseInfo discuss : comment.discussList) {
                SpannableString content = new SpannableString(discuss.name + ": " + discuss.content);
                ForegroundColorSpan span = new ForegroundColorSpan(mView.getRes().getColor(R.color.primary));
                content.setSpan(span, 0, discuss.name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                discussList.add(content);
            }
            map.put("discuss", discussList);

            dataList.add(map);
        }
        mView.setCommentList(dataList);
    }

    // 处理发送按钮点击事件
    @Override
    public boolean sendClicked(ListView discussListView, List<SpannableString> discussList, String text) {
        // 先判断是否登录，未登录不能回复
        if (mData.getUid() == -1) {
            return false;
        }

        // 更新回复数据
//        mData.set_Api_DiscussComment(text);

        String name = mData.get_Api_UserBaseInfo(mData.getUid()).name;
        SpannableString discussString = new SpannableString(name + ": " + text);
        ForegroundColorSpan span = new ForegroundColorSpan(mView.getRes().getColor(R.color.primary));
        discussString.setSpan(span, 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        discussList.add(discussString);
        mView.updateDiscussList(discussListView, discussList);
        return true;
    }

    // 处理简单的页面跳转数据
    @Override
    public void handleActivitySwitch(Context context, Class<?> cls) {
        Intent newIntent = new Intent(context, cls);
        mData.setFromIntent(newIntent);
        mView.handleActivitySwitch(newIntent);
    }
}
