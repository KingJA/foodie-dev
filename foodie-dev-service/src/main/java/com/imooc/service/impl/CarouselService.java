package com.imooc.service.impl;

import com.imooc.pojo.Carousel;

import java.util.List;

public interface CarouselService {
    List<Carousel> queryAll(int isShow);

}
