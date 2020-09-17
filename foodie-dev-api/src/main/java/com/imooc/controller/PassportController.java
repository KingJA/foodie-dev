package com.imooc.controller;


import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.pojo.vo.UsersVO;
import com.imooc.service.UserService;
import com.imooc.utils.ApiResult;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.MD5Util;
import com.imooc.utils.RedisOperator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Description:TODO
 * Create Time:2020/8/30 14:06
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Api(value = "这是注册登录", tags = "注册登录")
@RestController
@RequestMapping("passport")
public class PassportController extends BaseController{

    @Autowired
    private UserService userService;


    @ApiOperation(value = "判断用户名是否存在", notes = "用户名是否存在notes", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public ApiResult queryUsernameIsExist(@RequestParam String username) {
        if (StringUtils.isBlank(username)) {
            return ApiResult.errorMsg("用户名不能为空");
        }
        if (userService.queryUserIsExist(username)) {
            return ApiResult.errorMsg("用户名已存在");
        }
        return ApiResult.ok();
    }

    @ApiOperation(value = "注册用户", notes = "注册用户notes", httpMethod = "POST")
    @PostMapping("/regist")
    public ApiResult regist(@RequestBody UserBO userBO,
                            HttpServletRequest request, HttpServletResponse response) {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ApiResult.errorMsg("账号或密码不能为空");
        }
        if (userService.queryUserIsExist(username)) {
            return ApiResult.errorMsg("用户名已存在");
        }
        if (password.length() < 6) {
            return ApiResult.errorMsg("密码长度不能小于6位");
        }
        if (!password.equals(confirmPassword)) {
            return ApiResult.errorMsg("重复密码不一致");
        }
        Users result = userService.createUser(userBO);

        //生成token并按uiserId保存到redis,返回带有token的UserVo到cookie
        //生成用户token，存入redis会话
        UsersVO usersVO= getConventUserVO( result);

        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(usersVO),true);

        //TODO 同步购物车数据
        return ApiResult.ok();
    }



    @ApiOperation(value = "登录", notes = "登录notes", httpMethod = "POST")
    @PostMapping("/login")
    public ApiResult login(@RequestBody UserBO userBO,
                           HttpServletRequest request, HttpServletResponse response) {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ApiResult.errorMsg("账号或密码不能为空");
        }
        Users result = userService.queryUserForLogin(username, MD5Util.md5(password));
        if (result == null) {
            return ApiResult.errorMsg("账号或密码不正确");
        }
        //生成用户token，存入redis会话
        UsersVO usersVO= getConventUserVO( result);
        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(usersVO),true);
        //TODO 同步购物车数据
        return ApiResult.ok(usersVO);
    }

    @ApiOperation(value = "退出登录", notes = "退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public ApiResult logout(@RequestParam String userId,
                           HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request,response,"user");

        // TODO 1.用户退出需要清空购物车
        //2.分布式会话需要清除会话信息
        redisOperator.del(REDIS_USER_TOKEN+":"+userId);
        return ApiResult.ok();
    }

}
