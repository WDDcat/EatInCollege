package com.example.eatincollege.community;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.text.Spannable;
import android.text.SpannableString;
import android.widget.ListView;

import com.example.eatincollege.BasePresenter;
import com.example.eatincollege.BaseView;

import java.util.List;
import java.util.Map;

public interface CommunityContract {
    interface View extends BaseView<Presenter> {
        void setCommentList(List<Map<String, Object>> dataList);

        Resources getRes();

        void updateDiscussList(ListView discussListView, List<SpannableString> discussList);

        void handleActivitySwitch(Intent intent);
    }

    interface Presenter extends BasePresenter {
        void loadData();

        boolean sendClicked(ListView discussListView,
                         List<SpannableString> discussList,
                         String text);

        void handleActivitySwitch(Context context, Class<?> cls);
    }
}
