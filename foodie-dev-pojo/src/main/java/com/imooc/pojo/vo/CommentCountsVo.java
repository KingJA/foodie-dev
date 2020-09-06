package com.imooc.pojo.vo;

import lombok.Data;

/**
 * Description:TODO
 * Create Time:2020/9/5 0005 上午 10:24
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Data
public class CommentCountsVo {
    private Integer totalCounts;
    private Integer goodCounts;
    private Integer normalCounts;
    private Integer badCounts;
}
