package com.imooc.mapper;

import com.imooc.pojo.vo.ItemCommentVo;
import com.imooc.pojo.vo.SearchItemVo;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsCommentsMapperCustom {
    List<ItemCommentVo> getItemComments(@Param("paramsMap") Map map);
    List<SearchItemVo> getSearchItems(@Param("paramsMap") Map map);
    List<SearchItemVo> getSearchItemsByThirdCat(@Param("paramsMap") Map map);
}