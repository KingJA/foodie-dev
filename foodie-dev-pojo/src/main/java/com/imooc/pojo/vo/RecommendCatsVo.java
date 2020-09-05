package com.imooc.pojo.vo;

import java.util.List;

import lombok.Data;

/**
 * Description:TODO
 * Create Time:2020/9/5 0005 上午 10:24
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Data
public class RecommendCatsVo {
    private Integer rootCarId;
    private String rootCatName;
    private String name;
    private String slogan;
    private String bgColor;
    private String catImage;
    private List<CategorySubVo> simpleItemList;
}
