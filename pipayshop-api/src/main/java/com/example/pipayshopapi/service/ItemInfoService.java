package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ItemInfo;
import com.example.pipayshopapi.entity.vo.ItemInfoVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
public interface ItemInfoService extends IService<ItemInfo> {
    /**
     * 根据网店id获取网店商品信息
     * @param itemId
     * @return
     * */
    PageDataVO getItemInfo(String itemId, Integer page, Integer limit, Boolean price);

    /**
     * 根据用户id获取网店信息
     * @param userId
     * @return
     */
    List<ItemInfoVO> getItemInfoByUid(String userId);
    /**
     * 根据用户id查询 对应的 网店关注列表
     * @param userId
     * @return
     */
    PageDataVO getFollowList(String userId, Integer page,Integer limit);
    /**
     * 根据用户id获取网店数量
     * @param userId
     * @return
     */
    Integer getItemCountByUserId(String userId);


    /**
     * 根据用户id-网店-升级vip
     * @param userId
     * @return
     */
    boolean upVip(String userId);

    /**
     * 根据用户id-判断对应网店是否vip
     * @param userId
     * @return
     */
    boolean isVip(String userId);

    /**
     * 根据网店id获取网店评价信息
     * @param itemId
     * @return
     */
    PageDataVO getItemEvaluate(String itemId,Integer page,Integer limit);

    Boolean setItemScore();


}
