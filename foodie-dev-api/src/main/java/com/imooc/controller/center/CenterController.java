package com.imooc.controller.center;


import com.imooc.service.center.CenterUserService;
import com.imooc.utils.ApiResult;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Description:TODO
 * Create Time:2020/8/30 14:06
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Api(value = "用户中心", tags = "用户中心")
@RestController
@RequestMapping("center")
public class CenterController {

    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息", httpMethod = "GET")
    @GetMapping("/userInfo")
    public ApiResult setDefault(@ApiParam(value = "用户Id", required = true, name = "userId")
                                   @RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            return ApiResult.errorMsg("获取用户信息出错");
        }
        return ApiResult.ok(centerUserService.queryUserInfo(userId));
    }
}
