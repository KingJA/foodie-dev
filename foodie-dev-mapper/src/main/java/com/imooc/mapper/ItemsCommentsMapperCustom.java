package com.imooc.mapper;

import com.imooc.pojo.vo.MyCommentVO;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsCommentsMapperCustom {

    void saveComments(Map<String,Object> map);
    List<MyCommentVO> getMyComments(@Param("paramsMap") Map<String,Object> map);

}