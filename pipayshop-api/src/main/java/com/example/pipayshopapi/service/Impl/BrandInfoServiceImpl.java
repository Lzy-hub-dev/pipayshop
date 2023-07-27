package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.pipayshopapi.entity.BrandInfo;
import com.example.pipayshopapi.entity.vo.BrandInfoVO;
import com.example.pipayshopapi.entity.vo.ShopCategoryVO;
import com.example.pipayshopapi.mapper.BrandInfoMapper;
import com.example.pipayshopapi.mapper.ShopCategoryMapper;
import com.example.pipayshopapi.mapper.ShopCategoryTopMapper;
import com.example.pipayshopapi.service.BrandInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nws
 * @since 2023-07-26
 */
@Service
public class BrandInfoServiceImpl extends ServiceImpl<BrandInfoMapper, BrandInfo> implements BrandInfoService {

    @Resource
    private BrandInfoMapper brandInfoMapper;

    /**
     * 查找二级分类的id查找的品牌集合
     * @return
     */
    @Override
    public List<BrandInfoVO> selectAllBrandList(String cateId) {
        List<BrandInfoVO> brandInfoVOS = brandInfoMapper.selectAllContentList(cateId);
        return brandInfoVOS;
    }

    @Override
    public List<BrandInfoVO> itemSelectAllBrandList() {
        List<BrandInfoVO> brandInfos = brandInfoMapper.itemSelectAllContentList();
        return brandInfos;
    }
}
