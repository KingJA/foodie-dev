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
public class CategoryVo {
    private Integer id;
    private Integer fatherId;
    private Integer type;
    private String name;
    private List<CategorySubVo> subCatList;
}
