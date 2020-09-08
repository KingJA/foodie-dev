package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.enums.CommentLevel;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.ItemsCommentsMapper;
import com.imooc.mapper.ItemsCommentsMapperCustom;
import com.imooc.mapper.ItemsImgMapper;
import com.imooc.mapper.ItemsMapper;
import com.imooc.mapper.ItemsMapperCustom;
import com.imooc.mapper.ItemsParamMapper;
import com.imooc.mapper.ItemsSpecMapper;
import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsComments;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.CommentCountsVo;
import com.imooc.pojo.vo.ItemCommentVo;
import com.imooc.pojo.vo.SearchItemVo;
import com.imooc.pojo.vo.ShopcartVO;
import com.imooc.service.ItemService;
import com.imooc.utils.PagedGridResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
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

  @Autowired
    public ItemsMapperCustom itemsMapperCustom;


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

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult getPageComments(String itemId, Integer level, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("level", level);
        PageHelper.startPage(page, pageSize);
        List<ItemCommentVo> list = itemsCommentsMapperCustom.getItemComments(map);
        return getPagedGridResult(list, page);
    }

    @Override
    public PagedGridResult getSearchItems(String keywords, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("keywords", keywords);
        map.put("sort", sort);
        PageHelper.startPage(page, pageSize);
        List<SearchItemVo> list = itemsMapperCustom.getSearchItems(map);
        return getPagedGridResult(list, page);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult getSearchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("catId", catId);
        map.put("sort", sort);
        PageHelper.startPage(page, pageSize);
        List<SearchItemVo> list = itemsMapperCustom.getSearchItemsByThirdCat(map);
        return getPagedGridResult(list, page);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ShopcartVO> queryItemsBySpecId(String specIds) {
        String[] specIdsAttr = specIds.split(",");
        List<String> list=new ArrayList<>();
        Collections.addAll(list,specIdsAttr);
        return itemsMapperCustom.queryItemsBySpecId(list);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsSpec queryItemSpecBySpecId(String specId) {
        return itemsSpecMapper.selectByPrimaryKey(specId);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String getItemMainImgById(String itemId) {
        ItemsImg itemsImg = new ItemsImg();
        itemsImg.setItemId(itemId);
        itemsImg.setIsMain(YesOrNo.YES.type);
        ItemsImg result = itemsImgMapper.selectOne(itemsImg);
        return result.getUrl()==null?"":result.getUrl();
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void decreaseStock(String spaceId, int buyCounts) {
        // 加锁
        // 扣库存
        // 解锁
        int result = itemsMapperCustom.decreaseStock(spaceId, buyCounts);
        if (result != 1) {
            throw new RuntimeException("订单创建失败，原因：库存不足");
        }
    }

    private PagedGridResult getPagedGridResult(List<?> list, Integer page) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;

    }
}
