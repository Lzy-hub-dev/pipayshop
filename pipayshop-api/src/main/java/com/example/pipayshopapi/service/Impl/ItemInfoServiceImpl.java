package com.example.pipayshopapi.service.Impl;

import com.example.pipayshopapi.entity.ItemInfo;
import com.example.pipayshopapi.entity.vo.ItemInfoVO;
import com.example.pipayshopapi.entity.vo.UserInfoVO;
import com.example.pipayshopapi.mapper.ItemInfoMapper;
import com.example.pipayshopapi.service.ItemInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
@Service
public class ItemInfoServiceImpl extends ServiceImpl<ItemInfoMapper, ItemInfo> implements ItemInfoService {

    @Resource
    private ItemInfoMapper itemInfoMapper;

    /**
     * 获取网店商品详情的网店信息接口
     * @param commodityId
     * @return
     */
    @Override
    public ItemInfoVO getItemInfo(String commodityId) {
        ItemInfoVO itemInfo = itemInfoMapper.getItemInfo(commodityId);
        return itemInfo;
    }
}
