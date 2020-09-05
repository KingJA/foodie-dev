package com.imooc.service.impl;

import com.imooc.mapper.CategoryMapper;
import com.imooc.mapper.CategoryMapperCustom;
import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVo;
import com.imooc.pojo.vo.RecommendCatsVo;
import com.imooc.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.entity.Example;

@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    public CategoryMapper categoryMapper;
    @Autowired
    public CategoryMapperCustom categoryMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> getAllRootLevelCategory() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type",1);
        return categoryMapper.selectByExample(example);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<CategoryVo> getSubCategoryList(Integer rootId) {
        return categoryMapperCustom.getSubCategoryList(rootId);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<RecommendCatsVo> getRecommendGoodsList(Integer rootCatId) {
        Map<String,Object>paramMap=new HashMap<>();
        paramMap.put("rootCatId",rootCatId);
        return categoryMapperCustom.getRecommendGoodsList(paramMap);
    }
}
