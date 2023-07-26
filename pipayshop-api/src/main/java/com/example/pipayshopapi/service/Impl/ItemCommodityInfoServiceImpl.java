package com.example.pipayshopapi.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.pipayshopapi.entity.ItemCommodityInfo;
import com.example.pipayshopapi.entity.OrderInfo;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.commodityPageVO;
import com.example.pipayshopapi.entity.vo.commodityVO;
import com.example.pipayshopapi.mapper.ItemCommodityInfoMapper;
import com.example.pipayshopapi.service.ItemCommodityInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 网店的商品表 服务实现类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Service
public class ItemCommodityInfoServiceImpl extends ServiceImpl<ItemCommodityInfoMapper, ItemCommodityInfo> implements ItemCommodityInfoService {

    @Resource
    private ItemCommodityInfoMapper commodityInfoMapper;

    /**
     *某一二级分类下的商品列表分页展示
     */
    @Override
    public PageDataVO commodityOfCateList(commodityPageVO commodityPageVO) {

        Integer page =commodityPageVO.getPage();
        Integer limit=commodityPageVO.getLimit();
        int startIndex= (page-1)*limit;

        List<commodityVO> commodityList = commodityInfoMapper.commodityOfCateList(commodityPageVO.getCategoryId(),startIndex,limit);

        return new PageDataVO( commodityInfoMapper.listCount(commodityPageVO.getCategoryId()),commodityList);

    }
}
