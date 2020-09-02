package com.imooc.pojo.bo;

import lombok.Data;

/**
 * Description:TODO
 * Create Time:2020/9/2 23:47
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Data
public class UserBO {
    private String username;
    private String password;
    private String confirmPassword;
}
