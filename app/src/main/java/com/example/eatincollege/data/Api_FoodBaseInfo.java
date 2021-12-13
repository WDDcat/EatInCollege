package com.example.eatincollege.data;

import java.util.ArrayList;
import java.util.List;

public class Api_FoodBaseInfo {
    public int f_id;    // 菜品 id
    public String name; // 菜品名称
    public double price;// 菜品价格
    public String type; // 菜品种类
    public String canteen;  // 所属餐厅
    public String image;    // 菜品图片
    public boolean stared;  // 是否被收藏
    public List<String> material = new ArrayList<>();   // 材料列表
}
