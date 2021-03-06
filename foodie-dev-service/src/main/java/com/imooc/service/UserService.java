package com.imooc.service;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;

/**
 * Description:TODO
 * Create Time:2020/9/1 0001 下午 4:10
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface UserService {
    public boolean queryUserIsExist(String username);
    public Users queryUserForLogin(String username, String password);

    public Users createUser(UserBO userBO);
}
