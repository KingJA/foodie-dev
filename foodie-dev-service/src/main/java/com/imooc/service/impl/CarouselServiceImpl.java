package com.imooc.service.impl;

import com.imooc.mapper.CarouselMapper;
import com.imooc.pojo.Carousel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import tk.mybatis.mapper.entity.Example;

@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    public CarouselMapper carouselMapper;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Carousel> queryAll(int isShow) {
        Example example = new Example(Carousel.class);
        example.orderBy("sort").desc();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isShow",isShow);
        List<Carousel> resultList = carouselMapper.selectByExample(example);
        return resultList;
    }
}
