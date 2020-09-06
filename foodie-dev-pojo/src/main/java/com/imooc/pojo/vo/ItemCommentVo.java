package com.imooc.pojo.vo;

import lombok.Data;

/**
 * Description:TODO
 * Create Time:2020/9/5 23:28
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Data
public class ItemCommentVo {
    private Integer comment_level;
    private String content;
    private String specName;
    private String createdTime;
    private String userFace;
    private String nickname;
}
