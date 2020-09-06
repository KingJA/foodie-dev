package com.imooc.service.impl;

import com.imooc.enums.CommentLevel;
import com.imooc.mapper.ItemsCommentsMapper;
import com.imooc.mapper.ItemsCommentsMapperCustom;
import com.imooc.mapper.ItemsImgMapper;
import com.imooc.mapper.ItemsMapper;
import com.imooc.mapper.ItemsParamMapper;
import com.imooc.mapper.ItemsSpecMapper;
import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsComments;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.CommentCountsVo;
import com.imooc.pojo.vo.ItemCommentVo;
import com.imooc.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.entity.Example;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    public ItemsMapper itemsMapper;
    @Autowired
    public ItemsImgMapper itemsImgMapper;
    @Autowired
    public ItemsSpecMapper itemsSpecMapper;
    @Autowired
    public ItemsParamMapper itemsParamMapper;

    @Autowired
    public ItemsCommentsMapper itemsCommentsMapper;

    @Autowired
    public ItemsCommentsMapperCustom itemsCommentsMapperCustom;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Items getItemDetail(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> getItemImgList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsImgMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> getItemSpecList(String itemId) {
        Example example = new Example(ItemsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsSpecMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParam getItemParam(String itemId) {
        Example example = new Example(ItemsParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsParamMapper.selectOneByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemCommentVo> getPageComments(String itemId, Integer level) {
        Map<String, Object> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("level", level);
        return itemsCommentsMapperCustom.getItemComments(map);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CommentCountsVo getCommentCounts(String itemId) {
        Integer goodCounts = getCommentCounts(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = getCommentCounts(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = getCommentCounts(itemId, CommentLevel.BAD.type);
        Integer totalCounts = goodCounts + normalCounts + badCounts;
        CommentCountsVo commentCountsVo = new CommentCountsVo();
        commentCountsVo.setGoodCounts(goodCounts);
        commentCountsVo.setNormalCounts(normalCounts);
        commentCountsVo.setBadCounts(badCounts);
        commentCountsVo.setTotalCounts(totalCounts);
        return commentCountsVo;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    Integer getCommentCounts(String itemId, Integer commentLevel) {
        ItemsComments comment = new ItemsComments();
        comment.setItemId(itemId);
        comment.setCommentLevel(commentLevel);
        return itemsCommentsMapper.selectCount(comment);

    }
}
