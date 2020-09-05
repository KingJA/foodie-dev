package com.imooc.mapper;

import com.imooc.pojo.vo.CategoryVo;
import com.imooc.pojo.vo.RecommendCatsVo;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CategoryMapperCustom {

    List<CategoryVo> getSubCategoryList(Integer rootId);

    public List<RecommendCatsVo> getRecommendGoodsList(@Param("paramMap") Map map);
}