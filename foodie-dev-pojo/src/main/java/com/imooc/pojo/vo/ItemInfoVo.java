package com.imooc.pojo.vo;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;

import java.util.List;

import lombok.Data;

/**
 * Description:TODO
 * Create Time:2020/9/5 0005 上午 10:24
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Data
public class ItemInfoVo {
    private Items item;
    private List<ItemsImg> itemImgList;
    private List<ItemsSpec> itemSpecList;
    private ItemsParam itemParams;

}
