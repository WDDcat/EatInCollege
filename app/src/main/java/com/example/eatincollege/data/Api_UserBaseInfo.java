package com.example.eatincollege.data;

import java.util.ArrayList;
import java.util.List;

public class Api_UserBaseInfo {
    public int u_id;    // 用户 id
    public String name; // 用户名
    public List<Integer> favouriteList = new ArrayList<>(); // 收藏菜品列表
}
