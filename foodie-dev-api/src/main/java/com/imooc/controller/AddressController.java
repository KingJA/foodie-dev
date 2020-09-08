package com.imooc.controller;


import com.imooc.pojo.bo.AddressBO;
import com.imooc.service.AddressService;
import com.imooc.utils.ApiResult;
import com.imooc.utils.MobileEmailUtils;
import com.sun.corba.se.spi.ior.iiop.IIOPFactories;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@Api(value = "收获地址", tags = "收获地址")
@RestController
@RequestMapping("address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "根据用户Id查询收获地址", notes = "根据用户Id查询收获地址", httpMethod = "POST")
    @PostMapping("/list")
    public ApiResult queryUsernameIsExist(@RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            return ApiResult.errorMsg("收获地址错误");
        }
        return ApiResult.ok(addressService.getAddressList(userId));
    }

    @ApiOperation(value = "增加收获地址", notes = "增加收获地址", httpMethod = "POST")
    @PostMapping("/add")
    public ApiResult addAddress(@RequestBody AddressBO addressBO) {
        ApiResult checkRes = checkAddress(addressBO);
        if (checkRes.getStatus() != 200) {
            return checkRes;
        }
        addressService.addNewUserAddress(addressBO);
        return ApiResult.ok();
    }

    @ApiOperation(value = "修改收获地址", notes = "修改收获地址", httpMethod = "POST")
    @PostMapping("/update")
    public ApiResult updateAddress(@RequestBody AddressBO addressBO) {
        ApiResult checkRes = checkAddress(addressBO);
        if (checkRes.getStatus() != 200) {
            return checkRes;
        }
        addressService.updateUserAddress(addressBO);
        return ApiResult.ok();
    }

    @ApiOperation(value = "修改收获地址", notes = "修改收获地址", httpMethod = "POST")
    @PostMapping("/delete")
    public ApiResult deleteAddress(@ApiParam(value = "用户Id", required = true, name = "userId")
                                   @RequestParam String userId,
                                   @ApiParam(value = "地址Id", required = true, name = "addressId")
                                   @RequestParam String addressId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return ApiResult.errorMsg("删除地址出错");
        }
        addressService.deleteNewUserAddress(userId, addressId);
        return ApiResult.ok();
    }

    private ApiResult checkAddress(AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return ApiResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return ApiResult.errorMsg("收货人姓名不能太长");
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return ApiResult.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length() != 11) {
            return ApiResult.errorMsg("收货人手机号长度不正确");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return ApiResult.errorMsg("收货人手机号格式不正确");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return ApiResult.errorMsg("收货地址信息不能为空");
        }

        return ApiResult.ok();
    }


    @ApiOperation(value = "设为默认地址", notes = "设为默认地址", httpMethod = "POST")
    @PostMapping("/setDefalut")
    public ApiResult setDefault(@ApiParam(value = "用户Id", required = true, name = "userId")
                                   @RequestParam String userId,
                                   @ApiParam(value = "地址Id", required = true, name = "addressId")
                                   @RequestParam String addressId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return ApiResult.errorMsg("默认地址出错");
        }
        addressService.setAddressDefault(userId, addressId);
        return ApiResult.ok();
    }
}
