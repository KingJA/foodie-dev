package com.imooc.service;

import com.imooc.pojo.Category;
import com.imooc.pojo.Stu;
import com.imooc.pojo.vo.CategoryVo;
import com.imooc.pojo.vo.RecommendCatsVo;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    public List<Category> getAllRootLevelCategory();

    public List<CategoryVo> getSubCategoryList(Integer rootId);


    public List<RecommendCatsVo> getRecommendGoodsList(Integer rootCatId);

}
