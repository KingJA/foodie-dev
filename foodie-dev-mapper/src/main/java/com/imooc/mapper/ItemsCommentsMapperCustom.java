package com.imooc.mapper;

import com.imooc.pojo.vo.ItemCommentVo;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsCommentsMapperCustom {
    List<ItemCommentVo> getItemComments(@Param("paramsMap") Map map);
}