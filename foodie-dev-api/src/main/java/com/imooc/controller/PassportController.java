package com.imooc.controller;


import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.ApiResult;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:TODO
 * Create Time:2020/8/30 14:06
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @GetMapping("/queryUsernameIsExit")
    public ApiResult queryUsernameIsExit(@RequestParam String username) {
        if (StringUtils.isBlank(username)) {
            return ApiResult.errorMsg("用户名不能为空");
        }
        if (userService.queryUserIsExist(username)) {
            return ApiResult.errorMsg("用户名已存在");
        }
        return ApiResult.ok();
    }
    @PostMapping("/regist")
    public ApiResult regist(UserBO userBO) {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();
        if (StringUtils.isBlank(username)||StringUtils.isBlank(password)) {
            return ApiResult.errorMsg("账号或密码不能为空");
        }
        if (userService.queryUserIsExist(username)) {
            return ApiResult.errorMsg("用户名已存在");
        }
        if (password.length()<6) {
            return ApiResult.errorMsg("密码长度不能小于6位");
        }
        if (!password.equals(confirmPassword)) {
            return ApiResult.errorMsg("重复密码不一致");
        }
        userService.createUser(userBO);
        return ApiResult.ok();
    }
}
