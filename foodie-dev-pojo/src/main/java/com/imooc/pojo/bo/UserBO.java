package com.imooc.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:TODO
 * Create Time:2020/9/2 23:47
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@ApiModel(value = "用户对象BO", description = "从客户端，由用户传入的数据封装在此entity中")
@Data
public class UserBO {

    @ApiModelProperty(value = "用户名1", name = "username", example = "imooc", required = true)
    private String username;
    @ApiModelProperty(value = "密码1", name = "password", example = "123456", required = true)
    private String password;
    @ApiModelProperty(value = "确认密码1", name = "confirmPassword", example = "123456", required = false)
    private String confirmPassword;
}