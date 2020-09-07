package com.imooc.service;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.CommentCountsVo;
import com.imooc.pojo.vo.ItemCommentVo;
import com.imooc.pojo.vo.SearchItemVo;
import com.imooc.utils.PagedGridResult;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemService {

    /**
     * 获取商品详情
     * @param itemId
     * @return
     */
    Items getItemDetail(String itemId);

    /**
     * 获取商品图片列表
     * @param itemId
     * @return
     */
    List<ItemsImg> getItemImgList(String itemId);

    /**
     * 获取商品规格列表
     * @param itemId
     * @return
     */
    List<ItemsSpec> getItemSpecList(String itemId);

    /**
     * 获取商品参数
     * @param itemId
     * @return
     */
    ItemsParam getItemParam(String itemId);

    /**
     * 获取商品评价各等级数量
     * @param itemId
     * @return
     */
    CommentCountsVo getCommentCounts(String itemId);

    /**
     * 根据等级获取评论
     * @param itemId
     * @param level
     * @return
     */
    PagedGridResult getPageComments(String itemId, Integer level, Integer page, Integer pageSize);



    PagedGridResult getSearchItems(String keywords, String sort, Integer page, Integer pageSize);
    PagedGridResult getSearchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize);

}
