package com.example.eatincollege.data;

import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class DataLocal implements DataInterface {
    public static DataLocal INSTANCE = null;
    private static JSONObject mJsonRoot;

    public static DataLocal getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataLocal();
        }
        return INSTANCE;
    }

    private static Intent intent;

    public void setFromIntent(Intent intent) {
        this.intent = intent;
    }

    public Intent getFromIntent() {
        return intent;
    }

    private static int f_id = -1;

    public void setFid(int f_id) {
        this.f_id = f_id;
    }

    public int getFid() {
        return f_id;
    }

    private static int u_id = -1;

    private void setUid(int u_id) {
        this.u_id = u_id;
    }

    public int getUid() {
        return u_id;
    }

    DataLocal() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("assets/" + "data.json");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            inputStream.close();
            bufferedReader.close();

            mJsonRoot = new JSONObject(builder.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    // 登录操作，返回成功与否
    @Override
    public boolean get_Api_Login(String name, String password) {
        try {
            JSONArray userList = mJsonRoot.getJSONArray("user");
            for (int i = 0; i < userList.length(); i++) {
                JSONObject user = userList.getJSONObject(i);
                if (user.getString("name").equals(name) &&
                        user.getString("password").equals(password)) {
                    setUid(user.getInt("id"));
                    return true;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 登出操作
    @Override
    public void get_Api_Logout() {
        setUid(-1);
    }

    @Override
    public boolean get_Api_UserNameValid(String name) {
        try {
            JSONArray userList = mJsonRoot.getJSONArray("user");
            for (int i = 0; i < userList.length(); i++) {
                JSONObject user = userList.getJSONObject(i);
                if (user.getString("name").equals(name)) {
                    return false;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    // 根据菜品 id 获取 菜品基本信息
    @Override
    public Api_FoodBaseInfo get_Api_FoodBaseInfo(int f_id) {
        try {
            JSONArray foodList = mJsonRoot.getJSONArray("food");
            for (int i = 0; i < foodList.length(); i++) {
                JSONObject food = foodList.getJSONObject(i);
                if (food.getInt("id") == f_id) {
                    Api_FoodBaseInfo foodBaseInfo = new Api_FoodBaseInfo();
                    foodBaseInfo.f_id = food.getInt("id");
                    foodBaseInfo.name = food.getString("name");
                    foodBaseInfo.price = food.getDouble("price");
                    foodBaseInfo.type = food.getString("type");
                    foodBaseInfo.canteen = food.getString("canteen");
                    foodBaseInfo.image = food.getString("image");

                    JSONArray materialList = food.getJSONArray("material");
                    for (int j = 0; j < materialList.length(); j++) {
                        foodBaseInfo.material.add(materialList.getString(j));
                    }

                    if (u_id != -1) {
                        List<Integer> favouriteList = get_Api_UserBaseInfo(getUid()).favouriteList;
                        for (int j = 0; j < favouriteList.size(); j++) {
                            if (favouriteList.get(j) == f_id) {
                                foodBaseInfo.stared = true;
                                return foodBaseInfo;
                            }
                        }
                    }
                    foodBaseInfo.stared = false;
                    return foodBaseInfo;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 根据用户 id 获取 用户基本信息
    @Override
    public Api_UserBaseInfo get_Api_UserBaseInfo(int u_id) {
        try {
            JSONArray userList = mJsonRoot.getJSONArray("user");
            for (int i = 0; i < userList.length(); i++) {
                JSONObject user = userList.getJSONObject(i);
                if (user.getInt("id") == u_id) {
                    Api_UserBaseInfo userBaseInfo = new Api_UserBaseInfo();
                    userBaseInfo.u_id = user.getInt("id");
                    userBaseInfo.name = user.getString("name");

                    JSONArray favouriteList = user.getJSONArray("favourite");
                    for (int j = 0; j < favouriteList.length(); j++) {
                        userBaseInfo.favouriteList.add(favouriteList.getInt(j));
                    }
                    return userBaseInfo;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 获取推荐的菜品列表
    @Override
    public List<Api_FoodBaseInfo> get_Api_RecommendFood() {
        List<Api_FoodBaseInfo> recommendFoodList = new ArrayList<>();
        try {
            JSONArray foodList = mJsonRoot.getJSONArray("food");
            for (int i = 0; i < foodList.length(); i++) {
                JSONObject food = foodList.getJSONObject(i);
                if (food.getBoolean("recommend")) {
                    Api_FoodBaseInfo foodBaseInfo = new Api_FoodBaseInfo();
                    foodBaseInfo.f_id = food.getInt("id");
                    foodBaseInfo.name = food.getString("name");
                    foodBaseInfo.price = food.getDouble("price");
                    foodBaseInfo.type = food.getString("type");
                    foodBaseInfo.canteen = food.getString("canteen");
                    foodBaseInfo.image = food.getString("image");
                    recommendFoodList.add(foodBaseInfo);
                }
            }
            return recommendFoodList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recommendFoodList;
    }

    // 根据 餐厅 和 种类 筛选 菜品列表
    @Override
    public List<Api_FoodBaseInfo> get_Api_FoodListByCondition(String restaurant, String type) {
        List<Api_FoodBaseInfo> foodListByCondition = new ArrayList<>();
        try {
            JSONArray foodList = mJsonRoot.getJSONArray("food");
            for (int i = 0; i < foodList.length(); i++) {
                JSONObject food = foodList.getJSONObject(i);
                if ((food.getString("canteen").equals(restaurant) || restaurant.equals("")) &&
                        (food.getString("type").equals(type) || type.equals(""))) {
                    Api_FoodBaseInfo foodBaseInfo = new Api_FoodBaseInfo();
                    foodBaseInfo.f_id = food.getInt("id");
                    foodBaseInfo.name = food.getString("name");
                    foodBaseInfo.price = food.getDouble("price");
                    foodBaseInfo.type = food.getString("type");
                    foodBaseInfo.canteen = food.getString("canteen");
                    foodBaseInfo.image = food.getString("image");
                    foodListByCondition.add(foodBaseInfo);
                }
            }
            return foodListByCondition;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return foodListByCondition;
    }

    // 获取当前登录用户的收藏菜品列表，如未登录则返回空列表
    @Override
    public List<Api_FoodBaseInfo> get_Api_FavouriteList() {
        List<Api_FoodBaseInfo> favouriteList = new ArrayList<>();
        Api_UserBaseInfo user = get_Api_UserBaseInfo(getUid());
        if (user != null) {
            for (int i = 0; i < user.favouriteList.size(); i++) {
                Api_FoodBaseInfo foodBaseInfo = get_Api_FoodBaseInfo(user.favouriteList.get(i));
                favouriteList.add(foodBaseInfo);
            }
        }
        return favouriteList;
    }

    // 获取评论数据列表
    @Override
    public List<Api_CommentBaseInfo> get_Api_CommentList() {
        List<Api_CommentBaseInfo> commentBaseInfoList = new ArrayList<>();
        try {
            JSONArray commentList = mJsonRoot.getJSONArray("comment");
            for (int i = 0; i < commentList.length(); i++) {
                JSONObject comment = commentList.getJSONObject(i);
                Api_CommentBaseInfo commentBaseInfo = new Api_CommentBaseInfo();
                commentBaseInfo.u_id = comment.getInt("u_id");
                commentBaseInfo.name = get_Api_UserBaseInfo(commentBaseInfo.u_id).name;
                commentBaseInfo.content = comment.getString("content");
                commentBaseInfo.image = comment.getString("image");
                commentBaseInfo.like = comment.getInt("like");
                commentBaseInfo.star = comment.getInt("star");

                JSONArray discussList = comment.getJSONArray("discuss");
                for (int j = 0; j < discussList.length(); j++) {
                    JSONObject discuss = discussList.getJSONObject(j);
                    Api_DiscussBaseInfo discussBaseInfo = new Api_DiscussBaseInfo();
                    discussBaseInfo.u_id = discuss.getInt("u_id");
                    discussBaseInfo.name = get_Api_UserBaseInfo(discussBaseInfo.u_id).name;
                    discussBaseInfo.content = discuss.getString("content");
                    commentBaseInfo.discussList.add(discussBaseInfo);
                }

                commentBaseInfoList.add(commentBaseInfo);
            }
            return commentBaseInfoList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return commentBaseInfoList;
    }

    //
    @Override
    public void set_Api_StarFood(int f_id, boolean star) {    //TODO: u_id
        try {
            JSONArray userList = mJsonRoot.getJSONArray("user");
            for (int i = 0; i < userList.length(); i++) {
                JSONObject user = userList.getJSONObject(i);
                if (user.getInt("id") == getUid()) {
                    JSONArray favouriteList = user.getJSONArray("favourite");
                    if (star) {
                        favouriteList.put(f_id);
                    } else {
                        for (int j = 0; j < favouriteList.length(); j++) {
                            if ((int) favouriteList.get(j) == f_id) {
                                favouriteList.remove(j);
                            }
                        }
                    }
                }
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void set_Api_SignUp(String name, String pwd) {
        try {
            JSONArray userList = mJsonRoot.getJSONArray("user");
            JSONObject user = new JSONObject();
            user.put("id", 11111);
            user.put("name", name);
            user.put("password", pwd);
            user.put("favourite", new JSONArray());
            userList.put(user);
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
    }
}
