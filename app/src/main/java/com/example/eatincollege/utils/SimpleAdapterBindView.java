package com.example.eatincollege.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eatincollege.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleAdapterBindView {
    public static void setImageViewBinder(Context context, SimpleAdapter adapter) {
        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if (view instanceof ImageView && data instanceof String) {
                    ImageView imageView = (ImageView) view;
                    Glide.with(context).load(data).into(imageView);
                    return true;
                }
                return false;
            }
        });
    }

//    public static void setImageViewListViewBinder(Context context, SimpleAdapter adapter) {
//        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
//            @Override
//            public boolean setViewValue(View view, Object data, String textRepresentation) {
//                if (view instanceof ImageView && data instanceof String) {
//                    ImageView imageView = (ImageView) view;
//                    Glide.with(context).load(data).into(imageView);
//                    return true;
//                }
//                if (view instanceof ListView && data instanceof ArrayList) {
//                    ListView listView = (ListView) view;
//                    List<Map<String, Object>> dataList = new ArrayList<>();
//                    for (SpannableString content : (ArrayList<SpannableString>) data) {
//                        Map<String, Object> map = new HashMap<>();
//                        map.put("content", content);
//                        dataList.add(map);
//                    }
//                    SimpleAdapter simpleAdapter = new SimpleAdapter(context, dataList, R.layout.item_discuss,
//                            new String[]{"content"},
//                            new int[]{R.id.item_discuss_tv_content});
//                    setTextViewSpannableBinder(context, simpleAdapter);
//                    listView.setAdapter(simpleAdapter);
//                    return true;
//                }
//                if (view instanceof EditText) {
//
//                }
//                return false;
//            }
//        });
//    }

    public static void setTextViewSpannableBinder(Context context, SimpleAdapter adapter) {
        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if (view instanceof TextView && data instanceof SpannableString) {
                    TextView textView = (TextView) view;
                    textView.setText((SpannableString)data);
                    return true;
                }
                return false;
            }
        });
    }
}
