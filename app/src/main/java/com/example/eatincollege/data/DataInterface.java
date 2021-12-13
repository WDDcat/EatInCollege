package com.example.eatincollege.data;

import io.reactivex.Observable;

import java.util.List;

public interface DataInterface {
    boolean get_Api_Login(String name, String password);

    void get_Api_Logout();

    boolean get_Api_UserNameValid(String name);

    Api_FoodBaseInfo get_Api_FoodBaseInfo(int f_id);

    Api_UserBaseInfo get_Api_UserBaseInfo(int u_id);

    List<Api_FoodBaseInfo> get_Api_RecommendFood();

    List<Api_FoodBaseInfo> get_Api_FoodListByCondition(String restaurant, String type);

    List<Api_FoodBaseInfo> get_Api_FavouriteList();

    List<Api_CommentBaseInfo> get_Api_CommentList();

    void set_Api_StarFood(int f_id, boolean star);

    void set_Api_SignUp(String name, String pwd);
}
