package com.imooc.pojo.vo;

import lombok.Data;

/**
 * Description:TODO
 * Create Time:2020/9/5 0005 上午 10:24
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Data
public class CategorySubVo {
    private Integer subId;
    private Integer subType;
    private String subName;
    private Integer subFatherId;
}
