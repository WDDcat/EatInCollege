package com.example.eatincollege.utils;

import android.content.Context;

public class AttrHelper {
    public static int px2dp(Context context, float pixelValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (pixelValue / density + 0.5f);
    }

    public static int dp2px(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    public static int px2sp(Context context, float pixelValue) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pixelValue / scaledDensity + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scaledDensity + 0.5f);
    }
}
