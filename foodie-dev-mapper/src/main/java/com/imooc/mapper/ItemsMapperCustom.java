package com.imooc.mapper;

import com.imooc.pojo.vo.ItemCommentVo;
import com.imooc.pojo.vo.SearchItemVo;
import com.imooc.pojo.vo.ShopcartVO;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom {
    List<SearchItemVo> getSearchItems(@Param("paramsMap") Map map);
    List<SearchItemVo> getSearchItemsByThirdCat(@Param("paramsMap") Map map);
    List<ShopcartVO> queryItemsBySpecId(@Param("paramsList") List specIdList);

    int decreaseStock(@Param("specId")String specId,@Param("pendingCounts")int pendingCounts);
}