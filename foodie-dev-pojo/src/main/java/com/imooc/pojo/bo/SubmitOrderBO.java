package com.imooc.pojo.bo;

import lombok.Data;

/**
 * Description:TODO
 * Create Time:2020/9/8 0008 下午 2:44
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Data
public class SubmitOrderBO {
    private String userId;
    private String itemSpecIds;
    private String addressId;
    private Integer payMethod;
    private String leftMsg;

    @Override
    public String toString() {
        return "SubmitOrderBO{" +
                "userId='" + userId + '\'' +
                ", itemSpecIds='" + itemSpecIds + '\'' +
                ", addressId='" + addressId + '\'' +
                ", payMethod=" + payMethod +
                ", leftMsg='" + leftMsg + '\'' +
                '}';
    }
}
