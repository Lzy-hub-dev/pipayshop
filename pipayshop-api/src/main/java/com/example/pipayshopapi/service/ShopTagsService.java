package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ShopTags;
import com.example.pipayshopapi.entity.vo.PageDataVO;

/**
 * <p>
 * 实体店的标签 服务类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
public interface ShopTagsService extends IService<ShopTags> {

    /**
     * 获取标签列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageDataVO getShopTagsList(Integer pageNum, Integer pageSize);


    /**
     * 根据标签id查询标签信息
     * @param tagsId
     * @return
     */
    ShopTags getShopTagsById(String tagsId);


    /**
     * 根据标签id删除标签
     * @param tagsId
     * @return
     */
    Boolean deleteShopTagsById(String tagsId);


    /**
     * 根据标签id修改标签
     * @param shopTags
     * @return
     */
    Boolean updateShopTagsById(ShopTags shopTags);

    /**
     * 新增标签
     * @param shopTags
     * @return
     */
    Boolean addShopTags(ShopTags shopTags);
}
