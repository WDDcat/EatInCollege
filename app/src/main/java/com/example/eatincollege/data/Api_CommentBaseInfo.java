package com.example.eatincollege.data;

import java.util.ArrayList;
import java.util.List;

public class Api_CommentBaseInfo {
    public int u_id;    // 用户id
    public String name; // 用户名
    public String content;  // 评论内容
    public String image;    // 评论图片
    public int like;        // 点赞
    public int star;        // 收藏
    public List<Api_DiscussBaseInfo> discussList = new ArrayList<>();   // 回复内容列表
}
