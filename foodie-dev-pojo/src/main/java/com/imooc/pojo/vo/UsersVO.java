package com.imooc.pojo.vo;

import lombok.Data;

/**
 * Description:TODO
 * Create Time:2020/9/17 0017 上午 11:45
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Data
public class UsersVO {
    /**
     * 主键id 用户id
     */
    private String id;

    /**
     * 用户名 用户名
     */
    private String username;

    /**
     * 昵称 昵称
     */
    private String nickname;

    /**
     * 头像 头像
     */
    private String face;

    /**
     * 性别 性别 1:男  0:女  2:保密
     */
    private Integer sex;

    // 用户会话token
    private String userUniqueToken;
}
